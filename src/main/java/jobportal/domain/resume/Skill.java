package jobportal.domain.resume;
import java.util.Objects;
public class Skill {
    private int id;
    private String name;
    public Skill(int id, String name) {
        this.id = id;
        this.name = name == null ? "" : name.trim();
    }
    public int getId() { return id; }
    public String getName() { return name; }
    @Override public String toString() { return name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;
        Skill s = (Skill)o;
        return name.equalsIgnoreCase(s.name);
    }
    @Override public int hashCode() { return Objects.hash(name.toLowerCase()); }
}
