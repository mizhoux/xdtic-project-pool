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

    Integer getUserIdByUsername(String username);

    String getUsernameById(Integer id);

    Integer getUserIdByEmail(String email);

    Integer getUserIdByPhone(String phone);

    long countUsers(@Param("condition") String condition);

    List<User> getUsers(@Param("condition") String condition);

    int updateUser(User user);

    int updatePassword(String username, String oldPassword, String newPassword);

    int addUser(String username, String password);

    int deleteUser(Integer id);

    int deleteUsers(List<Integer> ids);

    List<Integer> getCollectedProIds(Integer id);
}
