package com.delta.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements CompletionHandler<Void, ClientHandler>, Runnable {

	private String ip;  
    private int port;  
    private String id;
    private Integer count = 0;
    private AsynchronousSocketChannel clientChannel;  
    private CountDownLatch latch; 
    private Object lock = new Object();
	public ClientHandler(String id, String ip, Integer port) throws IOException {
		this.ip = ip; 
		this.id = id;
        this.port = port; 
        clientChannel = AsynchronousSocketChannel.open();  
	}
	
	@Override
	public void run() {
		latch = new CountDownLatch(1);  
        clientChannel.connect(new InetSocketAddress(ip, port), this, this);  
//        try {  
//            latch.await();  
//        } catch (InterruptedException e) {  
//            e.printStackTrace();  
//        }  
//        try {  
//            clientChannel.close();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
        synchronized (lock) {
			try {
				lock.wait();
				while (true) {
					String data = (id == "" ? "undefined" : id) + " - " + (++count + " - " + System.currentTimeMillis() + "\r");
					System.out.println(data);
					sendMsg(data);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void completed(Void result, ClientHandler attachment) {
		System.out.println("connect to server successfully");
		synchronized (lock) {
			lock.notify();
		}
	}

	@Override
	public void failed(Throwable exc, ClientHandler attachment) {
		exc.printStackTrace();  
        try {  
            clientChannel.close();  
            latch.countDown();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	public void sendMsg(String msg){  
        byte[] req = msg.getBytes();  
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);  
        writeBuffer.put(req);  
        writeBuffer.flip();  
        clientChannel.write(writeBuffer, writeBuffer,new WriteHandler(clientChannel, latch));  
    }  

}
