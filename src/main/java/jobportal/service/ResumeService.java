package jobportal.service;
import jobportal.domain.resume.Resume;
import jobportal.domain.resume.Skill;
import jobportal.repository.ResumeRepository;
import java.util.List;
public class ResumeService {
    private final ResumeRepository resumeRepo;
    public ResumeService(ResumeRepository resumeRepo) { this.resumeRepo = resumeRepo; }
    public Resume create(int ownerId, String summary, List<Skill> skills) {
        Resume r = new Resume(0, summary);
        if (skills != null) for (Skill s : skills) r.addSkill(s);
        return resumeRepo.save(ownerId, r);
    }
    public List<Resume> list(int ownerId) { return resumeRepo.findAllByOwner(ownerId); }
}
