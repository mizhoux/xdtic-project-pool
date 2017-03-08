package wenjing.xdtic.action;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户页面的路由
 *
 * @author wenjing
 */
@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("")
    public String index() {
        return "user/center";
    }

    @GetMapping("{pageName}")
    public String route(@PathVariable String pageName) {
        return "user/" + pageName;
    }

    @GetMapping(value = "loginBySession")
    public String userLoginBySession(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/user/center";
        }
        return "user/login";
    }

}
