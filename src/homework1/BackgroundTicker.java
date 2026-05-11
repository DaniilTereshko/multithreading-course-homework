package homework1;

import homework1.util.LoggerUtil;
import java.util.List;

public class BackgroundTicker {
    public static void main(String[] args) {
        startDaemon("daemon-1", 500);

        Thread userThread1 = new Thread(BackgroundTicker::userThreadWork, "user-thread-1");
        Thread userThread2 = new Thread(BackgroundTicker::userThreadWork, "user-thread-2");
        Thread userThread3 = new Thread(BackgroundTicker::userThreadWork, "user-thread-3");

        List<Thread> threads = List.of(userThread1, userThread2, userThread3);
        threads.forEach(Thread::start);

    }

    private static void userThreadWork() {
        try {
            Thread.sleep(2000);
            LoggerUtil.log("Finished work", Thread.currentThread());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static Thread startDaemon(String name, int periodMs) {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    LoggerUtil.log("Tick...", Thread.currentThread());
                    Thread.sleep(periodMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, name);
        thread.setDaemon(true);
        thread.start();
        return thread;
    }
}
