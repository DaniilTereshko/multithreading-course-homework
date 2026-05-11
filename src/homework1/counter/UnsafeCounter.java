package homework1.counter;

public class UnsafeCounter implements Counter {
    private int value;

    @Override
    public void inc() {
        value++;
    }

    @Override
    public long value() {
        return value;
    }
}
