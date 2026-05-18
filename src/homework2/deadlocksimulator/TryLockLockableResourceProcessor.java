package homework2.deadlocksimulator;

import homework2.utils.Logger;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockLockableResourceProcessor implements LockableResourceProcessor {
    private final Random random = new Random();
    @Override
    public void processResources(LockableResource r1, LockableResource r2) throws InterruptedException {
        while (true) {
            ReentrantLock r1Lock = r1.getLock();
            ReentrantLock r2Lock = r2.getLock();
            try {
                Logger.log("Пытаюсь захватить lock r1");
                boolean r1IsLocked = r1Lock.tryLock(10, TimeUnit.MILLISECONDS);
                Logger.log("Пытаюсь захватить lock r2");
                boolean r2IsLocked = r2Lock.tryLock(10, TimeUnit.MILLISECONDS);

                if (!r1IsLocked) {
                    Logger.log("Не смог захватить lock r1");
                    Thread.sleep(random.nextLong(10, 15));
                    continue;
                }

                if (!r2IsLocked) {
                    Logger.log("Не смог захватить lock r2, освобождаю lock r1");
                    r1Lock.unlock();
                    Thread.sleep(random.nextLong(10, 15));
                    continue;
                }

                Logger.log("Делаем полезную работу...");
                return;
            } finally {
                if (r1Lock.isHeldByCurrentThread()) {
                    Logger.log("Освобождаю lock r1");
                    r1Lock.unlock();
                }
                if (r2Lock.isHeldByCurrentThread()) {
                    Logger.log("Освобождаю lock r2");
                    r2Lock.unlock();
                }
            }
        }
    }
}
