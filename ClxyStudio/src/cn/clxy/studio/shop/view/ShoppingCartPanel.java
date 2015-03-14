package cn.clxy.studio.shop.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import cn.clxy.studio.shop.model.Cart;
import cn.clxy.studio.shop.model.Product;

public class ShoppingCartPanel extends ShopBasePanel {
	private static final long serialVersionUID = 1L;

	public ShoppingCartPanel(String id) {
		this(id, null);
	}

	public ShoppingCartPanel(String id, Cart cart) {
		super(id);

		add(new ListView<Product>("cart", new PropertyModel<List<Product>>(
				this, "cart.products")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Product> item) {
				Product product = item.getModelObject();
				item.add(new Label("name", product.getName()));
				item.add(new Label("price", "$" + product.getPrice()));

				item.add(new Link<Product>("remove", item.getModel()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						Product selected = getModelObject();
						getCart().getProducts().remove(selected);
					}

					@Override
					public boolean isVisible() {
						return !CheckoutPage.class.isInstance(this.getPage());
					}
				});
			}
		});

		add(new Label("total", new Model<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				NumberFormat nf = new DecimalFormat("#0.00");
				return nf.format(getCart().getTotal());
			}
		}));
	}
}
