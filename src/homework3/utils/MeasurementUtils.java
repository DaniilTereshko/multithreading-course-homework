package homework3.utils;

import java.util.function.Supplier;

/** Утилиты для измерений */
public final class MeasurementUtils {
    private MeasurementUtils() { }

    /** Измеряет и логирует длительность выполнения задачи (Supplier-версия) */
    public static <T> T measure(String taskName, Supplier<T> task) {
        long startNanos = System.nanoTime();
        try {
            return task.get();
        } catch (Exception e) {
            Logger.info("Exception in task-name: %s message:", taskName, e.getMessage());
            throw e;
        } finally {
            long endNanos = System.nanoTime();
            long timeElapsedMs = (endNanos - startNanos) / 1_000_000;
            Logger.info("Time: task-name: %s time: %sms", taskName, timeElapsedMs);
        }
    }

    /** Измеряет и логирует длительность выполнения задачи (Runnable-версия) */
    public static void measure(String taskName, Runnable task) {
        long startNanos = System.nanoTime();
        try {
            task.run();
        } catch (Exception e) {
            Logger.error("Exception in task-name: %s message: %s", taskName, e.getMessage());
            throw e;
        } finally {
            long endNanos = System.nanoTime();
            long timeElapsedMs = (endNanos - startNanos) / 1_000_000;
            Logger.info("Time: task-name: %s time: %sms", taskName, timeElapsedMs);
        }
    }
}
