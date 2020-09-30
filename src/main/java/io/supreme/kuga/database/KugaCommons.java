package io.supreme.kuga.database;

import io.supreme.kuga.KugaBukkit;
import io.supreme.kuga.KugaBungee;
import io.supreme.kuga.database.config.JedisConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Closeable;

public class KugaCommons implements Closeable {

    private JedisPool jedisPool;

    public void setupBungeeJedisConnection() {
        new KugaCommons().connect(new JedisConfig(
                new KugaBungee().getConfiguration().getString("jedis.host"),
                new KugaBungee().getConfiguration().getInt("jedis.port"),
                new KugaBungee().getConfiguration().getString("jedis.password"),
                new KugaBungee().getConfiguration().getInt("jedis.database"),
                new KugaBungee().getConfiguration().getBoolean("jedis.use-password")
        ));
    }

    public void setupBukittJedisConnection() {
        new KugaCommons().connect(new JedisConfig(
                KugaBukkit.getPlugin().getConfig().getString("jedis.host"),
                KugaBukkit.getPlugin().getConfig().getInt("jedis.port"),
                KugaBukkit.getPlugin().getConfig().getString("jedis.password"),
                KugaBukkit.getPlugin().getConfig().getInt("jedis.database"),
                KugaBukkit.getPlugin().getConfig().getBoolean("jedis.use-password")
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

}
