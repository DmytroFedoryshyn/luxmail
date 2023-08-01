package luxmail.service;


import java.util.List;
import luxmail.model.Mail;
import luxmail.model.User;

public interface MailService {
    List<Mail> getMails(User user);

    List<Mail> filterMails(User user, String string);

    void deleteMail(User user, Mail mail);

    void sendMail(Mail mail);

    void replyToMail(Mail mail, Long mailId);

    Mail getById(Long id);
}
