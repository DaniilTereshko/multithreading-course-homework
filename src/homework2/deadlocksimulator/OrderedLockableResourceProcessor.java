package homework2.deadlocksimulator;

import homework2.utils.Logger;

public class OrderedLockableResourceProcessor implements LockableResourceProcessor {
    @Override
    public void processResources(LockableResource r1, LockableResource r2) throws InterruptedException {
        var firstResource = r1.getId() > r2.getId() ? r1 : r2;
        var secondResource = r1.getId() <= r2.getId() ? r1 : r2;

        Logger.log("Пытаюсь взять монитор ресурса %s", firstResource.getName());
        synchronized (firstResource.getMonitor()) {
            Logger.log("Взял монитор ресурса %s", firstResource.getName());
            Thread.sleep(10);
            Logger.log("Пытаюсь взять монитор ресурса %s", secondResource.getName());
            synchronized (secondResource.getMonitor()) {
                Logger.log("Взял монитор %s", secondResource.getName());
                Logger.log("Начинаю выполнять работу...");
                Logger.log("Заканчиваю выполнять работу...");
            }
            Logger.log("Освободил монитор ресурса %s", secondResource.getName());
        }
        Logger.log("Освободил монитор ресурса %s", firstResource.getName());
    }
}
