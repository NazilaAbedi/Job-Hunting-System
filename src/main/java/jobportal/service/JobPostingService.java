package jobportal.service;
import jobportal.domain.job.JobPosting;
import jobportal.domain.resume.Skill;
import jobportal.repository.JobPostingRepository;
import java.util.List;
public class JobPostingService {
    private final JobPostingRepository jobRepo;
    public JobPostingService(JobPostingRepository jobRepo) { this.jobRepo = jobRepo; }
    public JobPosting create(int employerId, String title, String description, String location,
                             double minSalary, double maxSalary, List<Skill> requiredSkills) {
        if (employerId <= 0) throw new IllegalArgumentException("A valid employer is required.");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Job title is required.");
        if (minSalary < 0 || maxSalary < 0) throw new IllegalArgumentException("Salary cannot be negative.");
        if (minSalary > maxSalary) throw new IllegalArgumentException("Minimum salary cannot exceed maximum salary.");
        JobPosting job = new JobPosting(0, employerId, title.trim(), description, location, minSalary, maxSalary);
        if (requiredSkills != null) for (Skill s : requiredSkills) job.addRequiredSkill(s);
        return jobRepo.save(job);
    }
    public void close(int jobId) {
        JobPosting job = jobRepo.findById(jobId).orElseThrow(() -> new IllegalArgumentException("Job not found."));
        job.closePosting();
        jobRepo.save(job);
    }
    public List<JobPosting> listActive() { return jobRepo.findAllActive(); }
    public List<JobPosting> search(String keyword) { return jobRepo.search(keyword); }
}
