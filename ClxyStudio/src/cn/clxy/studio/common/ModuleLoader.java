package cn.clxy.studio.common;

import java.util.ArrayList;
import java.util.List;

import cn.clxy.studio.common.util.StringUtilExt;

/**
 * Load modules from xml configuration. Should use annotation?
 * 
 * @author clxy
 */
public final class ModuleLoader {

	public static List<Module> load(String param) {

		List<Module> modules = new ArrayList<Module>();

		if (StringUtilExt.isEmpty(param)) {
			return modules;
		}

		String[] names = param.split("[ ]*,[ ]*");

		try {

			for (String name : names) {
				@SuppressWarnings("unchecked")
				Class<Module> clazz = (Class<Module>) Class.forName(name);

				Module module = clazz.newInstance();
				module.load();
				modules.add(module);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return modules;
	}
}
