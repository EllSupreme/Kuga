package io.supreme.kuga.manager;

import io.supreme.kuga.data.DataLibrary;
import io.supreme.kuga.server.KugaServer;
import io.supreme.kuga.server.ServerGame;
import net.md_5.bungee.Util;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Recipe;
import org.bukkit.util.FileUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class KugaManager {

    private int port;
    private String serveridname;
    private int id;
    private ServerInfo info;

    public List<KugaServer> servers = new ArrayList<>();

    public void startServer(ServerGame serverGame) throws IOException {

        this.id = 1;
        this.port = getAvailablePort();
        this.serveridname = serverGame.getGameName() + "-" + id;

        File newServerFile = new File(DataLibrary.ACTIVE_SERVERS, serveridname);
        FileUtils.deleteDirectory(newServerFile);
        FileUtils.copyDirectory(DataLibrary.TEMPLATE_SERVER, newServerFile);

        ProxyServer server = ProxyServer.getInstance();

        InetSocketAddress ip = Util.getAddr("localhost:" + port);
        server.constructServerInfo(serveridname, ip, "", false);

        File serverPropertiesTemplate = new File(DataLibrary.TEMPLATE_SERVER, "server.properties");
        File serverProperties = new File(newServerFile, "server.properties");

        File configKuga = new File(newServerFile + "/plugins/Kuga/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configKuga);

        config.set("server.serverGame", serverGame.getGameName());
        config.set("server.serverID", id);


        try {
            final Properties props = new Properties();
            props.load(new FileInputStream(serverPropertiesTemplate));
            props.setProperty("server-port", new StringBuilder().append(port).toString());
            props.setProperty("server-name", new StringBuilder().append(serveridname).toString());
            props.store(new FileOutputStream(serverProperties), null);
        } catch (Exception ex3) {
            ex3.printStackTrace();
        }

        System.out.println("Kuga : A server has been started.");

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
        setup.directory(DataLibrary.ACTIVE_SERVERS);
        setup.redirectErrorStream(true);
        try {
            return setup.start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int getAvailablePort() {
        return (int) (Math.random() * (54999 - 26001)) + 26001;
    }

}
