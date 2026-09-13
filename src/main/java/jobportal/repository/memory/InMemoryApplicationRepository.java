package jobportal.repository.memory;
import jobportal.domain.job.Application;
import jobportal.repository.ApplicationRepository;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
public class InMemoryApplicationRepository implements ApplicationRepository {
    private final Map<Integer, Application> byId = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(3000);
    @Override public Application save(Application app) {
        if (app == null) throw new IllegalArgumentException("app is null");
        if (app.getId() == 0) app.setId(seq.incrementAndGet());
        byId.put(app.getId(), app);
        return app;
    }
    @Override public Optional<Application> findById(int id) { return Optional.ofNullable(byId.get(id)); }
    @Override public List<Application> findAll() { return new ArrayList<>(byId.values()); }
    @Override public List<Application> findByJobPostingId(int jobPostingId) {
        List<Application> out = new ArrayList<>();
        for (Application a : byId.values()) if (a.getJobPostingId() == jobPostingId) out.add(a);
        return out;
    }
    @Override public List<Application> findByJobSeekerId(int jobSeekerId) {
        List<Application> out = new ArrayList<>();
        for (Application a : byId.values()) if (a.getJobSeekerId() == jobSeekerId) out.add(a);
        return out;
    }
}
