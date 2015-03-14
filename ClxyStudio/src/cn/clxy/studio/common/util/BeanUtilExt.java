package cn.clxy.studio.common.util;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;

public final class BeanUtilExt {

	/**
	 * Compare object by all properties value.<br>
	 * Check same class!
	 * 
	 * @param orig
	 * @param dest
	 * @return
	 */
	public static boolean equalsByAllProperty(Object orig, Object dest) {

		if (orig == dest) {
			return true;
		}

		if (orig == null || dest == null) {
			return false;
		}

		Class<?> clazz = orig.getClass();
		if (!clazz.isInstance(dest)) {
			return false;
		}

		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
		String props[] = new String[pds.length];
		for (int i = 0, l = pds.length; i < l; i++) {
			props[i] = pds[i].getName();
		}

		return equals(orig, dest, props);
	}

	/**
	 * Compare object by {@link Object#equals(Object)}, or specified properties
	 * value.<br>
	 * Do not check same class!
	 * 
	 * @param orig
	 * @param dest
	 * @param props
	 * @return
	 */
	public static boolean equals(Object orig, Object dest, String... props) {

		if (orig == dest) {
			return true;
		}

		if (orig == null || dest == null) {
			return false;
		}

		if (props == null || props.length == 0) {
			return orig.equals(dest);
		}

		try {
			for (String prop : props) {
				Object op = PropertyUtils.getProperty(orig, prop);
				Object dp = PropertyUtils.getProperty(dest, prop);
				if (!equals(op, dp)) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Get specified property.
	 * 
	 * @param obj
	 * @param property
	 * @return
	 */
	public static Object getProperty(Object obj, String property) {
		try {
			return PropertyUtils.getProperty(obj, property);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private BeanUtilExt(){}
}
