package homework3;

import homework3.utils.BusyCpuUtil;
import homework3.utils.Logger;
import homework3.utils.ThreadSleepUtil;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public final class StoryService {
    private final int fetchDraftMs;
    private final int editTextMs;
    private final int finalizeMs;
    private final double chanceOfEditingError;

    public StoryService(int fetchDraftMs, int editTextMs, int finalizeMs, double
            chanceOfEditingError) {
        if (fetchDraftMs < 0 || editTextMs < 0 || finalizeMs < 0 || chanceOfEditingError <= 0 || chanceOfEditingError > 1.0) {
            throw new IllegalArgumentException("Invalid story service params");
        }

        this.fetchDraftMs = fetchDraftMs;
        this.editTextMs = editTextMs;
        this.finalizeMs = finalizeMs;
        this.chanceOfEditingError = chanceOfEditingError;
    }

    // Имитация внешнего I/O (блокирующая задержка): вернёт "draft"
    public String fetchDraft(StoryTask task) {
       // Logger.info("Fetch draft start task: %s", task.id());
        //ThreadSleepUtil.safeSleepWithoutThrow(fetchDraftMs);
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(fetchDraftMs));
        var draft = "Draft-%d: {%s} by {%s}".formatted(task.id(), task.title(), task.author());
       // Logger.info("Fetch draft done task: %s", task.id());
        return draft;

    }

    // Имитация CPU (busy‐loop); с заданной вероятностью бросает исключение
    public String editText(String draft) {
       // Logger.info("Edit text start task");
        BusyCpuUtil.spinOnCpuMillis(editTextMs);
        if (ThreadLocalRandom.current().nextDouble() < chanceOfEditingError) {
            throw new IllegalArgumentException("Edit text error");
        }
        String edited = "Edited " + draft;
        //Logger.info("Edit text done task");
        return edited;

    }

    // Имитация I/O + формирование StoryResult
    public StoryResult finalizeStory(StoryTask task, String editedText) {
        //Logger.info("Finalize story task: %d", task.id());
        //ThreadSleepUtil.safeSleepWithJitter(finalizeMs);
        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(finalizeMs));
        var finalText = "Final: id=%s finalText={%s}".formatted(task.id(), editedText);
        return StoryResult.of(task.id(), finalText);
    }
}
