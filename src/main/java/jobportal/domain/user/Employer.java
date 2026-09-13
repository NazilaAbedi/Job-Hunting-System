package jobportal.domain.user;
import jobportal.domain.company.Company;
import jobportal.domain.job.Application;
import jobportal.domain.job.ApplicationStatus;
import jobportal.domain.job.JobPosting;
import jobportal.domain.job.Review;
import java.util.*;
public class Employer extends User {
    private Company company;
    private final List<Integer> jobPostingIds = new ArrayList<>();
    public Employer(int id, String username, String passwordHash, String email, String fullName, Company company) {
        super(id, username, passwordHash, email, fullName);
        this.company = company;
    }
    public Company getCompany() { return company; }
    public List<Integer> getJobPostingIds() { return Collections.unmodifiableList(jobPostingIds); }
    public void postJob(JobPosting job) { if (job != null && !jobPostingIds.contains(job.getId())) jobPostingIds.add(job.getId()); }
    public void editJob(JobPosting job) {}
    public void viewApplications(JobPosting job) {}
    public void updateApplicationStatus(Application app, ApplicationStatus newStatus, String notes) {
        if (app == null || newStatus == null) return;
        app.setStatus(newStatus);
        app.addHistory(newStatus, notes);
    }
    public void leaveReview(Application app, Review review) { if (app != null && review != null) app.addReview(review); }
}
