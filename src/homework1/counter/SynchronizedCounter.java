package homework1.counter;

public class SynchronizedCounter implements Counter {
    private int value;

    @Override
    public synchronized void inc() {
        value++;
    }

    @Override
    public long value() {
        return value;
    }
}
