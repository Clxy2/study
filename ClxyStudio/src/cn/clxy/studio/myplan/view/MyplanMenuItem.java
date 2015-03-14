package cn.clxy.studio.myplan.view;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class MyplanMenuItem extends Panel {

	public MyplanMenuItem(String id) {
		super(id);
		add(new BookmarkablePageLink<Void>("homeMenu", MyplanHomePage.class));
	}

	private static final long serialVersionUID = 1L;
}
