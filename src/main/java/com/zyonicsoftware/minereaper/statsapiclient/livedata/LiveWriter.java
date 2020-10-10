package com.zyonicsoftware.minereaper.statsapiclient.livedata;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class LiveWriter {

    private Socket clientSocket;
    private PrintWriter output;

    public void startConnection(String address, int port) throws IOException {
        this.clientSocket = new Socket(address, port);
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        System.out.println("[MR-StatsAPIClient] Established Sending Connection on " + this.clientSocket.getInetAddress() + ":" + this.clientSocket.getPort());
    }

    public void closeConnection() throws IOException {
        this.clientSocket.close();
        this.output.close();
        System.out.println("[MR-StatsAPIClient] Sending Connection closed");
    }

    public void sendMessage(String message) {
        this.output.println(message);
    }

}
