package jobportal.interfaces;
import jobportal.domain.common.Notification;
public interface INotifiable {
    void receiveNotification(Notification notification);
}
