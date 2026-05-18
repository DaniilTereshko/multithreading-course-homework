package homework2.deadlocksimulator;

public class DeadlockSimulatorDemo {
    public static void main(String[] args) throws InterruptedException {
        final int iterations = 1000;
        final long pauseBetweenLocksMs = 100;
        final long tryLockTimeoutMs = 200;
        final long backoffMs = 20;

        LockableResourceProcessor processor = new TryLockLockableResourceProcessor();

        LockableResource rA = new LockableResource(1, "A");
        LockableResource rB = new LockableResource(2, "B");

        Thread t1 = new Thread(() -> {
            function(iterations, processor, rA, rB);
        }, "thread-1");

        Thread t2 = new Thread(() -> {
            function(iterations, processor, rB, rA);
        }, "thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private static void function(int iterations, LockableResourceProcessor processor, LockableResource rA, LockableResource rB) {
        for (int i = 0; i < iterations; i++) {
            try {
                processor.processResources(rA, rB);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
