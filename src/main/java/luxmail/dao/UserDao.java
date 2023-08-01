package luxmail.dao;


import java.util.List;
import luxmail.model.User;

public interface UserDao {
    List<User> getAll();

    User getByLogin(String login);

    User getById(Long id);

    User update(User user);
}
