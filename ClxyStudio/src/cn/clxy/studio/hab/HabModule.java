package cn.clxy.studio.hab;

import cn.clxy.studio.common.Module;

public class HabModule implements Module {

	@Override
	public void load() {
		// Application.get().mountPackage("hab", HabHomePage.class);
	}

	@Override
	public String getHomePage() {
		return null;
	}

	@Override
	public String getBriefPage() {
		return null;
	}

	@Override
	public String getMenuItem() {
		return ("habMenu");
	}
}
