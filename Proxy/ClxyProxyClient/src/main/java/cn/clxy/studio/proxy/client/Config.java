package cn.clxy.studio.proxy.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;

public class Config {

	public static final String SERVLET_NAME = "ClxyProxyServlet";
	public static final String ORIGIN_AUTH = "origin-auth";
	public static final String ORIGIN_REQUEST_URL = "origin-request-url";
	public static final Set<String> HOP_HEADERS = new HashSet<>(
			Arrays.asList(
					"proxy-connection", "connection", "keep-alive", "transfer-encoding", "te",
					"trailer", "proxy-authorization", "proxy-authenticate", "upgrade",
					"host")
			);

	private String user;
	private String localServer;
	private int httpPort = 9999;
	private int httpsPort = 8443;

	private Set<String> gfw = new HashSet<>();

	private List<URI> servers = new ArrayList<>();
	private Properties client = new Properties();

	public URI getProxyURI() {
		return servers.get(new Random().nextInt(servers.size()));
	}

	@SuppressWarnings("unchecked")
	public static void loadConfig(InputStream is) {

		try {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
			engine.eval(new InputStreamReader(is, StandardCharsets.UTF_8));
			Map<String, Object> config = (Map<String, Object>) engine.get("Config");

			instance.httpPort = Integer.parseInt((String) config.get("httpPort"));
			instance.httpsPort = Integer.parseInt((String) config.get("httpsPort"));
			instance.localServer = (String) config.get("host");
			instance.user = (String) config.get("user");

			List<String> servers = (List<String>) config.get("servers");
			for (String s : servers) {
				URI uri = URI.create(s);
				if (Util.ping(uri)) {
					instance.servers.add(uri);
				}
			}

			List<String> gfwJs = (List<String>) config.get("domains");
			instance.gfw = new HashSet<String>(gfwJs);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static void loadGfw(InputStream is) {

		try {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
			engine.eval(new InputStreamReader(is, StandardCharsets.UTF_8));
			List<String> gfwJs = (List<String>) engine.get("domains");
			instance.gfw = new HashSet<String>(gfwJs);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	public static void loadApp(InputStream is) {
		instance.client = loadProperties(is);
	}

	public static Config get() {
		return instance;
	}

	public String getUser() {
		return user;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public int getHttpsPort() {
		return httpsPort;
	}

	public Set<String> getGfw() {
		return gfw;
	}

	public Properties getClient() {
		return client;
	}

	public String getLocalServer() {
		return localServer;
	}

	private static Properties loadProperties(InputStream is) {

		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		return p;
	}

	private Config() {
	}

	private static Config instance = new Config();
	private static final Logger log = Logger.getLogger(Config.class);
}
