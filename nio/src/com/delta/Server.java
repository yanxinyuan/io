package com.delta;

import java.io.IOException;

import com.delta.config.ConfigManager;

public class Server {

	private static ServerHandler serverHandle;
	
	public static void main(String[] args) throws IOException {
		ConfigManager.load("server");
		Integer port = ConfigManager.getInteger("socket.port", Integer.valueOf(8090));
		serverHandle = new ServerHandler(port);
		new Thread(serverHandle,"Server").start();  
	}

}
