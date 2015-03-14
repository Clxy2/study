package cn.clxy.studio.proxy.exception;

import javax.servlet.http.HttpServletResponse;

public class BadRequestException extends ProxyException {

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super(HttpServletResponse.SC_BAD_REQUEST, "Bad request.");
	}

	public BadRequestException(String content) {
		super(HttpServletResponse.SC_BAD_REQUEST, content);
	}
}
