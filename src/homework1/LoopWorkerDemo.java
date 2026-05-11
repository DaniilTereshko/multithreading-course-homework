package homework1;

import homework1.util.LoggerUtil;

public class LoopWorkerDemo {
    public static void main(String[] args) {
        LoopWorker loopWorker = new LoopWorker("loop-worker-1");
        loopWorker.start();

        safeSleep(2000);

        loopWorker.stopAsync();
        safeSleep(1000);

        LoggerUtil.log("Main finished work and loop-worker-1 was interrupted: " + loopWorker.isInterrupted(), Thread.currentThread());
    }

    private static class LoopWorker extends Thread {
        public LoopWorker(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    LoggerUtil.log("Thread working...", Thread.currentThread());

                    int count = 0;
                    count++;

                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    LoggerUtil.log("Thread interrupted", Thread.currentThread());
                    Thread.currentThread().interrupt();
                }
            }
        }

        public void stopAsync() {
            this.interrupt();
        }
    }

    private static void safeSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
