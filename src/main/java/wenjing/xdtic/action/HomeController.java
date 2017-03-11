package wenjing.xdtic.action;

import wenjing.xdtic.cache.XdticCache;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 基本的路由控制器
 *
 * @author mizhou
 */
@Controller
public class HomeController {

    @Autowired
    private XdticCache cache;

    @GetMapping({"/", "index", "home", "login"})
    public String index() {
        return "user/login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        cache.remove(request.getRemoteAddr());
        request.getSession().invalidate();

        return "redirect:/index";
    }

    @GetMapping("hall")
    public String getHallPage() {
        return "hall/hall";
    }

    /**
     * 当没有控制器可以匹配输入的 url 时，会进入次方法
     *
     * @return
     */
    @GetMapping("/**")
    public String get404Page() {
        return "error";
    }

}
