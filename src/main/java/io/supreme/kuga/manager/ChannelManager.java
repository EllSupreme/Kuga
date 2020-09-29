package io.supreme.kuga.manager;

import io.supreme.kuga.Kuga;
import io.supreme.kuga.database.KugaCommons;
import io.supreme.kuga.server.ServerGame;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.Closeable;
import java.io.IOException;

/**
 * Le but est :
 * -> On demande la création d'un serverType -> serverReceive va recevoir l'ordre d'en crée avec le serverRequestManager.
 */
public class ChannelManager implements Closeable {

    private ServerDemandListener serverDemandListener;

    public ChannelManager() {
        this.serverDemandListener = new ServerDemandListener();
        Kuga.getPlugin().getExecutorService().submit(() -> {
            try (Jedis jedis = new KugaCommons().getJedisPool().getResource()) {
                jedis.subscribe(serverDemandListener, "ServerChannel");
                System.out.print("ServerChannel");
            }
        });
    }

    public void sendRequestDemandServer(ServerGame serverGame) {
        ServerRequestManager serverRequestManager = new ServerRequestManager(serverGame);
        String json = Kuga.getPlugin().getGSON().toJson(serverRequestManager);
        Kuga.getPlugin().getExecutorService().submit(() -> {
            try (Jedis jedis = new KugaCommons().getJedisPool()
                    .getResource()) {
                jedis.publish("ServerChannel", json);
            }
        });
    }

    @Override
    public void close() throws IOException {
        try {
            this.serverDemandListener.unsubscribe();
        } catch (JedisConnectionException jedisConnectionException) {
            System.err.println(jedisConnectionException.getMessage());
        }
    }

    protected class ServerDemandListener extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            super.onMessage(channel, message);
        }
    }

}
