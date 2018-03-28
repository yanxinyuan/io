package com.delta;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable {

	private String ip;  
    private int port;  
    private SocketChannel socketChannel;  
    private String id;
    private Integer count = 0;
    
	public ClientHandler(String id, String ip, Integer port) throws IOException {
		this.ip = ip; 
		this.id = id;
        this.port = port; 
        socketChannel = SocketChannel.open();  
        socketChannel.configureBlocking(true);
	}
	
	@Override
	public void run() {
		try {
			Boolean flag = socketChannel.connect(new InetSocketAddress(ip, port));
			if(!flag) {
				throw new Exception("Connect to server failed.");
			}  
			while (true) {
				String data = (id == "" ? "undefined" : id) + " - " + (++count + " - " + System.currentTimeMillis() + "\r");
				System.out.println(data);
				sendMessage(socketChannel, data);
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  		
	}
	
	private void sendMessage(SocketChannel channel,String message) throws IOException {
        byte[] bytes = message.getBytes();  
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);  
        writeBuffer.put(bytes);  
        writeBuffer.flip();  
        channel.write(writeBuffer);  
	}

}
