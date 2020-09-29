package io.supreme.kuga.server;

import java.util.List;

public class KugaServer {

    private int id;

    private ServerGame serverGame;
    private int port, maxPlayers, currentPlayers;
    private String serverName, mapName;

    private List<String> properties;

    public KugaServer(int id, int port, ServerGame serverGame, int maxPlayers, String serverName) {
        this.id = id;
        this.port = port;
        this.serverGame = serverGame;
        this.maxPlayers = maxPlayers;
        this.serverName = serverName;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerGame getServerGame() {
        return serverGame;
    }

    public void setServerGame(ServerGame serverGame) {
        this.serverGame = serverGame;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getServerName() {
        return serverName;
    }

    public void setMaxPlayers(String serverName) {
        this.serverName = serverName;
    }

}
