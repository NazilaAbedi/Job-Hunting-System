package jobportal.repository;
import jobportal.domain.user.User;
import java.util.*;
public interface UserRepository {
    User save(User user);
    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
}
