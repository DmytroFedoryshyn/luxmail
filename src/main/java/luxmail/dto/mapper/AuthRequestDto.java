package luxmail.dto.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    private String login;
    private String password;
}
