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

    protected void startConnection(String address, int port) throws IOException {
        this.clientSocket = new Socket(address, port);
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        ThreadHandler.startExecute(this.readerProcess = () -> {
            while (true) {
                try {
                    String currentLine = this.input.readLine();
                    if(currentLine != null) {
                        this.read(currentLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void closeConnection() throws IOException {
        this.clientSocket.close();
        this.input.close();
        ThreadHandler.removeExecute(this.readerProcess);
    }

    protected abstract void read(String message);

}
