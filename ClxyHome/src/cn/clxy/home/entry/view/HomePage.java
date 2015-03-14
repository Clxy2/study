package cn.clxy.home.entry.view;

import cn.clxy.home.common.view.BasePage;

public class HomePage extends BasePage {

	@Override
	protected void addBody() {

		add(new HomePanel("body"));
	}
}
