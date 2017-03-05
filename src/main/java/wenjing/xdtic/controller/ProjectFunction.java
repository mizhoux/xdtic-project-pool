package wenjing.xdtic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wenjing.xdtic.dao.MessageDao;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.SignInfoDao;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.Message;
import wenjing.xdtic.model.PagingModel;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.RespCode;
import wenjing.xdtic.model.SignInfo;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@RestController
@RequestMapping("fn")
public class ProjectFunction {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SignInfoDao signInfoDao;

    @PostMapping(value = "project/post", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode postProject(
            @RequestParam Integer uid,
            @RequestParam String title, Project project) {

        Project.syncDataForBack(project);

        project.setUserId(uid);
        project.setName(title);
        project.setUsername(userDao.getUsername(uid));

        boolean success = projectDao.addProject(project);
        if (success) {
            Message message = Message.of(project, Message.Type.POST);
            messageDao.addMessage(message);
        }

        return success ? RespCode.OK : RespCode.ERROR;
    }

    @PostMapping(value = "project/update", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateProject(
            @RequestParam("uid") Integer userId,
            @RequestParam boolean reject, Project project) {

        Project.syncDataForBack(project);
        project.setUserId(userId);

        boolean success;
        if (reject) {
            project.setStatus("check");
            success = projectDao.updateProjectWithStatus(project);
        } else {
            success = projectDao.updateProject(project);
        }

        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("project/collect")
    public RespCode collectProject(
            @RequestParam("userid") Integer userId, @RequestParam Integer proId) {
        boolean success = projectDao.collectProject(userId, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("project/uncollect")
    public RespCode uncollectProject(
            @RequestParam("userid") Integer userId, @RequestParam Integer proId) {
        boolean success = projectDao.uncollectProject(userId, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("get/project/myJoin")
    public PagingModel<Project> getMyJoiningProjects(
            @RequestParam("uid") Integer userId,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        int offset = pageNum * pageSize;

        Supplier<List<Project>> projectsSupplier = () -> projectDao.getJoinedProjects(userId, offset, pageSize);
        Supplier<Long> countSupplier = () -> projectDao.getJoinedProjectsCount(userId);

        return PagingModel.of(projectsSupplier, "projects", countSupplier, pageNum, pageSize);
    }

    @GetMapping("get/project/myCollect")
    public PagingModel<Project> getMyCollectedProjects(
            @RequestParam("uid") Integer userId,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        int offset = pageNum * pageSize;

        Supplier<List<Project>> projectsSupplier = () -> projectDao.getCollectedProjects(userId, offset, pageSize);
        Supplier<Long> countSupplier = () -> projectDao.getCollectedProjectsCount(userId);

        return PagingModel.of(projectsSupplier, "projects", countSupplier, pageNum, pageSize);
    }

    @GetMapping("get/project/myPost")
    public PagingModel<Project> getMyPostedProjects(
            @RequestParam Integer uid,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        int offset = pageNum * pageSize;

        Supplier<List<Project>> projectsSupplier = () -> projectDao.getPostedProjects(uid, offset, pageSize);
        Supplier<Long> countSupplier = () -> projectDao.getPostedProjectsCount(uid);

        return PagingModel.of(projectsSupplier, "projects", countSupplier, pageNum, pageSize);
    }

    @GetMapping("get/project")
    public PagingModel<Project> getAcceptedProjects(
            @RequestParam("userid") Integer userId,
            @RequestParam("keyWords") String keyword,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        int offset = pageNum * pageSize;

        Supplier<List<Project>> projectsSupplier = () -> projectDao.getAcceptedProjects(keyword, offset, pageSize, userId);
        Supplier<Long> countSupplier = () -> projectDao.getAcceptedProjectsCount(keyword);

        return PagingModel.of(projectsSupplier, "projects", countSupplier, pageNum, pageSize);
    }

    @ResponseBody
    @GetMapping("get/hotProject")
    public HashMap<String, Object> getHotProjects(
            @RequestParam("userid") Integer userId,
            @RequestParam("keyWords") String keyword, @RequestParam Integer hotSize) {

        List<Project> projects = projectDao.getHotProjects(keyword, hotSize, userId);

        HashMap<String, Object> hotProjects = new HashMap<>(2);
        hotProjects.put("hotSize", projects.size());
        hotProjects.put("projects", projects);

        return hotProjects;
    }

    @PostMapping("project/toJoin")
    public RespCode toJoinProject(SignInfo signInfo) {
        SignInfo.syncDataForBack(signInfo);

        boolean success = signInfoDao.addSignInfo(signInfo);
        if (success) {
            Project project = projectDao.getProject(signInfo.getProId());
            Message message = Message.of(project, Message.Type.JOIN);
            messageDao.addMessage(message);
        }

        return success ? RespCode.OK : RespCode.ERROR;
    }

}
