package io.nathan.kuga.server;

import java.util.List;
import java.util.UUID;

public class KugaServer {

    private int id;
    private UUID uuid;

    private ServerGame serverGame;
    private int port, maxPlayers, onlinePlayers;

    public KugaServer(int id, ServerGame serverGame, UUID uuid, int port, int maxPlayers, int onlinePlayers) {
        this.id = id;
        this.serverGame = serverGame;
        this.uuid = uuid;
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public ServerGame getServerGame() {
        return serverGame;
    }

    public void setServerGame(ServerGame serverGame) {
        this.serverGame = serverGame;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

}
