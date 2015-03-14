package cn.clxy.studio.common.view;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import cn.clxy.studio.Application;
import cn.clxy.studio.common.Module;

public abstract class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	public BasePage() {
		super();
		initComponents();
	}

	private void initComponents() {

		add(new Header("header"));
		add(new Footer("footer"));

		addMenu();
		addBody();
	}

	protected void addBody() {
		add(new Label("body", "I'm base page.Override me"));
	}

	protected void addMenu() {

		Class<? extends Module> clazz = getCurrentModule();

		// Add all menu items.
		RepeatingView rv = new RepeatingView("menuItem");
		add(rv);

		for (Module module : Application.getModules()) {

			Component item = module.getMenuItem();
			rv.add(item);

			// Set current menu item selected.
			if (clazz == null || !clazz.isInstance(module)) {
				continue;
			}

			item.add(new AttributeAppender("class", new Model<String>(
					"selected"), " "));
		}
	}

	protected Class<? extends Module> getCurrentModule() {
		return null;
	}
}
