package cn.clxy.studio.common.util;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.clxy.studio.common.data.User;
import cn.clxy.studio.common.web.WebUtil;

import com.google.appengine.api.backends.BackendService;
import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public final class GaeUtil {

	public static void deleteMem(Object key) {
		MemcacheServiceFactory.getMemcacheService().delete(key);
	}

	public static void putMem(Object key, Object value) {

		if (!(value instanceof Serializable)) {
			throw new RuntimeException("Must Serializable instance!");
		}

		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

		byte[] bytes = BeanUtil.serialize((Serializable) value);
		int length = bytes.length;

		log.warn("memcache " + key + " = " + length + " bytes");

		if (length <= memcacheLimit) {
			ms.put(key, value);
			return;
		}

		ms.put(key + lengthKey, length);
		for (int count = 0;; count++) {
			String subKey = key + "." + count;
			int start = count * memcacheLimit;
			int to = Math.min(start + memcacheLimit, length);
			ms.put(subKey, Arrays.copyOfRange(bytes, start, to));
			log.warn("   --- memcache putted " + subKey + "=" + (to - start) + " bytes");
			if (to == length) {
				break;
			}
		}
	}

	public static <T> T getMem(Object key) {
		return getMem(key, null);
	}

	public static <T> T getMem(Object key, Fetcher<T> fetcher) {

		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		T result = getMemBig(key);
		if (fetcher == null) {
			return result;
		}

		if (result == null) {
			result = fetcher.fetch();
			ms.put(key, result);
		}

		return result;
	}

	public static boolean isBackend() {
		BackendService bs = BackendServiceFactory.getBackendService();
		String bn = bs.getCurrentBackend();
		return !StringUtil.isEmpty(bn);
	}

	public static String getLoginUrl() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLoginURL(defaultReturn);
	}

	public static String getLogoutUrl() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLogoutURL(defaultReturn);
	}

	public static User getUserByGae() {

		User result = WebUtil.getUser();
		if (result == null) {
			return null;// TODO should create a new user anyway?
		}

		UserService service = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User gaeUser = service.getCurrentUser();

		if (gaeUser == null) {
			result.setFirstName(null);
			result.setUserId(null);
		} else {
			String email = gaeUser.getEmail();
			result.setFirstName(email.substring(0, email.indexOf('@')));
			result.setEmail(gaeUser.getEmail());
			result.setUserId(gaeUser.getUserId());
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getMemBig(Object key) {

		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

		Integer length = (Integer) ms.get(key + lengthKey);
		if (length == null) {
			return (T) ms.get(key);
		}

		byte[] bytes = new byte[length];
		for (int count = 0;; count++) {
			String subKey = key + "." + count;
			byte[] subBytes = (byte[]) ms.get(subKey);
			if (subBytes == null) {
				break;
			}
			System.arraycopy(subBytes, 0, bytes, count * memcacheLimit, subBytes.length);
		}

		return (T) BeanUtil.deserialize(bytes);
	}

	private static final String defaultReturn = "/";

	public interface Fetcher<T> {
		T fetch();
	}

	private static final String lengthKey = ".length";
	private static final int memcacheLimit = 950000;
	private static final Log log = LogFactory.getLog(GaeUtil.class);

	private GaeUtil() {
	}
}
