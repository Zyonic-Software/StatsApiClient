package com.zyonicsoftware.minereaper.statsapiclient.main;

import com.zyonicsoftware.minereaper.statsapiclient.livedata.LiveWriter;
import de.azraanimating.jsonbuilder.JSONBuilder;

import java.io.IOException;
import java.util.UUID;

public class StatsApiClient {

    private final String gametype;
    private final UUID uuid;
    private final LiveWriter liveWriter;

    public StatsApiClient(final String gametype) {
        this.gametype = gametype;
        this.uuid = UUID.randomUUID();
        this.liveWriter = new LiveWriter();
        System.out.println("");
    }

    public UUID getUuid() {
        return uuid;
    }

    public void closeConnection() throws IOException {
        this.liveWriter.closeConnection();
    }

    public void openConnection(String address, int port) throws IOException {
        this.liveWriter.startConnection(address, port);
    }

    public void registerSession() {
        JSONBuilder jsonBuilder = new JSONBuilder();

        jsonBuilder.createObject("messageType", "REGISTER");
        jsonBuilder.createObject("uuid", this.uuid.toString());
        jsonBuilder.createObject("type", this.gametype);

        this.liveWriter.sendMessage(jsonBuilder.build());
    }

    public void updateSession(String data) {
        JSONBuilder jsonBuilder = new JSONBuilder();

        jsonBuilder.createObject("messageType", "UPDATE");
        jsonBuilder.createObject("uuid", this.uuid.toString());
        jsonBuilder.createObject("type", this.gametype);
        jsonBuilder.createObject("data", data);

        this.liveWriter.sendMessage(jsonBuilder.build());
    }

    public void closeSession() {
        JSONBuilder jsonBuilder = new JSONBuilder();

        jsonBuilder.createObject("messageType", "REMOVE");
        jsonBuilder.createObject("uuid", this.uuid.toString());
        jsonBuilder.createObject("type", this.gametype);

        this.liveWriter.sendMessage(jsonBuilder.build());
    }
}
