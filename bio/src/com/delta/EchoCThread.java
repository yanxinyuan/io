package com.delta;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class EchoCThread extends Thread {

	private String ip;
	
	private Integer port;
	
	private String id;
	
	private Integer count = 0;
	
	public EchoCThread(String ip, Integer port, String id) {
		this.ip = ip;
		this.port = port;
		this.id = id;
	}
	
	 public void run() {
		 Socket socket = null;
			try {
				socket = new Socket(ip, port);
				System.out.println("Connect to " + ip + ":" + port + " success");
				BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				while (true) {
					String data = (id == "" ? "undefined" : id) + " - " + (++count + " - " + System.currentTimeMillis() + "\r");
					System.out.println(data);
					wr.write(data);
					wr.flush();
					TimeUnit.SECONDS.sleep(1);
				}
			} catch (Exception e) {
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
	 }
}
