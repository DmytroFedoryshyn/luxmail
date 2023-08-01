package luxmail.dto;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import luxmail.model.Mail;
import luxmail.model.User;

@Getter
@Setter
public class MailResponseDto {
    private Long id;
    private User sender;
    private List<User> recipients = new LinkedList<>();
    private String title;
    private String content;
    private Mail replyTo;
    private LocalDateTime timestamp;
}
