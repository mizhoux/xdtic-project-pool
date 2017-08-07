package xdtic.projpool.controller;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xdtic.projpool.model.Project;
import xdtic.projpool.service.ProjectService;

/**
 * Admin Controller
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private ProjectService proService;

    @GetMapping("")
    public String index() {
        return "admin/index";
    }

    @GetMapping("login")
    public String getLoginPage(HttpServletRequest request) {
        request.setAttribute("loginFail", Boolean.FALSE);
        return "admin/login";
    }

    @GetMapping("project/check")
    public String getUnchekedProjectsPage() {
        return "admin/project/check";
    }

    @GetMapping("project")
    public String getProjectDetailPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = proService.getProject(proId);
        project.ifPresent(p -> request.setAttribute("project", p));

        return project.map(p -> "admin/project/detail").orElse("error");
    }

    @GetMapping("project/look")
    public String getAcceptedProjectsPage() {
        return "admin/project/look";
    }

    @GetMapping("user/look")
    public String getUsersPage() {
        return "admin/user/look";
    }

}
