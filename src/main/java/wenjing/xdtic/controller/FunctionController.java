package wenjing.xdtic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.ResponseCode;
import wenjing.xdtic.model.User;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
@RequestMapping("/fn")
public class FunctionController {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名和密码验证用户是否可注册
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/valid/user", method = POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ResponseCode> validUser(
            @RequestParam String username,
            @RequestParam String password) {
        System.out.println("valid username: " + username + ", password: " + password);
        User user = userDao.selectuser(username, password);
        if (user != null) {
            return new ResponseEntity<>(new ResponseCode("ok"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseCode("error"), HttpStatus.OK);
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
            @RequestParam String password) {
        User user = userDao.selectuser(username, password);
        if (user != null) {
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
    public ResponseEntity<ResponseCode> validUsername(
            @RequestBody User user) { // 将前端数据转化为 User

        String username = user.getUsername();
        boolean userExisted = userDao.containsUser(username);
        if (userExisted == true) {
            return new ResponseEntity<>(new ResponseCode("error"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseCode("ok"), HttpStatus.OK);

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
