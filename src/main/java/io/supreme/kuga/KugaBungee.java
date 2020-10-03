package io.supreme.kuga;

import io.supreme.kuga.database.KugaCommons;
import io.supreme.kuga.database.config.JedisConfig;
import io.supreme.kuga.manager.LibraryManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class KugaBungee extends Plugin {

    private Configuration config;

    @Override
    public void onLoad() {

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new LibraryManager().initialize();
        new KugaCommons().setupBungeeJedisConnection();
        super.onLoad();
    }

    @Override
    public void onEnable() {
        Kuga.getPlugin().out("  _  __                        \n" +
                " | |/ /  _   _    __ _    __ _ \n" +
                " | ' /  | | | |  / _` |  / _` |\n" +
                " | . \\  | |_| | | (_| | | (_| |\n" +
                " |_|\\_\\  \\__,_|  \\__, |  \\__,_|\n" +
                "                 |___/         ");
        Kuga.getPlugin().out("Kuga : Start on the version v "+config.getString("kuga.version")+" .");
        Kuga.getPlugin().out("Kuga :           Github : https://github.com/EllSupreme/Kuga              ");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public Configuration getConfig() {
        return config;
    }
}
