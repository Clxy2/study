package cn.clxy.studio.shop.view;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.component.IRequestablePage;

import cn.clxy.studio.shop.model.Product;

public class ProductPanel extends ShopBasePanel {
	private static final long serialVersionUID = 1L;

	public ProductPanel(String id) {
		this(id, null);
	}

	public ProductPanel(String id, IModel<?> model) {
		super(id, model);

		add(new FeedbackPanel("feedback"));

		Form<Product> form = new Form<Product>("form");
		add(form);

		form.setModel(new CompoundPropertyModel<Product>(new Product()));
		form.add(new TextField<String>("name").setRequired(true));
		form.add(new TextField<String>("description").setRequired(true));
		form.add(new TextField<Integer>("price").setRequired(true));
//		form.add(new TextField<String>("image"));

		form.add(new Link<IRequestablePage>("cancel") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ShopHomePage.class);
			}
		});

		form.add(new Button("save") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				setResponsePage(ShopHomePage.class);
			}
		});
	}

}
