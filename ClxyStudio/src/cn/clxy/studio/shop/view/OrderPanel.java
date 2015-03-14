package cn.clxy.studio.shop.view;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.component.IRequestablePage;

import cn.clxy.studio.shop.model.Customer;

public class OrderPanel extends ShopBasePanel {
	private static final long serialVersionUID = 1L;

	public OrderPanel(String id) {
		this(id, null);
	}

	public OrderPanel(String id, IModel<?> model) {
		super(id, model);

		add(new FeedbackPanel("feedback"));

		Form<Customer> form = new Form<Customer>("form");
		add(form);

		Customer customer = getCustomer();
		CompoundPropertyModel<Customer> cpmodel = new CompoundPropertyModel<Customer>(
				customer);
		form.setModel(cpmodel);
		form.add(new TextField<String>("firstname").setRequired(true));
		form.add(new TextField<String>("lastname").setRequired(true));

		form.add(new TextField<String>("address.street"));
		form.add(new TextField<Integer>("address.zipcode"));
		form.add(new TextField<String>("address.city"));

		form.add(new Link<IRequestablePage>("cancel") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ShopHomePage.class);
			}
		});
		form.add(new Button("order") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				getCart().getProducts().clear();
				setResponsePage(ShopHomePage.class);
			}
		});
	}
}