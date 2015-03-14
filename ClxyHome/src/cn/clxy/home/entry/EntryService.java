package cn.clxy.home.entry;

import org.apache.wicket.Page;
import org.apache.wicket.request.target.coding.IndexedParamUrlCodingStrategy;

import cn.clxy.home.Application;
import cn.clxy.home.common.Service;
import cn.clxy.home.entry.view.HomePage;

public class EntryService implements Service {

	@Override
	public void load() {
		Application.get().mount(new IndexedParamUrlCodingStrategy("home", HomePage.class));
	}

	@Override
	public Class<? extends Page> getHomePage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Page> getBriefPage() {
		// TODO Auto-generated method stub
		return null;
	}

}
