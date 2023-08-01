package luxmail.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import luxmail.model.Mail;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MailRowMapper implements RowMapper<Mail> {
    private final SenderRowMapper senderRowMapper;

    public MailRowMapper(SenderRowMapper userRowMapper) {
        this.senderRowMapper = userRowMapper;
    }

    @Override
    public Mail mapRow(ResultSet rs, int rowNum) throws SQLException {
        Mail mail = new Mail();
        mail.setId(rs.getLong("id"));

        mail.setSender(senderRowMapper.mapRow(rs, rowNum));

        mail.setTitle(rs.getString("title"));
        mail.setContent(rs.getString("content"));

        long replyToId = rs.getLong("replyTo_id");
        if (replyToId > 0) {
            Mail replyTo = new Mail();
            replyTo.setId(replyToId);
            mail.setReplyTo(replyTo);
        }

        mail.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());

        return mail;
    }
}
