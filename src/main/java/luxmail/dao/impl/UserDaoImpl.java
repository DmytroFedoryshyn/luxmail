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
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User getByLogin(String login) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{login}, new UserRowMapper());
        } catch (Exception e) {
            throw new DataProcessingException("Could not retrieve user by login", e);
        }
    }

    @Override
    public User getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
        } catch (Exception e) {
            throw new DataProcessingException("Could not retrieve user by ID", e);
        }
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, email = ?, password = ?, role = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), user.getId());
            return user;
        } catch (Exception e) {
            throw new DataProcessingException("Could not update user", e);
        }
    }
}
