package luxmail.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    public User() {

    }

    public User(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof User)) return false;
        return ((User) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
