package com.delta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class EchoSThread extends Thread {
	
    protected Socket socket;

    public EchoSThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
        } catch (IOException e) {
            return;
        }
        String line = null;
        while (true) {
            try {
                line = brinp.readLine();
                long time = System.currentTimeMillis();
                String[] array = line.split(" - ");
                System.out.println(array[0] + " - " + array[1] + " - " + (time - Long.valueOf(array[2].trim())) + "ms");
//                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
//                    socket.close();
//                    return;
//                } else {
//                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}