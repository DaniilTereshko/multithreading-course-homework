package homework3;

import homework3.utils.Logger;
import homework3.utils.MeasurementUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StoryPipelineDemo {
    private final static int AMOUNT_OF_TASKS = 10_000;

    private final static int POOL_SIZE = 100;
    private final static int IO_MILLIS = 4000;
    private final static int CPU_MILLIS = 1;
    private final static double CHANCE_OF_ERROR = 0.2; // вероятность ошибки на editText (0 < chance ≤ 1)

    private final static StoryStorage storyStorage = new StoryStorage();
    private final static StoryService storyService = new StoryService(IO_MILLIS, CPU_MILLIS, IO_MILLIS, CHANCE_OF_ERROR);

    public static void main(String[] args) {
        List<StoryTask> tasks = new ArrayList<>();
        for (int i = 1; i <= AMOUNT_OF_TASKS; i++) {
            tasks.add(StoryTask.of(i, "Author-" + i, "Chapter-" + i));
        }

        Logger.log("Starting story pipeline demo: tasksCount=%s", tasks.size());

        Logger.info("Started using cached thread pool...");
        try (var executor = Executors.newCachedThreadPool()) {
            MeasurementUtils.measure(
                    "Using cached executor",
                    () -> runPipeline(
                            executor,
                            tasks
                    )
            );
        }
        storyStorage.clear();

        Logger.info("Started using virtual threads...");
        try (var vtExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            MeasurementUtils.measure(
                    "Using virtual executor",
                    () -> runPipeline(
                            vtExecutor,
                            tasks
                    )
            );
        }

    }

    private static void runPipeline(ExecutorService executorService, List<StoryTask> tasks) {
        List<CompletableFuture<Void>> futureTasks = new ArrayList<>();
        for (StoryTask task : tasks) {
            CompletableFuture<Void> futureTask = CompletableFuture.supplyAsync(() -> storyService.fetchDraft(task), executorService)
                    .thenApplyAsync(storyService::editText)
                    .thenApplyAsync(res -> storyService.finalizeStory(task, res), executorService)
                    .thenAccept(storyStorage::save)
                    .exceptionally((ex) -> {
                       // Logger.error("Task failed: taskId=%s", task.id(), ex.getMessage());
                        return null;
                    });
            futureTasks.add(futureTask);
        }

        CompletableFuture.allOf(futureTasks.toArray(CompletableFuture[]::new)).join();
    }
}
