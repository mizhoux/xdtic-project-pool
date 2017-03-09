package wenjing.xdtic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.Admin;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Repository
public class AdminDao {

    @Autowired
    private JdbcTemplate jdbcTmpl;

    /**
     * 通过用户名和密码查询管理员
     *
     * @param username
     * @param password
     * @return
     */
    public Admin getAdmin(String username, String password) {
        String SQL = "SELECT * FROM admin WHERE username = ? AND password = ?";
        Admin admin = jdbcTmpl.query(SQL,
                rs -> rs.next() ? parseAdmin(rs) : null, username, password);
        return admin;
    }

    private Admin parseAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();

        admin.setId(rs.getInt("id"));
        admin.setEmail(rs.getString("email"));
        admin.setPhone(rs.getString("phone"));
        admin.setRealname(rs.getString("realname"));
        admin.setUsername(rs.getString("username"));

        return admin;
    }

}
