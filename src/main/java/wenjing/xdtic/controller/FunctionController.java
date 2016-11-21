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

    @RequestMapping(value = "/valid/username", method = POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCode> validUsername(@RequestBody User user) {
        String username = user.getUsername();
        System.out.println("req username: " + username);
        if ("mizhou".equals(username)) {
            return new ResponseEntity<>(new ResponseCode("error"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseCode("ok"), HttpStatus.OK);
    }

    @RequestMapping(value = "/valid/user", method = POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<ResponseCode> validUser(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        System.out.println("valid username: " + username + ", password: " + password);
        if ("mizhoux".equals(username) && "zm2016".equals(password)) {
            return new ResponseEntity<>(new ResponseCode("ok"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseCode("error"), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/login", method = POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String userLogin(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        System.out.println("login: username: " + username + ", password: " + password);
        if ("mizhoux".equals(username) && "zm2016".equals(password)) {
            return "/page/user/center";
        //    return new ResponseEntity<>(new ResponseCode("ok"), HttpStatus.OK);
        }
        return "failed";
        //return new ResponseEntity<>(new ResponseCode("error"), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/register", method = POST)
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String passConfirm) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("passConfirm: " + passConfirm);
        if ("mizhou".equals(username)) {
            System.err.println("register error");
            return "/page/user/register";
        }
        return "/page/user/login";
    }
}
