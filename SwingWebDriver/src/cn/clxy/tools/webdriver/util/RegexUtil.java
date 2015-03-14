package cn.clxy.tools.webdriver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexUtil {

    public static String find(String regex, String text) {

        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void main(String[] args) {

        String c = "<input type=\"hidden\" name=\"PPFT\" id=\"i0327\" value=\"B9uh81Qbu2k*SQQT4BoEQoHwNqp3FBxqrFn9LUHAs6IgISHdMPrv4psFOsnToGc41XB0y9Fr7mzpynjMtMY9PBtv1UPngdNcqfdIv8nl14MgMhu9HK1Bxib1QD9KqVOr9LG92MZvfODN!BUXT9TjmO6ICdF4N47Q3IzUXFJvhV0KuLNGLqT79e6kUz7w\"/>'";
        String r = "<input.*name=['\"]PPFT['\"].*value\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        System.out.println(find(r, c));
    }

    private RegexUtil() {
    }
}
