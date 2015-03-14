function FindProxyForURL(url, host) {

	if (Config.debug) {
		log("HOST=" + host + "; URL=" + url);
		log("needProxy(url, host) = " + needProxy(url, host));
	}

	var proxyUrl = "PROXY " + Config.host + ":" + Config.httpPort + "; DIRECT";
	log(proxyUrl);
	if (needProxy(url, host))
		return proxyUrl;

	return "DIRECT";
}

var Config = {
	debug : true,
	host : '127.0.0.1',
	httpPort : '9999',
	domains : [ 'cloudfront.net', 'youtube.com', 'ytimg.com',
			'youtube-nocookie.com', 'facebook.com', 'fbcdn.net',
			'facebook.net', 'twitter.com', 'blogspot.com', 'blogger.com',
			'googleusercontent.com', 'gstatic.com', 'sourceforge.net',
			'wenxuecity.com', 'wordpress.com', 'bbc.co.uk', 'forum.avcool.com',
			'bbcchinese.com', 'iteye.com' ],
	httpsPort : '8443',
	user : 'd7b3184b052b695323184e17b0acf4de',
	servers : [
	// 'https://clxyproxy1.appspot.com/proxy',
	// 'https://clxyproxy2.appspot.com/proxy',
	'https://proxy-clxy2.rhcloud.com/proxy'
	// 'http://localhost:8081/proxy/proxy'
	]
};

function needProxy(url, host) {

	url = url.toLowerCase();
	host = host.toLowerCase();
	hostip = dnsResolve(host);
	isHttp = (url.substring(0, 5) == "http:");
	isHttps = (url.substring(0, 6) == "https:");

	// Always bypass local
	if (isPlainHostName(host) || (Config.host == host)
			|| isInNet(hostip, "192.168.0.0", "255.255.0.0")) {
		return false;
	}

	// Only http and https
	if (!(isHttp || isHttps)) {
		return false;
	}

	for (var i = 0, l = Config.domains.length; i < l; i++) {
		if (dnsDomainIs(host, Config.domains[i])) {
			return true;
		}
	}

	return false;
}

function log(msg) {

	if (!Config.debug)
		return;

	// document.write(msg);
	// alert(msg);
	// console.log(msg);
}