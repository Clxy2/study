package cn.clxy.studio.common.util;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

public final class JPAUtil {

	@SuppressWarnings("unchecked")
	public static <E extends Serializable> List<E> findAll(
			final EntityManager em, final Class<E> clazz) {

		return em.createQuery("select from " + clazz.getName()).getResultList();
	}

	private JPAUtil() {
	}
}
