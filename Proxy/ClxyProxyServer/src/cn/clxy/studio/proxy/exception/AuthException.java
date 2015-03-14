package cn.clxy.studio.proxy.exception;

import javax.servlet.http.HttpServletResponse;

public class AuthException extends ProxyException {

	private static final long serialVersionUID = 1L;

	public AuthException() {
		super(HttpServletResponse.SC_UNAUTHORIZED, "Auth failed.");
	}
}
