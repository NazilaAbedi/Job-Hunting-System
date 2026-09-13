package jobportal.service;
import jobportal.domain.common.Report;
import jobportal.domain.job.JobPosting;
import jobportal.repository.JobPostingRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CompetitiveMarketAnalysisService {
    private final JobPostingRepository jobRepo;
    public CompetitiveMarketAnalysisService(JobPostingRepository jobRepo) {
        this.jobRepo = jobRepo;
    }
    public Report generateCompetitiveReport(String keyword, String location) {
        List<JobPosting> jobs = jobRepo.findAllActive();
        if (keyword != null && !keyword.isBlank()) {
            String k = keyword.toLowerCase();
            jobs = jobs.stream().filter(j ->
                    (j.getTitle()!=null && j.getTitle().toLowerCase().contains(k)) ||
                    (j.getDescription()!=null && j.getDescription().toLowerCase().contains(k))
            ).toList();
        }
        if (location != null && !location.isBlank()) {
            String l = location.toLowerCase();
            jobs = jobs.stream().filter(j -> j.getLocation()!=null && j.getLocation().toLowerCase().contains(l)).toList();
        }
        Map<String, Object> content = new HashMap<>();
        content.put("matchedPostings", jobs.size());
        content.put("avgMinSalary", jobs.stream().mapToDouble(JobPosting::getMinSalary).average().orElse(0));
        content.put("avgMaxSalary", jobs.stream().mapToDouble(JobPosting::getMaxSalary).average().orElse(0));
        content.put("note", "Mock/Stub competitive analysis. Replace with real market data sources in future iterations.");
        return new Report("Competitive Market Report (Mock)", content);
    }
}
