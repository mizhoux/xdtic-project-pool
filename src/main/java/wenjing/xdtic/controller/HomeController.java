package wenjing.xdtic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.SignInfo;
import wenjing.xdtic.model.User;

/**
 *
 * @author mizhou
 */
@Controller
public class HomeController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @RequestMapping({"/", "index", "home", "login"})
    public String index() {
        return "page/user/login";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "page/user/login";
    }

    @RequestMapping("user")
    public String getUserCenterPage(Integer userid, HttpSession session) {
        User user = userDao.getUser(userid);
        session.setAttribute("user", user);

        return "page/user/center";
    }

    @RequestMapping("myProject")
    public String getMyProjectPage() {
        return "page/myProject/myProject";
    }

    @RequestMapping("myProject/postProject")
    public String getPostProjectPage() {
        return "page/myProject/postProject";
    }

    @RequestMapping("project")
    public ModelAndView getProjectDetailPage(HttpSession session,
            @RequestParam Integer proId,
            @RequestParam(required = false) Integer uid) {

        User user = (User) session.getAttribute("user");
        if (user == null && uid != null) { // 主要调试时候用，重新部署时候会重置 session
            user = userDao.getUser(uid);
            session.setAttribute("user", user);
            System.out.println("getProjectDetailPage: get User: " + user);
        }

        Project project = projectDao.getProject(proId);
        boolean isCollected = projectDao.isProjectCollected(user.getId(), proId);
        project.setIsCollected(isCollected);

        Map<String, Object> attrs = new HashMap<>(4);
        attrs.put("project", project);

        User projectCreator = projectDao.getCreator(project);
        attrs.put("projectCreator", projectCreator);

        boolean userIsJoined = projectDao.isUserJoined(user, project);
        attrs.put("userIsJoined", userIsJoined);

        return new ModelAndView("page/myProject/myCollect/detail", attrs);
    }

    @RequestMapping("myProject/myPost/detail")
    public ModelAndView getPostProjectDetailPage(
            HttpSession session, @RequestParam Integer proId,
            @RequestParam(required = false) Integer uid) {

        Project project = projectDao.getProject(proId);
        User user = (User) session.getAttribute("user");

        boolean isCollected = projectDao.isProjectCollected(user.getId(), proId);
        project.setIsCollected(isCollected);

        return new ModelAndView("page/myProject/myPost/detail", "project", project);
    }

    @RequestMapping("myProject/myPost/editDetail")
    public ModelAndView getProjectEditDetailPage(HttpSession session, @RequestParam Integer proId) {
        Project project = projectDao.getProject(proId);
        User user = (User) session.getAttribute("user");
        boolean isCollected = projectDao.isProjectCollected(user.getId(), proId);
        project.setIsCollected(isCollected);
        return new ModelAndView("/page/myProject/myPost/editDetail", "project", project);
    }

    @RequestMapping("myProject/{myProjectType}/signInfo")
    public ModelAndView getSignInfosPage(HttpSession session,
            @PathVariable String myProjectType, @RequestParam Integer proId) {
        List<SignInfo> signInfos = new ArrayList<>();
        return new ModelAndView("page/myProject/myPost/signInfo", "signInfos", signInfos);
    }
}
