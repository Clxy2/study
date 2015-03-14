package cn.clxy.home.common;

import org.apache.wicket.Page;

/**
 * Serviceクラス。
 * @author s0822
 */
public interface Service {

	/**
	 * 必要なURL整備。
	 */
	void load();

	/**
	 * @return
	 */
	Class<? extends Page> getHomePage();

	/**
	 * ホームページより簡略な表示ページ。
	 * @return
	 */
	Class<? extends Page> getBriefPage();
}
