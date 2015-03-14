package cn.clxy.studio.common.view;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class Footer extends Panel {

	private static final long serialVersionUID = 1L;

	public Footer(String id) {

		super(id);
		add(new Label("copyright", "I'm copyright."));
	}
}
