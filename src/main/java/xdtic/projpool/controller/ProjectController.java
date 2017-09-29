package xdtic.projpool.controller;

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
 * Project Controller
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
public class ProjectController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projService;

    @Autowired
    private SignInfoService signService;

    @GetMapping("myProject")
    public String getMyProjectPage() {
        return "myProject/myProject";
    }

    @GetMapping("myProject/postProject")
    public String getPostProjectPage() {
        return "myProject/postProject";
    }

    // 查看收藏（点赞）的项目的详情
    @GetMapping({"project", "myProject/myCollect/detail"})
    public String getProjectDetailPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = projService.getProject(proId);
        project.ifPresent(p -> {
            User user = (User) request.getSession().getAttribute("user");
            p.setIsCollected(projService.containsCollection(user.getId(), proId));

            request.setAttribute("project", p);

            userService.getUser(p.getUserId())
                    .ifPresent(creator -> request.setAttribute("projectCreator", creator));

            boolean userIsJoined = projService.containsSignInfo(user.getId(), p.getId());
            request.setAttribute("userIsJoined", userIsJoined);
        });

        return project.map(p -> "myProject/myCollect/detail").orElse("error");
    }

    // 查看提交的项目的详情
    @GetMapping("myProject/myPost/detail")
    public String getPostProjectDetailPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = projService.getProject(proId);
        project.ifPresent(p -> {
            User user = (User) request.getSession().getAttribute("user");
            p.setIsCollected(projService.containsCollection(user.getId(), proId));

            request.setAttribute("project", p);
        });

        return project.map(p -> "myProject/myPost/detail").orElse("error");
    }

    @GetMapping("myProject/myPost/editDetail")
    public String getProjectEditDetailPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = projService.getProject(proId);
        project.ifPresent(p -> {
            User user = (User) request.getSession().getAttribute("user");
            p.setIsCollected(projService.containsCollection(user.getId(), proId));

            request.setAttribute("project", p);
        });

        return project.map(p -> "myProject/myPost/editDetail").orElse("error");
    }

    @GetMapping("myProject/myPost/signInfo")
    public String getSignInfoPage(
            HttpServletRequest request, @RequestParam Integer proId) {

        Optional<Project> project = projService.getProject(proId);
        project.ifPresent(p -> {
            List<SignInfo> signInfos = signService.getSignInfoByProId(proId);
            request.setAttribute("project", p);
            request.setAttribute("signInfos", signInfos);
        });

        return project.map(p -> "myProject/myPost/signInfo").orElse("error");
    }

    @GetMapping("signInfo")
    public String getSignInfoDetail(
            HttpServletRequest request, @RequestParam Integer signId) {

        Optional<SignInfo> signInfo = signService.getSignInfo(signId);
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

        Optional<Project> project = projService.getProject(proId);
        project.ifPresent(p -> {
            p.setIsCollected(projService.containsCollection(userId, proId));
            request.setAttribute("project", p);
        });

        return project.map(p -> "myProject/myCollect/toJoin").orElse("error");
    }

}
