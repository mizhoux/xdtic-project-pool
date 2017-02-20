package wenjing.xdtic.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.User;

/**
 * 基本的路由控制器
 *
 * @author mizhou
 */
@Controller
public class HomeController {

    @Autowired
    private UserDao userDao;

    @RequestMapping({"/", "index", "home", "login"})
    public String index() {
        return "page/user/login";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "page/user/login";
    }

    @RequestMapping("user")
    public String getUserCenterPage(Integer userid, HttpSession session) {
        User user = userDao.getUser(userid);
        session.setAttribute("user", user);

        return "page/user/center";
    }

    @RequestMapping("hall")
    public String getHallPage() {
        return "page/hall/hall";
    }
    
}
