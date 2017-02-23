package wenjing.xdtic.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("")
    public String getUserCenterPage(Integer userid, HttpSession session) {
        User user = userDao.getUser(userid);
        session.setAttribute("user", user);

        return "page/user/center";
    }

    @RequestMapping("profile")
    public String getUserProfilePage(Integer userid, HttpSession session) {
        User user = userDao.getUser(userid);
        session.setAttribute("user", user);

        return "page/user/profile";
    }

    @RequestMapping("{pageName}")
    public String route(@PathVariable String pageName) {
        return "page/user/" + pageName;
    }

    /**
     * 测试代码，用户个人信息查询
     *
     * @param id 用户 ID
     * @return
     */
    @ResponseBody
    @RequestMapping("info/{id}")
    public User getPesonalInformationById(@PathVariable("id") Integer id) {
        User user = userDao.getUser(id);
        return user;
    }

}
