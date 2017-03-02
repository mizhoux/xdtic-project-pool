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
    private ProjectDao projectDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SignInfoDao signInfoDao;

    @PostMapping(value = "project/post", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode postProject(
            @RequestParam Integer uid,
            @RequestParam String title, Project project) {

        project.setUserid(uid);
        project.setProname(title);
        Project.syscDataForBack(project);

        boolean success = projectDao.addProject(project);
        if (success) {
            Message message = Message.of(project, Message.Type.POST);
            messageDao.addMessage(message);
        }

        return success ? RespCode.OK : RespCode.ERROR;
    }

    @PostMapping(value = "project/update", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateProject(@RequestParam Integer uid, Project project) {

        project.setUserid(uid);
        Project.syscDataForBack(project);

        boolean success = projectDao.updateProject(project);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("project/collect")
    public RespCode collectProject(
            @RequestParam Integer userid, @RequestParam Integer proId) {
        boolean success = projectDao.collectProject(userid, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("project/uncollect")
    public RespCode uncollectProject(
            @RequestParam Integer userid, @RequestParam Integer proId) {
        boolean success = projectDao.uncollectProject(userid, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("get/project/myJoin")
    public PagingModel<Project> getMyJoiningProjects(
            @RequestParam Integer uid,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        int offset = pageNum * pageSize;

        Supplier<List<Project>> projectsSupplier = () -> projectDao.getJoinedProjects(uid, offset, pageSize);
        Supplier<Long> countSupplier = () -> projectDao.getJoinedProjectsCount(uid);

        return PagingModel.of(projectsSupplier, "projects", countSupplier, pageNum, pageSize);
    }

    @GetMapping("get/project/myCollect")
    public PagingModel<Project> getMyCollectedProjects(
            @RequestParam Integer uid,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        int offset = pageNum * pageSize;

        Supplier<List<Project>> projectsSupplier = () -> projectDao.getCollectedProjects(uid, offset, pageSize);
        Supplier<Long> countSupplier = () -> projectDao.getCollectedProjectsCount(uid);

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
            @RequestParam Integer userid, @RequestParam String keyWords,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        int offset = pageNum * pageSize;

        Supplier<List<Project>> projectsSupplier = () -> projectDao.getAcceptedProjects(userid, keyWords, offset, pageSize);
        Supplier<Long> countSupplier = () -> projectDao.getAcceptedProjectsCount(keyWords);

        return PagingModel.of(projectsSupplier, "projects", countSupplier, pageNum, pageSize);
    }

    @ResponseBody
    @GetMapping("get/hotProject")
    public HashMap<String, Object> getHotProjects(
            @RequestParam Integer userid,
            @RequestParam String keyWords, @RequestParam Integer hotSize) {

        List<Project> projects = projectDao.getHotProjects(userid, keyWords, hotSize);

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
