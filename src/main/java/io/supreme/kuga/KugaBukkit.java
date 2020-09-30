package io.supreme.kuga;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.supreme.kuga.manager.LibraryManager;
import io.supreme.kuga.database.KugaCommons;
import io.supreme.kuga.manager.KugaManager;
import io.supreme.kuga.manager.LoadBalancing;
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

    public static KugaBukkit getPlugin() {
        return getPlugin(KugaBukkit.class);
    }

    public Gson getGSON() {
        return gson;
    }

    public void out(String string) {
        System.out.println(string);
    }

}
