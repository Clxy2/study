package cn.clxy.studio.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private List<Product> products = new ArrayList<Product>();

	private Customer customer = new Customer();

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> other) {
		products = other;
	}

	public double getTotal() {
		double total = 0;
		for (Product product : products) {
			total += product.getPrice();
		}
		return total;
	}

}
