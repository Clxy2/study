package cn.clxy.studio.proxy.client;

import java.util.Properties;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.HttpCookieStore;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class ProxyHttpClient extends HttpClient {

	public ProxyHttpClient(Properties prop) {

		super(new SslContextFactory(true));

		setFollowRedirects(false);
		setCookieStore(new HttpCookieStore.Empty());

		String value = prop.getProperty("maxThreads");
		QueuedThreadPool executor = new QueuedThreadPool(Integer.parseInt(value));
		executor.setName(Config.SERVLET_NAME);
		setExecutor(executor);

		value = prop.getProperty("maxConnections");
		setMaxConnectionsPerDestination(Integer.parseInt(value));

		long timeout = Long.parseLong(prop.getProperty("idleTimeout"));
		setIdleTimeout(timeout);
		setConnectTimeout(timeout);

		// BufferSize x 4.
		setRequestBufferSize(getRequestBufferSize() * 4);
		setResponseBufferSize(getResponseBufferSize() * 4);
	}
}
