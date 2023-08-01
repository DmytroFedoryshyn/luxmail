package luxmail.dto;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailRequestDto {
    private Long senderId;
    private List<String> recipients = new LinkedList<>();
    private String title;
    private String content;
    private Long replyToId;
    private LocalDateTime timestamp;
}
