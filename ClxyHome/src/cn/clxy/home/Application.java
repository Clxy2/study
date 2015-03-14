package cn.clxy.home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.ISessionStore;

import cn.clxy.home.common.Service;
import cn.clxy.home.common.util.StringUtils;
import cn.clxy.home.entry.EntryService;
import cn.clxy.home.entry.view.HomePage;

public class Application extends WebApplication {

	// 全てのサービス。
	private List<Service> services;

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	/**
	 * <pre>
	 * The default 
	 * {@code SecondLevelCacheSessionStore(this, new DiskPageStore())}
	 *  won't work on GAE, because Disk I/O is not allowed.
	 * Let's simply use a {@link HttpSessionStore}.
	 * </pre>
	 */
	@Override
	protected ISessionStore newSessionStore() {
		return new HttpSessionStore(this);
	}

	@Override
	protected void init() {

		services = Collections.unmodifiableList(loadService());
	}

	@Override
	public String getConfigurationType() {
		return DEPLOYMENT;
	}

	public List<Service> getServices() {
		return services;
	}

	private List<Service> loadService() {

		List<Service> ss = new ArrayList<Service>();

		// 必須の入口。
		ss.add(new EntryService());

		// そのた。
		String serviceParameter = getInitParameter("services");
		if (StringUtils.isEmpty(serviceParameter)) {
			return ss;
		}

		String[] array = serviceParameter.split("[ ]*,[ ]*");
		Set<String> serviceNames = new HashSet<String>(Arrays.asList(array));

		for (String serviceName : serviceNames) {
			Package p = Package.getPackage(serviceName);
			System.out.println(p);
		}
		return ss;
	}
}
