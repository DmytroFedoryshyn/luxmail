package luxmail.dto.mapper.impl;

import luxmail.dto.UserRequestDto;
import luxmail.dto.UserResponseDto;
import luxmail.dto.mapper.DtoMapper;
import luxmail.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements DtoMapper<User, UserRequestDto, UserResponseDto> {
    @Override
    public UserResponseDto toDto(User entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setEmail(entity.getEmail());

        return dto;
    }

    @Override
    public User fromDto(UserRequestDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());

        return user;
    }
}
