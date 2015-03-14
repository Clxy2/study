package cn.clxy.home.entry.view;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class HomePanel extends Panel {

	public HomePanel(String id) {
		this(id, null);
	}

	public HomePanel(String id, IModel<?> model) {

		super(id, model);
		add(new Label("login", "login"));
	}

	private static final long serialVersionUID = 1L;
}
