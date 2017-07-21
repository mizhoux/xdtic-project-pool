package xdtic.projpool.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdtic.projpool.dao.UserDao;
import xdtic.projpool.model.PagingModel;
import xdtic.projpool.model.RespCode;
import xdtic.projpool.model.User;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean addUser(String username, String password) {
        return userDao.addUser(username, password);
    }

    public Optional<User> getUser(Integer id) {
        return userDao.getUser(id);
    }

    public Optional<User> getUser(String username, String password) {
        return userDao.getUser(username, password);
    }

    public List<User> getUsers(String keyword, int pageNum, int pageSize) {

        int offset = pageNum * pageSize;
        return userDao.getUsers(keyword, offset, pageSize);
    }

    public PagingModel<User> getPagingUsers(String keyword, int pageNum, int pageSize) {

        Supplier<List<User>> users = () -> getUsers(keyword, pageNum, pageSize);
        Supplier<Long> totalNumberOfUsers = () -> countUsers(keyword);

        return PagingModel.of("users", users, totalNumberOfUsers, pageNum, pageSize);
    }

    public boolean containsUsername(String username) {
        return userDao.containsUsername(username);
    }

    public long countUsers(String keyword) {
        return userDao.countUsers(keyword);
    }

    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        return userDao.updatePassword(username, oldPassword, newPassword);
    }

    public boolean deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }

    public boolean deleteUsers(List<Integer> ids) {
        if (ids.isEmpty()) {
            return true;
        }

        return userDao.deleteUsers(ids);
    }

    public RespCode validUser(User user) {
        Integer userId = userDao.getUserIdByUsername(user.getUsername());
        if (userId != 0 && !userId.equals(user.getId())) {
            return RespCode.errorOf("用户名已被使用");
        }

        userId = userDao.getUserIdByEmail(user.getEmail());
        if (userId != 0 && !userId.equals(user.getId())) {
            return RespCode.errorOf("邮箱已被使用");
        }

        userId = userDao.getUserIdByPhone(user.getPhone());
        if (userId != 0 && !userId.equals(user.getId())) {
            return RespCode.errorOf("电话号码已被使用");
        }

        return RespCode.OK;
    }

}
