package luxmail.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import luxmail.dto.MailRequestDto;
import luxmail.dto.MailResponseDto;
import luxmail.dto.UserResponseDto;
import luxmail.dto.mapper.impl.MailMapper;
import luxmail.exception.DataProcessingException;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
    private final MailService mailService;
    private final UserService userService;
    private final MailMapper mailMapper;

    @Autowired
    public MailController(MailService mailService, UserService userService,
                          MailMapper mailMapper) {
        this.mailService = mailService;
        this.userService = userService;
        this.mailMapper = mailMapper;
    }

    @GetMapping("/mails")
    public List<MailResponseDto> getLatestMails() {
        User user = getAuthenticatedUser();
        return mailService.getMails(user).stream().map(mailMapper::toDto).collect(Collectors.toList());
    }

    @DeleteMapping("/mail/{mail_id}")
    public void deleteMail(@PathVariable("mail_id") Long mailId) {
        User user = getAuthenticatedUser();
        mailService.deleteMail(user, mailService.getById(mailId));
    }

    @PostMapping("/mails/{searchString}")
    public List<MailResponseDto> searchMails(@PathVariable String searchString) {
        User user = getAuthenticatedUser();
        return mailService.filterMails(user, searchString).stream().map(mailMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/mail")
    public boolean sendMail(@RequestBody MailRequestDto mail) {
        for (String recipientName : mail.getRecipients()) {
            try {
                userService.getByLogin(recipientName);
            } catch (DataProcessingException e) {
                return false;
            }
        }

        mail.setTimestamp(LocalDateTime.now());
        mailService.sendMail(mailMapper.fromDto(mail));

        return true;
    }
    
    @GetMapping("/api/mails")
    public List<MailResponseDto> getLatestMailsApi() {
        User user = getAuthenticatedUser();
        return mailService.getMails(user).stream().map(mailMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/api/mails/{searchString}")
    public List<MailResponseDto> searchMailsApi(@PathVariable String searchString) {
        User user = getAuthenticatedUser();
        return mailService.filterMails(user, searchString).stream().map(mailMapper::toDto).collect(Collectors.toList());
    }

    @DeleteMapping("/api/mail/{mail_id}")
    public void deleteMailApi(@PathVariable("mail_id") Long mailId) {
        User user = getAuthenticatedUser();
        mailService.deleteMail(user, mailService.getById(mailId));
    }

    @PostMapping("/api/mail")
    public void sendMailApi(@RequestBody MailRequestDto mail) {
        mail.setTimestamp(LocalDateTime.now());
        mailService.sendMail(mailMapper.fromDto(mail));
    }

    @PostMapping("/api/mail/{mail_id}")
    public void replyToMailApi(@PathVariable("mail_id") Long mailId, @RequestBody MailRequestDto replyMail) {
        replyMail.setReplyToId(mailId);
        mailService.sendMail(mailMapper.fromDto(replyMail));
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getByLogin(username);
    }
}
