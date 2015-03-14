package cn.clxy.studio.proxy.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.server.Request;

import cn.clxy.studio.proxy.client.Util;

/**
 * @author clxy
 */
public class ProxyHandler extends ConnectHandler {

	protected static final Logger log = Logger.getLogger(ProxyHandler.class);

	public ProxyHandler() {
		super();
		setBufferSize(4 * getBufferSize());
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (!HttpMethod.CONNECT.is(request.getMethod())) {
			return;// only handle connect.
		}

		String origAddress = request.getRequestURI();
		// check if forward to local server.
		String newAddress = Util.processAddress(origAddress);
		log.debug("Connecting to {" + newAddress + "} <=-- {" + origAddress + "}");

		try {
			handleConnect(baseRequest, request, response, newAddress);
		} catch (Exception x) {
			onConnectFailure(request, response, null, x);
			log.debug(x);
		}
	}

	@Override
	public boolean validateDestination(String host, int port) {
		return true;// allow all.
	}
}
