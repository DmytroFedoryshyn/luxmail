package luxmail.dto.mapper.impl;

import java.util.stream.Collectors;
import luxmail.dto.MailRequestDto;
import luxmail.dto.MailResponseDto;
import luxmail.dto.mapper.DtoMapper;
import luxmail.model.Mail;
import luxmail.service.MailService;
import luxmail.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class MailMapper implements DtoMapper<Mail, MailRequestDto, MailResponseDto> {
    private final UserService userService;
    private final MailService mailService;

    public MailMapper(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @Override
    public MailResponseDto toDto(Mail entity) {
        MailResponseDto dto = new MailResponseDto();
        dto.setId(entity.getId());
        dto.setSender(entity.getSender());
        dto.setRecipients(entity.getRecipients());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setReplyTo(entity.getReplyTo());
        dto.setTimestamp(entity.getTimestamp());

        return dto;
    }

    @Override
    public Mail fromDto(MailRequestDto dto) {
        Mail mail = new Mail();
        mail.setSender(userService.getById(dto.getSenderId()));
        mail.setRecipients(dto.getRecipients().stream().map(userService::getByLogin).collect(Collectors.toList()));
        mail.setTitle(dto.getTitle());
        mail.setContent(dto.getContent());
        if (dto.getReplyToId() != null) {
            mail.setReplyTo(mailService.getById(dto.getReplyToId()));
        }
        mail.setTimestamp(dto.getTimestamp());

        return mail;
    }
}
