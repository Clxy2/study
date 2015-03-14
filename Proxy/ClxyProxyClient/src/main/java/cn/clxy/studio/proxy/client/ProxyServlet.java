package cn.clxy.studio.proxy.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.InputStreamContentProvider;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.util.resource.Resource;

/**
 * @see org.eclipse.jetty.proxy.ProxyServlet
 */
public class ProxyServlet extends HttpServlet {

	protected static final String ASYNC_CONTEXT = ProxyServlet.class.getName() + ".asyncContext";

	private HttpClient client;
	private long timeout;

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ProxyServlet.class);

	@Override
	public void init() throws ServletException {

		timeout = Long.parseLong(Config.get().getClient().getProperty("timeout"));
		client = createHttpClient();
		// Put the HttpClient in the context to leverage ContextHandler.MANAGED_ATTRIBUTES
		getServletContext().setAttribute(Config.SERVLET_NAME + ".HttpClient", client);
	}

	public void destroy() {
		try {
			client.stop();
		} catch (Exception x) {
			log.debug(x);
		}
	}

	/**
	 * HttpClient.
	 * @return
	 * @throws ServletException
	 */
	protected HttpClient createHttpClient() throws ServletException {

		HttpClient client = new ProxyHttpClient(Config.get().getClient());

		try {
			client.start();
			// Content must not be decoded, otherwise the client gets confused
			client.getContentDecoderFactories().clear();
			return client;
		} catch (Exception x) {
			throw new ServletException(x);
		}
	}

	@Override
	protected void service(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		if (Util.isSelf(request)) {
			Resource resource = Util.getResource(request);
			if (resource != null && resource.exists() && !resource.isDirectory()) {
				OutputStream os = response.getOutputStream();
				resource.writeTo(os, 0, resource.length());
				os.close();
			} else {
				PrintWriter pw = response.getWriter();
				pw.write("I'm working...");
				pw.close();
			}
			return;
		}

		final int requestId = Util.getSystemID(request);
		final Config config = Config.get();

		boolean needProxy = Util.isOutSide(request.getServerName());
		URI originURI = Util.getURI(request);
		URI newURI = originURI;
		if (needProxy) {
			newURI = config.getProxyURI();
		}

		log.info("Proxy:" + !originURI.equals(newURI) +
				" [" + originURI + "]" + " -> [" + newURI + "]");

		final Request proxyRequest = client.newRequest(newURI)
				.method(HttpMethod.fromString(request.getMethod()))
				.version(HttpVersion.fromString(request.getProtocol()));

		// Copy headers
		for (Enumeration<String> names = request.getHeaderNames(); names.hasMoreElements();) {

			String name = names.nextElement();

			// Remove hop-by-hop headers
			if (Config.HOP_HEADERS.contains(name.toLowerCase(Locale.ENGLISH))) {
				continue;
			}

			for (Enumeration<String> values = request.getHeaders(name); values.hasMoreElements();) {
				String value = values.nextElement();
				if (value != null) {
					proxyRequest.header(name, value);
				}
			}
		}

		proxyRequest.header(HttpHeader.VIA, "http/1.1 clxy");
		proxyRequest.header(HttpHeader.X_FORWARDED_FOR, request.getRemoteAddr());
		proxyRequest.header(HttpHeader.X_FORWARDED_PROTO, request.getScheme());
		proxyRequest.header(
				HttpHeader.X_FORWARDED_HOST, request.getHeader(HttpHeader.HOST.asString()));
		proxyRequest.header(HttpHeader.X_FORWARDED_SERVER, request.getLocalName());

		if (needProxy) {
			// proxyRequest.header(HttpHeader.HOST, newURI.toString());
			proxyRequest.header(Config.ORIGIN_AUTH, config.getUser());
			proxyRequest.header(Config.ORIGIN_REQUEST_URL, originURI.toString());
		}

		if (Util.needDebug(originURI)) {
			log.debug("{" + requestId + "} " +
					request.getMethod() + " {" + originURI + "} -> {" + newURI + "}");
			log.debug(Util.getHeaders(proxyRequest));
		}

		proxyRequest.content(new InputStreamContentProvider(request.getInputStream()) {
			@Override
			public long getLength() {
				return request.getContentLength();
			}
		});

		final AsyncContext asyncContext = request.startAsync();
		// We do not timeout the continuation, but the proxy request
		asyncContext.setTimeout(0);
		request.setAttribute(ASYNC_CONTEXT, asyncContext);

		proxyRequest.timeout(timeout, TimeUnit.MILLISECONDS);
		proxyRequest.send(new ProxyResponseListener(request, response));
	}

	private class ProxyResponseListener extends Response.Listener.Adapter {

		private final HttpServletRequest request;
		private final HttpServletResponse response;

		public ProxyResponseListener(HttpServletRequest request, HttpServletResponse response) {
			this.request = request;
			this.response = response;
		}

		@Override
		public void onBegin(Response proxyResponse) {
			response.setStatus(proxyResponse.getStatus());
		}

		@Override
		public void onHeaders(Response proxyResponse) {

			for (HttpField field : proxyResponse.getHeaders()) {

				String name = field.getName();
				String lower = name.toLowerCase(Locale.ENGLISH);
				String value = field.getValue();

				if (value == null || Config.HOP_HEADERS.contains(lower)) {
					continue;
				}

				// handle comma in multi-cookie. like "x=a,b, y=c".
				if (lower.equals("set-cookie")) {
					// log.debug(value);
					for (String cookie : Util.parseCookie(value)) {
						response.addHeader(name, cookie);
					}
				} else {
					response.addHeader(name, value);
				}
			}

			response.setHeader(HttpHeader.CONNECTION.asString(), "close");

			if (Util.needDebug(Util.getURI(request))) {
				log.debug("{" + Util.getSystemID(request) + "} " + " " + Util.getHeaders(response));
			}
		}

		@Override
		public void onContent(Response proxyResponse, ByteBuffer content) {

			byte[] buffer;
			int offset;
			int length = content.remaining();
			if (content.hasArray()) {
				buffer = content.array();
				offset = content.arrayOffset();
			} else {
				buffer = new byte[length];
				content.get(buffer);
				offset = 0;
			}

			try {
				response.getOutputStream().write(buffer, offset, length);
			} catch (IOException x) {
				proxyResponse.abort(x);
			}
		}

		@Override
		public void onSuccess(Response proxyResponse) {
			AsyncContext asyncContext = (AsyncContext) request.getAttribute(ASYNC_CONTEXT);
			asyncContext.complete();
		}

		@Override
		public void onFailure(Response proxyResponse, Throwable failure) {

			if (!(failure.getClass().getName().endsWith("EofException"))) {
				log.debug(Util.getURI(request) + " proxying failed", failure);
			}

			if (!response.isCommitted()) {
				response.setStatus(
						(failure instanceof TimeoutException) ?
								HttpServletResponse.SC_GATEWAY_TIMEOUT
								: HttpServletResponse.SC_BAD_GATEWAY);
			}

			AsyncContext asyncContext = (AsyncContext) request.getAttribute(ASYNC_CONTEXT);
			asyncContext.complete();
		}

		@Override
		public void onComplete(Result result) {
			// log.debug("{" + getRequestId(request) + "} proxying complete");
		}
	}
}
