package cn.clxy.studio.home.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.clxy.studio.common.action.NoAuth;
import cn.clxy.studio.common.util.GaeUtil;
import cn.clxy.studio.home.data.ReputationData;
import cn.clxy.studio.home.service.ReputationService;

/**
 * 入口。
 * @author clxy
 */
@NoAuth
public class IndexAction {

	@Resource
	protected ReputationService reputationService;

	@RequestMapping()
	public String index() {
		return "index";
	}

	@RequestMapping("about")
	public ModelAndView about() {
		Map<String, ReputationData> datas = reputationService.getReputations();
		ModelAndView result = new ModelAndView("about", datas);
		return result;
	}

	@RequestMapping("refreshReputation*")
	public String refresh() {

		if (GaeUtil.isBackend()) {
			reputationService.refresh();
			return "Updated";
		}
		return "NotByBackendError";
	}
}
