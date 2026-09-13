package jobportal.service;
import jobportal.domain.job.Review;
public class SentimentAnalysisService {
    public enum Sentiment { POSITIVE, NEGATIVE, NEUTRAL }
    public Sentiment analyze(Review review) {
        if (review == null || review.getComment() == null) return Sentiment.NEUTRAL;
        return analyzeText(review.getComment());
    }
    public Sentiment analyzeText(String text) {
        if (text == null || text.isBlank()) return Sentiment.NEUTRAL;
        String t = text.toLowerCase();
        int score = 0;
        if (t.contains("good") || t.contains("great") || t.contains("excellent") || t.contains("nice")) score += 2;
        if (t.contains("bad") || t.contains("poor") || t.contains("terrible") || t.contains("awful")) score -= 2;
        if (t.contains("fast") || t.contains("helpful")) score += 1;
        if (t.contains("slow") || t.contains("unhelpful")) score -= 1;
        if (score >= 2) return Sentiment.POSITIVE;
        if (score <= -2) return Sentiment.NEGATIVE;
        return Sentiment.NEUTRAL;
    }
}
