package wenjing.xdtic.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 基本的路由控制器
 *
 * @author mizhou
 */
@Controller
public class HomeController {

    @GetMapping({"/", "index", "home", "login"})
    public String index() {
        return "page/user/login";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "page/user/login";
    }

    @GetMapping("hall")
    public String getHallPage() {
        return "page/hall/hall";
    }

}
