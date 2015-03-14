package cn.clxy.studio.common.util;

public final class StringUtilExt {

	public static boolean isEmpty(String string) {
		return (string == null) || (string.length() == 0);
	}

	private StringUtilExt() {
	}
}
