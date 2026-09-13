package jobportal.domain.job;
import java.time.LocalDateTime;
public class Review {
    private int id;
    private int ratingScore;
    private String comment;
    private LocalDateTime reviewDate;
    public Review(int id, int ratingScore, String comment) {
        this.id = id;
        this.ratingScore = ratingScore;
        this.comment = comment;
        this.reviewDate = LocalDateTime.now();
    }
    public int getId() { return id; }
    public int getRatingScore() { return ratingScore; }
    public String getComment() { return comment; }
    public LocalDateTime getReviewDate() { return reviewDate; }
}
