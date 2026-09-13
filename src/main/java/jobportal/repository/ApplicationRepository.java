package jobportal.repository;
import jobportal.domain.job.Application;
import java.util.*;
public interface ApplicationRepository {
    Application save(Application app);
    Optional<Application> findById(int id);
    List<Application> findAll();
    List<Application> findByJobPostingId(int jobPostingId);
    List<Application> findByJobSeekerId(int jobSeekerId);
}
