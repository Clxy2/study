package cn.clxy.tools.webdriver.util;


public final class BeanUtil {

    public static boolean in(Object orig, Object[] values) {

        for (Object value : values) {
            if (equals(orig, value)) {
                return true;
            }
        }

        return false;
    }

    public static boolean equals(Object orig, Object dest) {

        if (orig == dest) {
            return true;
        }

        if (orig == null || dest == null) {
            return false;
        }

        return orig.equals(dest);
    }

    private BeanUtil() {
    }
}
