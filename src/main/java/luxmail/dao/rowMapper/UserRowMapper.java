package luxmail.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import luxmail.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString( "firstname"));
        user.setLastName(rs.getString( "lastname"));
        user.setEmail(rs.getString( "email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    }
}
