package wenjing.xdtic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.SignInfoDao;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.SignInfo;
import wenjing.xdtic.model.User;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
public class ProjectController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private SignInfoDao signInfoDao;

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
            @RequestParam(required = false) Integer proId,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer uid) {

        User user = (User) session.getAttribute("user");

        if (proId == null) {
            proId = id;
        }

        Project project = projectDao.get(proId);
        
        project.setIsCollected(projectDao.isProjectCollected(user.getId(), proId));

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

        Project project = projectDao.get(proId);
        User user = (User) session.getAttribute("user");

        project.setIsCollected(projectDao.isProjectCollected(user.getId(), proId));

        return new ModelAndView("page/myProject/myPost/detail", "project", project);
    }

    @RequestMapping("myProject/myPost/editDetail")
    public ModelAndView getProjectEditDetailPage(HttpSession session, @RequestParam Integer proId) {
        
        Project project = projectDao.get(proId);
        User user = (User) session.getAttribute("user");
        project.setIsCollected(projectDao.isProjectCollected(user.getId(), proId));
        
        return new ModelAndView("/page/myProject/myPost/editDetail", "project", project);
    }

    @GetMapping("myProject/myPost/signInfo")
    public ModelAndView getSignInfosPage(@RequestParam Integer proId) {
        Project project = projectDao.get(proId);
        List<SignInfo> signInfos = signInfoDao.getSignInfos(proId);

        ModelAndView mav = new ModelAndView("page/myProject/myPost/signInfo");
        mav.addObject("project", project);
        mav.addObject("signInfos", signInfos);

        return mav;
    }

    @GetMapping("signInfo")
    public ModelAndView getSignInfoDetail(HttpSession session, @RequestParam Integer signId) {
        ModelAndView mav = new ModelAndView("page/myProject/myPost/signDetail");
        
        SignInfo signInfo = signInfoDao.getSignInfo(signId);
        User signUser = userDao.getUser(signInfo.getUid());

        mav.addObject("signUser", signUser);
        mav.addObject("signInfo", signInfo);

        return mav;
    }

    @GetMapping("project/toJoin")
    public ModelAndView getToJoinPage(HttpSession session,
            @RequestParam Integer proId, @RequestParam Integer uid) {

        ModelAndView mav = new ModelAndView("page/myProject/myCollect/toJoin");

        Project project = projectDao.get(proId);
        project.setIsCollected(projectDao.isProjectCollected(uid, proId));

        User user = (User) session.getAttribute("user");

        mav.addObject("project", project);
        mav.addObject("user", user);

        return mav;
    }

}
