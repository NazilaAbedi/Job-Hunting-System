package jobportal.domain.common;
import java.time.LocalDateTime;
public class Report {
    private final String title;
    private final LocalDateTime generatedDate;
    private final Object content;
    public Report(String title, Object content) {
        this.title = title;
        this.generatedDate = LocalDateTime.now();
        this.content = content;
    }
    public String getTitle() { return title; }
    public LocalDateTime getGeneratedDate() { return generatedDate; }
    public Object getContent() { return content; }
    @Override public String toString() {
        return "Report{title='" + title + "', generatedDate=" + generatedDate + ", content=" + content + "}";
    }
}
