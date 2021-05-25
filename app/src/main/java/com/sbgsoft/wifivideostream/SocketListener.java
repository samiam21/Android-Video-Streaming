package com.sbgsoft.wifivideostream;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener implements Runnable {
    private PrintWriter output;
    private BufferedReader input;
    public static String SERVER_IP = "";
    public static final int SERVER_PORT = 8080;
    ServerSocket serverSocket;

    @Override
    public void run() {
        Socket socket;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            try {
                socket = serverSocket.accept();
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.v("TESTING TESTING", "Connected!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
