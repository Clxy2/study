package cn.clxy.studio.entry.view;

import cn.clxy.studio.common.Module;
import cn.clxy.studio.common.view.BasePage;
import cn.clxy.studio.entry.EntryModule;

public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	@Override
	protected void addBody() {
		add(new HomePanel("body"));
	}

	@Override
	protected Class<? extends Module> getCurrentModule() {
		return EntryModule.class;
	}
}
