package cn.clxy.studio.common.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

import cn.clxy.studio.common.data.MapData;
import cn.clxy.studio.common.util.GaeUtil;

@NoAuth
@Layout(none = true)
public class GaeAction {

	private static String loginUrl = GaeUtil.getLoginUrl();
	private static String logoutUrl = GaeUtil.getLogoutUrl();

	@RequestMapping("authUrl*")
	public Map<String, String> createAuthUrl(HttpServletRequest request) {

		String root = request.getContextPath();
		MapData<String, String> result = new MapData<>();

		result.put("loginUrl", root + loginUrl);
		result.put("logoutUrl", root + logoutUrl);

		return result;
	}
}
