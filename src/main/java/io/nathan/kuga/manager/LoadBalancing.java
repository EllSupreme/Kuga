package io.nathan.kuga.manager;

import io.nathan.kuga.Kuga;
import io.nathan.kuga.server.ServerGame;

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
