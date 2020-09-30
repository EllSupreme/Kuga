package io.supreme.kuga;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.supreme.kuga.data.DataLibrary;
import io.supreme.kuga.database.KugaCommons;
import io.supreme.kuga.database.config.JedisConfig;
import io.supreme.kuga.manager.LoadBalancing;
import io.supreme.kuga.server.KugaServer;
import io.supreme.kuga.server.ServerGame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KugaBukkit extends JavaPlugin {

    private KugaServer kugaServer;
    private LoadBalancing loadBalancing = new LoadBalancing();
    private ExecutorService executorService;
    private Gson gson = new GsonBuilder().create();

    @Override
    public void onLoad() {
        new KugaCommons().connect(new JedisConfig(
                getConfig().getString("jedis.host"),
                getConfig().getInt("jedis.port"),
                getConfig().getString("jedis.password"),
                getConfig().getInt("jedis.database"),
                getConfig().getBoolean("jedis.use-password")
        ));
        new DataLibrary().initialize();
        this.setServer();
        this.executorService = Executors.newCachedThreadPool();
        super.onLoad();
    }

    @Override
    public void onEnable() {
        if (getConfig().getBoolean("autoStart")) {
            this.loadBalancing.start();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void setServer() {

        String stringServerGame = getConfig().getString("server.serverGame");
        ServerGame serverGame = getStringToServerGame(stringServerGame);
        int serverID = getConfig().getInt("server.serverID");
        String serverGameIdName = stringServerGame + "-" + serverID;

        kugaServer = new KugaServer(serverID, Bukkit.getPort(), serverGame, serverGame.getMaxPlayers(), serverGameIdName);

    }

    public KugaServer getKugaServer() {
        return kugaServer;
    }

    private ServerGame getStringToServerGame(String serverGame) {
        if (serverGame.equalsIgnoreCase(ServerGame.TEST.getGameName())) {
            return ServerGame.TEST;
        }
        return null;
    }

    public static KugaBukkit getPlugin() {
        return getPlugin(KugaBukkit.class);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Gson getGSON() {
        return gson;
    }

}
