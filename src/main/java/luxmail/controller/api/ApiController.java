package luxmail.controller.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import luxmail.dto.MailRequestDto;
import luxmail.dto.MailResponseDto;
import luxmail.dto.UserResponseDto;
import luxmail.dto.mapper.impl.MailMapper;
import luxmail.dto.mapper.impl.UserMapper;
import luxmail.model.User;
import luxmail.service.MailService;
import luxmail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final MailService mailService;
    private final UserService userService;
    private final MailMapper mailMapper;
    private final UserMapper userMapper;

    @Autowired
    public ApiController(MailService mailService, UserService userService,
                         MailMapper mailMapper, UserMapper userMapper) {
        this.mailService = mailService;
        this.userService = userService;
        this.mailMapper = mailMapper;
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/mails")
    public List<MailResponseDto> getLatestMails() {
        User user = getAuthenticatedUser();
        return mailService.getMails(user).stream().map(mailMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/mails/{searchString}")
    public List<MailResponseDto> searchMails(@PathVariable String searchString) {
        User user = getAuthenticatedUser();
        return mailService.filterMails(user, searchString).stream().map(mailMapper::toDto).collect(Collectors.toList());
    }

    @DeleteMapping("/mail/{mail_id}")
    public void deleteMail(@PathVariable("mail_id") Long mailId) {
        User user = getAuthenticatedUser();
        mailService.deleteMail(user, mailService.getById(mailId));
    }

    @PostMapping("/mail")
    public void sendMail(@RequestBody MailRequestDto mail) {
        mail.setTimestamp(LocalDateTime.now());
        mailService.sendMail(mailMapper.fromDto(mail));
    }

    @PostMapping("/mail/{mail_id}")
    public void replyToMail(@PathVariable("mail_id") Long mailId, @RequestBody MailRequestDto replyMail) {
        mailService.replyToMail(mailMapper.fromDto(replyMail), mailId);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getByLogin(username);
    }
}
