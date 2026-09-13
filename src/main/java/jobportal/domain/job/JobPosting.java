package jobportal.domain.job;
import jobportal.domain.resume.Skill;
import java.time.LocalDateTime;
import java.util.*;
public class JobPosting {
    private int id;
    private String title;
    private String description;
    private String location;
    private double minSalary;
    private double maxSalary;
    private LocalDateTime postDate;
    private boolean isActive;
    private int employerId;
    private final List<Skill> requiredSkills = new ArrayList<>();
    private final List<Integer> applicantIds = new ArrayList<>();
    public JobPosting(int id, int employerId, String title, String description, String location, double minSalary, double maxSalary) {
        this.id = id;
        this.employerId = employerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.postDate = LocalDateTime.now();
        this.isActive = true;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEmployerId() { return employerId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public double getMinSalary() { return minSalary; }
    public double getMaxSalary() { return maxSalary; }
    public LocalDateTime getPostDate() { return postDate; }
    public boolean isActive() { return isActive; }
    public List<Skill> getRequiredSkills() { return Collections.unmodifiableList(requiredSkills); }
    public void addRequiredSkill(Skill skill) { if (skill != null && !requiredSkills.contains(skill)) requiredSkills.add(skill); }
    public List<Integer> getApplicants() { return Collections.unmodifiableList(applicantIds); }
    public void addApplicantId(int jobSeekerId) { if (!applicantIds.contains(jobSeekerId)) applicantIds.add(jobSeekerId); }
    public void closePosting() { isActive = false; }
    @Override public String toString() {
        return "JobPosting{id=" + id + ", title='" + title + "', location='" + location + "', salary=" + minSalary + "-" + maxSalary + ", active=" + isActive + "}";
    }
}
