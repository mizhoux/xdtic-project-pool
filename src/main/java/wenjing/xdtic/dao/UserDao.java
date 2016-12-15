package wenjing.xdtic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    private static final String SQL_GET_USER_BY_ID
            = "SELECT * FROM user WHERE id = ?";

    private static final String SQL_GET_USERNAME_BY_ID
            = "SELECT username FROM user WHERE id = ?";

    private static final String SQL_GET_USER_BY_USERNAME_AND_PASSWORD
            = "SELECT * FROM  user WHERE username = ? AND password = ?";

    private static final String SQL_ADD_USER_BY_USERNAME_AND_PASSWORD
            = "INSERT INTO user SET username = ?, password = ?";

    private static final String SQL_UPDATE_USER
            = "UPDATE user SET username = ?, name = ?, nickname= ?, email = ?, sex = ?, "
            + "profe = ?, phone = ?, stunum = ?, profile= ?, pexperice = ? WHERE id = ?";

    private static final String SQL_UPDATE_PASSWORD
            = "UPDATE user SET password = ? WHERE username = ? AND password = ?";

    @Autowired
    private JdbcTemplate jdbcTmpl;

    /**
     * 根据 用户id 数据库中查询出用户
     *
     * @param id 用户id
     * @return 查询的用户
     */
    public User getUser(Integer id) {
        User user = jdbcTmpl.query(SQL_GET_USER_BY_ID, this::parseUser, id);
        return user;
    }

    public String getUsername(Integer id) {
        String username = jdbcTmpl.query(SQL_GET_USERNAME_BY_ID,
                rs -> rs.next() ? rs.getString(1) : null, id);
        return username;
    }

    /**
     * 根据用户名和密码从数据库中查询出用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 查询的用户
     */
    public User getUser(String username, String password) {
        User user = jdbcTmpl.query(
                SQL_GET_USER_BY_USERNAME_AND_PASSWORD, this::parseUser, username, password);

        //查询并返回对象
        return user;
    }

    /**
     * 判断用户名在数据库中是否已经存在
     *
     * @param username 用户名
     * @return 用户名是否已经存在
     */
    public boolean containsUsername(String username) {
        User user = jdbcTmpl.query(
                SQL_GET_USER_BY_USERNAME_AND_PASSWORD, this::parseUser, username);
        return user != null;
    }

    /**
     * 将 ResultSet 中的数据转换为用户
     *
     * @param rs ResultSet
     * @return 用户
     * @throws SQLException
     */
    public User parseUser(ResultSet rs) throws SQLException {
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setNickname(rs.getString("nickname"));
            user.setEmail(rs.getString("email"));
            user.setSex(rs.getString("sex"));
            user.setProfe(rs.getString("profe"));
            user.setPhone(rs.getString("phone"));
            user.setStunum(rs.getString("stunum"));
            user.setProfile(rs.getString("profile"));
            user.setPexperice(rs.getString("pexperice"));

            return user;
        }
        return null;
    }

    /**
     * 添加用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否添加成功
     */
    public boolean addUser(String username, String password) {
        int result = jdbcTmpl.update(
                SQL_ADD_USER_BY_USERNAME_AND_PASSWORD, username, password);

        return result == 1;
    }

    /**
     * 更新用户
     *
     * @param user 要更新的用户信息
     * @return 是否更新成功
     */
    public boolean updateUser(User user) {

        int result = jdbcTmpl.update(SQL_UPDATE_USER,
                user.getUsername(), user.getName(), user.getNickname(),
                user.getEmail(), user.getSex(), user.getProfe(), user.getPhone(),
                user.getStunum(), user.getProfile(), user.getPexperice(), user.getId());

        return result == 1;
    }

    /**
     * 更新用户密码
     *
     * @param username 用户名
     * @param passOld 旧密码
     * @param passNew 新密码
     * @return 是否更新成功
     */
    public boolean updatePassword(String username, String passOld, String passNew) {
        int result = jdbcTmpl.update(SQL_UPDATE_PASSWORD, passNew, username, passOld);
        return result == 1;
    }

    public User getUserByMap(Integer id) {

        try {
            //Map数据集，此时 键为String类型，值为 Object 类型
            Map<String, Object> map = jdbcTmpl.queryForMap(SQL_GET_USER_BY_ID, id);
            User user = new User();
            user.setId((Integer) map.get("id"));//将得到的数据赋值，并返回
            user.setUsername((String) map.get("username"));
            user.setPassword((String) map.get("password"));
            user.setEmail((String) map.get("email"));
            user.setNickname((String) map.get("nickname"));
            user.setProfe((String) map.get("profe"));
            user.setSex((String) map.get("sex"));
            user.setPhone((String) map.get("phone"));
            user.setStunum((String) map.get("stunum"));
            user.setPexperice((String) map.get("pexperice"));
            user.setProfile((String) map.get("profile"));

            return user;

        } catch (EmptyResultDataAccessException ex) { // 捕获异常
            return null;    //  Spring 查询不到输入数据时抛出异常，此时返回 null
        }
    }

}
