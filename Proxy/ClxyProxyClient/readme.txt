xxx==== 证书
0：查看
keytool -list  -v -keystore keystore -storepass clxystudio

1：生成根证书和Key，注意保留证书的国家，省市及其他所有相关信息。
OpenSSL req -new -x509 -days 3650 -sha1 -newkey rsa:1024 -keyout clxy.key -out clxy.crt -subj "/CN=studio.clxy.cn/OU=ClxyStudio,/O=CLXY/L=SZ/ST=JS/C=cn"
注：
	/CN=studio.clxy.cn/OU=ClxyStudio,/O=CLXY/L=SZ/ST=JS/C=cn
	pem= 1111

2：生成证书库
keytool -genkey -validity 3650 -keyalg RSA -keysize 1024  -alias clxy -keystore keystore -dname "CN=studio.clxy.cn, OU=ClxyStudio, O=CLXY, L=SZ, ST=JS, C=cn"
注：同上，CN=studio.clxy.cn, OU=ClxyStudio, O=CLXY, L=Suzhou, ST=JiangSu, C=cn
 
3：生成签名请求
keytool -certreq -sigalg MD5withRSA -alias clxy -keystore keystore -file clxy.csr

4：用【1】签名【3】
OpenSSL ca -in clxy.csr -out clxy.crt -policy policy_anything -notext -cert ca.crt -keyfile ca.key -config openssl.cfg
policy参数解决字符集不匹配问题。

5：导入根证书
keytool -import -v -trustcacerts -alias root -file ca.crt -keystore keystore

6：导入签名证书
keytool -import -v -file clxy.crt -alias clxy -keystore keystore

xxx==== Firefox中设置
	http://127.0.0.1:9999/proxy.pac
	这样即使代理服务器没有打开，仍然可以浏览。

xxx==== 使用时
1. 生成可运行jar
2. 需要copy src/main/resources下所有文件及根目录下proxy.pac到运行目录。

xxx==== Range问题尚未处理。
	尚未发生请求过大的情况。TODO

xxx==== 无法登陆。不能保持Session=不能设置Cookie？
- 本地一次代理可以。
- 走GAE二次代理的情况下失败。
	1. 比较两种Request，结果正常。
	2. GAE返回Response 302，比较结果：GAE无Vary，Content-Encoding。GAE多Expires。
		但新的SessionID正确设置在Set-Cookie内。
	3. 再次提交的GET请求。发现在第2步得到的新SessionID丢失，Request中依然是旧SessionID。

原因：复数Cookie的格式。
	GAE的Response里，以【部分1, 部分2】方式返回，应该将其拆成【部分1】【部分2】列表。
	但需要当心有cookie内容里面也包含逗号。
	例如：
	remember_me=no; domain=iteye.com; path=/; expires=Sat, 08-Mar-2014 15:07:54 GMT, _javaeye3_session_=BAh...; domain=.iteye.com; path=/; HttpOnly
	GMT后面的逗号。

xxx==== jetty有提供ProxyServlet和ConnectHandler。
- ProxyServlet处理HTTP。内部使用HttpClient转发。
- ConnectHandler处理HTTPS的Connect。内部使用Connection读取。

0 成功：同时使用ProxyServlet和ConnectHandler可以实现。
	0.1 但不清楚如何在ConnectHandler内修改Request，以进行二次转发。
		不修改Request，判断Host，需要代理则转去ProxyServlet——Server需要配置SSL。在ProxyServlet处二次转发。
	0.2 且希望能够合并成一种。
		没必要合并。

尝试过方案。
1. 失败：ConnectHandler内使用HttpClient同时处理Http和Https。
	原因：不明。
2. 失败：配置SSL，希望能够通过ProxyServlet同时处理Http和Https。
	原因：Servlet都是HttpServlet，不支持Https(Http 1.1新增加)的connect方法。
