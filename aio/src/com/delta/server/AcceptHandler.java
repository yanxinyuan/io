package com.delta.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, ServerHandler> {

	@Override
	public void completed(AsynchronousSocketChannel channel, ServerHandler serverHandler) {
        serverHandler.channel.accept(serverHandler, this); 
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        channel.read(buffer, buffer, new ReadHandler(channel));  	
	}

	@Override
	public void failed(Throwable exc, ServerHandler serverHandler) {
		exc.printStackTrace();  
        serverHandler.latch.countDown();  		
	}

}
