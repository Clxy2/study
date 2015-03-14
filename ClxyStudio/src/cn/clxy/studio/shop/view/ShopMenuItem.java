package cn.clxy.studio.shop.view;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class ShopMenuItem extends Panel {

	public ShopMenuItem(String id) {
		super(id);
		add(new BookmarkablePageLink<Void>("homeMenu", ShopHomePage.class));
	}

	private static final long serialVersionUID = 1L;
}
