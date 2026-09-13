package jobportal.config;

import jobportal.domain.company.Company;
import jobportal.domain.job.JobPosting;
import jobportal.domain.resume.Resume;
import jobportal.domain.resume.Skill;
import jobportal.domain.user.Employer;
import jobportal.domain.user.JobSeeker;
import jobportal.repository.memory.*;
import jobportal.service.*;

import java.util.List;

/** Lightweight composition root shared by CLI and desktop UI. */
public final class ApplicationContext {
    public final InMemoryUserRepository users = new InMemoryUserRepository();
    public final InMemoryJobPostingRepository jobs = new InMemoryJobPostingRepository();
    public final InMemoryApplicationRepository applications = new InMemoryApplicationRepository();
    public final InMemoryResumeRepository resumes = new InMemoryResumeRepository();

    public final AuthService auth = new AuthService(users);
    public final NotificationService notifications = new NotificationService(users);
    public final JobPostingService jobPostings = new JobPostingService(jobs);
    public final ApplicationService applicationService = new ApplicationService(applications, jobs, users, notifications);
    public final ResumeService resumeService = new ResumeService(resumes);
    public final MatchingService matching = new MatchingService();
    public final AnalyticsService analytics = new AnalyticsService(jobs);
    public final SalarySuggestionService salaries = new SalarySuggestionService(jobs);

    public void seedDemoData() {
        auth.registerAdmin("admin", "admin123", "admin@example.com", "System Admin");

        Company acme = new Company(1, "Acme Labs", "Software", "https://example.com", "Product engineering studio");
        Employer employer = (Employer) auth.registerEmployer("employer", "emp123", "employer@example.com", "Sara Ahmadi", acme);
        JobSeeker seeker = (JobSeeker) auth.registerJobSeeker("seeker", "seek123", "seeker@example.com", "Arman Karimi", 4200);

        Resume resume = resumeService.create(seeker.getId(),
                "Computer Science student focused on backend engineering, clean architecture and data-driven products.",
                List.of(new Skill(1, "Java"), new Skill(2, "OOP"), new Skill(3, "SQL"), new Skill(4, "Git")));
        seeker.addResume(resume);

        JobPosting j1 = jobPostings.create(employer.getId(), "Java Backend Developer",
                "Build reliable APIs and services with Java. Strong OOP fundamentals and SQL are valued.",
                "Tehran · Hybrid", 3500, 5600,
                List.of(new Skill(1, "Java"), new Skill(2, "OOP"), new Skill(3, "SQL")));
        JobPosting j2 = jobPostings.create(employer.getId(), "Junior Data Analyst",
                "Turn product data into clear reports and actionable insights.",
                "Remote", 2800, 4400,
                List.of(new Skill(1, "SQL"), new Skill(2, "Excel"), new Skill(3, "Analytics")));
        JobPosting j3 = jobPostings.create(employer.getId(), "Software Engineering Intern",
                "Work with the engineering team on production features, tests and developer tooling.",
                "Tehran · On-site", 1800, 3000,
                List.of(new Skill(1, "Java"), new Skill(2, "Git")));
        employer.postJob(j1);
        employer.postJob(j2);
        employer.postJob(j3);
    }
}

