package cn.clxy.home.common.view;

import org.apache.wicket.markup.html.panel.Panel;

public final class Header extends Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param id
	 */
	public Header(String id) {

		super(id);
		setRenderBodyOnly(true);
	}
}
