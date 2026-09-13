package jobportal.domain.job;
import java.time.LocalDateTime;
public class ApplicationHistory {
    private final LocalDateTime changeDate;
    private final ApplicationStatus status;
    private final String notes;
    public ApplicationHistory(ApplicationStatus status, String notes) {
        this.changeDate = LocalDateTime.now();
        this.status = status;
        this.notes = notes;
    }
    public LocalDateTime getChangeDate() { return changeDate; }
    public ApplicationStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    @Override public String toString() {
        return changeDate + " -> " + status + (notes==null || notes.isBlank() ? "" : " (" + notes + ")");
    }
}
