package io.supreme.kuga.loadbalancing;

public class LoadBalancing extends Thread {

    @Override
    public void run() {

        while (!(this.isInterrupted())) {

            // LoadBalancing


            try {
                Thread.sleep(25 * 1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        super.run();
    }

}
