package homework1.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LoggerUtil {

    public static void log(String message, Thread t) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.printf("[%s] [%s] [%s] %s%n",
                time,
                t.getName(),
                t.getState(),
                message
        );
    }
}
