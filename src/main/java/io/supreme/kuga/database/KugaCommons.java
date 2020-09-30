package io.supreme.kuga.database;

import io.supreme.kuga.database.config.JedisConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Closeable;

public class KugaCommons implements Closeable {

    private JedisPool jedisPool;

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
