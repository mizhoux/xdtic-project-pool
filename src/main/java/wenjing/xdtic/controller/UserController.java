package wenjing.xdtic.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.User;

/**
 * 用户页面的路由
 *
 * @author wenjing
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("profile")
    public String getUserProfilePage(
            @RequestParam("userid") Integer id, HttpSession session) {
        User user = userDao.getUser(id);
        session.setAttribute("user", user);

        return "page/user/profile";
    }

    @RequestMapping("{pageName}")
    public String route(@PathVariable String pageName) {
        return "page/user/" + pageName;
    }

    // 测试代码，个人信息查询
    @ResponseBody //return的值作为http请求的内容返回客户端 
    @RequestMapping("info/{id}")//请求数据
    public User getPesonalInformationById(@PathVariable("id") Integer id) {
        User user = userDao.getUser(id);
        return user;
    }

}
