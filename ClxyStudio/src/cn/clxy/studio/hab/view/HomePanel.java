package cn.clxy.studio.hab.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import cn.clxy.studio.hab.data.TabOrder;
import cn.clxy.studio.hab.service.UserHabService;

import com.google.inject.Inject;

public class HomePanel extends Panel {

	@Inject
	private UserHabService service;

	public HomePanel(String id) {
		this(id, null);
	}

	public HomePanel(String id, IModel<?> model) {

		super(id, model);

		// TODO user sequence sort tabs.
		List<TabOrder> tos = service.getHabTab();
		log.info(tos.toString());

		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AbstractTab(new Model<String>("Total")) {
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId) {
				return new TotalPanel(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<String>("Input Expense")) {
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId) {
				return new ExpensePanel(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<String>("Input Incoming")) {
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId) {
				return new IncomingPanel(panelId);
			}
		});

		tabs.add(new AbstractTab(new Model<String>("Setting")) {
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId) {
				return new SettingPanel(panelId);
			}
		});

		add(new TabbedPanel("tabs", tabs));
	}

	private static final Logger log = Logger.getLogger(HomePanel.class
			.getName());
	private static final long serialVersionUID = 1L;
}
