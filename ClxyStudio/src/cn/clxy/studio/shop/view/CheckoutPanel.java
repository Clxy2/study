package cn.clxy.studio.shop.view;

import org.apache.wicket.model.IModel;

public class CheckoutPanel extends ShopBasePanel {
	private static final long serialVersionUID = 1L;

	public CheckoutPanel(String id) {
		this(id, null);
	}

	public CheckoutPanel(String id, IModel<?> model) {
		super(id, model);

		add(new OrderPanel("order"));
		// add(new ProductPanel("product"));
		add(new ShoppingCartPanel("shoppingcart"));
	}
}