package jobportal.domain.user;
import jobportal.domain.job.Application;
import jobportal.domain.job.JobPosting;
import jobportal.domain.job.Review;
import jobportal.domain.resume.Resume;
import jobportal.interfaces.IMatchable;
import jobportal.interfaces.ISearchable;
import java.util.*;
public class JobSeeker extends User implements IMatchable, ISearchable {
    private double salaryExpectation;
    private final List<Resume> resumes = new ArrayList<>();
    private final List<Integer> savedJobIds = new ArrayList<>();
    //CONSTRUCTOR
    public JobSeeker(int id, String username, String passwordHash, String email, String fullName, double salaryExpectation) {
        super(id, username, passwordHash, email, fullName);
        this.salaryExpectation = salaryExpectation;
    }
    //METHODS
    public double getSalaryExpectation() { return salaryExpectation; }
    public void setSalaryExpectation(double salaryExpectation) { this.salaryExpectation = salaryExpectation; }
    public List<Resume> getResumes() { return Collections.unmodifiableList(resumes); }
    public void addResume(Resume r) { if (r != null) resumes.add(r); }
    public void applyForJob(JobPosting job) {}
    public void withdrawApplication(Application app) {}
    public void saveJob(JobPosting job) { if (job != null && !savedJobIds.contains(job.getId())) savedJobIds.add(job.getId()); }
    public void manageResume() {}
    public void leaveReview(Application app, Review review) { if (app != null && review != null) app.addReview(review); }
    //IMATCHABLE INTERFACE
    @Override public double calculateMatchScore(Object target) { return 0.0; }
    //ISEARCHABLE INTERFACE
    @Override public List<Object> search(String keyword) { return new ArrayList<>(); }
}
