package cn.clxy.studio.entry.view;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class EntryMenuItem extends Panel {

	public EntryMenuItem(String id) {
		super(id);
		add(new BookmarkablePageLink<Void>("homeMenu", HomePage.class));
	}

	private static final long serialVersionUID = 1L;
}
