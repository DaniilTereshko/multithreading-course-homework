package homework1;

import homework1.util.LoggerUtil;

public class VolatileFlagDemo {
    private volatile static boolean running;

    public static void main(String[] args) {
        Thread worker = new Thread(() -> {
            running = true;

            int x = 0;
            while (running) {
                x++;
            }

            LoggerUtil.log("Work finished", Thread.currentThread());
        }, "worker");

        worker.start();
        safeSleep(1000);
        stop();

        LoggerUtil.log("Main finished", Thread.currentThread());
    }

    private static void stop() {
        running = false;
    }

    private static void safeSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
