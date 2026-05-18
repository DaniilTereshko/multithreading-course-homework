package homework2.countersuite.load;

import homework2.countersuite.Counter;
import homework2.utils.Logger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class CounterLoad {
    public static void runLoad(Counter counter, int threadsCount, int iterationsPerThread) {
        Thread[] threads = new Thread[threadsCount];
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(() -> {
                try {
                    latch.await();
                    for (int j = 0; j < iterationsPerThread; j++) {
                        counter.increment();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "thread-%d".formatted(i));
            threads[i].start();
        }

        long timeStart = System.nanoTime();
        latch.countDown();

        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        long timeEnd = System.nanoTime();

        long expectedValue = (long) threadsCount * iterationsPerThread;
        Logger.log("%s: expected=%d, actual=%d, timeMs=%d",
                counter.getClass().getSimpleName(), expectedValue, counter.getCount(), TimeUnit.NANOSECONDS.toMillis(timeEnd - timeStart));
    }
}