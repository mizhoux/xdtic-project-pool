package wenjing.xdtic.dao;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.User;

/**
 * 用户表的数据库访问类
 *
 * @author wenjing
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTmpl;

    /**
     * 添加用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否添加成功
     */
    public boolean addUser(String username, String password) {
        String SQL = "INSERT INTO user SET username = ?, password = ?";
        return jdbcTmpl.update(SQL, username, password) == 1;
    }

    /**
     * 根据 用户ID 查询用户
     *
     * @param id 用户ID
     * @return 查询到的用户
     */
    public User getUser(Integer id) {
        String SQL = "SELECT * FROM user WHERE id = ?";
        return jdbcTmpl.query(SQL, this::extractUser, id);
    }

    /**
     * 获得用户名
     *
     * @param id 用户 ID
     * @return 用户名
     */
    public String getUsername(Integer id) {
        String SQL = "SELECT username FROM user WHERE id = ?";
        return jdbcTmpl.queryForObject(SQL, String.class, id);
    }

    /**
     * 更新用户
     *
     * @param user 要更新的用户信息
     * @return 是否更新成功
     */
    public boolean updateUser(User user) {
        String SQL
                = "UPDATE user SET username = ?, realname = ?, nickname= ?, email = ?, gender = ?, "
                + "specialty = ?, phone = ?, stu_num = ?, skill = ?, experience = ? WHERE id = ?";
        int result = jdbcTmpl.update(SQL,
                user.getUsername(), user.getRealname(), user.getNickname(),
                user.getEmail(), user.getGender(), user.getSpecialty(), user.getPhone(),
                user.getStuNum(), user.getSkill(), user.getExperience(), user.getId());

        return result == 1;
    }

    /**
     * 更新用户密码
     *
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否更新成功
     */
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        String SQL = "UPDATE user SET password = ? WHERE username = ? AND password = ?";
        return jdbcTmpl.update(SQL, newPassword, username, oldPassword) == 1;
    }

    /**
     * 根据用户名和密码从数据库中查询出用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 查询的用户
     */
    public User getUser(String username, String password) {
        String SQL = "SELECT * FROM user WHERE username = ? AND password = ?";
        return jdbcTmpl.query(SQL, this::extractUser, username, password);
    }

    public List<User> getUsers(String keywords, Integer offset, Integer size) {
        String SQL = "SELECT u.* FROM user AS u WHERE u.username LIKE ? LIMIT ?, ?";
        return jdbcTmpl.query(SQL, this::parseUser, getMysqlLikeKey(keywords), offset, size);
    }

    public Long getUsersCount(String keywords) {
        String SQL = "SELECT COUNT(*) FROM user u WHERE u.username LIKE ? ";
        return jdbcTmpl.queryForObject(SQL, Long.class, getMysqlLikeKey(keywords));
    }

    /**
     * 判断用户名在数据库中是否已经存在
     *
     * @param username 用户名
     * @return 用户名是否已经存在
     */
    public boolean containsUsername(String username) {
        String SQL = "SELECT id FROM user WHERE username = ?";
        return jdbcTmpl.query(SQL, rs -> rs.next() ? TRUE : FALSE, username);
    }

    /**
     * 将 ResultSet 中的数据转换为用户（用于 ResultSetExtractor，rs 的游标需要自己处理）
     *
     * @param rs ResultSet
     * @return 用户
     * @throws SQLException
     */
    private User extractUser(ResultSet rs) throws SQLException {
        return rs.next() ? parseUser(rs, 1) : null;
    }

    /**
     * 将 ResultSet 中的数据转换为用户（用于 RowMapper，rs 的游标会自动移动）
     *
     * @param rs
     * @param rowNum
     * @return 用户
     * @throws SQLException
     */
    private User parseUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setNickname(rs.getString("nickname"));
        user.setRealname(rs.getString("realname"));
        user.setGender(rs.getString("gender"));
        user.setSpecialty(rs.getString("specialty"));
        user.setStuNum(rs.getString("stu_num"));
        user.setSkill(rs.getString("skill"));
        user.setExperience(rs.getString("experience"));

        // 兼容前端
        User.syncDataForFront(user);

        return user;
    }

    private String getMysqlLikeKey(String keyword) {
        return "%" + keyword + "%";
    }

    public boolean deleteUser(Integer id) {
        String SQL = "DELETE FROM user WHERE id = ?";
        return jdbcTmpl.update(SQL, id) == 1;
    }

    public boolean deleteUsers(List<Integer> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        
        String SQL = "DELETE FROM user WHERE id IN "
                + ids.stream().map(String::valueOf).collect(Collectors.joining(",", "(", ")"));
        return jdbcTmpl.update(SQL) == ids.size();
    }

}
