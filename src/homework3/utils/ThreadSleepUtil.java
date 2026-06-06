package homework3.utils;

import java.security.SecureRandom;

/**
 * Обёртка над Thread.sleep(...)
 */
public final class ThreadSleepUtil {
    private static final SecureRandom RANDOM = new SecureRandom();

    private ThreadSleepUtil() {
    }

    /**
     * Спит указанное время; при прерывании восстанавливает флаг.
     */
    public static void safeSleepWithoutThrow(long ms) {
        if (ms <= 0) return;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Спит указанное время с небольшим джиттером; при прерывании восстанавливает флаг.
     */
    public static void safeSleepWithJitter(long ms) {
        if (ms <= 0) return;
        try {
            long jitter = RANDOM.nextLong(ms / 2);
            if (RANDOM.nextBoolean()) jitter *= -1;
            Thread.sleep(ms + jitter);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Спит случайное время в диапазоне; при прерывании восстанавливает флаг.
     */
    public static void safeSleepRandomMillis(long lowerBound, long upperBound) {
        try {
            long sleepMillis = RANDOM.nextLong(lowerBound, upperBound);
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}