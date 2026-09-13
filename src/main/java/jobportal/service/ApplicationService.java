package jobportal.service;
import jobportal.domain.job.Application;
import jobportal.domain.job.ApplicationStatus;
import jobportal.domain.job.JobPosting;
import jobportal.domain.user.JobSeeker;
import jobportal.domain.user.User;
import jobportal.repository.ApplicationRepository;
import jobportal.repository.JobPostingRepository;
import jobportal.repository.UserRepository;
import java.util.List;
public class ApplicationService {
    private final ApplicationRepository appRepo;
    private final JobPostingRepository jobRepo;
    private final UserRepository userRepo;
    private final NotificationService notif;
    public ApplicationService(ApplicationRepository appRepo, JobPostingRepository jobRepo, UserRepository userRepo, NotificationService notif) {
        this.appRepo = appRepo;
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
        this.notif = notif;
    }
    public Application apply(int jobSeekerId, int jobPostingId, String coverLetter) {
        JobPosting job = jobRepo.findById(jobPostingId).orElseThrow(() -> new IllegalArgumentException("Job not found."));
        User u = userRepo.findById(jobSeekerId).orElseThrow(() -> new IllegalArgumentException("User not found."));
        if (!(u instanceof JobSeeker)) throw new IllegalArgumentException("Only JobSeekers can apply.");
        if (!job.isActive()) throw new IllegalStateException("Job is closed.");
        boolean alreadyApplied = appRepo.findByJobSeekerId(jobSeekerId).stream()
                .anyMatch(existing -> existing.getJobPostingId() == jobPostingId);
        if (alreadyApplied) throw new IllegalStateException("You have already applied to this job.");
        Application app = new Application(0, jobSeekerId, jobPostingId, coverLetter);
        app = appRepo.save(app);
        job.addApplicantId(jobSeekerId);
        jobRepo.save(job);
        notif.notifyUser(jobSeekerId, "Applied to job #" + jobPostingId + " successfully. ApplicationId=" + app.getId());
        notif.notifyUser(job.getEmployerId(), "New application for job #" + jobPostingId + " (ApplicationId=" + app.getId() + ")");
        return app;
    }
    public void updateStatus(int employerId, int applicationId, ApplicationStatus status, String notes) {
        Application app = appRepo.findById(applicationId).orElseThrow(() -> new IllegalArgumentException("Application not found."));
        JobPosting job = jobRepo.findById(app.getJobPostingId()).orElseThrow(() -> new IllegalArgumentException("Job not found."));
        if (job.getEmployerId() != employerId) throw new IllegalArgumentException("Not authorized for this job.");
        app.setStatus(status);
        app.addHistory(status, notes);
        appRepo.save(app);
        notif.notifyUser(app.getJobSeekerId(), "Application #" + app.getId() + " status updated to " + status);
    }
    public List<Application> listByJob(int employerId, int jobPostingId) {
        JobPosting job = jobRepo.findById(jobPostingId).orElseThrow(() -> new IllegalArgumentException("Job not found."));
        if (job.getEmployerId() != employerId) throw new IllegalArgumentException("Not authorized for this job.");
        return appRepo.findByJobPostingId(jobPostingId);
    }
    public List<Application> listBySeeker(int jobSeekerId) { return appRepo.findByJobSeekerId(jobSeekerId); }
}
