package cn.clxy.studio.hab.view;

import cn.clxy.studio.common.Module;
import cn.clxy.studio.common.view.BasePage;
import cn.clxy.studio.hab.HabModule;

public class HabHomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	@Override
	protected void addBody() {
		add(new HomePanel("body"));
	}

	@Override
	protected Class<? extends Module> getCurrentModule() {
		return HabModule.class;
	}
}
