package io.supreme.kuga;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.supreme.kuga.manager.LibraryManager;
import io.supreme.kuga.database.KugaCommons;
import io.supreme.kuga.manager.KugaManager;
import io.supreme.kuga.manager.LoadBalancing;
import io.supreme.kuga.manager.ServerUpdateManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class KugaBukkit extends JavaPlugin {

    private LoadBalancing loadBalancing = new LoadBalancing();
    private Gson gson = new GsonBuilder().create();

    @Override
    public void onLoad() {
        new KugaCommons().setupBukittJedisConnection();
        if (Bukkit.getPort() != 25565) {
            new KugaManager().setServer();
        }
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
        Kuga.getPlugin().out("Kuga : Start on the version v "+getConfig().getString("version")+" .");
        Kuga.getPlugin().out("Kuga :           Github : https://github.com/EllSupreme/Kuga              ");
        if (getConfig().getBoolean("autoStart")) {
            this.loadBalancing.start();
        }
        new ServerUpdateManager().runTaskTimerAsynchronously(this, 40L, 20L);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
