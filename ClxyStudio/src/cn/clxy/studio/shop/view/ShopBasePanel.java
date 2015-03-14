package cn.clxy.studio.shop.view;

import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import cn.clxy.studio.shop.ShopModule;
import cn.clxy.studio.shop.model.Cart;
import cn.clxy.studio.shop.model.Customer;
import cn.clxy.studio.shop.model.Product;

public abstract class ShopBasePanel extends Panel {
	private static final long serialVersionUID = 1L;

	public ShopBasePanel(String id) {
		this(id, null);
	}

	public ShopBasePanel(String id, IModel<?> model) {
		super(id, model);
	}

	public Cart getCart() {
		return ((ClxySession) getSession()).getCart();
	}

	public Customer getCustomer() {
		return ((ClxySession) getSession()).getCustomer();
	}

	public List<Product> getAllProducts() {
		return ShopModule.products;
	}

}