package xdtic.projpool.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdtic.projpool.dao.UserMapper;
import xdtic.projpool.model.PagingModel;
import xdtic.projpool.model.RespCode;
import xdtic.projpool.model.User;
import xdtic.projpool.util.Pair;

/**
 * User Service
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean addUser(String username, String password) {
        return userMapper.addUser(username, password) == 1;
    }

    public Optional<User> getUser(Integer id) {
        User user = userMapper.getUserById(id);

        return Optional.ofNullable(user);
    }

    public Optional<User> getUser(String username, String password) {
        User user = userMapper.getUser(username, password);

        return Optional.ofNullable(user);
    }

    public PagingModel<User> getPagingUsers(String keyword, int pageNum, int pageSize) {
        keyword = keyword == null ? "" : keyword.trim();
        Pair<List<User>, Long> pair = getUsers(keyword, pageNum, pageSize);

        return PagingModel.<User>builder()
                .entities(pair.left())
                .entitiesName("users")
                .pageNum(pageNum)
                .size(pair.left().size())
                .hasMore((pageNum + 1) * pageSize < pair.right())
                .build();
    }

    private Pair<List<User>, Long> getUsers(String keyword, int pageNum, int pageSize) {
        //    String condition = getSearchCondition(keyword);

        Page<Object> page = PageHelper.startPage(pageNum + 1, pageSize);
        List<User> users = userMapper.getUsers(keyword);

        return Pair.of(users, page.getTotal());
    }

    private String getSearchCondition(String keyword) {

        StringJoiner columnJoiner = new StringJoiner(",',',", "CONCAT(", ")");

        columnJoiner.add("username").add("IFNULL(realname, '')");
        String columns = columnJoiner.toString();

        StringBuilder condition = new StringBuilder(55);
        condition.append(columns).append(" LIKE '%").append(keyword).append("%'");

        return condition.toString();
    }

    public boolean containsUsername(String username) {
        Integer userId = userMapper.getUserIdByUsername(username);

        return userId != null;
    }

    public boolean updateUser(User user) {
        int result = userMapper.updateUser(user);

        return result == 1;
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        int result = userMapper.updatePassword(username, oldPassword, newPassword);

        return result == 1;
    }

    public boolean deleteUser(Integer id) {
        int result = userMapper.deleteUser(id);

        return result == 1;
    }

    public boolean deleteUsers(List<Integer> ids) {
        if (ids.isEmpty()) {
            return true;
        }

        return userMapper.deleteUsers(ids) == ids.size();
    }

    public RespCode validUser(Integer currentUserId, User user) {
        Integer userId = userMapper.getUserIdByUsername(user.getUsername());

        if (userId != null && !userId.equals(currentUserId)) {
            return RespCode.errorOf("用户名已被使用");
        }

        userId = userMapper.getUserIdByEmail(user.getEmail());
        if (userId != null && !userId.equals(currentUserId)) {
            return RespCode.errorOf("邮箱已被使用");
        }

        userId = userMapper.getUserIdByPhone(user.getPhone());
        if (userId != null && !userId.equals(currentUserId)) {
            return RespCode.errorOf("电话号码已被使用");
        }

        return RespCode.OK;
    }

}
