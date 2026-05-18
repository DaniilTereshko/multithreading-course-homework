package homework2.countersuite.impl;

import homework2.countersuite.Counter;

public class SynchronizedCounter implements Counter {
    private long value;

    @Override
    public synchronized void increment() {
        incrementBy(1);
    }

    @Override
    public synchronized void incrementBy(long delta) {
        value += delta;
    }

    @Override
    public synchronized long getCount() {
        return value;
    }
}
