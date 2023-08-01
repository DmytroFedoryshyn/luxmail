package luxmail.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Setter;
import luxmail.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Setter
public class SenderRowMapper implements RowMapper<User> {
    private static final String prefix = "sender_";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(prefix + "id"));
        user.setFirstName(rs.getString(prefix + "firstname"));
        user.setLastName(rs.getString(prefix + "lastname"));
        user.setEmail(rs.getString(prefix + "email"));
        user.setPassword(rs.getString(prefix + "password"));
        user.setRole(rs.getString(prefix + "role"));
        return user;
    }
}