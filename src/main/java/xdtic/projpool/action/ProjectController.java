package xdtic.projpool.action;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xdtic.projpool.model.Project;
import xdtic.projpool.model.SignInfo;
import xdtic.projpool.model.User;
import xdtic.projpool.service.ProjectService;
import xdtic.projpool.service.SignInfoService;
import xdtic.projpool.service.UserService;

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
        project.ifPresent(pro -> {
            User user = (User) request.getSession().getAttribute("user");
            pro.setIsCollected(proService.containsCollection(user.getId(), proId));

            request.setAttribute("project", pro);

            userService.getUser(pro.getUserId())
                    .ifPresent(creator -> request.setAttribute("projectCreator", creator));

            boolean userIsJoined = proService.containsSignInfo(user.getId(), pro.getId());
            request.setAttribute("userIsJoined", userIsJoined);
        });

        return project.map(p -> "myProject/myCollect/detail").orElse("error");
    }

    @GetMapping("myProject/myPost/detail")
    public String getPostProjectDetailPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = proService.getProject(proId);
        project.ifPresent(pro -> {
            User user = (User) request.getSession().getAttribute("user");
            pro.setIsCollected(proService.containsCollection(user.getId(), proId));

            request.setAttribute("project", pro);
        });

        return project.map(p -> "myProject/myPost/detail").orElse("error");
    }

    @GetMapping("myProject/myPost/editDetail")
    public String getProjectEditDetailPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = proService.getProject(proId);
        project.ifPresent(pro -> {
            User user = (User) request.getSession().getAttribute("user");
            pro.setIsCollected(proService.containsCollection(user.getId(), proId));

            request.setAttribute("project", pro);
        });

        return project.map(p -> "myProject/myPost/editDetail").orElse("error");
    }

    @GetMapping("myProject/myPost/signInfo")
    public String getSignInfosPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = proService.getProject(proId);
        project.ifPresent(pro -> {
            List<SignInfo> signInfos = siService.getSignInfos(proId);
            request.setAttribute("project", pro);
            request.setAttribute("signInfos", signInfos);
        });

        return project.map(p -> "myProject/myPost/signInfo").orElse("error");
    }

    @GetMapping("signInfo")
    public String getSignInfoDetail(
            HttpServletRequest request, @RequestParam Integer signId) {

        Optional<SignInfo> signInfo = siService.getSignInfo(signId);
        signInfo.ifPresent(si -> request.setAttribute("signInfo", si));

        signInfo.map(si -> si.getUserId())
                .flatMap(uid -> userService.getUser(uid))
                .ifPresent(u -> request.setAttribute("signUser", u));

        return signInfo.map(si -> "myProject/myPost/signDetail").orElse("error");
    }

    @GetMapping("project/toJoin")
    public String getToJoinPage(HttpServletRequest request,
            @RequestParam Integer proId,
            @RequestParam("uid") Integer userId) {

        Optional<Project> project = proService.getProject(proId);
        project.ifPresent(pro -> {
            pro.setIsCollected(proService.containsCollection(userId, proId));
            request.setAttribute("project", pro);
        });

        return project.map(p -> "myProject/myCollect/toJoin").orElse("error");
    }

}
