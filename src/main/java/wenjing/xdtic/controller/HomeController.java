package wenjing.xdtic.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.User;

/**
 *
 * @author admin
 */
@Controller
public class HomeController {

    @Autowired
    private UserDao userDao;

    @RequestMapping({"/", "index", "home", "login"})
    public String index() {
        return "/page/user/login";
    }

    @RequestMapping("user")
    public String index(Integer userid, HttpSession session) {
        User user = userDao.getUser(userid);
        session.setAttribute("user", user);

        return "page/user/center";
    }

    @RequestMapping("myProject")
    public String getMyProjectPage() {
        return "/page/myProject/myProject";
    }

    @RequestMapping("{pageFolder}/{pageName}")
    public String route(@PathVariable String pageFolder, @PathVariable String pageName) {
        return "page/" + pageFolder + "/" + pageName;
    }
}
