package jobportal.repository.memory;
import jobportal.domain.user.User;
import jobportal.repository.UserRepository;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> byId = new HashMap<>();
    private final Map<String, Integer> byUsername = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1000);
    //METHODS
    @Override public User save(User user) {
        if (user == null) throw new IllegalArgumentException("user is null");
        if (user.getId() == 0) user.setId(seq.incrementAndGet());
        byId.put(user.getId(), user);
        byUsername.put(user.getUsername().toLowerCase(), user.getId());
        return user;
    }
    @Override public Optional<User> findById(int id) { return Optional.ofNullable(byId.get(id)); }
    @Override public Optional<User> findByUsername(String username) {
        if (username == null) return Optional.empty();
        Integer id = byUsername.get(username.toLowerCase());
        return id == null ? Optional.empty() : findById(id);
    }
    @Override public List<User> findAll() { return new ArrayList<>(byId.values()); }
}
