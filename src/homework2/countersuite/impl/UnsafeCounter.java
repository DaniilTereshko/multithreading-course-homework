package homework2.countersuite.impl;

import homework2.countersuite.Counter;

public class UnsafeCounter implements Counter {
    private long value;

    @Override
    public void increment() {
        incrementBy(1);
    }

    @Override
    public void incrementBy(long delta) {
        value += delta;
    }

    @Override
    public long getCount() {
        return value;
    }
}
