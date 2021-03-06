package cn.clxy.studio.shop.view;

import cn.clxy.studio.common.Module;
import cn.clxy.studio.common.view.BasePage;
import cn.clxy.studio.shop.ShopModule;

public class ShopHomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	protected void addBody() {
		add(new HomePanel("body"));
	}

	@Override
	protected Class<? extends Module> getCurrentModule() {
		return ShopModule.class;
	}
}
