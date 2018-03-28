package com.delta;

import java.util.concurrent.TimeUnit;

import com.delta.config.ConfigManager;

public class Client {

	private static String id = "";
	
	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			id = args[0];
		}
		ConfigManager.load("client");
		Integer port = ConfigManager.getInteger("socket.port", Integer.valueOf(8090));
		String ip = ConfigManager.getProperty("socket.ip", "127.0.0.1");
		Integer tcount = ConfigManager.getInteger("client.count", Integer.valueOf(8090));
		for (int i = 1; i <= tcount; i++) {
			new EchoCThread(ip, port, id + "." + i).start();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
			}
		}
	}
}
