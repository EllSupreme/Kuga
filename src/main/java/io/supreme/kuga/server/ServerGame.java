package io.supreme.kuga.server;

public enum ServerGame {

    TEST("Test", 20);

    private String gameName;
    private int maxPlayers;

    ServerGame(String gameName, int maxPlayers) {
        this.gameName = gameName;
        this.maxPlayers = maxPlayers;
    }

    public String getGameName() {
        return gameName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

}
