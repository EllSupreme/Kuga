package io.supreme.kuga;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.supreme.kuga.data.DataLibrary;
import io.supreme.kuga.database.KugaCommons;
import io.supreme.kuga.database.config.JedisConfig;
import io.supreme.kuga.loadbalancing.LoadBalancing;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Kuga extends JavaPlugin {

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

    public static Kuga getPlugin() {
        return getPlugin(Kuga.class);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Gson getGSON() {
        return gson;
    }

}
