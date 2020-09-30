package io.supreme.kuga;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.supreme.kuga.data.DataLibrary;
import io.supreme.kuga.database.KugaCommons;
import io.supreme.kuga.database.config.JedisConfig;
import io.supreme.kuga.manager.LoadBalancing;
import io.supreme.kuga.server.KugaServer;
import io.supreme.kuga.server.ServerGame;
import net.md_5.bungee.Util;
import net.md_5.bungee.api.ProxyServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetSocketAddress;
import java.util.UUID;
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
        out("  _  __                        \n" +
                " | |/ /  _   _    __ _    __ _ \n" +
                " | ' /  | | | |  / _` |  / _` |\n" +
                " | . \\  | |_| | | (_| | | (_| |\n" +
                " |_|\\_\\  \\__,_|  \\__, |  \\__,_|\n" +
                "                 |___/         ");
        out("Kuga : Start on the version v "+getConfig().getString("version")+" .");
        out("Kuga :           Github : https://github.com/EllSupreme/Kuga              ");
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

        ServerGame serverGame = ServerGame.getServerGame(getConfig().getString("server.serverGame"));
        UUID serverUUID = UUID.fromString(getConfig().getString("server.serverUUID"));

        ProxyServer server = ProxyServer.getInstance();

        InetSocketAddress ip = Util.getAddr("localhost:" + Bukkit.getPort());
        server.constructServerInfo(serverUUID.toString(), ip, "", false);

        kugaServer = new KugaServer(getAvailableID(), serverGame, serverUUID, Bukkit.getPort(), serverGame.getMaxPlayers(), 0);

    }

    public KugaServer getKugaServer() {
        return kugaServer;
    }

    public int getAvailableID() {
        return (int) (Math.random() * (30 - 1)) + 1;
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

    public void out(String string) {
        System.out.println(string);
    }

}
