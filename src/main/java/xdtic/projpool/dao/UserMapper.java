package xdtic.projpool.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xdtic.projpool.model.User;

/**
 * User Mapper
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public interface UserMapper {

    User getUser(String username, String password);

    User getUserById(Integer id);

    Integer getUserId(String username);

    String getUsername(Integer id);

    Integer getUserIdByEmail(String email);

    Integer getUserIdByPhone(String phone);

    List<Integer> getCollectedProIds(Integer id);

    long countUsers(@Param("condition") String condition);

    List<User> getUsers(@Param("condition") String condition);

    List<User> getAllUsers();

    int updateUser(User record);

    int updatePassword(String username, String oldPassword, String newPassword);

    int addUser(String username, String password);

    int deleteUser(Integer id);

    int deleteUsers(List<Integer> ids);

    int insert(User record);

}
