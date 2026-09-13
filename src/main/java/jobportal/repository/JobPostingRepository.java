package jobportal.repository;
import jobportal.domain.job.JobPosting;
import java.util.*;
public interface JobPostingRepository {
    JobPosting save(JobPosting job);
    Optional<JobPosting> findById(int id);
    List<JobPosting> findAll();
    List<JobPosting> findAllActive();
    List<JobPosting> search(String keyword);
}
