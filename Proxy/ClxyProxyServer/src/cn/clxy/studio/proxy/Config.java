package cn.clxy.studio.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class Config {

	private static Config instance;
	private int rangeFetchLimit = 256 * 1024;
	private Set<String> compressFilter = new HashSet<String>();
	private Set<String> users = new HashSet<String>();

	public static final String ORIGIN_AUTH = "origin-auth";
	public static final String ORIGIN_REQUEST_URL = "origin-request-url";
	public static final Set<String> IGNORE_HEADERS = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			String[] array = {
					// ==== Proxy ========
					ORIGIN_AUTH, ORIGIN_REQUEST_URL,
					// ==== GAE limits =====
					"Content-Length", "Host", "User-Agent", "Vary", "Via",
					"X-Forwarded-For", "X-ProxyUser-IP",
					// ==== Others =======
					"connection", "keep-alive", "proxy-authenticate", "proxy-authorization", "te",
					"trailers", "transfer-encoding", "upgrade", "if-range" };
			for (String s : array) {
				add(s.toLowerCase());
			}
		}
	};

	public static Config get() {
		return instance;
	}

	public boolean isValidUser(String user) {
		return users.contains(user);
	}

	public boolean isContentCompressed(String type) {
		return compressFilter.contains(type.toLowerCase());
	}

	public static void load(InputStream is) {

		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			log.severe(e.toString());
		}

		instance = new Config();

		instance.rangeFetchLimit = Integer.parseInt(p.getProperty("rangeFetchLimit"));

		String strFilter = p.getProperty("filters");
		Set<String> filters = new HashSet<String>(Arrays.asList(strFilter.split("\\|")));
		instance.compressFilter = filters;

		String users = p.getProperty("users");
		instance.users.addAll(Arrays.asList(users.split("\\|")));
	}

	public int getRangeFetchLimit() {
		return rangeFetchLimit;
	}

	public Set<String> getCompressFilter() {
		return compressFilter;
	}

	public Set<String> getUsers() {
		return users;
	}

	private Config() {
	}

	private static final Logger log = Logger.getLogger(Config.class.getName());
}
