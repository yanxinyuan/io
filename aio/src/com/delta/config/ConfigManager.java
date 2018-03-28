/*
 * Delta CONFIDENTIAL
 *
 * (C) Copyright Delta Electronics, Inc. 2015 All Rights Reserved
 *
 * NOTICE:  All information contained herein is, and remains the
 * property of Delta Electronics. The intellectual and technical
 * concepts contained herein are proprietary to Delta Electronics
 * and are protected by trade secret, patent law or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Delta Electronics.
 */

package com.delta.config;

import java.util.Properties;

import com.delta.util.ConfigUtil;

public class ConfigManager {

	private static Properties props = new Properties();

	public static void load(String configPath) {
		props.putAll(ConfigUtil.load(configPath));
	}

	public static String getProperty(String key) {
		String str = props.getProperty(key);
		if (str != null) {
			return str.trim();
		}
		return null;
	}

	public static String getProperty(String key, String defaultVal) {
		String str = props.getProperty(key, defaultVal);
		if (str != null) {
			return str.trim();
		}
		return null;
	}

	public static Integer getInteger(String key) {
		String val = props.getProperty(key);
		Integer intVal = null;
		try {
			intVal = Integer.valueOf(val.trim());
		} catch (Exception e) {
			return null;
		}
		return intVal;
	}

	public static Integer getInteger(String key, Integer defaultVal) {
		String val = props.getProperty(key);
		Integer intVal = null;
		try {
			intVal = Integer.valueOf(val.trim());
		} catch (Exception e) {
			return defaultVal;
		}
		return intVal;
	}

	public static Boolean getBoolean(String key) {
		String val = props.getProperty(key);
		if (val == null) {
			return null;
		}
		return Boolean.valueOf(val.trim());
	}

	public static Boolean getBoolean(String key, Boolean defaultVal) {
		String val = props.getProperty(key);
		if (val == null) {
			return defaultVal;
		}
		return Boolean.valueOf(val.trim());
	}

}
