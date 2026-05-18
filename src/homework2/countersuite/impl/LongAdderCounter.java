package homework2.countersuite.impl;

import homework2.countersuite.Counter;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderCounter implements Counter {
    private final LongAdder longAdder = new LongAdder();

    @Override
    public void increment() {
        longAdder.increment();
    }

    @Override
    public void incrementBy(long delta) {
        longAdder.add(delta);
    }

    @Override
    public long getCount() {
        return longAdder.longValue();
    }
}
