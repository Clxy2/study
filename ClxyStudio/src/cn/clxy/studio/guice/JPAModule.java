package cn.clxy.studio.guice;

import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

public class JPAModule extends ServletModule {

	@Override
	protected void configureServlets() {

		install(new JpaPersistModule("jpaUnit"));
		filter("/*").through(PersistFilter.class);
		bind(JPAInitializer.class).asEagerSingleton();
	}

}