package homework3.utils;

import java.security.SecureRandom;

/** Утилита активной загрузки CPU (busy-wait) на заданное время. */
public final class BusyCpuUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private BusyCpuUtil() {}

    /** Крутится на CPU указанное кол-во миллисекунд. */
    public static void spinOnCpuMillis(long ms) {
        long end = System.nanoTime() + ms * 1_000_000L;
        long acc = 0;
        while (System.nanoTime() < end) {
            acc += (acc * 31 + 7); // немного бесполезной арифметики
        }
        if (acc == 42) System.out.print(""); // анти-оптимизация JIT
    }

    /** То же, но с небольшим джиттером. */
    public static void spinOnCpuMillisWithJitter(long ms) {
        if (ms <= 0) return;
        long jitter = RANDOM.nextLong(ms / 2);
        if (RANDOM.nextBoolean()) jitter *= -1;
        spinOnCpuMillis(ms + jitter);
    }
}
