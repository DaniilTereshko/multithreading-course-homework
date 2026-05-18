package homework2.deadlocksimulator;

import java.util.concurrent.locks.ReentrantLock;

public final class LockableResource {
    private final int id;
    private final String name;
    private final ReentrantLock lock = new ReentrantLock();
    private final Object monitor = new Object();

    public LockableResource(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public Object getMonitor() {
        return monitor;
    }
}
