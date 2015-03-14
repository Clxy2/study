package cn.clxy.studio.common.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cn.clxy.studio.common.data.BaseData;
import cn.clxy.studio.common.data.User;
import cn.clxy.studio.common.util.StringUtil;
import cn.clxy.studio.common.web.WebUtil;

public class UtilDao {

	@PersistenceContext
	protected EntityManager em;

	public <T> void insert(T t) {
		setUpdateInfo(t);
		em.persist(t);
	}

	public <T> void merge(T t) {
		setUpdateInfo(t);
		em.merge(t);
	}

	public <T> void insert(Collection<T> list) {
		for (T t : list) {
			insert(t);
		}
	}

	public <T> void merge(List<T> list) {
		for (T t : list) {
			merge(t);
		}
	}

	public <T> Integer deleteAll(Class<T> clazz) {
		Query query = em.createQuery("delete from " + clazz.getSimpleName() + " t");
		return query.executeUpdate();
	}

	public <T> List<T> findAll(Class<T> clazz) {
		return findAll(clazz, null);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> clazz, String condition, Object... params) {
		return (List<T>) createQuery(clazz, condition, params).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> clazz, String condition, Object... params) {

		List<T> list = (List<T>) createQuery(clazz, condition, params)
				.setMaxResults(1).getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	private <T> Query createQuery(Class<T> clazz, String condition, Object... params) {

		String sql = "Select t from " + clazz.getSimpleName() + " t";
		if (StringUtil.isEmpty(condition)) {
			return em.createQuery(sql);
		}

		sql += " where " + condition;
		Query query = em.createQuery(sql);
		for (int i = 0, l = params.length; i < l; i++) {
			query.setParameter(i, params[i]);
		}
		return query;
	}

	private static void setUpdateInfo(Object data) {

		if (!(data instanceof BaseData)) {
			return;
		}

		BaseData bd = (BaseData) data;
		Date time = new Date();
		String id = getUserID();
		if (bd.getCreateAt() == null) {
			bd.setCreateAt(time);
		}
		if (bd.getCreateBy() == null) {
			bd.setCreateBy(id);
		}
		if (bd.getUpdateAt() == null) {
			bd.setUpdateAt(time);
		}
		if (bd.getUpdateBy() == null) {
			bd.setUpdateBy(id);
		}
	}

	private static String getUserID() {

		User user = WebUtil.getUser();
		if (user == null) {
			return null;
		}
		Integer id = user.getId();
		if (id == null) {
			return null;
		}
		return id.toString();
	}
}
