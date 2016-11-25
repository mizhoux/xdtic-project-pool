package wenjing.xdtic.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.RespCode;
import wenjing.xdtic.model.User;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
//登录注册方法
@Controller
@RequestMapping("/fn")
public class FunctionController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/user/resetPass", method = POST)
    public String updateUserPassword(@RequestParam String username,
            @RequestParam String passOld,
            @RequestParam String passNew,
            @RequestParam String passNewConfirm) {
        if (passNew.equals(passNewConfirm)) {
            boolean updatePassSucc = userDao.updatepassword(username, passOld, passNew);
            if (updatePassSucc) {
                return "/page/user/login";
            }
        }
        return ""; // 更新密码不成功
    }

    @ResponseBody
    @RequestMapping(value = "/update/profile", method = POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateUserProfile(User user) {
        System.out.println("updateUserProfile:\n" + user);
        boolean updateSucc = userDao.updateUser(user);
        return updateSucc ? RespCode.OK : RespCode.ERROR;
    }

    /**
     * 根据用户名和密码验证用户是否可注册
     *
     * @param user
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/valid/user", method = POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<RespCode> validUser(
            @RequestParam String username,
            @RequestParam String password) {

        System.out.println("valid username: " + username + ", password: " + password);

        User user1 = userDao.getUser(username, password);
        if (user1 != null) {
            return new ResponseEntity<>(RespCode.OK, HttpStatus.OK);
        }
        return new ResponseEntity<>(RespCode.ERROR, HttpStatus.OK);
    }

    /**
     * 根据用户名和密码进行登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login", method = POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String userLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {
        User user = userDao.getUser(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "/page/user/center";
        }

        return "/page/user/register";
    }

    /**
     * 验证用户名是否可用
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/valid/username", method = POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespCode> validUsername(
            @RequestBody User user) { // 将前端数据转化为 User

        String username = user.getUsername();
        boolean userExisted = userDao.containsUsername(username);
        if (userExisted == true) {
            return new ResponseEntity<>(RespCode.ERROR, HttpStatus.OK);
        }
        return new ResponseEntity<>(RespCode.OK, HttpStatus.OK);

    }

    /**
     * 根据用户名和密码进行注册
     *
     * @param username 用户名
     * @param password 密码
     * @param passConfirm
     * @return
     */
    @RequestMapping(value = "/user/register", method = POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String passConfirm) {

        boolean addSucc = userDao.addUser(username, password);
        if (addSucc) {
            return "/page/user/login";
        }
        return "/page/user/register";
    }
}
