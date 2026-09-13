package jobportal.repository;
import jobportal.domain.resume.Resume;
import java.util.*;
public interface ResumeRepository {
    Resume save(int ownerId, Resume resume);
    Optional<Resume> findById(int ownerId, int resumeId);
    List<Resume> findAllByOwner(int ownerId);
}
