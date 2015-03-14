package cn.clxy.studio.mooncar.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.clxy.studio.common.dao.DaoUtil;
import cn.clxy.studio.mooncar.data.NameData;
import cn.clxy.studio.mooncar.data.OriginNameData;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * @author clxy
 * @deprecated GAE上存储条件无法满足需求，所以放弃存储。
 */
public class MooncarDao {

	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	public Collection<OriginNameData> findBySite(String site) {

		String sql = "select from " + OriginNameData.class.getSimpleName() + " t ";
		if (!StringUtils.isEmpty(site)) {
			sql += "where site='" + site + "'";
		}

		return (Collection<OriginNameData>) em.createQuery(sql).getResultList();
	}

	// TODO JPA处理可能为空的条件的方式好难看！也许应该试试Criteria。
	@SuppressWarnings("unchecked")
	public Collection<NameData> findByName(String name) {

		String sql = "select from " + NameData.class.getSimpleName() + " t ";
		if (!StringUtils.isEmpty(name)) {
			sql += "where t.name like :p";
		}

		Query query = em.createQuery(sql);
		if (!StringUtils.isEmpty(name)) {
			query.setParameter("p", name);
		}

		return query.getResultList();
	}

	public void save(List<?> datas) {

		log.warn("Transform started.");
		List<Entity> es = DaoUtil.toEntity(datas);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		log.warn("Save started.");
		datastore.put(es);
		log.warn("Save end.");
	}

	private static final Log log = LogFactory.getLog(MooncarDao.class);
}
