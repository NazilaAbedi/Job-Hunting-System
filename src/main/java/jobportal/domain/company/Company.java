package jobportal.domain.company;
public class Company {
    private int id;
    private String name;
    private String industry;
    private String website;
    private String description;
    public Company(int id, String name, String industry, String website, String description) {
        this.id = id;
        this.name = name;
        this.industry = industry;
        this.website = website;
        this.description = description;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getIndustry() { return industry; }
    public String getWebsite() { return website; }
    public String getDescription() { return description; }
    @Override public String toString() { return name + " (" + industry + ")"; }
}
