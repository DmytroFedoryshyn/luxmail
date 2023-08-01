package luxmail.service;

import java.util.List;
import luxmail.model.User;

public interface UserService {
    List<User> getAll();

    User getByLogin(String login);

    User getById(Long id);

    User update(User user);
}
