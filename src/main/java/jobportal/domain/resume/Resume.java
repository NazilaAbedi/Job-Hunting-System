package jobportal.domain.resume;
import java.util.*;
import java.nio.charset.StandardCharsets;
public class Resume {
    private int id;
    private String summary;
    private final List<WorkExperience> workExperiences = new ArrayList<>();
    private final List<Education> education = new ArrayList<>();
    private final List<Skill> skills = new ArrayList<>();
    public Resume(int id, String summary) {
        this.id = id;
        this.summary = summary;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSummary() { return summary; }
    public List<WorkExperience> getWorkExperiences() { return Collections.unmodifiableList(workExperiences); }
    public List<Education> getEducation() { return Collections.unmodifiableList(education); }
    public List<Skill> getSkills() { return Collections.unmodifiableList(skills); }
    public void addWorkExperience(WorkExperience exp) { if (exp != null) workExperiences.add(exp); }
    public void addEducation(Education edu) { if (edu != null) education.add(edu); }
    public void addSkill(Skill skill) { if (skill != null && !skills.contains(skill)) skills.add(skill); }
    public byte[] generatePdf() {
        String txt = "RESUME #" + id + "\nSummary: " + (summary==null?"":summary) + "\nSkills: " + skills + "\n";
        return txt.getBytes(StandardCharsets.UTF_8);
    }
    @Override public String toString() {
        return "Resume{id=" + id + ", summary='" + summary + "', skills=" + skills + "}";
    }
}
