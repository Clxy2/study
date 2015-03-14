package cn.clxy.studio.proxy.exception;

import javax.servlet.http.HttpServletResponse;

public class ProxyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;
	private String content;

	public ProxyException(int code, String content) {
		super(content);
		this.code = code;
		this.content = content;
	}

	public ProxyException(Throwable cause) {
		super(cause);
		this.code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		this.content = cause.getLocalizedMessage();
	}

	public static ProxyException wrap(Exception e) {
		return (e instanceof ProxyException) ? (ProxyException) e : new ProxyException(e);
	}

	public int getCode() {
		return code;
	}

	public String getContent() {
		return content;
	}
}
