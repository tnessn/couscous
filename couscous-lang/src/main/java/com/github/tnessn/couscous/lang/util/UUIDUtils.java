package com.github.tnessn.couscous.lang.util;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * The Class UUIDUtils.
 *
 * @author huangjinfeng
 */
public class UUIDUtils {
	
	/**
	 * 生成32为uuid.
	 *
	 * @return 32位uuid
	 */
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
}

