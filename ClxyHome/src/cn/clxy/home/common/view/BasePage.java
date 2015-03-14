package cn.clxy.home.common.view;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.BoxBorder;

public abstract class BasePage extends WebPage {

	public BasePage() {
		super();
		initComponents();
	}

	private void initComponents() {

		add(new Header("header"));
		add(new Footer("footer"));

		//TODO dynamic.
		add(new BoxBorder("menu"));
		
		addBody();
	}

	protected void addBody() {
		add(new Label("body", "I'm base page.Override me"));
	}
}
