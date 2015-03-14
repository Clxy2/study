package cn.clxy.tools.webdriver.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

public final class HttpUtil {

    public static HttpEntity createFormEntity(List<NameValuePair> nvps, String code) {

        try {
            return new UrlEncodedFormEntity(nvps, code);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <E> E execute(HttpClient client, HttpUriRequest request,
            ResponseHandler<E> handler) {

        try {
            return client.execute(request, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printCookie(CookieStore cs) {

        List<Cookie> cookies = cs.getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
            return;
        }

        for (Cookie c : cookies) {
            System.out.println("- " + c);
        }
    }

    public static Cookie createCookie(String name, String value, String domain) {

        BasicClientCookie cookie = new BasicClientCookie(name, value);

        cookie.setVersion(1);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setSecure(true);
        // Set attributes EXACTLY as sent by the server
        cookie.setAttribute(ClientCookie.VERSION_ATTR, "1");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, domain);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        cookie.setExpiryDate(c.getTime());

        return cookie;
    }

    public static String getRefresh(String content) {
        return RegexUtil.find(refreshRegex, content);
    }

    private static final String refreshRegex = "<meta http-equiv=\"refresh\" content=\".*url=([^\"]+)\">";

    private HttpUtil() {
    }
}
