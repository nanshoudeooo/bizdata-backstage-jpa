package com.bizdata.framework.shiro.realm;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * username与session_id映射
 *
 * @version 1.0
 *
 * @author sdevil507
 */
public class UserNameSessionIdMap {
	/**
	 * 线程安全map
	 */
	private static Map<String, String> map = new ConcurrentSkipListMap<>();

	public static void put(String username, String session_id) {
		map.put(username, session_id);
	}

	public static String get(String username) {
		return map.get(username);
	}
}
