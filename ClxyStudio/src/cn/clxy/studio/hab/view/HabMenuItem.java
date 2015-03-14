package cn.clxy.studio.hab.view;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class HabMenuItem extends Panel {

	public HabMenuItem(String id) {
		super(id);
		add(new BookmarkablePageLink<Void>("homeMenu", HabHomePage.class));
	}

	private static final long serialVersionUID = 1L;
}
