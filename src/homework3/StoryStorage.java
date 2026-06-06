package homework3;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class StoryStorage {

    private final Map<Long, StoryResult> storyResultStorage = new ConcurrentHashMap<>();

    public void save(StoryResult result) {
        storyResultStorage.put(result.taskId(), result);
    }

    public Optional<StoryResult> get(long taskId) {
        return Optional.ofNullable(storyResultStorage.get(taskId));
    }

    public int size() {
        return storyResultStorage.size();
    }

    public Map<Long, StoryResult> snapshot() {
        return new ConcurrentHashMap<>(storyResultStorage);
    }

    public void clear() {
        storyResultStorage.clear();
    }
}
