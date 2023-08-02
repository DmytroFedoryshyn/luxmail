package luxmail.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import luxmail.dto.UserRequestDto;
import luxmail.dto.UserResponseDto;
import luxmail.dto.mapper.impl.UserMapper;
import luxmail.model.User;
import luxmail.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @RequestMapping("/user")
    Principal getUser(Principal user) {
        return user;
    }

    @GetMapping("/userByLogin/{login}")
    UserResponseDto getUserByLogin(@PathVariable String login) {
        User user = userService.getByLogin(login);
        return userMapper.toDto(user);
    }

    @PutMapping("/user/{id}")
    UserResponseDto updateUser(@RequestBody UserRequestDto dto, @PathVariable Long id) {
        User user = userMapper.fromDto(dto);
        user.setId(id);
        return userMapper.toDto(userService.update(user));
    }

    @GetMapping("/api/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
