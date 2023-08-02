package luxmail.service.impl;

import java.util.List;
import luxmail.dao.MailDao;
import luxmail.model.Mail;
import luxmail.model.User;
import luxmail.service.MailService;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private final MailDao mailDao;

    public MailServiceImpl(MailDao mailDao) {
        this.mailDao = mailDao;
    }

    @Override
    public List<Mail> getMails(User user) {
        return mailDao.getMails(user);
    }

    @Override
    public List<Mail> filterMails(User user, String string) {
        return mailDao.filterMails(user, string);
    }

    @Override
    public void deleteMail(User user, Mail mail) {
        mailDao.deleteMail(user, mail);
    }

    @Override
    public void sendMail(Mail mail) {
        mailDao.sendMail(mail);
    }

    @Override
    public Mail getById(Long id) {
        return mailDao.getById(id);
    }
}
