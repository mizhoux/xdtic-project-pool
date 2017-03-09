package wenjing.xdtic.action;

import java.util.List;
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
    public ModelAndView getProjectDetailPage(HttpSession session,
            @RequestParam(required = false) Integer proId,
            @RequestParam(required = false) Integer id) {

        if (proId == null) {
            proId = id;
        }

        User user = (User) session.getAttribute("user");
        Project project = proService.getProject(proId);
        project.setIsCollected(proService.containsCollection(user.getId(), proId));

        ModelAndView mav = new ModelAndView("myProject/myCollect/detail");
        mav.addObject("project", project);

        User projectCreator = userService.getUser(project.getUserId());
        mav.addObject("projectCreator", projectCreator);

        boolean userIsJoined = proService.containsSignInfo(user.getId(), project.getId());
        mav.addObject("userIsJoined", userIsJoined);

        return mav;
    }

    @GetMapping("myProject/myPost/detail")
    public ModelAndView getPostProjectDetailPage(
            HttpSession session, @RequestParam Integer proId) {

        Project project = proService.getProject(proId);
        User user = (User) session.getAttribute("user");

        project.setIsCollected(proService.containsCollection(user.getId(), proId));

        return new ModelAndView("myProject/myPost/detail", "project", project);
    }

    @GetMapping("myProject/myPost/editDetail")
    public ModelAndView getProjectEditDetailPage(
            HttpSession session, @RequestParam Integer proId) {

        Project project = proService.getProject(proId);
        User user = (User) session.getAttribute("user");
        project.setIsCollected(proService.containsCollection(user.getId(), proId));

        return new ModelAndView("myProject/myPost/editDetail", "project", project);
    }

    @GetMapping("myProject/myPost/signInfo")
    public ModelAndView getSignInfosPage(@RequestParam Integer proId) {
        Project project = proService.getProject(proId);
        List<SignInfo> signInfos = siService.getSignInfos(proId);

        ModelAndView mav = new ModelAndView("myProject/myPost/signInfo");
        mav.addObject("project", project);
        mav.addObject("signInfos", signInfos);

        return mav;
    }

    @GetMapping("signInfo")
    public ModelAndView getSignInfoDetail(
            HttpSession session, @RequestParam Integer signId) {
        ModelAndView mav = new ModelAndView("myProject/myPost/signDetail");

        SignInfo signInfo = siService.getSignInfo(signId);
        User signUser = userService.getUser(signInfo.getUid());

        mav.addObject("signUser", signUser);
        mav.addObject("signInfo", signInfo);

        return mav;
    }

    @GetMapping("project/toJoin")
    public ModelAndView getToJoinPage(HttpSession session,
            @RequestParam Integer proId,
            @RequestParam("uid") Integer userId) {

        ModelAndView mav = new ModelAndView("myProject/myCollect/toJoin");

        Project project = proService.getProject(proId);
        project.setIsCollected(proService.containsCollection(userId, proId));

        User user = (User) session.getAttribute("user");

        mav.addObject("project", project);
        mav.addObject("user", user);

        return mav;
    }

}
