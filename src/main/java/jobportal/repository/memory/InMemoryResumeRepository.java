package jobportal.repository.memory;
import jobportal.domain.resume.Resume;
import jobportal.repository.ResumeRepository;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
public class InMemoryResumeRepository implements ResumeRepository {
    private final Map<Integer, Map<Integer, Resume>> store = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(4000);
    @Override public Resume save(int ownerId, Resume resume) {
        if (resume == null) throw new IllegalArgumentException("resume is null");
        store.putIfAbsent(ownerId, new HashMap<>());
        if (resume.getId() == 0) resume.setId(seq.incrementAndGet());
        store.get(ownerId).put(resume.getId(), resume);
        return resume;
    }
    @Override public Optional<Resume> findById(int ownerId, int resumeId) {
        Map<Integer, Resume> m = store.get(ownerId);
        if (m == null) return Optional.empty();
        return Optional.ofNullable(m.get(resumeId));
    }
    @Override public List<Resume> findAllByOwner(int ownerId) {
        Map<Integer, Resume> m = store.get(ownerId);
        if (m == null) return List.of();
        return new ArrayList<>(m.values());
    }
}
