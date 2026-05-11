package homework1;

import homework1.util.LoggerUtil;
import java.util.List;
import java.util.Random;

public class StartVsRunDemo {

    private final static Random random = new Random();

    public static void main(String[] args) {
        Thread workerThread = new WorkerThread("worker-thread");
        Thread workerRunnable = new Thread(new WorkerRunnable(), "worker-runnable");
        Thread workerLambda = new Thread(() -> {
                try {
                    LoggerUtil.log("Thread started...", Thread.currentThread());
                    Thread.sleep(random.nextLong(10, 81));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
        }, "worker-lambda");

        List<Thread> threads = List.of(workerThread, workerRunnable, workerLambda);
        threads.forEach(t -> LoggerUtil.log("Before start", t));

        threads.forEach(Thread::start);
        //threads.forEach(Thread::run);

        threads.forEach(t -> LoggerUtil.log("After start", t));

        LoggerUtil.log("Before sleep", Thread.currentThread());
        safeSleep(50);
        LoggerUtil.log("After sleep", Thread.currentThread());

        LoggerUtil.log("Main finished", Thread.currentThread());
    }

    private static class WorkerThread extends Thread {
        public WorkerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                LoggerUtil.log("Thread started...", Thread.currentThread());
                Thread.sleep(random.nextLong(10, 81));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static class WorkerRunnable implements Runnable {

        @Override
        public void run() {
            try {
                LoggerUtil.log("Thread started...", Thread.currentThread());
                Thread.sleep(random.nextLong(10, 81));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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
