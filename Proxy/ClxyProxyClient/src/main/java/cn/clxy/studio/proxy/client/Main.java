package cn.clxy.studio.proxy.client;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Password;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class Main {

	public static void main(String[] args) {

		// load configuration.
		Config config = loadConfig();

		HandlerCollection handlers = new HandlerCollection();

		// Servlet handle get and post.
		ServletContextHandler servletHandler = new ServletContextHandler();
		servletHandler.addServlet(new ServletHolder(ProxyServlet.class), "/*");
		handlers.addHandler(servletHandler);

		// Handler handle connect for https.
		handlers.addHandler(new ProxyHandler());

		try {

			Server server = createServer(config);
			server.setHandler(handlers);
			server.start();
			server.join();
		} catch (Exception e) {
			log.error(e);
		}
	}

	private static Config loadConfig() {

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Config.loadConfig(cl.getResourceAsStream("proxy.pac"));
		Config.loadApp(cl.getResourceAsStream("app.properties"));

		return Config.get();
	}

	private static Server createServer(Config config) throws Exception {

		// for http.
		Server server = new Server(config.getHttpPort());

		// for https.
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		// keystore使用JDK的keytool生成。
		// 参考http://wiki.eclipse.org/Jetty/Howto/Configure_SSL
		Resource keystore = new FileResource(cl.getResource("keystore"));
		String password = Password.obfuscate("clxystudio");

		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setKeyStoreResource(keystore);
		sslContextFactory.setKeyStorePassword(password);
		sslContextFactory.setKeyManagerPassword(password);
		sslContextFactory.setTrustStoreResource(keystore);
		sslContextFactory.setTrustStorePassword(password);
		sslContextFactory.setExcludeCipherSuites("SSL_RSA_WITH_DES_CBC_SHA",
				"SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA",
				"SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
				"SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");
		HttpConfiguration httpsConfig = new HttpConfiguration();
		httpsConfig.setSecureScheme("https");
		httpsConfig.setSecurePort(config.getHttpsPort());
		httpsConfig.addCustomizer(new SecureRequestCustomizer());
		ServerConnector sslConnector = new ServerConnector(
				server,
				new SslConnectionFactory(sslContextFactory, "http/1.1"),
				new HttpConnectionFactory(httpsConfig));
		sslConnector.setPort(config.getHttpsPort());
		server.addConnector(sslConnector);

		return server;
	}

	private static Logger log = Logger.getLogger(Main.class);
}
