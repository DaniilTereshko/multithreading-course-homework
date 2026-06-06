package homework3.utils;

/**
 * Компактный logger: печатает имя потока и метку времени
 */
public final class Logger {
    private static volatile boolean logEnabled = true;

    private Logger() {
    }

    public static void log(String fmt, Object... args) {
        if (!logEnabled) return;
        String message = (args == null || args.length == 0) ? fmt :
                String.format(fmt, args);
        Thread current = Thread.currentThread();
        String logFormat = "[%tT.%1$tL][thread:%s] %s%n";
        System.out.printf(logFormat, System.currentTimeMillis(),
                current.getName(), message);
    }

    public static void info(String fmt, Object... args) {
        logWithLevel("INFO",
                fmt, args);
    }

    public static void error(String fmt, Object... args) {
        logWithLevel("ERROR", fmt, args);
    }

    public static void disablePrintLogs() {
        logEnabled = false;
    }

    public static void enablePrintLogs() {
        logEnabled = true;
    }

    private static void logWithLevel(String level, String fmt, Object... args) {
        String finalFmt = "[%s] %s".formatted(level.toUpperCase(), fmt);
        log(finalFmt, args);
    }

}
