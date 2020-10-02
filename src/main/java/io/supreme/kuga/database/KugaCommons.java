package io.supreme.kuga.database;

import io.supreme.kuga.Kuga;
import io.supreme.kuga.KugaBukkit;
import io.supreme.kuga.KugaBungee;
import io.supreme.kuga.database.config.JedisConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Closeable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KugaCommons implements Closeable {

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private JedisPool jedisPool;

    public void setupBungeeJedisConnection() {
        new KugaCommons().connect(new JedisConfig(
                Kuga.getPlugin().getKugaBungee().getConfig().getString("jedis.host"),
                Kuga.getPlugin().getKugaBungee().getConfig().getInt("jedis.port"),
                Kuga.getPlugin().getKugaBungee().getConfig().getString("jedis.password"),
                Kuga.getPlugin().getKugaBungee().getConfig().getInt("jedis.database"),
                Kuga.getPlugin().getKugaBungee().getConfig().getBoolean("jedis.use-password")
        ));
    }

    public void setupBukittJedisConnection() {
        new KugaCommons().connect(new JedisConfig(
                Kuga.getPlugin().getKugaBukkit().getConfig().getString("jedis.host"),
                Kuga.getPlugin().getKugaBukkit().getConfig().getInt("jedis.port"),
                Kuga.getPlugin().getKugaBukkit().getConfig().getString("jedis.password"),
                Kuga.getPlugin().getKugaBukkit().getConfig().getInt("jedis.database"),
                Kuga.getPlugin().getKugaBukkit().getConfig().getBoolean("jedis.use-password")
        ));
    }

    @Override
    public void close() {
        if (jedisPool != null) {
            this.jedisPool.close();
        }
    }

    public void connect(JedisConfig config) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        if (config.usePassword()) {
            this.jedisPool = new JedisPool(poolConfig, config.getHost(), config.getPort(), 3000, config.getPassword(), config.getDatabase());
        } else {
            this.jedisPool = new JedisPool(poolConfig, config.getHost(), config.getPort(), 3000, null, config.getDatabase());
        }
    }

    public JedisPool getJedisPool() {
        return this.jedisPool;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

}
