package io.supreme.kuga;

import io.supreme.kuga.database.KugaCommons;
import io.supreme.kuga.database.config.JedisConfig;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class KugaBungee extends Plugin {

    private Configuration config;

    @Override
    public void onLoad() {
        new KugaCommons().connect(new JedisConfig(
                config.getString("jedis.host"),
                config.getInt("jedis.port"),
                config.getString("jedis.password"),
                config.getInt("jedis.database"),
                config.getBoolean("jedis.use-password")
        ));
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
