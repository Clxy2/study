package cn.clxy.studio.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

public final class AnnotationUtil {

	/**
	 * パッケージと親を沿って、Annotationを探す。
	 * @param p
	 * @param clazz
	 * @return
	 */
	public static <T extends Annotation> T find(Package p, Class<T> clazz) {

		if (p == null) {
			return null;
		}

		T result = p.getAnnotation(clazz);
		if (result != null) {
			return result;
		}

		return find(PackageUtil.getParent(p), clazz);
	}

	/**
	 * メソッドのAnnotationを探す。ない場合、定義クラスも探す。<br>
	 * クラス自分だけ親など捜さない。
	 * @see AnnotationUtils#findAnnotation(Method, Class)
	 * @param m
	 * @param clazz
	 * @return
	 */
	public static <T extends Annotation> T find(Method m, Class<T> clazz) {

		// T result = m.getAnnotation(clazz);
		T result = AnnotationUtils.findAnnotation(m, clazz);
		if (result != null) {
			return result;
		}
		return AnnotationUtils.findAnnotation(m.getDeclaringClass(), clazz);
	}

	public static <T extends Annotation> T find(HandlerMethod hm, Class<T> clazz) {
		return find(hm.getMethod(), clazz);
	}

	private AnnotationUtil() {
	}
}
