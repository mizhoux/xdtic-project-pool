package wenjing.xdtic.action;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.SignInfo;
import wenjing.xdtic.model.User;
import wenjing.xdtic.service.ProjectService;
import wenjing.xdtic.service.SignInfoService;
import wenjing.xdtic.service.UserService;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
public class ProjectController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService proService;

    @Autowired
    private SignInfoService siService;

    @GetMapping("myProject")
    public String getMyProjectPage() {
        return "myProject/myProject";
    }

    @GetMapping("myProject/postProject")
    public String getPostProjectPage() {
        return "myProject/postProject";
    }

    @GetMapping({"project", "myProject/myCollect/detail"})
    public String getProjectDetailPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = proService.getProject(proId);
        project.ifPresent(p -> {
            User user = (User) request.getSession().getAttribute("user");
            p.setIsCollected(proService.containsCollection(user.getId(), proId));

            request.setAttribute("project", p);

            userService
                    .getUser(p.getUserId())
                    .ifPresent(u -> request.setAttribute("projectCreator", u));

            boolean userIsJoined = proService.containsSignInfo(user.getId(), p.getId());
            request.setAttribute("userIsJoined", userIsJoined);
        });

        return project.map(p -> "myProject/myCollect/detail").orElse("error");
    }

    @GetMapping("myProject/myPost/detail")
    public String getPostProjectDetailPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = proService.getProject(proId);
        project.ifPresent(p -> {
            User user = (User) request.getSession().getAttribute("user");
            p.setIsCollected(proService.containsCollection(user.getId(), proId));

            request.setAttribute("project", p);
        });

        return project.map(p -> "myProject/myPost/detail").orElse("error");
    }

    @GetMapping("myProject/myPost/editDetail")
    public String getProjectEditDetailPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = proService.getProject(proId);
        project.ifPresent(p -> {
            User user = (User) request.getSession().getAttribute("user");
            p.setIsCollected(proService.containsCollection(user.getId(), proId));

            request.setAttribute("project", p);
        });

        return project.map(p -> "myProject/myPost/editDetail").orElse("error");
    }

    @GetMapping("myProject/myPost/signInfo")
    public String getSignInfosPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = proService.getProject(proId);
        project.ifPresent(p -> {
            List<SignInfo> signInfos = siService.getSignInfos(proId);
            request.setAttribute("project", p);
            request.setAttribute("signInfos", signInfos);
        });

        return project.map(p -> "myProject/myPost/signInfo").orElse("error");
    }

    @GetMapping("signInfo")
    public ModelAndView getSignInfoDetail(
            HttpSession session, @RequestParam Integer signId) {

        ModelAndView mav = new ModelAndView("myProject/myPost/signDetail");

        Optional<SignInfo> signInfo = siService.getSignInfo(signId);
        signInfo.ifPresent(si -> mav.addObject("signInfo", si));

        signInfo.map(si -> si.getUserId())
                .map(userId -> userService.getUser(userId))
                .ifPresent(user -> mav.addObject("signUser", user));

        return mav;
    }

    @GetMapping("project/toJoin")
    public String getToJoinPage(HttpServletRequest request,
            @RequestParam Integer proId,
            @RequestParam("uid") Integer userId) {

        Optional< Project> project = proService.getProject(proId);
        project.ifPresent(p -> {
            p.setIsCollected(proService.containsCollection(userId, proId));
            request.setAttribute("project", p);
        //    request.setAttribute("user", request.getSession().getAttribute("user"));
        });

        return project.map(p -> "myProject/myCollect/toJoin").orElse("error");
    }

}
