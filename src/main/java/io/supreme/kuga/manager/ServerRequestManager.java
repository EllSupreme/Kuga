package io.supreme.kuga.manager;

import io.supreme.kuga.server.ServerGame;

public class ServerRequestManager {

    private ServerGame serverGame;

    public ServerRequestManager(ServerGame serverGame) {
        this.serverGame = serverGame;
    }

    public ServerGame getServerGame() {
        return serverGame;
    }

}
