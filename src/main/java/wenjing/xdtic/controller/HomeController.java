package wenjing.xdtic.controller;

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
import wenjing.xdtic.model.User;

/**
 *
 * @author admin
 */
@Controller
public class HomeController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @RequestMapping({"/", "index", "home", "login"})
    public String index() {
        return "/page/user/login";
    }

    @RequestMapping("user")
    public String index(Integer userid, HttpSession session) {
        User user = userDao.getUser(userid);
        session.setAttribute("user", user);

        return "page/user/center";
    }

    @RequestMapping("myProject")
    public String getMyProjectPage() {
        return "/page/myProject/myProject";
    }

    @RequestMapping("myProject/{myProjectType}/detail")
    public ModelAndView getProjectDetailPage(HttpSession session,
            @PathVariable String myProjectType, @RequestParam Integer proId) {
        Project project = projectDao.getProject(proId);
        User user = (User) session.getAttribute("user");
        boolean isCollected = projectDao.isProjectCollected(user.getId(), proId);
        project.setIsCollected(isCollected);
        return new ModelAndView("/page/myProject/myPost/detail", "project", project);
    }
    
    @RequestMapping("myProject/{myProjectType}/editDetail")
    public ModelAndView getProjectEditDetailPage(HttpSession session,
            @PathVariable String myProjectType, @RequestParam Integer proId) {
        Project project = projectDao.getProject(proId);
        User user = (User) session.getAttribute("user");
        boolean isCollected = projectDao.isProjectCollected(user.getId(), proId);
        project.setIsCollected(isCollected);
        return new ModelAndView("/page/myProject/myPost/editDetail", "project", project);
    }
    
}
