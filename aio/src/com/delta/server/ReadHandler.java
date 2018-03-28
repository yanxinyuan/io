package com.delta.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

	 private AsynchronousSocketChannel channel;  
	    
	 public ReadHandler(AsynchronousSocketChannel channel) {  
		 this.channel = channel;  
	 }  
	    
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();  
        byte[] bytes = new byte[attachment.remaining()];  
        attachment.get(bytes);  
        try {  
            String data = new String(bytes, "UTF-8");  
            long time = System.currentTimeMillis();
            String[] array = data.split(" - ");
            System.out.println(array[0] + " - " + array[1] + " - " + (time - Long.valueOf(array[2].trim())) + "ms");
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		 try {  
			 this.channel.close();  
		 } catch (Exception e) {  
			 e.printStackTrace();  
		 }  
	}

}
