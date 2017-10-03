package xdtic.projpool.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 基本的路由控制器
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
public class HomeController {

    @GetMapping({"/", "index", "login"})
    public String index() {
        return "user/login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/";
    }

    @GetMapping("hall")
    public String getHallPage() {
        return "hall/hall";
    }

    /**
     * 当没有控制器可以匹配输入的 url 时，会进入此方法
     *
     * @return
     */
    @GetMapping("/**")
    public String get404Page() {
        return "error";
    }

}
