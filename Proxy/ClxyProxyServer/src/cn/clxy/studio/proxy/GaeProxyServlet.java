package cn.clxy.studio.proxy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.clxy.studio.proxy.exception.ProxyException;
import cn.clxy.studio.proxy.service.ProxyService;

public class GaeProxyServlet extends HttpServlet {

	private ProxyService service;

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(GaeProxyServlet.class.getName());

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Config.load(cl.getResourceAsStream("config.properties"));
		service = new ProxyService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		try {
			service.fetch(req, res);
		} catch (Exception e) {

			ProxyException pe = ProxyException.wrap(e);
			res.setStatus(pe.getCode());
			String error = pe.toString();
			log.severe(error);
			res.addHeader("proxy-error", error);
			if (!res.isCommitted()) {
				PrintWriter out = res.getWriter();
				if (out != null) {
					out.write(pe.getContent());
					out.close();
				}
			}
		}
	}
}
