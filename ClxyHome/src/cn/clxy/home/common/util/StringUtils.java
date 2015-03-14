package cn.clxy.home.common.util;

public final class StringUtils {

	public static boolean isEmpty(String string) {
		return (string == null) || (string.length() == 0);
	}

	private StringUtils() {
	}
}
