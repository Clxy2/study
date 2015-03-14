package cn.clxy.studio.common;


/**
 * @author CLXY
 */
public interface Module {

	/**
	 * 必要なURL整備。
	 */
	void load();

	/**
	 * @return
	 */
	String getHomePage();

	/**
	 * ホームページより簡略な表示ページ。
	 * 
	 * @return
	 */
	String getBriefPage();

	/**
	 * 用来生成菜单选项。
	 * 
	 * @return
	 */
	String getMenuItem();
}
