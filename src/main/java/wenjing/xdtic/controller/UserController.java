package wenjing.xdtic.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.User;

/**
 *
 * @author admin
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/{function}")
    public String index(@PathVariable String function) {
        return "/page/user/" + function;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session) {

        User user = userDao.selectuser(username, password);

        if (user == null) {
            return "register";
        } else {
            session.setAttribute("user", user);

            return "personalcenter";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)//创建资源
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email) {
        boolean success = userDao.addUser(username, password, email);//创建
        if (success) {
            return "login";
        } else {
            return "register";
        }

    }

    //个人信息查询
    @ResponseBody //return的值作为http请求的内容返回客户端 
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)//请求数据
    public User getPesonalInformationById(@PathVariable("id") Integer id
    ) {
        User user = userDao.getUser(id);
        return user;
    }

    @ResponseBody //return的值作为http请求的内容返回客户端 
    @RequestMapping(method = RequestMethod.GET)//请求数据
    public User getPesonalInformationByUsernameAndPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        User user = userDao.selectuser(username, password);
        return user;
    }

    //个人信息修改(更新数据) 个人信息修改出现在登陆之后？
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)//更新数据
    //更新之后返回个人信息
    public User updateUser(@RequestParam Integer id,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String profe,
            @RequestParam(required = false) String stunum,
            @RequestParam(required = false) String profile,
            @RequestParam(required = false) String pexperice) {
        System.out.println("enter method 'updateUser'... ");

        User oldUser = userDao.getUser(id);

        User user = new User();
        user.setId(id);//将得到的数据赋值，并返回

        user.setNickname(nickname == null ? oldUser.getNickname() : nickname);
        user.setSex(sex == null ? oldUser.getNickname() : sex);
        user.setEmail(email == null ? oldUser.getEmail() : email);
        user.setPhone(phone == null ? oldUser.getPhone() : phone);
        user.setProfe(profe == null ? oldUser.getProfe() : profe);
        user.setName(name == null ? oldUser.getName() : name);
        user.setProfile(profile == null ? oldUser.getProfile() : profile);
        user.setPexperice(pexperice == null ? oldUser.getPexperice() : pexperice);

        User updatedUser = userDao.updateUser(user);

        return updatedUser;

    }

    //修改密码（更新数据）
    //更新之后转到新的登陆界面    
    @ResponseBody
    @RequestMapping(value = "/update_passwd", method = RequestMethod.PUT)
    public User updatepassword(
            @RequestParam("id") Integer id,
            @RequestParam("old_password") String oldPassword,
            @RequestParam("new_password") String newPassword,
            @RequestParam("email") String email) {
        System.out.println("update passwd entered...");

        User user = userDao.getUser(id);

        if (oldPassword.equals(user.getPassword())) {
            user.setPassword(newPassword);

            User updatedUser = userDao.updatepassword(user);

            return updatedUser;
        }
        return null;
    }

}
