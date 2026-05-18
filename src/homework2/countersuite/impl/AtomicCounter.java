package homework2.countersuite.impl;

import homework2.countersuite.Counter;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter implements Counter {
    private final AtomicLong value = new AtomicLong(0);

    @Override
    public void increment() {
        incrementBy(1);
    }

    @Override
    public void incrementBy(long delta) {
        value.addAndGet(delta);
    }

    @Override
    public long getCount() {
        return value.get();
    }
}
