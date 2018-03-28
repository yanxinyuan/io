package com.delta;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.delta.config.ConfigManager;

public class Server {

	public static void main(String[] args) {
		ConfigManager.load("server");
		Integer port = ConfigManager.getInteger("socket.port", Integer.valueOf(8090));
		System.out.println("Listen to address, port = " + port + " ...");
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			while (true) {
				Socket socket = server.accept();
				System.out.println("Accept from address " + socket.getInetAddress() + ":" + socket.getPort());
				new EchoSThread(socket).start();
			}
		} catch (IOException e) {
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
				}
			}
		}
		
	}
	
}
