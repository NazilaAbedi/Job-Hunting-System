package jobportal.service;
import jobportal.domain.common.Report;
import jobportal.domain.job.JobPosting;
import jobportal.domain.resume.Skill;
import jobportal.repository.JobPostingRepository;
import jobportal.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AnalyticsService {
    private final JobPostingRepository jobRepo;
    public AnalyticsService(JobPostingRepository jobRepo) { this.jobRepo = jobRepo; }
    public Report generateSalaryReport(String skill, String location) {
        List<JobPosting> jobs = jobRepo.findAllActive();
        double sumMin = 0, sumMax = 0;
        int cnt = 0;
        for (JobPosting j : jobs) {
            boolean ok = true;
            if (location != null && !location.isBlank()) {
                ok = j.getLocation() != null && j.getLocation().toLowerCase().contains(location.toLowerCase());
            }
            if (ok && skill != null && !skill.isBlank()) {
                ok = j.getRequiredSkills().stream().anyMatch(s -> s.getName().equalsIgnoreCase(skill));
            }
            if (ok) { sumMin += j.getMinSalary(); sumMax += j.getMaxSalary(); cnt++; }
        }
        String content = (cnt == 0) ? "No data for given filters."
                : "Average salary range: " + (sumMin/cnt) + " - " + (sumMax/cnt) + " based on " + cnt + " postings.";
        return new Report("Salary Report", content);
    }
    public Report getTopDemandedSkills() {
        List<JobPosting> jobs = jobRepo.findAllActive();
        Map<String, Integer> counts = new HashMap<>();
        for (JobPosting j : jobs) {
            for (Skill s : j.getRequiredSkills()) {
                String key = s.getName().toLowerCase();
                counts.put(key, counts.getOrDefault(key, 0) + 1);
            }
        }
        return new Report("Top Demanded Skills", counts);
    }
    public Report getWorkforceDiversityReport(UserRepository userRepo) {
        java.util.Map<String, Object> content = new java.util.HashMap<>();
        java.util.Map<String, Integer> byLocation = new java.util.HashMap<>();
        for (JobPosting j : jobRepo.findAllActive()) {
            String loc = (j.getLocation()==null || j.getLocation().isBlank()) ? "UNKNOWN" : j.getLocation().trim().toUpperCase();
            byLocation.put(loc, byLocation.getOrDefault(loc, 0) + 1);
        }
        content.put("jobPostingsByLocation", byLocation);
        java.util.Map<String, Integer> bySkill = new java.util.HashMap<>();
        for (JobPosting j : jobRepo.findAllActive()) {
            for (Skill s : j.getRequiredSkills()) {
                String key = s.getName().toLowerCase();
                bySkill.put(key, bySkill.getOrDefault(key, 0) + 1);
            }
        }
        content.put("demandedSkillsDistribution", bySkill);
        if (userRepo != null) {
            int seekers = 0, employers = 0, admins = 0;
            for (var u : userRepo.findAll()) {
                String cn = u.getClass().getSimpleName();
                if ("JobSeeker".equals(cn)) seekers++;
                else if ("Employer".equals(cn)) employers++;
                else if ("Admin".equals(cn)) admins++;
            }
            java.util.Map<String, Integer> roles = new java.util.HashMap<>();
            roles.put("JobSeeker", seekers);
            roles.put("Employer", employers);
            roles.put("Admin", admins);
            content.put("userRoleMix", roles);
        }
        content.put("note", "Extendable: add demographic fields later and enhance metrics.");
        return new jobportal.domain.common.Report("Workforce Diversity Report", content);
    }
    public Report getHiringTrends() {
        int active = jobRepo.findAllActive().size();
        int total = jobRepo.findAll().size();
        return new Report("Hiring Trends", "Total postings: " + total + ", Active postings: " + active);
    }
}
