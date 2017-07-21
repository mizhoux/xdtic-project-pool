package xdtic.projpool.action;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xdtic.projpool.util.RemoteAddressCache;

/**
 * 基本的路由控制器
 *
 * @author mizhou
 */
@Controller
public class HomeController {

    @Autowired
    private RemoteAddressCache addrCache;

    @GetMapping({"/", "index", "login"})
    public String index() {
        return "user/login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        addrCache.remove("U".concat(request.getRemoteAddr()));
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
