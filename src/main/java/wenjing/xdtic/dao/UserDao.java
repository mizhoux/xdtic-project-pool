package wenjing.xdtic.dao;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
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
        String sql = "INSERT INTO user SET username = ?, password = MD5(?)";
        return jdbcTmpl.update(sql, username, password) == 1;
    }

    /**
     * 根据 用户ID 查询用户
     *
     * @param id 用户ID
     * @return 查询到的用户
     */
    public Optional<User> getUser(Integer id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        return jdbcTmpl.query(sql, this::extractUser, id);
    }

    /**
     * 根据用户名和密码从数据库中查询出用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 查询的用户
     */
    public Optional<User> getUser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        return jdbcTmpl.query(sql, this::extractUser, username, password);
    }

    public List<User> getUsers(String keyword, int offset, int size) {
        String sql = "SELECT u.* FROM user AS u WHERE "
                + getSearchCondition(keyword) + " LIMIT ?, ?";
        return jdbcTmpl.query(sql, this::mapUser, offset, size);
    }

    /**
     * 获得用户名
     *
     * @param id 用户 ID
     * @return 用户名
     */
    public String getUsername(Integer id) {
        String sql = "SELECT username FROM user WHERE id = ?";
        return jdbcTmpl.queryForObject(sql, String.class, id);
    }

    private static final String SQL_UPDATE_USER
            = "UPDATE user SET username = ?, realname = ?, nickname= ?, email = ?, gender = ?, "
            + "major = ?, phone = ?, stu_num = ?, skill = ?, experience = ? WHERE id = ?";

    /**
     * 更新用户
     *
     * @param user 要更新的用户信息
     * @return 是否更新成功
     */
    public boolean updateUser(User user) {

        int result = jdbcTmpl.update(SQL_UPDATE_USER,
                user.getUsername(), user.getRealname(), user.getNickname(),
                user.getEmail(), user.getGender(), user.getMajor(), user.getPhone(),
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
        String sql = "UPDATE user SET password = MD5(?) WHERE username = ? AND password = ?";
        return jdbcTmpl.update(sql, newPassword, username, oldPassword) == 1;
    }

    public Long countUsers(String keyword) {
        String sql = "SELECT COUNT(*) FROM user u WHERE " + getSearchCondition(keyword);
        return jdbcTmpl.queryForObject(sql, Long.class);
    }

    /**
     * 判断用户名在数据库中是否已经存在
     *
     * @param username 用户名
     * @return 用户名是否已经存在
     */
    public boolean containsUsername(String username) {
        String sql = "SELECT id FROM user WHERE username = ?";
        return jdbcTmpl.query(sql, rs -> rs.next() ? TRUE : FALSE, username);
    }

    public boolean deleteUser(Integer id) {
        String sql = "DELETE FROM user WHERE id = ?";
        return jdbcTmpl.update(sql, id) == 1;
    }

    public boolean deleteUsers(List<Integer> ids) {
        String sql = "DELETE FROM user WHERE id IN "
                + ids.stream().map(String::valueOf).collect(Collectors.joining(",", "(", ")"));
        return jdbcTmpl.update(sql) == ids.size();
    }

    /**
     * 从 ResultSet 提取数据，将其映射为 User，目标函数为 ResultSetExtractor.extractData
     *
     * @param rs
     * @return
     * @throws SQLException
     * @see org.springframework.jdbc.core.ResultSetExtractor
     */
    private Optional<User> extractUser(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapUser(rs, 1)) : Optional.empty();
    }

    /**
     * 将 ResultSet 中的数据其映射为 User，目标函数为 RowMapper.mapRow
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     * @see org.springframework.jdbc.core.RowMapper
     */
    private User mapUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setNickname(rs.getString("nickname"));
        user.setRealname(rs.getString("realname"));
        user.setGender(rs.getString("gender"));
        user.setMajor(rs.getString("major"));
        user.setStuNum(rs.getString("stu_num"));
        user.setSkill(rs.getString("skill"));
        user.setExperience(rs.getString("experience"));

        return user;
    }

    private String getSearchCondition(String keyword) {
        StringJoiner columnJoiner = new StringJoiner(",',',", "CONCAT(", ")");
        columnJoiner.add("u.username").add("IFNULL(u.realname, '')");
        String columns = columnJoiner.toString();

        StringBuilder condition = new StringBuilder(55);
        condition.append(columns).append(" LIKE '%").append(keyword).append("%'");

        return condition.toString();
    }

}
