package jobportal.domain.resume;
import java.time.LocalDate;
public class Education {
    private String institutionName;
    private String degree;
    private String fieldOfStudy;
    private LocalDate graduationDate;
    public Education(String institutionName, String degree, String fieldOfStudy, LocalDate graduationDate) {
        this.institutionName = institutionName;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.graduationDate = graduationDate;
    }
    public String getInstitutionName() { return institutionName; }
    public String getDegree() { return degree; }
    public String getFieldOfStudy() { return fieldOfStudy; }
    public LocalDate getGraduationDate() { return graduationDate; }
}
