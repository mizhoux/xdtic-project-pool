package xdtic.projpool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xdtic.projpool.model.Admin;

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
    public Optional<Admin> getAdmin(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        return jdbcTmpl.query(sql, this::extractAdmin, username, password);
    }

    private Optional<Admin> extractAdmin(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Admin admin = new Admin();

            admin.setId(rs.getInt("id"));
            admin.setEmail(rs.getString("email"));
            admin.setPhone(rs.getString("phone"));
            admin.setRealname(rs.getString("realname"));
            admin.setUsername(rs.getString("username"));

            return Optional.of(admin);
        }

        return Optional.empty();
    }

}
