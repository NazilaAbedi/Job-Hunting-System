package jobportal.presentation.cli;
import jobportal.domain.company.Company;
import jobportal.domain.job.Application;
import jobportal.domain.job.ApplicationStatus;
import jobportal.domain.job.JobPosting;
import jobportal.domain.resume.Resume;
import jobportal.domain.resume.Skill;
import jobportal.domain.user.Admin;
import jobportal.domain.user.Employer;
import jobportal.domain.user.JobSeeker;
import jobportal.domain.user.User;
import jobportal.repository.memory.*;
import jobportal.service.*;
import java.util.*;
public class App {
    private final InMemoryUserRepository userRepo = new InMemoryUserRepository();
    private final InMemoryJobPostingRepository jobRepo = new InMemoryJobPostingRepository();
    private final InMemoryApplicationRepository appRepo = new InMemoryApplicationRepository();
    private final InMemoryResumeRepository resumeRepo = new InMemoryResumeRepository();
    private final AuthService authService = new AuthService(userRepo);
    private final NotificationService notificationService = new NotificationService(userRepo);
    private final JobPostingService jobPostingService = new JobPostingService(jobRepo);
    private final ApplicationService applicationService = new ApplicationService(appRepo, jobRepo, userRepo, notificationService);
    private final ResumeService resumeService = new ResumeService(resumeRepo);
    private final MatchingService matchingService = new MatchingService();
    private final AnalyticsService analyticsService = new AnalyticsService(jobRepo);
    private final SalarySuggestionService salarySuggestionService = new SalarySuggestionService(jobRepo);
    private final ResumeTemplateService resumeTemplateService = new ResumeTemplateService();
    private final ReportExportService reportExportService = new ReportExportService();
    private final SentimentAnalysisService sentimentAnalysisService = new SentimentAnalysisService();
    private final CompetitiveMarketAnalysisService competitiveMarketAnalysisService = new CompetitiveMarketAnalysisService(jobRepo);
    private final Scanner sc = new Scanner(System.in);
    public void seed() {
        authService.registerAdmin("admin", "admin123", "admin@example.com", "System Admin");
        Company c = new Company(1, "Acme Corp", "Software", "https", "");
        Employer em = (Employer) authService.registerEmployer("employer", "emp123", "employer@example.com", "Employer One", c);
        JobSeeker js = (JobSeeker) authService.registerJobSeeker("seeker", "seek123", "seeker@example.com", "Job Seeker", 3500);
        List<Skill> skills = List.of(new Skill(1, "Java"), new Skill(2, "OOP"), new Skill(3, "SQL"));
        Resume r = resumeService.create(js.getId(), "Computer Science student.", skills);
        js.addResume(r);
        JobPosting j1 = jobPostingService.create(em.getId(), "Java Developer", "Backend Java role", "Tehran", 3000, 5000, List.of(new Skill(1,"Java"), new Skill(2,"OOP")));
        JobPosting j2 = jobPostingService.create(em.getId(), "Data Analyst", "SQL + reporting", "Tehran", 2500, 4200, List.of(new Skill(1,"SQL")));
        em.postJob(j1); em.postJob(j2);
    }
    public void start() {
        System.out.println("=== Job Portal (CLI) ===");
        while (true) {
            System.out.println("\n1) Login  2) Register JobSeeker  3) Register Employer  4) Register Admin  0) Exit");
            System.out.print("> ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1" -> loginFlow();
                case "2" -> registerJobSeekerFlow();
                case "3" -> registerEmployerFlow();
                case "4" -> registerAdminFlow();
                case "0" -> { System.out.println("Bye."); return; }
                default -> System.out.println("Invalid.");
            }
        }
    }
    private void loginFlow() {
        System.out.print("Username: ");
        String u = sc.nextLine().trim();
        System.out.print("Password: ");
        String p = sc.nextLine();
        Optional<User> user = authService.login(u, p);
        if (user.isEmpty()) { System.out.println("Login failed."); return; }
        User me = user.get();
        System.out.println("Logged in as: " + me);
        if (me instanceof JobSeeker js) jobSeekerMenu(js);
        else if (me instanceof Employer em) employerMenu(em);
        else if (me instanceof Admin ad) adminMenu(ad);
        else System.out.println("Unknown role.");
    }
    private void jobSeekerMenu(JobSeeker js) {
        while (true) {
            System.out.println("\n[JobSeeker] 1) List Jobs 2) Search Jobs 3) Create Resume 4) My Resumes 5) Apply 6) My Applications 7) Inbox 8) Salary Suggestion 9) Export Resume 0) Logout");
            System.out.print("> ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1" -> listJobs();
                case "2" -> searchJobs();
                case "3" -> createResume(js);
                case "4" -> listResumes(js);
                case "5" -> apply(js);
                case "6" -> listMyApplications(js);
                case "7" -> showInbox(js);
                case "8" -> salarySuggestion(js);
                case "9" -> exportResume(js);
                case "0" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }
    private void employerMenu(Employer em) {
        while (true) {
            System.out.println("\n[Employer] 1) Post Job 2) My Jobs 3) View Applications 4) Update Application Status 5) Inbox 0) Logout");
            System.out.print("> ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1" -> postJob(em);
                case "2" -> listMyJobs(em);
                case "3" -> viewApplications(em);
                case "4" -> updateApplicationStatus(em);
                case "5" -> showInbox(em);
                case "0" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }
    private void adminMenu(Admin ad) {
        while (true) {
            System.out.println("\n[Admin] 1) Hiring Trends 2) Top Skills 3) Salary Report 4) Ban User 5) Inbox 6) Diversity Report 7) Competitive Report (Mock) 8) Sentiment Demo (Stub) 0) Logout");
            System.out.print("> ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1" -> System.out.println(analyticsService.getHiringTrends());
                case "2" -> System.out.println(analyticsService.getTopDemandedSkills());
                case "3" -> salaryReport();
                case "4" -> banUser(ad);
                case "5" -> showInbox(ad);
                case "6" -> System.out.println(analyticsService.getWorkforceDiversityReport(userRepo));
                case "7" -> competitiveReport();
                case "8" -> sentimentDemo();
                case "0" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }
    private void listJobs() {
        System.out.println("\nActive Jobs:");
        for (JobPosting j : jobPostingService.listActive()) System.out.println(j);
    }
    private void searchJobs() {
        System.out.print("Keyword: ");
        String k = sc.nextLine();
        List<JobPosting> results = jobPostingService.search(k);
        System.out.println("Results (" + results.size() + "):");
        for (JobPosting j : results) System.out.println(j);
    }
    private void createResume(JobSeeker js) {
        System.out.print("Summary: ");
        String summary = sc.nextLine();
        System.out.print("Skills (comma-separated): ");
        String skillsLine = sc.nextLine();
        List<Skill> skills = parseSkills(skillsLine);
        Resume r = resumeService.create(js.getId(), summary, skills);
        js.addResume(r);
        System.out.println("Created resume: " + r);
    }
    private void listResumes(JobSeeker js) {
        List<Resume> rs = resumeService.list(js.getId());
        if (rs.isEmpty()) { System.out.println("No resumes."); return; }
        for (Resume r : rs) System.out.println(r);
    }
    private void apply(JobSeeker js) {
        System.out.print("JobId: ");
        int jobId = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Cover letter (optional): ");
        String cl = sc.nextLine();
        Application app = applicationService.apply(js.getId(), jobId, cl);
        System.out.println("Applied: " + app);
        List<Resume> rs = resumeService.list(js.getId());
        if (!rs.isEmpty()) {
            JobPosting job = jobRepo.findById(jobId).orElse(null);
            if (job != null) {
                double score = matchingService.calculateMatchScore(js, job, rs.get(0));
                System.out.println("MatchScore (Resume#" + rs.get(0).getId() + "): " + score);
            }
        }
    }
    private void listMyApplications(JobSeeker js) {
        List<Application> apps = applicationService.listBySeeker(js.getId());
        if (apps.isEmpty()) { System.out.println("No applications."); return; }
        for (Application a : apps) {
            System.out.println(a);
            System.out.println("  History: " + a.getHistory());
        }
    }
    private void postJob(Employer em) {
        System.out.print("Title: "); String title = sc.nextLine();
        System.out.print("Description: "); String desc = sc.nextLine();
        System.out.print("Location: "); String loc = sc.nextLine();
        System.out.print("MinSalary: "); double min = Double.parseDouble(sc.nextLine().trim());
        System.out.print("MaxSalary: "); double max = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Required skills (comma-separated): ");
        List<Skill> skills = parseSkills(sc.nextLine());
        JobPosting job = jobPostingService.create(em.getId(), title, desc, loc, min, max, skills);
        em.postJob(job);
        System.out.println("Posted: " + job);
    }
    private void listMyJobs(Employer em) {
        for (JobPosting j : jobRepo.findAll()) if (j.getEmployerId() == em.getId()) System.out.println(j);
    }
    private void viewApplications(Employer em) {
        System.out.print("JobId: ");
        int jobId = Integer.parseInt(sc.nextLine().trim());
        List<Application> apps = applicationService.listByJob(em.getId(), jobId);
        System.out.println("Applications (" + apps.size() + "):");
        for (Application a : apps) {
            System.out.println(a);
            System.out.println("  History: " + a.getHistory());
        }
    }
    private void updateApplicationStatus(Employer em) {
        System.out.print("ApplicationId: ");
        int appId = Integer.parseInt(sc.nextLine().trim());
        System.out.println("Statuses: SUBMITTED, VIEWED, UNDER_REVIEW, INTERVIEW_SCHEDULED, OFFERED, HIRED, REJECTED");
        System.out.print("NewStatus: ");
        ApplicationStatus st = ApplicationStatus.valueOf(sc.nextLine().trim().toUpperCase());
        System.out.print("Notes: ");
        String notes = sc.nextLine();
        applicationService.updateStatus(em.getId(), appId, st, notes);
        System.out.println("Updated.");
    }
    private void salaryReport() {
        System.out.print("Skill (optional): ");
        String s = sc.nextLine();
        System.out.print("Location (optional): ");
        String l = sc.nextLine();
        System.out.println(analyticsService.generateSalaryReport(s, l));
    }
    private void banUser(Admin ad) {
        System.out.print("Username to ban: ");
        String u = sc.nextLine().trim();
        userRepo.findByUsername(u).ifPresentOrElse(user -> {
            ad.banUser(user);
            System.out.println("Banned: " + user);
        }, () -> System.out.println("User not found."));
    }
    private void showInbox(User u) {
        if (u.getInbox().isEmpty()) { System.out.println("Inbox is empty."); return; }
        System.out.println("Inbox:");
        for (var n : u.getInbox()) System.out.println(" - " + n);
    }
    private void registerJobSeekerFlow() {
        System.out.print("Username: "); String u = sc.nextLine().trim();
        System.out.print("Password: "); String p = sc.nextLine();
        System.out.print("Email: "); String e = sc.nextLine().trim();
        System.out.print("Full name: "); String fn = sc.nextLine().trim();
        System.out.print("Salary expectation: "); double se = Double.parseDouble(sc.nextLine().trim());
        User user = authService.registerJobSeeker(u, p, e, fn, se);
        System.out.println("Registered: " + user);
    }
    private void registerEmployerFlow() {
        System.out.print("Username: "); String u = sc.nextLine().trim();
        System.out.print("Password: "); String p = sc.nextLine();
        System.out.print("Email: "); String e = sc.nextLine().trim();
        System.out.print("Full name: "); String fn = sc.nextLine().trim();
        System.out.print("Company name: "); String cn = sc.nextLine().trim();
        System.out.print("Industry: "); String ind = sc.nextLine().trim();
        System.out.print("Website: "); String web = sc.nextLine().trim();
        System.out.print("Description: "); String desc = sc.nextLine().trim();
        Company c = new Company(0, cn, ind, web, desc);
        User user = authService.registerEmployer(u, p, e, fn, c);
        System.out.println("Registered: " + user);
    }
    private void registerAdminFlow() {
        System.out.print("Username: "); String u = sc.nextLine().trim();
        System.out.print("Password: "); String p = sc.nextLine();
        System.out.print("Email: "); String e = sc.nextLine().trim();
        System.out.print("Full name: "); String fn = sc.nextLine().trim();
        User user = authService.registerAdmin(u, p, e, fn);
        System.out.println("Registered: " + user);
    }
    private void salarySuggestion(JobSeeker js) {
        List<Resume> rs = resumeService.list(js.getId());
        if (rs.isEmpty()) { System.out.println("No resumes. Create a resume first."); return; }
        System.out.print("Location keyword (optional): ");
        String loc = sc.nextLine();
        var suggestion = salarySuggestionService.suggestSalary(rs.get(0), loc);
        System.out.println(suggestion);
    }
    private void exportResume(JobSeeker js) {
        List<Resume> rs = resumeService.list(js.getId());
        if (rs.isEmpty()) { System.out.println("No resumes."); return; }
        byte[] bytes = resumeTemplateService.generate(rs.get(0), ResumeTemplateService.Template.STANDARD_V1);
        System.out.println("\n--- Resume Export (Template STANDARD_V1) ---");
        System.out.println(new String(bytes, java.nio.charset.StandardCharsets.UTF_8));
        System.out.println("--- End ---\n");
    }
    private void competitiveReport() {
        System.out.print("Keyword (optional): ");
        String k = sc.nextLine();
        System.out.print("Location (optional): ");
        String l = sc.nextLine();
        var rep = competitiveMarketAnalysisService.generateCompetitiveReport(k, l);
        System.out.println(rep);
        System.out.println("JSON export:");
        System.out.println(reportExportService.exportAsJson(rep));
    }
    private void sentimentDemo() {
        System.out.print("Enter a review text: ");
        String txt = sc.nextLine();
        var sentiment = sentimentAnalysisService.analyzeText(txt);
        System.out.println("Sentiment (stub): " + sentiment);
    }
    private List<Skill> parseSkills(String line) {
        List<Skill> out = new ArrayList<>();
        if (line == null || line.isBlank()) return out;
        String[] parts = line.split(",");
        int id = 1;
        for (String s : parts) {
            String name = s.trim();
            if (!name.isBlank()) out.add(new Skill(id++, name));
        }
        return out;
    }
}
