package homework2.countersuite;

import homework2.countersuite.impl.AtomicCounter;
import homework2.countersuite.impl.LongAdderCounter;
import homework2.countersuite.impl.ReentrantLockCounter;
import homework2.countersuite.impl.SynchronizedCounter;
import homework2.countersuite.impl.UnsafeCounter;
import homework2.countersuite.load.CounterLoad;

public class CountersLoadTesting {
    private final static int THREADS_COUNT = 8;
    private final static int HIGH_LOAD_THREADS_COUNT = 32;
    private final static int ITERATIONS_PER_THREAD = 250_000;
    private final static int HIGH_LOAD_ITERATIONS_PER_THREAD = 1_000_000;

    public static void main(String[] args) {
        Counter unsafeCounter = new UnsafeCounter();
        Counter synchronizedCounter = new SynchronizedCounter();
        Counter atomicCounter = new AtomicCounter();
        Counter reentrantLockCounter = new ReentrantLockCounter();
        Counter longAdderCounter = new LongAdderCounter();

        System.out.println("TESTS");
        CounterLoad.runLoad(unsafeCounter, THREADS_COUNT, ITERATIONS_PER_THREAD);
        CounterLoad.runLoad(synchronizedCounter, THREADS_COUNT, ITERATIONS_PER_THREAD);
        CounterLoad.runLoad(atomicCounter, THREADS_COUNT, ITERATIONS_PER_THREAD);
        CounterLoad.runLoad(reentrantLockCounter, THREADS_COUNT, ITERATIONS_PER_THREAD);
        CounterLoad.runLoad(longAdderCounter, THREADS_COUNT, ITERATIONS_PER_THREAD);

        System.out.println("HIGH LOAD TESTS");
        CounterLoad.runLoad(unsafeCounter, HIGH_LOAD_THREADS_COUNT, HIGH_LOAD_ITERATIONS_PER_THREAD);
        CounterLoad.runLoad(synchronizedCounter, HIGH_LOAD_THREADS_COUNT, HIGH_LOAD_ITERATIONS_PER_THREAD);
        CounterLoad.runLoad(atomicCounter, HIGH_LOAD_THREADS_COUNT, HIGH_LOAD_ITERATIONS_PER_THREAD);
        CounterLoad.runLoad(reentrantLockCounter, HIGH_LOAD_THREADS_COUNT, HIGH_LOAD_ITERATIONS_PER_THREAD);
        CounterLoad.runLoad(longAdderCounter, HIGH_LOAD_THREADS_COUNT, HIGH_LOAD_ITERATIONS_PER_THREAD);

    }
}
