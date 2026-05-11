package homework1;

import homework1.counter.Counter;
import homework1.counter.SynchronizedCounter;
import homework1.counter.UnsafeCounter;
import java.util.ArrayList;
import java.util.List;

public class MutexCounterDemo {

    public static void main(String[] args) {
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();

        runRace("unsafeCounter", unsafeCounter, 8, 100_000);
        runRace("synchronizedCounter", synchronizedCounter, 8, 100_000);
    }

    private static void runRace(String label, Counter counter, int threads, int itersPerThread) {
        List<Thread> threadsList = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < itersPerThread; j++) {
                    counter.inc();
                }
            }, "worker-" + i);

            threadsList.add(thread);
        }

        threadsList.forEach(Thread::start);
        threadsList.forEach(MutexCounterDemo::safeJoin);

        long expected = (long) threads * itersPerThread;

        System.out.printf("[%s] expected=%d, actual=%d\n", label, expected, counter.value());
    }

    public static void safeJoin(Thread thread) {
        try {
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
