package wenjing.xdtic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author admin
 */
@Controller
public class HomeController {

    @RequestMapping({"/", "index", "home", "test", "login"})
    public String index() {
        //    return "/page/user/login";
        return "login";
    }

    /*
    @RequestMapping(value = "/page/{f}/{s}")
    public String index2(@PathVariable("f") String f, @PathVariable("s") String s) {
        System.out.println("f: " + f);
        System.out.println("s: " + s);

        return "/page/" + f + "/" + s;
    }
     */
    
    @RequestMapping("register")
    public String login() {
        return "register";
    }

    @RequestMapping("user/personalinformation")
    public String personalinformation() {
        return "personalinformation";
    }

    @RequestMapping("systemassage")
    public String systemassage() {
        return "systemassage";
    }
}
