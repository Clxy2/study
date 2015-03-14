package cn.clxy.studio.proxy.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.clxy.studio.proxy.Config;
import cn.clxy.studio.proxy.Util;
import cn.clxy.studio.proxy.exception.AuthException;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.FetchOptions.Builder;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.ResponseTooLargeException;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class ProxyService {

	private static final FetchOptions options =
			Builder.disallowTruncate().setDeadline(120d).doNotFollowRedirects();

	private static final Logger log = Logger.getLogger(ProxyService.class.getName());

	public void fetch(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// check auth.
		String auth = req.getHeader(Config.ORIGIN_AUTH);
		if (!Config.get().isValidUser(auth)) {
			throw new AuthException();
		}

		HTTPRequest request = transRequest(req);
		HTTPResponse response = null;

		// fetch.
		try {
			response = URLFetchServiceFactory.getURLFetchService().fetch(request);
		} catch (ResponseTooLargeException e) {
			// TODO try range
			throw e;
		}

		transResponse(response, res);
	}

	private void transResponse(HTTPResponse from, HttpServletResponse to) throws IOException {

		to.setStatus(from.getResponseCode());
		// to.addHeader("Content-Type", "application/octet-stream");

		for (HTTPHeader header : from.getHeaders()) {

			String name = header.getName();
			if (Config.IGNORE_HEADERS.contains(name.toLowerCase())) {
				continue;
			}
			// TODO cookie and range.
			to.addHeader(name, header.getValue());
		}
		log.fine(Util.getHeaders(from));

		byte[] content = from.getContent();
		if (content != null) {
			OutputStream os = to.getOutputStream();
			to.setContentLength(content.length);
			os.write(content);
			os.close();
		}
	}

	@SuppressWarnings("unchecked")
	private HTTPRequest transRequest(
			final HttpServletRequest servletRequest) throws IOException {

		URL url = Util.getOriginUrl(servletRequest);
		HTTPMethod method = HTTPMethod.valueOf(servletRequest.getMethod());
		HTTPRequest request = new HTTPRequest(url, method, options);

		// copy headers.
		Enumeration<String> headers = servletRequest.getHeaderNames();
		while (headers.hasMoreElements()) {

			String name = headers.nextElement();
			String value = servletRequest.getHeader(name);

			if (Config.IGNORE_HEADERS.contains(name.toLowerCase())) {
				continue;
			}
			// TODO range.

			request.addHeader(new HTTPHeader(name, value));
		}
		log.fine(Util.getHeaders(request));

		if (method == HTTPMethod.POST) {
			request.setPayload(Util.toBytes(servletRequest.getInputStream()));
		}

		return request;
	}
}
