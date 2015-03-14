package cn.clxy.studio.home.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.clxy.studio.common.dao.UtilDao;
import cn.clxy.studio.common.util.GaeUtil;
import cn.clxy.studio.common.util.GaeUtil.Fetcher;
import cn.clxy.studio.home.data.ReputationData;

public class ReputationService {

	private Map<String, ReputationData> datas = new HashMap<>();

	@Resource
	protected UtilDao utilDao;

	public void refresh() {

		Map<String, ReputationData> fresh = getFromSide();
		if (fresh == null || fresh.isEmpty()) {
			return;
		}

		save(fresh);
		GaeUtil.putMem(KEY_REPUTATION, fresh);
		datas = fresh;
	}

	public Map<String, ReputationData> getReputations() {

		datas = GaeUtil.getMem(KEY_REPUTATION, new Fetcher<Map<String, ReputationData>>() {
			@Override
			public Map<String, ReputationData> fetch() {

				Site[] sides = Site.values();
				Map<String, ReputationData> result = new HashMap<>(sides.length);

				for (Site s : Site.values()) {

					String site = s.name();
					ReputationData data = utilDao.find(
							ReputationData.class, "site=?0 order by t.update desc", site);
					result.put(site, data);
				}

				return result;
			}
		});

		return datas;
	}

	private Map<String, ReputationData> getFromSide() {

		Site[] sides = Site.values();
		Map<String, ReputationData> result = new HashMap<String, ReputationData>(sides.length);
		for (Site s : Site.values()) {
			result.put(s.name(), s.fetch());
		}
		return result;
	}

	private void save(Map<String, ReputationData> fresh) {

		for (Entry<String, ReputationData> e : fresh.entrySet()) {

			ReputationData curData = datas.get(e.getKey());
			ReputationData newData = e.getValue();
			if (newData.isSame(curData)) {
				continue;
			}
			utilDao.insert(newData);
		}
	}

	static enum Site {

		iteye {
			@Override
			public ReputationData fetch() {

				ReputationData reputation = super.fetch();
				try {
					Document document =
							Jsoup.connect("http://clxy.iteye.com/blog/answered_problems").get();
					reputation.setReputation(document.select("th:contains(问答积分)+td").text());
					reputation.setGold(document.select("span.gold.gray").text());
					reputation.setSilver(document.select("span.silver.gray").text());
					reputation.setBronze(document.select("span.bronze.gray").text());
				} catch (Exception e) {
					log.debug(e);
				}
				return reputation;
			}
		},
		stackoverflow {
			@Override
			public ReputationData fetch() {

				ReputationData reputation = super.fetch();
				try {
					Document document =
							Jsoup.connect("http://stackoverflow.com/users/2541318/clxy").get();
					Elements element = document.select(".user-header-left .gravatar");
					reputation.setReputation(element.select(".reputation a").text());
					reputation.setGold(element.select(".badge1+.badgecount").text());
					reputation.setSilver(element.select(".badge2+.badgecount").text());
					reputation.setBronze(element.select(".badge3+.badgecount").text());
				} catch (Exception e) {
					log.debug(e);
				}
				return reputation;
			}
		};

		public ReputationData fetch() {
			return new ReputationData(name());
		}
	}

	private static final String KEY_REPUTATION = "home.reputation";
	private static final Log log = LogFactory.getLog(ReputationService.class);
}
