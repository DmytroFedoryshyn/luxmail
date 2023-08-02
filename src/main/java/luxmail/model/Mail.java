package luxmail.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Mail {
    private Long id;
    private User sender;
    private List<User> recipients = new LinkedList<>();
    private String title;
    private String content;
    private Mail replyTo;
    private LocalDateTime timestamp;

    public Mail(Long id) {
        this.id = id;
    }
}
