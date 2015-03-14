package cn.clxy.studio.common.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.clxy.studio.common.AppConfig;

/**
 * ページング条件データ。
 * @author clxy
 */
public class PaginationData implements Serializable {

	/**
	 * 総件数。
	 */
	protected int count;

	/**
	 * ページ番号。1から開始。
	 */
	protected int page = 1;

	/**
	 * 1ページ件数。
	 */
	protected int limit = defaultLimit;

	protected Map<String, Object> map = new HashMap<>();

	public PaginationData() {
	}

	public PaginationData(int count) {
		this.count = count;
	}

	/**
	 * 最初のページか否か。
	 * @return
	 * @deprecated
	 */
	public boolean isFirst() {
		return page == 1;
	}

	/**
	 * 最後のページか否か。
	 * @return
	 * @deprecated
	 */
	public boolean isLast() {
		return page == getPageCount();
	}

	/**
	 * 検索時使うスタート位置。
	 * @return
	 */
	public int getOffset() {
		return (page - 1) * limit;
	}

	/**
	 * ページ数を返す。
	 * @return
	 */
	public int getPageCount() {
		int pageCount = count / limit;
		return (count % limit == 0) ? pageCount : pageCount + 1;
	}

	public int getPageStart() {

		int pageCount = getPageCount();
		if (pageCount <= 10 || page <= 5) {
			return 1;
		}

		if ((pageCount - page) <= 5) {
			return pageCount - 9;
		} else {
			return page - 4;
		}
	}

	public int getPageEnd() {

		int pageCount = getPageCount();
		if (pageCount <= 10 || (pageCount - page) <= 5) {
			return pageCount;
		}

		if (page <= 5) {
			return 10;
		} else {
			return page + 5;
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	private static final long serialVersionUID = 1L;
	private static final int defaultLimit = AppConfig.getDefaultLimit();
}
