package luxmail.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatPassword;
    private String role;
}
