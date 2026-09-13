package jobportal.domain.user;
import jobportal.service.AnalyticsService;
public class Admin extends User {
    public Admin(int id, String username, String passwordHash, String email, String fullName) {
        super(id, username, passwordHash, email, fullName);
    }
    public void banUser(User user) { if (user != null) user.isActive = false; }
    public void manageSystemSettings() {}
    public Object generateMarketReport(AnalyticsService analyticsService) {
        return analyticsService != null ? analyticsService.getHiringTrends() : null;
    }
}
