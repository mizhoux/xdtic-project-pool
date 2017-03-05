package wenjing.xdtic.action;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wenjing.xdtic.model.Message;
import wenjing.xdtic.model.PagingModel;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.RespCode;
import wenjing.xdtic.model.SignInfo;
import wenjing.xdtic.service.MessageService;
import wenjing.xdtic.service.ProjectService;
import wenjing.xdtic.service.SignInfoService;
import wenjing.xdtic.service.UserService;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@RestController
@RequestMapping("fn")
public class ProjectFunction {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService proService;

    @Autowired
    private MessageService msgService;

    @Autowired
    private SignInfoService siService;

    @PostMapping(value = "project/post", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode postProject(
            @RequestParam Integer uid,
            @RequestParam String title, Project project) {

        Project.syncDataForBack(project);

        project.setUserId(uid);
        project.setName(title);
        project.setUsername(userService.getUsername(uid));

        boolean success = proService.addProject(project);
        if (success) {
            Message message = Message.of(project, Message.Type.POST);
            msgService.addMessage(message);
        }

        return success ? RespCode.OK : RespCode.ERROR;
    }

    @PostMapping(value = "project/update", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateProject(
            @RequestParam("uid") Integer userId,
            @RequestParam boolean reject, Project project) {

        Project.syncDataForBack(project);
        project.setUserId(userId);

        if (reject) { // 是之前被拒绝的项目
            project.setStatus("check");
        }

        boolean success = proService.updateProject(project);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("project/collect")
    public RespCode collectProject(
            @RequestParam("userid") Integer userId, @RequestParam Integer proId) {
        boolean success = proService.collectProject(userId, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("project/uncollect")
    public RespCode uncollectProject(
            @RequestParam("userid") Integer userId, @RequestParam Integer proId) {
        boolean success = proService.uncollectProject(userId, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("get/project/myJoin")
    public PagingModel<Project> getMyJoiningProjects(
            @RequestParam("uid") Integer userId,
            @RequestParam int pageNum, @RequestParam int pageSize) {

        return proService.getPagingJoinedProjects(userId, pageNum, pageSize);
    }

    @GetMapping("get/project/myCollect")
    public PagingModel<Project> getMyCollectedProjects(
            @RequestParam("uid") Integer userId,
            @RequestParam int pageNum, @RequestParam int pageSize) {

        return proService.getPagingCollectedProjects(userId, pageNum, pageSize);
    }

    @GetMapping("get/project/myPost")
    public PagingModel<Project> getMyPostedProjects(
            @RequestParam("uid") Integer userId,
            @RequestParam int pageNum, @RequestParam int pageSize) {

        return proService.getPagingPostedProjects(userId, pageNum, pageSize);
    }

    @GetMapping("get/project")
    public PagingModel<Project> getAcceptedProjects(
            @RequestParam("userid") Integer userId,
            @RequestParam("keyWords") String keyword,
            @RequestParam int pageNum, @RequestParam int pageSize) {

        return proService.getPagingAcceptedProjects(keyword, pageNum, pageSize, userId);
    }

    @ResponseBody
    @GetMapping("get/hotProject")
    public HashMap<String, Object> getHotProjects(
            @RequestParam("userid") Integer userId,
            @RequestParam("keyWords") String keyword, @RequestParam int hotSize) {

        List<Project> projects = proService.getHotProjects(keyword, hotSize, userId);

        HashMap<String, Object> hotProjects = new HashMap<>(2);
        hotProjects.put("hotSize", projects.size());
        hotProjects.put("projects", projects);

        return hotProjects;
    }

    @PostMapping("project/toJoin")
    public RespCode toJoinProject(SignInfo signInfo) {
        SignInfo.syncDataForBack(signInfo);

        boolean success = siService.addSignInfo(signInfo);
        if (success) {
            Project project = proService.getProject(signInfo.getProId());
            Message message = Message.of(project, Message.Type.JOIN);
            msgService.addMessage(message);
        }

        return success ? RespCode.OK : RespCode.ERROR;
    }

}
