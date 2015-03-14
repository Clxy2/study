package cn.clxy.tools.webdriver.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import cn.clxy.tools.webdriver.model.Account;
import cn.clxy.tools.webdriver.model.ClxyFile;
import cn.clxy.tools.webdriver.service.WebDriverService;
import cn.clxy.tools.webdriver.util.HttpUtil;
import cn.clxy.tools.webdriver.util.RegexUtil;

public class SkyService implements WebDriverService {

    /**
     * PPFTのValue。
     */
    private static final String regexPpft = "<input.*name=['\"]PPFT['\"].*value\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";

    /**
     * 登録パス。
     */
    private static final String regexSignin = "<a.*id=['\"]c[_]signin['\"].*href=['\"]([^\"]+)";

    /**
     * 登録後Rootパス。
     */
    private static final String regexLogon = "var\\s+srf_uPost\\s*=\\s*'([^']+)';";

    private CookieStore cookieStore;

    @Override
    public void addFile() {
        // TODO Auto-generated method stub

    }

    @Override
    public void compare() {
        // TODO Auto-generated method stub

    }

    @Override
    public void createFolder() {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteFile() {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteFolder() {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadFile() {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadFolder() {
        // TODO Auto-generated method stub

    }

    @Override
    public void downloadFolder(boolean nest) {
        // TODO Auto-generated method stub

    }

    @Override
    public void renameFile() {
        // TODO Auto-generated method stub

    }

    @Override
    public void renameFolder() {
        // TODO Auto-generated method stub

    }

    @Override
    public void uploadFile() {
        // TODO Auto-generated method stub

    }

    @Override
    public List<ClxyFile> getRoot(Account account) {

        // For test.
        account = new Account();
        account.setUserid("j_clxy@hotmail.com");
        account.setPassword("mmgg1=1");
        account.setUrl("http://cid-3671cb7e78572639.skydrive.live.com/home.aspx");

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                CookiePolicy.BROWSER_COMPATIBILITY);

        cookieStore = client.getCookieStore();
        Cookie cookie = HttpUtil.createCookie("CkTst", String.format("G%s", new Date().getTime()),
                "login.live.com");
        cookieStore.addCookie(cookie);

        cookie = HttpUtil.createCookie("wlidperf", String.format(
                "throughput=7&latency=421&FR=L&ST=%s", new Date().getTime()), ".live.com");
        cookieStore.addCookie(cookie);

        // SkyDriveへ遷移する。
        HttpGet httpget = new HttpGet(account.getUrl());

        // 登録しないなら、ここで公開部分が見える。
        String content = HttpUtil.execute(client, httpget, responseHandler);
        if (account.isAnonymous()) {
            cookieStore = client.getCookieStore();
            return parse(content);
        }

        // 登録パスを取得する。
        String logonUrl = RegexUtil.find(regexSignin, content);
        logonUrl = StringEscapeUtils.unescapeHtml(logonUrl);
        httpget = new HttpGet(logonUrl);
        content = HttpUtil.execute(client, httpget, responseHandler);

        // Liveの登録画面で登録する。
        String ppft = RegexUtil.find(regexPpft, content);
        logonUrl = RegexUtil.find(regexLogon, content);

        List<NameValuePair> nvps = prepairLogonParams(account);
        nvps.add(new BasicNameValuePair("PPFT", ppft));

        HttpPost httpost = new HttpPost(logonUrl);
        httpost.setEntity(HttpUtil.createFormEntity(nvps, HTTP.UTF_8));
        content = HttpUtil.execute(client, httpost, responseHandler);

        // SkyDriveのホームへ遷移する。
        String skyWelcome = HttpUtil.getRefresh(content);
        httpget = new HttpGet(skyWelcome);
        content = HttpUtil.execute(client, httpget, responseHandler);

        // 最後の認証画面でSubmitする。
        SkyFormParser fp = new SkyFormParser("fmHF");
        fp.parse(content);
        String skyRoot = fp.getAction();
        nvps = fp.getParams();

        httpost = new HttpPost(skyRoot);
        httpost.setEntity(HttpUtil.createFormEntity(nvps, HTTP.UTF_8));
        client.setRedirectHandler(new SkyRedirectHandler());
        content = HttpUtil.execute(client, httpost, responseHandler);
        cookieStore = client.getCookieStore();
        System.out.println(content);
        return (parse(content));
    }

    private static List<ClxyFile> parse(String content) {

        SkyRootParser parser = new SkyRootParser();
        parser.parse(content);
        return parser.getRoot();
    }

    private static final String pwdPad = "IfYouAreReadingThisYouHaveTooMuchFreeTime";

    private static List<NameValuePair> prepairLogonParams(Account account) {

        String uid = account.getUserid();
        String pwd = account.getPassword();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("idsbho", "1"));
        nvps.add(new BasicNameValuePair("login", uid));
        nvps.add(new BasicNameValuePair("passwd", pwd));

        String pp = pwdPad.substring(0, pwdPad.length() - pwd.length());
        nvps.add(new BasicNameValuePair("PwdPad", pp));

        nvps.add(new BasicNameValuePair("LoginOptions", "3"));
        nvps.add(new BasicNameValuePair("CS", ""));
        nvps.add(new BasicNameValuePair("FedState", ""));
        nvps.add(new BasicNameValuePair("PPSX", "Pas"));
        nvps.add(new BasicNameValuePair("type", "11"));
        nvps.add(new BasicNameValuePair("NewUser", "1"));
        nvps.add(new BasicNameValuePair("i1", "0"));
        nvps.add(new BasicNameValuePair("i2", "0"));

        return nvps;
    }

    public static void main(String[] args) {

        SkyService ss = new SkyService();
        System.out.println(ss.getRoot(null));
    }
}
