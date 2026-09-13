package jobportal.service;
import jobportal.domain.common.Notification;
import jobportal.domain.user.User;
import jobportal.repository.UserRepository;
public class NotificationService {
    private final UserRepository userRepo;
    public NotificationService(UserRepository userRepo) { this.userRepo = userRepo; }
    public void notifyUser(int userId, String message) {
        userRepo.findById(userId).ifPresent(u -> u.receiveNotification(new Notification(message)));
    }
    public void notifyUser(User user, String message) {
        if (user != null) user.receiveNotification(new Notification(message));
    }
}
