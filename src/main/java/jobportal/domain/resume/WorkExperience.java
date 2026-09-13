package jobportal.domain.resume;
import java.time.LocalDate;
public class WorkExperience {
    private String companyName;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String responsibilities;
    public WorkExperience(String companyName, String position, LocalDate startDate, LocalDate endDate, String responsibilities) {
        this.companyName = companyName;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.responsibilities = responsibilities;
    }
    public String getCompanyName() { return companyName; }
    public String getPosition() { return position; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getResponsibilities() { return responsibilities; }
}
