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
 *
 * @author admin
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //通过id查询获得用户的个人信息
    public User getUser(Integer id) {
        String SQL = "SELECT * FROM user WHERE id = " + id;
        User user = jdbcTemplate.query(SQL, this::parseUser);
        return user;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public User getUser(String username, String password) {
        String SQL = "SELECT * FROM  user WHERE username=? AND password= ?";
        User user = jdbcTemplate.query(SQL, this::parseUser, username, password);

        //查询并返回对象
        return user;
    }

    //通过用户名、密码 获取用户个人信息
    public User parseUser(ResultSet rs) throws SQLException {
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setNickname(rs.getString("nickname"));
            user.setProfe(rs.getString("profe"));
            user.setPhone(rs.getString("phone"));
            user.setStunum(rs.getString("stunum"));
            user.setPexperice(rs.getString("pexperice"));
            user.setProfile(rs.getString("profile"));

            return user;
        }
        return null;
    }

    //判断 username 是否已经存在
    public boolean containsUsername(String username) {
        String SQL = "SELECT id FROM user where username = ?";
        User user = jdbcTemplate.query(SQL, this::parseUser, username);
        return user != null;
    }

    public boolean addUser(String username, String password, String email) {
        String SQL = "INSERT INTO user SET username = ?, password = ?, email = ?";
        int result = jdbcTemplate.update(SQL, username, password, email);

        return result == 1;
    }

    public boolean addUser(String username, String password) {
        String SQL = "INSERT INTO user SET username = ?, password = ?";
        int result = jdbcTemplate.update(SQL, username, password);

        return result == 1;
    }

    /**
     * 更新用户
     *
     * @param user 要更新的用户信息
     * @return 是否更新成功
     */
    public boolean updateUser(User user) {
        String SQL = "UPDATE user SET "
                + "nickname= ?, name = ?, sex = ?, phone = ?, profe = ?,"
                + "stunum = ?,  profile= ?, pexperice = ?, email = ? "
                + "WHERE id = ?";
        int result = jdbcTemplate.update(SQL,
                user.getNickname(),
                user.getName(),
                user.getSex(),
                user.getPhone(),
                user.getProfe(),
                user.getStunum(),
                user.getProfile(),
                user.getPexperice(),
                user.getEmail(),
                user.getId());

        return result == 1;
    }

    public boolean updatepassword(String username, String passOld, String passNew) {
        String SQL = "UPDATE user SET password = ? WHERE username = ? AND password = ?";
        int result = jdbcTemplate.update(SQL, passNew, username, passOld);
        return result == 1;
    }

    public User getUserByMap(Integer id) {
        String SQL = "SELECT * FROM user WHERE id = " + id;

        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(SQL);
            //Map数据集返回对象名为string类型的值
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

        } catch (EmptyResultDataAccessException ex) {
            return null;// 捕获异常      spring查询不到输入数据时返回null
        }
    }

}
