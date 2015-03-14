package cn.clxy.studio.shop.view;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

public class HomePanel extends ShopBasePanel {
	private static final long serialVersionUID = 1L;

	public HomePanel(String id) {
		this(id, null);
	}

	public HomePanel(String id, IModel<?> model) {
		super(id, model);

		add(new ProductListPanel("productlist"));
		add(new ShoppingCartPanel("shoppingcart"));
		add(new Link<OrderPanel>("checkout") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new CheckoutPage());
			}

			@Override
			public boolean isVisible() {
				return !getCart().getProducts().isEmpty();
			}
		});
	}
}
