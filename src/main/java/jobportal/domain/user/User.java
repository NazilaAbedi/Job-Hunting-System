package jobportal.domain.user;
import jobportal.domain.common.Notification;
import jobportal.interfaces.INotifiable;
import jobportal.interfaces.IRankable;
import java.time.LocalDateTime;
import java.util.*;
public abstract class User implements INotifiable, IRankable {
    protected int id;
    protected String username;
    protected String passwordHash;
    protected String email;
    protected String fullName;
    protected LocalDateTime registrationDate;
    protected boolean isActive = true;
    protected double rank = 0.0;
    protected final List<Notification> inbox = new ArrayList<>();
    //CONSTRUCTOR
    protected User(int id, String username, String passwordHash, String email, String fullName) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.fullName = fullName;
        this.registrationDate = LocalDateTime.now();
    }
    //METHODS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public boolean isActive() { return isActive; }
    public boolean login(String passwordPlain) {
        return passwordHash != null && passwordHash.equals(hash(passwordPlain));
    }
    public void logout() {}
    public void updateProfile(String newEmail, String newFullName) {
        if (newEmail != null && !newEmail.isBlank()) this.email = newEmail.trim();
        if (newFullName != null && !newFullName.isBlank()) this.fullName = newFullName.trim();
    }
    public void changePassword(String oldPlain, String newPlain) {
        if (!login(oldPlain)) throw new IllegalArgumentException("Old password is incorrect.");
        this.passwordHash = hash(newPlain);
    }
    public List<Notification> getInbox() { return Collections.unmodifiableList(inbox); }
    //IMPLEMENTING INOTIFIABLE INTERFACE:
    public void receiveNotification(Notification notification) {
        if (notification != null) {
            inbox.add(notification);
            System.out.printf
                    ("New notification: %s\nDate of notification: %s\n", notification.getMessage(), notification.getSentDate());
        }
        else
            System.out.println("Notification is null!");
    }
    //IMPLEMENTING IRANKABLE INTERFACE:
    public double getRank() { return rank; }
    public void updateRank(double newRank) { this.rank = newRank; }
    //
    public static String hash(String plain) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] out = md.digest((plain == null ? "" : plain).getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : out) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hashing failed.", e);
        }
    }
    @Override public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", username='" + username + "', email='" + email + "', rank=" + rank + "}";
    }
}
