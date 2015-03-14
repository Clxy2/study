package cn.clxy.studio;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import cn.clxy.studio.common.Module;
import cn.clxy.studio.common.ModuleLoader;
import cn.clxy.studio.entry.EntryModule;
import cn.clxy.studio.entry.view.HomePage;
import cn.clxy.studio.guice.JPAModule;
import cn.clxy.studio.shop.view.ClxySession;

public class Application extends WebApplication {

	// 全てのサービス。
	private static List<Module> modules;

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {

		super.init();

		modules = loadModule();

		// Guice
		getComponentInstantiationListeners().add(
				new GuiceComponentInjector(this, new JPAModule()));

		// For GAE........................start.
		getResourceSettings().setResourcePollFrequency(null);

		setPageManagerProvider(new DefaultPageManagerProvider(this) {

			protected IDataStore newDataStore() {

				return new HttpSessionDataStore(getPageManagerContext(),
						new PageNumberEvictionStrategy(20));
			}
		});
		// For GAE........................end.

	}

	@Override
	public RuntimeConfigurationType getConfigurationType() {
		return RuntimeConfigurationType.DEVELOPMENT;
		// return RuntimeConfigurationType.DEPLOYMENT;
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new ClxySession(request);
	}

	public static List<Module> getModules() {
		return modules;
	}

	private List<Module> loadModule() {

		List<Module> ss = ModuleLoader.load(getInitParameter("modules"));
		// 必須の入口。
		ss.add(0, new EntryModule());

		return Collections.unmodifiableList(ss);
	}
}
