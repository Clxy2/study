package cn.clxy.studio.myplan.view;

import cn.clxy.studio.common.Module;
import cn.clxy.studio.common.view.BasePage;
import cn.clxy.studio.myplan.MyplanModule;

public class MyplanHomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	@Override
	protected void addBody() {
		add(new HomePanel("body"));
	}

	@Override
	protected Class<? extends Module> getCurrentModule() {
		return MyplanModule.class;
	}
}
