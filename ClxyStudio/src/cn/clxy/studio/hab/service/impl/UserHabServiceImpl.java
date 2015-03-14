package cn.clxy.studio.hab.service.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import cn.clxy.studio.common.util.JPAUtil;
import cn.clxy.studio.hab.data.TabOrder;
import cn.clxy.studio.hab.service.UserHabService;

public class UserHabServiceImpl implements UserHabService {

	private static final Logger log = Logger.getLogger(UserHabServiceImpl.class
			.getName());

	private EntityManager em;

	@Override
	// TODO Can not save multi objects in on transaction on GAE.Wired.
	// @Transactional
	public List<TabOrder> getHabTab() {

		log.info("service in.");

		// TODO For test
		// forTest();

		return JPAUtil.findAll(em, TabOrder.class);
	}

	private void forTest() {

		EntityTransaction et = em.getTransaction();
		et.begin();

		TabOrder to;

		to = new TabOrder();
		to.setTabName("Total");
		to.setOrder(0);
		em.persist(to);

		/*
		 * to = new TabOrder(); to.setTabName("Input"); to.setOrder(1);
		 * em.persist(to);
		 * 
		 * to = new TabOrder(); to.setTabName("Setting"); to.setOrder(1);
		 * em.persist(to);
		 */
		et.commit();
	}
}
