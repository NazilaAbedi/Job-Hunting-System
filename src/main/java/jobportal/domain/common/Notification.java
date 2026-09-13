package jobportal.domain.common;
import java.time.LocalDateTime;
public class Notification {
    private final String message;
    private final LocalDateTime sentDate;
    public Notification(String message) {
        this.message = message;
        this.sentDate = LocalDateTime.now();
    }
    public String getMessage() { return message; }
    public LocalDateTime getSentDate() { return sentDate; }
    @Override public String toString() { return "[" + sentDate + "] " + message; }
}
