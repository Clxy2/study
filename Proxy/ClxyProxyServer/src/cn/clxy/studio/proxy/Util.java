package cn.clxy.studio.proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import cn.clxy.studio.proxy.exception.BadRequestException;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;

public final class Util {

	private static final int bufferSize = 1024 * 1024;

	public static String getHeaders(HTTPResponse response) {

		StringBuilder sb = new StringBuilder("Response ")
				.append(response.getResponseCode())
				.append(" headers [");
		for (HTTPHeader header : response.getHeadersUncombined()) {
			String name = header.getName();
			sb.append(name + "=" + header.getValue() + " *** ");
		}
		sb.append("]");
		return sb.toString();
	}

	public static String getHeaders(HTTPRequest request) {

		StringBuilder sb = new StringBuilder("Request headers [");
		for (HTTPHeader header : request.getHeaders()) {
			String name = header.getName();
			sb.append(name + "=" + header.getValue() + " *** ");
		}
		sb.append("]");
		return sb.toString();
	}

	public static URL getOriginUrl(HttpServletRequest req) {

		String url = req.getHeader(Config.ORIGIN_REQUEST_URL);

		if (url == null) {
			throw new BadRequestException("No url found.");
		}

		try {
			return new URL(url);
		} catch (Exception e) {
			throw new BadRequestException("Bad url=" + url);
		}
	}

	public static void copy(InputStream in, OutputStream out) throws IOException {

		try {
			byte[] data = new byte[bufferSize];
			int len = 0;
			while ((len = in.read(data)) > 0) {
				out.write(data, 0, len);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] toBytes(InputStream in) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		copy(in, bos);
		return bos.toByteArray();
	}

	private Util() {
	}
}
