package com.zyonicsoftware.minereaper.statsapiclient.livedata;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class LiveWriter {

    private Socket clientSocket;
    private PrintWriter output;

    protected void startConnection(String address, int port) throws IOException {
        this.clientSocket = new Socket(address, port);
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    protected void closeConnection() throws IOException {
        this.clientSocket.close();
        this.output.close();
    }

    public void sendMessage(String message) {
        this.output.println(message);
    }

}
