package com.delta;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerHandler implements Runnable {

	private Selector selector;
	
	private ServerSocketChannel serverChannel;
	
	public ServerHandler(Integer port) throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(port));  
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);  
        System.out.println("Listen to address, port = " + port + " ...");
	}
	
	@Override
	public void run() {
		while(true){  
            try{  
                selector.select();
                // linux : epoll, windows : iocp
                Set<SelectionKey> keys = selector.selectedKeys(); 
                Iterator<SelectionKey> it = keys.iterator();  
                SelectionKey key = null;  
                while(it.hasNext()){  
                    key = it.next();  
                    it.remove();  
                    try{  
                    	if (key.isValid()) {
                    		handleInput(key);  
                    	}
                    }catch(Exception e){  
                        if(key != null){  
                            key.cancel();  
                            if(key.channel() != null){  
                                key.channel().close();  
                            }  
                        }  
                    }  
                }  
            } catch (Throwable e){  
                e.printStackTrace();  
            }  
        }
	}
	
	private void handleInput(SelectionKey key) throws IOException{  
		if (key.isAcceptable()) {
			ServerSocketChannel ssc	= (ServerSocketChannel) key.channel();
			SocketChannel sc = ssc.accept();
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ);
		}
		if (key.isReadable()) {
			SocketChannel sc = (SocketChannel) key.channel();
			ByteBuffer bf = ByteBuffer.allocate(1024);
			int readBytes = sc.read(bf);
			if (readBytes > 0) {
				bf.flip();
				byte[] bytes = new byte[bf.remaining()];
				bf.get(bytes);
				String data = new String(bytes, "UTF-8");
				long time = System.currentTimeMillis();
	            String[] array = data.split(" - ");
	            System.out.println(array[0] + " - " + array[1] + " - " + (time - Long.valueOf(array[2].trim())) + "ms");
			}
		}
    }  
	
}
