package jobportal.service;
import jobportal.domain.company.Company;
import jobportal.domain.user.Admin;
import jobportal.domain.user.Employer;
import jobportal.domain.user.JobSeeker;
import jobportal.domain.user.User;
import jobportal.repository.UserRepository;
import java.util.Optional;
public class AuthService {
    private final UserRepository userRepo;
    //CONSTRUCTOR
    public AuthService(UserRepository userRepo) { this.userRepo = userRepo; }
    //METHODS
    public User registerJobSeeker(String username, String passwordPlain, String email, String fullName, double salaryExpectation) {
        ensureRegistrationInput(username, passwordPlain, email, fullName);
        ensureUnique(username);
        JobSeeker js = new JobSeeker(0, username, User.hash(passwordPlain), email, fullName, salaryExpectation);
        return userRepo.save(js);
    }
    public User registerEmployer(String username, String passwordPlain, String email, String fullName, Company company) {
        ensureRegistrationInput(username, passwordPlain, email, fullName);
        if (company == null) throw new IllegalArgumentException("Company is required.");
        ensureUnique(username);
        Employer em = new Employer(0, username, User.hash(passwordPlain), email, fullName, company);
        return userRepo.save(em);
    }
    public User registerAdmin(String username, String passwordPlain, String email, String fullName) {
        ensureRegistrationInput(username, passwordPlain, email, fullName);
        ensureUnique(username);
        Admin a = new Admin(0, username, User.hash(passwordPlain), email, fullName);
        return userRepo.save(a);
    }
    public Optional<User> login(String username, String passwordPlain) {
        if (username == null) return Optional.empty();
        return userRepo.findByUsername(username)
                .filter(u -> u.isActive())
                .filter(u -> u.login(passwordPlain));
    }
    private void ensureRegistrationInput(String username, String passwordPlain, String email, String fullName) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username is required.");
        if (passwordPlain == null || passwordPlain.length() < 6) throw new IllegalArgumentException("Password must be at least 6 characters.");
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) throw new IllegalArgumentException("A valid email is required.");
        if (fullName == null || fullName.isBlank()) throw new IllegalArgumentException("Full name is required.");
    }

    private void ensureUnique(String username) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username is required.");
        if (userRepo.findByUsername(username).isPresent()) throw new IllegalArgumentException("Username already exists.");
    }
}
