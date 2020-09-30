package io.supreme.kuga.manager;

import io.supreme.kuga.KugaBukkit;
import io.supreme.kuga.server.KugaServer;
import io.supreme.kuga.server.ServerGame;
import net.md_5.bungee.Util;
import net.md_5.bungee.api.ProxyServer;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class KugaManager {

    private int port;

    private KugaServer kugaServer;

    public List<KugaServer> servers = new ArrayList<>();

    public void startServer(ServerGame serverGame) throws IOException {

        this.port = getAvailablePort();
        UUID uuid = UUID.randomUUID();

        File newServerFile = new File(LibraryManager.ACTIVE_SERVERS, uuid.toString());
        FileUtils.deleteDirectory(newServerFile);
        FileUtils.copyDirectory(LibraryManager.TEMPLATE_SERVER, newServerFile);

        File serverPropertiesTemplate = new File(LibraryManager.TEMPLATE_SERVER, "server.properties");
        File serverProperties = new File(newServerFile, "server.properties");

        File configKuga = new File(newServerFile + "/plugins/Kuga/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configKuga);

        config.set("server.serverGame", serverGame.getGameName());
        config.set("server.serverUUID", uuid.toString());

        try {
            final Properties props = new Properties();
            props.load(new FileInputStream(serverPropertiesTemplate));
            props.setProperty("server-port", new StringBuilder().append(port).toString());
            props.setProperty("server-name", new StringBuilder().append(serverGame.getGameName()).toString());
            props.store(new FileOutputStream(serverProperties), null);
        } catch (Exception ex3) {
            ex3.printStackTrace();
        }

        System.out.println("Kuga : A server : " + serverGame.getGameName() + " has been started.");

        this.createSpigotProcess();
    }

    private Process createSpigotProcess() {
        final List<String> args = new ArrayList<String>();
        args.add("java");
        args.add("-Xms1G");
        args.add("-Xmx2G");
        args.add("-XX:-OmitStackTraceInFastThrow");
        args.add("-DIReallyKnowWhatIAmDoingISwear=true");
        args.add("-jar");
        args.add("spigot.jar");
        final ProcessBuilder setup = new ProcessBuilder(args);
        setup.directory(LibraryManager.ACTIVE_SERVERS);
        setup.redirectErrorStream(true);
        try {
            return setup.start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void setServer() {

        ServerGame serverGame = ServerGame.getServerGame(KugaBukkit.getPlugin().getConfig().getString("server.serverGame"));
        UUID serverUUID = UUID.fromString(KugaBukkit.getPlugin().getConfig().getString("server.serverUUID"));

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

    public int getAvailablePort() {
        return (int) (Math.random() * (54999 - 26001)) + 26001;
    }

}
