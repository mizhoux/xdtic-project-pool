package wenjing.xdtic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller("admin")
public class AdminController {

    @GetMapping("project/check")
    public String getUnchekedProjectsPage() {
        return "page/admin/project/uncheck";
    }

    @GetMapping("project/accept")
    public String getAcceptedProjectsPage() {
        return "page/admin/project/accept";
    }

    @GetMapping("user/look")
    public String getUsersPage() {
        return "page/admin/user/look";
    }
}
