package luxmail.dao.impl;

import java.util.List;
import luxmail.dao.UserDao;
import luxmail.dao.rowMapper.UserRowMapper;
import luxmail.exception.DataProcessingException;
import luxmail.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        String sqlGetAllUsers = "SELECT * FROM users";
        return jdbcTemplate.query(sqlGetAllUsers, new UserRowMapper());
    }

    @Override
    public User getByLogin(String login) {
        String sqlGetUserByLogin = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sqlGetUserByLogin, new Object[]{login}, new UserRowMapper());
        } catch (Exception e) {
            throw new DataProcessingException("Could not retrieve user by login", e);
        }
    }

    @Override
    public User getById(Long id) {
        String sqlGetUserById = "SELECT * FROM users WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sqlGetUserById, new Object[]{id}, new UserRowMapper());
        } catch (Exception e) {
            throw new DataProcessingException("Could not retrieve user by ID", e);
        }
    }

    @Override
    public User update(User user) {
        String sqlUpdateUserById = "UPDATE users SET firstname = ?, lastname = ?, email = ?, password = ?, role = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sqlUpdateUserById, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), user.getId());
            return user;
        } catch (Exception e) {
            throw new DataProcessingException("Could not update user", e);
        }
    }
}
