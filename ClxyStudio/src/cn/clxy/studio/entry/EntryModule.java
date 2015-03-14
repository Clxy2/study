package cn.clxy.studio.entry;

import cn.clxy.studio.common.Module;

public class EntryModule implements Module {

	@Override
	public void load() {
		// Application.get().mount(new IndexedParamUrlCodingStrategy("home",
		// HomePage.class));
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
		return ("entryMenu");
	}
}
