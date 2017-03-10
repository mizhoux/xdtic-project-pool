package wenjing.xdtic.action;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wenjing.xdtic.model.PagingModel;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.RespCode;
import wenjing.xdtic.model.SignInfo;
import wenjing.xdtic.service.ProjectService;
import wenjing.xdtic.service.SignInfoService;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@RestController
@RequestMapping("fn")
public class ProjectFunction {

    @Autowired
    private ProjectService proService;

    @Autowired
    private SignInfoService siService;

    @PostMapping(value = "project/post", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode postProject(
            @RequestParam Integer uid,
            @RequestParam String title, Project project) {

        project.setUserid(uid); // 兼容前端
        project.setProname(title); // 兼容前端

        boolean success = proService.addProject(project);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @PostMapping(value = "project/update", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateProject(
            @RequestParam("uid") Integer userId,
            @RequestParam boolean reject, Project project) {

        project.setUserid(userId); // 兼容前端

        boolean success = proService.updateProject(project, reject);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("project/collect")
    public RespCode collectProject(
            @RequestParam("userid") Integer userId, @RequestParam Integer proId) {
        boolean success = proService.addCollection(userId, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @GetMapping("project/uncollect")
    public RespCode uncollectProject(
            @RequestParam("userid") Integer userId, @RequestParam Integer proId) {
        boolean success = proService.deleteCollection(userId, proId);
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
    public Map<String, Object> getHotProjects(
            @RequestParam("userid") Integer userId,
            @RequestParam("keyWords") String keyword, @RequestParam int hotSize) {

        return proService.getHotProjects(keyword, hotSize, userId);
    }

    @PostMapping("project/toJoin")
    public RespCode toJoinProject(SignInfo signInfo) {
        boolean success = siService.addSignInfo(signInfo);
        return success ? RespCode.OK : RespCode.ERROR;
    }

}
