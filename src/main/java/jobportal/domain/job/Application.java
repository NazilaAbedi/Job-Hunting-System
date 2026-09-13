package jobportal.domain.job;
import java.time.LocalDateTime;
import java.util.*;
public class Application {
    private int id;
    private LocalDateTime applicationDate;
    private ApplicationStatus status;
    private String coverLetter;
    private final int jobSeekerId;
    private final int jobPostingId;
    private final List<ApplicationHistory> history = new ArrayList<>();
    private final List<Review> reviews = new ArrayList<>();
    public Application(int id, int jobSeekerId, int jobPostingId, String coverLetter) {
        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.jobPostingId = jobPostingId;
        this.coverLetter = coverLetter;
        this.applicationDate = LocalDateTime.now();
        this.status = ApplicationStatus.SUBMITTED;
        this.history.add(new ApplicationHistory(this.status, "Application created"));
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getApplicationDate() { return applicationDate; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public String getCoverLetter() { return coverLetter; }
    public int getJobSeekerId() { return jobSeekerId; }
    public int getJobPostingId() { return jobPostingId; }
    public List<ApplicationHistory> getHistory() { return Collections.unmodifiableList(history); }
    public void addHistory(ApplicationStatus newStatus, String notes) { history.add(new ApplicationHistory(newStatus, notes)); }
    public List<Review> getReviews() { return Collections.unmodifiableList(reviews); }
    public void addReview(Review r) {
        if (r == null) return;
        if (reviews.size() >= 2) throw new IllegalStateException("Max 2 reviews per application.");
        reviews.add(r);
    }
    @Override public String toString() {
        return "Application{id=" + id + ", seekerId=" + jobSeekerId + ", jobId=" + jobPostingId + ", status=" + status + ", date=" + applicationDate + "}";
    }
}
