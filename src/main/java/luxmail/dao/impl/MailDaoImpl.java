package luxmail.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import luxmail.dao.MailDao;
import luxmail.dao.rowMapper.MailRowMapper;
import luxmail.dao.rowMapper.UserRowMapper;
import luxmail.exception.DataProcessingException;
import luxmail.model.Mail;
import luxmail.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class MailDaoImpl implements MailDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert mailInsert;
    private final MailRowMapper mailRowMapper;
    private final UserRowMapper userRowMapper;


    @Autowired
    public MailDaoImpl(JdbcTemplate jdbcTemplate, MailRowMapper mailRowMapper, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mailInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("mails")
                .usingGeneratedKeyColumns("id");
        this.mailRowMapper = mailRowMapper;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<Mail> getMails(User user) {
        String sql = "SELECT mails.*, " +
                "recipients.*, " +
                "senders.id as sender_id, senders.firstName as sender_firstname, senders.lastName as sender_lastname, " +
                "senders.email as sender_email, senders.password as sender_password, senders.role as sender_role " +
                "FROM mails " +
                "LEFT JOIN mail_recipients recipients ON mails.id = recipients.mail_id AND recipients.recipient_id = ? " +
                "LEFT JOIN users senders ON mails.sender_id = senders.id " +
                "WHERE (mails.senderdeleted = FALSE AND mails.sender_id = ?) OR (recipients.recipient_id = ? AND recipients.deleted = FALSE) " +
                "LIMIT 20";


        List<Mail> mails = jdbcTemplate.query(sql, new Object[]{user.getId(), user.getId(), user.getId()}, mailRowMapper);
        for (Mail mail : mails) {
            mail.setRecipients(fetchRecipientsForMail(mail.getId()));
        }

        return mails;
    }

    @Override
    public List<Mail> filterMails(User user, String searchString) {
        searchString = searchString.toLowerCase();
        searchString = "%" + searchString;
        searchString += "%";

        String sql = "SELECT mails.*, " +
                "recipients.*, " +
                "senders.id as sender_id, senders.firstName as sender_firstname, senders.lastName as sender_lastname, " +
                "senders.email as sender_email, senders.password as sender_password, senders.role as sender_role " +
                "FROM mails " +
                "LEFT JOIN mail_recipients recipients ON mails.id = recipients.mail_id AND recipients.recipient_id = ? " +
                "LEFT JOIN users senders ON mails.sender_id = senders.id " +
                "WHERE ((mails.senderdeleted = FALSE AND mails.sender_id = ?) OR (recipients.recipient_id = ? AND recipients.deleted = FALSE)) " +
                "AND (LOWER(mails.title) LIKE ? OR LOWER(mails.content) LIKE ?) " +
                "LIMIT 20";

        List<Mail> mails = jdbcTemplate.query(sql, new Object[]{user.getId(), user.getId(), user.getId(), searchString, searchString}, mailRowMapper);
        for (Mail mail : mails) {
            mail.setRecipients(fetchRecipientsForMail(mail.getId()));
        }

        return mails;
    }


    @Override
    public void deleteMail(User user, Mail mail) {
        String sqlSender = "UPDATE mails SET senderdeleted = TRUE WHERE id = ? AND sender_id = ?";
        String sqlRecipient = "UPDATE mail_recipients SET deleted = TRUE WHERE mail_id = ? AND recipient_id = ?";

        if (mail.getSender().getId().equals(user.getId())) {
            jdbcTemplate.update(sqlSender, mail.getId(), user.getId());
        }

        if (mail.getRecipients().contains(user)) {
            jdbcTemplate.update(sqlRecipient, mail.getId(), user.getId());
        }
    }

    @Override
    public void sendMail(Mail mail) {
        String sqlRecipient = "INSERT INTO mail_recipients (mail_id, recipient_id) VALUES (?, ?)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sender_id", mail.getSender().getId());
        parameters.put("title", mail.getTitle());
        parameters.put("content", mail.getContent());
        parameters.put("replyTo_id", (mail.getReplyTo() != null) ? mail.getReplyTo().getId() : null);
        parameters.put("timestamp", Timestamp.valueOf(mail.getTimestamp()));
        parameters.put("senderDeleted", false);

        Long mailId = mailInsert.executeAndReturnKey(parameters).longValue();

        for (User recipient : mail.getRecipients()) {
            jdbcTemplate.update(sqlRecipient, mailId, recipient.getId());
        }
    }


    @Override
    public void replyToMail(Mail mail, Long mailId) {

    }

    @Override
    public Mail getById(Long id) {
        String sql = "SELECT mails.*, " +
                "recipients.*, " +
                "senders.id as sender_id, senders.firstName as sender_firstname, senders.lastName as sender_lastname, " +
                "senders.email as sender_email, senders.password as sender_password, senders.role as sender_role " +
                "FROM mails " +
                "LEFT JOIN mail_recipients recipients ON mails.id = recipients.mail_id " +
                "LEFT JOIN users senders ON mails.sender_id = senders.id " +
                "WHERE mails.id = ? " +
                "LIMIT 1";

        try {

            Mail mail = jdbcTemplate.queryForObject(sql, new Object[]{id}, mailRowMapper);
            mail.setRecipients(fetchRecipientsForMail(mail.getId()));
            return mail;
        } catch (Exception e) {
            throw new DataProcessingException("Could not retrieve mail by ID", e);
        }
    }

    private List<User> fetchRecipientsForMail(Long mailId) {
        String sql = "SELECT u.*, mr.* FROM users u " +
                "JOIN mail_recipients mr ON u.id = mr.recipient_id " +
                "WHERE mr.mail_id = ?";
        return jdbcTemplate.query(sql, new Object[]{mailId}, userRowMapper);
    }
}
