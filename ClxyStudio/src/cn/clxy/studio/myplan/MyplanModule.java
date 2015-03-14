package cn.clxy.studio.myplan;

import cn.clxy.studio.common.Module;

public class MyplanModule implements Module {

	@Override
	public void load() {
		// Application.get().mountPackage("myplan", MyplanHomePage.class);
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
		return ("myplanMenu");
	}
}
