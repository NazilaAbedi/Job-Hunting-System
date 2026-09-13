package jobportal.service;
import jobportal.domain.job.JobPosting;
import jobportal.domain.resume.Resume;
import jobportal.domain.resume.Skill;
import jobportal.repository.JobPostingRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
public class SalarySuggestionService {
    public static class SalarySuggestion {
        public final double suggestedMin;
        public final double suggestedMax;
        public final String rationale;
        public SalarySuggestion(double suggestedMin, double suggestedMax, String rationale) {
            this.suggestedMin = suggestedMin;
            this.suggestedMax = suggestedMax;
            this.rationale = rationale;
        }
        @Override public String toString() {
            return "Suggested salary range: " + suggestedMin + " - " + suggestedMax + "\nReason: " + rationale;
        }
    }
    private final JobPostingRepository jobRepo;
    public SalarySuggestionService(JobPostingRepository jobRepo) {
        this.jobRepo = jobRepo;
    }
    public SalarySuggestion suggestSalary(Resume resume, String locationKeyword) {
        if (resume == null) return new SalarySuggestion(0, 0, "No resume provided.");
        Set<String> skills = resume.getSkills().stream()
                .map(s -> s.getName().toLowerCase())
                .collect(Collectors.toSet());
        List<JobPosting> pool = jobRepo.findAllActive();
        if (locationKeyword != null && !locationKeyword.isBlank()) {
            String lk = locationKeyword.toLowerCase();
            pool = pool.stream().filter(j -> j.getLocation() != null && j.getLocation().toLowerCase().contains(lk)).toList();
        }
        List<JobPosting> matched = pool.stream().filter(j -> overlaps(skills, j)).toList();
        List<JobPosting> used = matched.isEmpty() ? pool : matched;
        if (used.isEmpty()) return new SalarySuggestion(0, 0, "No job postings available to estimate.");
        double avgMin = used.stream().mapToDouble(JobPosting::getMinSalary).average().orElse(0);
        double avgMax = used.stream().mapToDouble(JobPosting::getMaxSalary).average().orElse(0);
        int nSkills = skills.size();
        double factor = 1.0;
        if (nSkills >= 8) factor = 1.15;
        else if (nSkills >= 5) factor = 1.10;
        else if (nSkills >= 3) factor = 1.05;
        double sMin = round2(avgMin * factor);
        double sMax = round2(avgMax * factor);
        String rationale = (matched.isEmpty()
                ? "Used global/location average salary because no skill-matching jobs were found. "
                : "Used average salary of postings that match your skills. ")
                + "Applied rule-based premium for " + nSkills + " skills (x" + factor + ").";
        return new SalarySuggestion(sMin, sMax, rationale);
    }
    private boolean overlaps(Set<String> seekerSkills, JobPosting job) {
        if (seekerSkills.isEmpty()) return false;
        for (Skill s : job.getRequiredSkills()) {
            if (seekerSkills.contains(s.getName().toLowerCase())) return true;
        }
        return false;
    }
    private double round2(double x) {
        return Math.round(x * 100.0) / 100.0;
    }
}
