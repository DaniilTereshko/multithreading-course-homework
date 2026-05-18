package homework2.countersuite.impl;

import homework2.countersuite.Counter;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter implements Counter {
    private long value;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void increment() {
        incrementBy(1);
    }

    @Override
    public void incrementBy(long delta) {
        try {
            lock.lock();
            value += delta;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getCount() {
        try {
            lock.lock();
            return value;
        } finally {
            lock.unlock();
        }
    }

}
