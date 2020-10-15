package io.supreme.kuga.manager;

import io.supreme.kuga.Kuga;
import io.supreme.kuga.manager.KugaManager;
import io.supreme.kuga.server.ServerGame;

import java.io.IOException;

public class LoadBalancing extends Thread {

    @Override
    public void run() {

        while (!(this.isInterrupted())) {

            if (Kuga.getPlugin().servers.size() == 0) {
                try {
                    new KugaManager().startServer(ServerGame.TEST);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(25 * 1000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
        super.run();
    }

}
