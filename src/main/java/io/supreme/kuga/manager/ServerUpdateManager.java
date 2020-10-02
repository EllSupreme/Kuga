package io.supreme.kuga.manager;

import io.supreme.kuga.Kuga;
import io.supreme.kuga.KugaBukkit;
import io.supreme.kuga.database.KugaCommons;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

public class ServerUpdateManager extends BukkitRunnable {

    @Override
    public void run() {
        try (Jedis jedis = new KugaCommons().getJedisPool().getResource()) {

            String jsonServer = Kuga.getPlugin().getGSON().toJson(Kuga.getPlugin().getKugaServer());

            jedis.publish("KugaServer", jsonServer);
        }
    }
}
