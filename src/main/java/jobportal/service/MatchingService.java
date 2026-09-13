package jobportal.service;
import jobportal.domain.job.JobPosting;
import jobportal.domain.resume.Resume;
import jobportal.domain.resume.Skill;
import jobportal.domain.user.JobSeeker;
import java.util.HashSet;
import java.util.Set;
public class MatchingService {
    public double calculateMatchScore(JobSeeker seeker, JobPosting job, Resume resume) {
        if (seeker == null || job == null || resume == null) return 0;
        Set<String> req = new HashSet<>();
        for (Skill s : job.getRequiredSkills()) req.add(s.getName().toLowerCase());
        Set<String> have = new HashSet<>();
        for (Skill s : resume.getSkills()) have.add(s.getName().toLowerCase());
        if (req.isEmpty()) return 0;
        int common = 0;
        for (String r : req) if (have.contains(r)) common++;
        double skillScore = (common * 1.0 / req.size()) * 70.0;
        double locationScore = (job.getLocation() != null && !job.getLocation().isBlank()) ? 15.0 : 0.0;
        double salaryScore = 0.0;
        double exp = seeker.getSalaryExpectation();
        if (exp >= job.getMinSalary() && exp <= job.getMaxSalary()) salaryScore = 15.0;
        return Math.round((skillScore + locationScore + salaryScore) * 100.0) / 100.0;
    }
}
