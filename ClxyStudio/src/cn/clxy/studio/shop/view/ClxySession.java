package cn.clxy.studio.shop.view;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import cn.clxy.studio.shop.model.Cart;
import cn.clxy.studio.shop.model.Customer;

public class ClxySession extends WebSession {
	private static final long serialVersionUID = 1L;
	private Cart cart = new Cart();
	private Customer customer = new Customer();

	public ClxySession(Request request) {
		super(request);
	}

	public Customer getCustomer() {
		return customer;
	}

	public Cart getCart() {
		return cart;
	}
}
