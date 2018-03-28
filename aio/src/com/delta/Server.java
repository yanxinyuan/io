package com.delta;

import com.delta.config.ConfigManager;
import com.delta.server.ServerHandler;

public class Server {

	public static void main(String[] args) {
		ConfigManager.load("server");
		Integer port = ConfigManager.getInteger("socket.port", Integer.valueOf(8090));
		new Thread(new ServerHandler(port), "Server").start(); 
	}

}
