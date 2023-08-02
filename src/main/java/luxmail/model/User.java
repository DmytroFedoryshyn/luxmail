package luxmail.model;

import lombok.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @NonNull
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

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
