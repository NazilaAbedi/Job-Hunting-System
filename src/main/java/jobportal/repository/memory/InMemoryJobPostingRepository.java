package jobportal.repository.memory;
import jobportal.domain.job.JobPosting;
import jobportal.repository.JobPostingRepository;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
public class InMemoryJobPostingRepository implements JobPostingRepository {
    private final Map<Integer, JobPosting> byId = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(2000);
    @Override public JobPosting save(JobPosting job) {
        if (job == null) throw new IllegalArgumentException("job is null");
        if (job.getId() == 0) job.setId(seq.incrementAndGet());
        byId.put(job.getId(), job);
        return job;
    }
    @Override public Optional<JobPosting> findById(int id) { return Optional.ofNullable(byId.get(id)); }
    @Override public List<JobPosting> findAll() { return new ArrayList<>(byId.values()); }
    @Override public List<JobPosting> findAllActive() {
        List<JobPosting> out = new ArrayList<>();
        for (JobPosting j : byId.values()) if (j.isActive()) out.add(j);
        return out;
    }
    @Override public List<JobPosting> search(String keyword) {
        String k = (keyword == null ? "" : keyword.toLowerCase());
        List<JobPosting> out = new ArrayList<>();
        for (JobPosting j : byId.values()) {
            if (!j.isActive()) continue;
            boolean hit = (j.getTitle()!=null && j.getTitle().toLowerCase().contains(k))
                    || (j.getDescription()!=null && j.getDescription().toLowerCase().contains(k))
                    || (j.getLocation()!=null && j.getLocation().toLowerCase().contains(k))
                    || j.getRequiredSkills().stream().anyMatch(s -> s.getName().toLowerCase().contains(k));
            if (hit) out.add(j);
        }
        return out;
    }
}
