package cn.clxy.studio.proxy.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.Resource;

public final class Util {

	private static final String cookiePattern = ",(?=\\s*\\w+\\=)";

	/**
	 * Handle multi-cookie like:<br>
	 * "x=a, y=b" or "x=a,b, y=c" or "x=a; HttpOnly, y=b"
	 * @param string
	 * @return
	 */
	public static String[] parseCookie(String string) {
		return string.split(cookiePattern);
	}

	/**
	 * Self request will cause infinitely loop.
	 * @param request
	 * @return
	 */
	public static boolean isSelf(HttpServletRequest request) {

		String server = request.getServerName();
		int port = request.getServerPort();
		Config config = Config.get();
		return locals.contains(server)
				&& (port == config.getHttpPort() || port == config.getHttpsPort());
	}

	public static String getHeaders(Request request) {

		StringBuilder sb = new StringBuilder("Request headers [");
		for (HttpField field : request.getHeaders()) {
			String name = field.getName();
			sb.append(name + "=" + field.getValue() + " *** ");
		}
		sb.append("]");
		return sb.toString();
	}

	public static String getHeaders(HttpServletResponse response) {

		StringBuilder sb = new StringBuilder("Response " + response.getStatus() + " headers [");
		for (String name : response.getHeaderNames()) {
			sb.append(name + "=" + response.getHeaders(name) + " *** ");
		}
		sb.append("]");
		return sb.toString();
	}

	public static String processAddress(String address) {

		String host = address;
		int colon = address.indexOf(':');
		if (colon > 0) {
			host = address.substring(0, colon);
		}

		if (isOutSide(host)) {
			return Config.get().getLocalServer() + ":" + Config.get().getHttpsPort();
		}

		return address;
	}

	public static URI getURI(HttpServletRequest request) {

		StringBuffer uri = request.getRequestURL();
		String query = request.getQueryString();
		if (query != null) {
			uri.append("?").append(query);
		}
		return URI.create(uri.toString());
	}

	/**
	 * Out side of gfw.
	 * @param url
	 * @return
	 */
	public static boolean isOutSide(String url) {

		Set<String> gfw = Config.get().getGfw();
		String s = url.toLowerCase();
		int index = 0;

		do {
			if (gfw.contains(s)) {
				return true;
			}
			index = s.indexOf('.');
			s = s.substring(index + 1);
		} while (index > 0);

		return false;
	}

	/**
	 * Ignore some sites when debuging.
	 * @param uri
	 * @return
	 */
	public static boolean needDebug(URI uri) {

		String url = uri.toString().toLowerCase();
		for (String nd : noDebugUrls) {
			if (url.indexOf(nd) > 0) {
				return false;
			}
		}
		return true;
	}

	public static int getSystemID(Object object) {
		return System.identityHashCode(object);
	}

	private static final Set<String> noDebugUrls = new HashSet<>(
			Arrays.asList(new String[] {
					"google.com", "baidu.com", "google-analytics.com",
					"doubleclick.net", "baidustatic.com", "35go.net" }));

	private static final Set<String> locals = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			try {
				InetAddress a = InetAddress.getLocalHost();
				add(a.getHostName());
				add(a.getHostAddress());
				add("localhost");
				add("127.0.0.1");
			} catch (Exception e) {
				// do nothing.
			}
		}
	};

	public static Resource getResource(HttpServletRequest request) throws IOException {

		String origPath = request.getPathInfo();
		String path = URIUtil.addPaths(request.getServletPath(), origPath);
		Resource base = Resource.newResource(".");
		path = URIUtil.canonicalPath(path);
		Resource result = base.addPath(path);

		if (result.exists()) {
			return result;
		}

		return Resource.newClassPathResource(origPath);
	}

	public static boolean ping(URI uri) {

		boolean result = true;
		try {
			URLConnection conn = uri.toURL().openConnection();
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			conn.connect();
		} catch (Exception e) {
			log.debug(e);
			result = false;
		}
		log.debug("Ping " + uri + " ... " + result);
		return result;
	}

	private Util() {
	}

	static {
		System.setProperty("jsse.enableSNIExtension", "false");
	}

	private static final Logger log = Logger.getLogger(Util.class);
	private static final int timeout = 5000;
}
