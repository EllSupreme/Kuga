package io.nathan.kuga;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.nathan.kuga.server.KugaServer;

import java.util.ArrayList;
import java.util.List;

public class Kuga {

    public List<KugaServer> servers = new ArrayList<>();
    private KugaServer kugaServer;
    public static Kuga instance;
    private KugaBungee kugaBungee;
    private Gson gson = new GsonBuilder().create();

    public Kuga() {
        instance = this;
    }

    public Gson getGSON() {
        return gson;
    }

    public List<KugaServer> getKugaServers() {
        return servers;
    }

    public KugaServer getKugaServer() {
        return kugaServer;
    }

    public void out(String string) {
        System.out.println(string);
    }

    public static Kuga getPlugin() {
        return instance;
    }

    public void newKugaServer(KugaServer kugaServer) {
        this.kugaServer = kugaServer;
    }

    public KugaBungee getKugaBungee() {
        return kugaBungee;
    }

}
