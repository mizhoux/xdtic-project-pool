package wenjing.xdtic.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.dao.AdminDao;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.Admin;
import wenjing.xdtic.model.PagingProjects;
import wenjing.xdtic.model.PagingUsers;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.RespCode;
import wenjing.xdtic.model.User;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
@RequestMapping("fn")
public class AdminFunction {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private ProjectDao projectDao;

    @PostMapping(value = "admin/login", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String login(
            HttpServletRequest request, HttpSession session,
            @RequestParam String username, @RequestParam String password) {

        Admin admin = adminDao.getAdmin(username, password);

        if (admin == null) {
            request.setAttribute("loginFail", Boolean.TRUE);
            return "page/admin/login";
        }

        session.setAttribute("admin", admin);
        return "page/admin/index";
    }

    @ResponseBody
    @GetMapping("admin/get/project/uncheck")
    public PagingProjects getUncheckedProjects(
            @RequestParam String keyWords,
            @RequestParam Integer pageNum, @RequestParam Integer size) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getUncheckedProjects(keyWords, offset, size);

        PagingProjects pagingProjects = new PagingProjects();
        pagingProjects.setProjects(projects);
        pagingProjects.setPageNum(pageNum);
        pagingProjects.setSize(size);

        long count = projectDao.getUncheckedProjectsCount(keyWords);
        if ((pageNum + 1) * size >= count) {
            pagingProjects.setHasMore(false);
        } else {
            pagingProjects.setHasMore(true);
        }

        return pagingProjects;
    }

    @ResponseBody
    @PostMapping(value = "admin/project/operate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespCode operateProject(@RequestBody Map<String, String> params) {

        Integer proId = Integer.valueOf(params.get("proId"));
        String operation = params.get("operation");

        boolean success = false;
        switch (operation) {
            case "reject":
                success = projectDao.rejectProject(proId);
                break;
            case "accept":
                success = projectDao.acceptProject(proId);
                break;
            case "delete":
                success = projectDao.deleteProject(proId);
                break;
        }
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @ResponseBody
    @GetMapping("admin/get/project/accept")
    public PagingProjects getAcceptedProjects(
            @RequestParam String keyWords,
            @RequestParam Integer pageNum, @RequestParam Integer size) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getCheckedProjects(keyWords, offset, size);

        PagingProjects pagingProjects = new PagingProjects();
        pagingProjects.setProjects(projects);
        pagingProjects.setPageNum(pageNum);
        pagingProjects.setSize(size);

        long count = projectDao.getCheckedProjectsCount(keyWords);
        if ((pageNum + 1) * size >= count) {
            pagingProjects.setHasMore(false);
        } else {
            pagingProjects.setHasMore(true);
        }

        return pagingProjects;
    }

    @ResponseBody
    @GetMapping("admin/get/user")
    public PagingUsers getUsers(
            @RequestParam String keyWords,
            @RequestParam Integer pageNum, @RequestParam Integer size) {

        int offset = pageNum * size;
        List<User> users = userDao.getUsers(keyWords, offset, size);

        PagingUsers pagingUsers = new PagingUsers();
        pagingUsers.setUsers(users);
        pagingUsers.setPageNum(pageNum);
        pagingUsers.setSize(size);

        long count = userDao.getUsersCount(keyWords);
        if ((pageNum + 1) * size >= count) {
            pagingUsers.setHasMore(false);
        } else {
            pagingUsers.setHasMore(true);
        }

        return pagingUsers;
    }

    @ResponseBody
    @PostMapping("admin/user/delete")
    public RespCode deleteUser(@RequestBody Map<String, Object> params) {
        Object uid = params.get("uid");
        if (uid instanceof List) {
            List<Integer> uids = (List<Integer>) uid;
            for (Integer id : uids) {
                userDao.deleteUser(id);
            }

            return RespCode.OK;
        } else {
            // delete ALL
            return RespCode.ERROR;
        }
    }

}
