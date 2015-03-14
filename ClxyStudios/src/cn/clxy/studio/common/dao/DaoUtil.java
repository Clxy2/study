package cn.clxy.studio.common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Table;

import org.springframework.core.annotation.AnnotationUtils;

import cn.clxy.studio.common.util.BeanUtil;

import com.google.appengine.api.datastore.Entity;

public final class DaoUtil {

	private static <T> Entity toEntity(T t, String name) {

		Entity entity = new Entity(name);
		Map<String, Object> map = BeanUtil.toMap(t);
		for (Entry<String, Object> e : map.entrySet()) {
			if ("class".equals(e.getKey()) || e.getValue() == null) {
				continue;
			}
			entity.setProperty(e.getKey(), e.getValue());
		}
		return entity;
	}

	public static <T> List<Entity> toEntity(List<T> list) {

		List<Entity> result = new ArrayList<>(list.size());
		String name = getTableName(list.get(0).getClass());
		for (T t : list) {
			result.add(toEntity(t, name));
		}
		return result;
	}

	public static <T> List<Entity> toEntity(List<T> list, String name) {

		List<Entity> result = new ArrayList<>(list.size());
		for (T t : list) {
			result.add(toEntity(t, name));
		}
		return result;
	}

	public static <T> String getTableName(List<T> list) {
		return getTableName(list.get(0).getClass());
	}

	public static <T> String getTableName(Class<T> clazz) {
		// Table table = clazz.getAnnotation(Table.class);
		Table table = AnnotationUtils.findAnnotation(clazz, Table.class);
		return (table == null) ? clazz.getSimpleName() : table.name();
	}

	private DaoUtil() {
	}
}
