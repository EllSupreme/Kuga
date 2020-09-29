package io.supreme.kuga;

import io.supreme.kuga.data.DataLibrary;
import io.supreme.kuga.database.KugaCommons;
import io.supreme.kuga.database.config.JedisConfig;
import io.supreme.kuga.loadbalancing.LoadBalancing;
import org.bukkit.plugin.java.JavaPlugin;

public class Kuga extends JavaPlugin {

    private LoadBalancing loadBalancing = new LoadBalancing();

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

}
