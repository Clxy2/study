package cn.clxy.home.auth.view;

import org.apache.wicket.markup.html.WebPage;

import cn.clxy.home.auth.service.LoginService;

public class LoginPanel extends WebPage {

	private LoginService loginService;

	public LoginPanel() {

		loginService.login();
	}
}
