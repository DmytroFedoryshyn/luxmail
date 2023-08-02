package luxmail.dao;


import java.util.List;
import luxmail.model.Mail;
import luxmail.model.User;

public interface MailDao {
    List<Mail> getMails(User user);

    List<Mail> filterMails(User user, String string);

    void deleteMail(User user, Mail mail);

    void sendMail(Mail mail);

    Mail getById(Long id);
}
