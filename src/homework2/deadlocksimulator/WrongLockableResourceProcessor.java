package homework2.deadlocksimulator;

import homework2.utils.Logger;

public class WrongLockableResourceProcessor implements LockableResourceProcessor {

    @Override
    public void processResources(LockableResource r1, LockableResource r2) throws InterruptedException {
        Logger.log("Пытаюсь взять монитор ресурса %s", r1.getName());
        synchronized (r1.getMonitor()) {
            Logger.log("Взял монитор ресурса %s", r1.getName());
            Thread.sleep(100);
            Logger.log("Пытаюсь взять монитор ресурса %s", r2.getName());
            synchronized (r2.getMonitor()) {
                Logger.log("Взял монитор %s", r2.getName());
                Logger.log("Начинаю выполнять работу...");
                Thread.sleep(3000);
                Logger.log("Заканчиваю выполнять работу...");
            }
            Logger.log("Освободил монитор ресурса %s", r2.getName());
        }
        Logger.log("Освободил монитор ресурса %s", r1.getName());
    }
}
