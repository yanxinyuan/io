package com.delta.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class ServerHandler implements Runnable {

	public CountDownLatch latch;  
	
    public AsynchronousServerSocketChannel channel;  
    
    public ServerHandler(int port) {  
        try {  
            channel = AsynchronousServerSocketChannel.open();  
            channel.bind(new InetSocketAddress(port));  
            System.out.println("Listen to address, port = " + port + " ...");
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    
	@Override
	public void run() {
        latch = new CountDownLatch(1);  
        channel.accept(this, new AcceptHandler());  
        try {  
            latch.await();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } 
	}

}
