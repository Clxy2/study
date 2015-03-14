package cn.clxy.studio.shop.view;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.IModel;

import cn.clxy.studio.common.util.ClxyConst;
import cn.clxy.studio.shop.model.Product;

public class ProductListPanel extends ShopBasePanel {
	private static final long serialVersionUID = 1L;

	public ProductListPanel(String id) {
		this(id, null);
	}

	public ProductListPanel(String id, IModel<?> model) {
		super(id, model);

		PageableListView<Product> products = new PageableListView<Product>(
				"products", getAllProducts(), ClxyConst.perPageRows) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Product> item) {
				Product product = item.getModelObject();
				item.add(new Label("name", product.getName()));
				item.add(new Label("description", product.getDescription()));
				item.add(new Label("price", "$" + product.getPrice()));

				item.add(new Link<Product>("add", item.getModel()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						Product selected = getModelObject();
						getCart().getProducts().add(selected);
					}
				});
			}
		};

		add(products);
		add(new PagingNavigator("navigator", products));
	}
}
