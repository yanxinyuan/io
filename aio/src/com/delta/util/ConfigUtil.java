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
package com.delta.util;

import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigUtil {
	
	public static Properties load(String configPath) {
		Properties properties = new Properties();
		ResourceBundle rb = ResourceBundle.getBundle(configPath);
		for (String key : rb.keySet()) {
			properties.put(key, rb.getString(key));
		}
		return properties;
	}
	
	public static String generateUrl(String ip, Integer port) {
	    return String.format("tcp://%s:%d", ip, port);
	}
	
}
