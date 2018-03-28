package com.delta.client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;


public class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {

	private AsynchronousSocketChannel clientChannel;  
    private CountDownLatch latch;  
    
    public WriteHandler(AsynchronousSocketChannel clientChannel,CountDownLatch latch) {  
        this.clientChannel = clientChannel;  
        this.latch = latch;  
    }  
    
	@Override
	public void completed(Integer result, ByteBuffer buffer) {
        if (buffer.hasRemaining()) {  
            clientChannel.write(buffer, buffer, this);  
        }  
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		exc.printStackTrace();
		try {  
            clientChannel.close();  
            latch.countDown();  
        } catch (Exception e) {  
        }  
	}

}
