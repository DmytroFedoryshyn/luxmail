package luxmail.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import luxmail.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RecipientRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("recipient_id"));
        user.setFirstName(rs.getString("recipient_firstname"));
        user.setLastName(rs.getString("recipient_lastname"));
        user.setEmail(rs.getString("recipient_email"));
        user.setPassword(rs.getString("recipient_password"));
        user.setRole(rs.getString("recipient_role"));
        return user;
    }
}
