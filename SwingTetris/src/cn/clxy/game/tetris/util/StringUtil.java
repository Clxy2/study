package cn.clxy.game.tetris.util;

import java.util.Formatter;

/**
 * Utils for operating string.
 * @author clxy
 */
public class StringUtil {

    /**
     * Return true when Null or length = 0.
     * @param string
     * @return
     */
    public static boolean isBlank(String string) {

        boolean result = string == null || string.length() == 0;
        return result;
    }

    public static String formatInterval(long i) {

        Formatter f = new Formatter();
        f.format("%d:%02d:%02d", (i % day) / hour, (i % hour) / minute, (i % minute) / second);

        return f.toString();
    }

    private static final long day = 24 * 60 * 60 * 1000;
    private static final long hour = 60 * 60 * 1000;
    private static final long minute = 60 * 1000;
    private static final long second = 1000;

    private StringUtil() {
    }
}
