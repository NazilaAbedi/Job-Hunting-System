package jobportal.service;
import jobportal.domain.common.Report;
import java.nio.charset.StandardCharsets;
public class ReportExportService {
    public byte[] exportAsPdfLikeBytes(Report report) {
        if (report == null) return new byte[0];
        String txt = "=== " + report.getTitle() + " ===\n"
                + "Generated: " + report.getGeneratedDate() + "\n\n"
                + String.valueOf(report.getContent()) + "\n";
        return txt.getBytes(StandardCharsets.UTF_8);
    }
    public String exportAsJson(Report report) {
        if (report == null) return "{}";
        String title = escape(report.getTitle());
        String date = escape(String.valueOf(report.getGeneratedDate()));
        String content = escape(String.valueOf(report.getContent()));
        return "{\n"
                + "  \"title\": \"" + title + "\",\n"
                + "  \"generatedDate\": \"" + date + "\",\n"
                + "  \"content\": \"" + content + "\"\n"
                + "}";
    }
    public String exportAsCsv(Report report) {
        if (report == null) return "";
        return "title,generatedDate,content\n"
                + escapeCsv(report.getTitle()) + ","
                + escapeCsv(String.valueOf(report.getGeneratedDate())) + ","
                + escapeCsv(String.valueOf(report.getContent())) + "\n";
    }
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
    private String escapeCsv(String s) {
        if (s == null) return "";
        String t = s.replace("\"", "\"\"");
        if (t.contains(",") || t.contains("\n")) return "\"" + t + "\"";
        return t;
    }
}
