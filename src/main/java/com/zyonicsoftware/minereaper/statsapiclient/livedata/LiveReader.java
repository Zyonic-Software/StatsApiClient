package com.zyonicsoftware.minereaper.statsapiclient.livedata;

import com.zyonicsoftware.minereaper.statsapiclient.main.ThreadHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public abstract class LiveReader {
    private Socket clientSocket;
    private BufferedReader input;
    private Runnable readerProcess;
    private boolean readerrun;

    protected void startConnection(String address, int port) throws IOException {
        this.clientSocket = new Socket(address, port);
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.readerrun = true;

        System.out.println("[MR-StatsAPIClient] Established Reading Connection on " + this.clientSocket.getInetAddress() + ":" + this.clientSocket.getPort());

        ThreadHandler.startExecute(this.readerProcess = () -> {
            System.out.println("[MR-StatsAPIClient] Reading");
            while (readerrun) {
                try {
                    if(this.clientSocket.isBound()) {
                        String currentLine = this.input.readLine();
                        if (currentLine != null) {
                            this.read(currentLine);
                        }
                    } else {
                        this.input.close();
                        this.clientSocket.close();
                        this.readerrun = false;
                        ThreadHandler.removeExecute(this.readerProcess);
                        System.out.println("[MR-StatsAPIClient] Reading Connection closed");
                    }
                } catch (IOException ignored) {
                    try {
                        this.input.close();
                        this.clientSocket.close();
                        this.readerrun = false;
                        ThreadHandler.removeExecute(this.readerProcess);
                        System.out.println("[MR-StatsAPIClient] Reading Connection closed");
                    } catch (Exception ignored1) { }
                }
            }
        });
    }

    protected void closeConnection() throws IOException {
        this.clientSocket.close();
        this.input.close();
        ThreadHandler.removeExecute(this.readerProcess);
        System.out.println("[MR-StatsAPIClient] Connection Closed");
    }

    protected abstract void read(String message);

}
