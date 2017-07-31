package xdtic.projpool.controller;

import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xdtic.projpool.model.PagingModel;
import xdtic.projpool.model.Project;
import xdtic.projpool.model.RespCode;
import xdtic.projpool.model.SignInfo;
import xdtic.projpool.service.ProjectService;
import xdtic.projpool.service.SignInfoService;

/**
 * Project Function
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Validated
@RestController
@RequestMapping("fn")
public class ProjectFunction {

    @Autowired
    private ProjectService proService;

    @Autowired
    private SignInfoService siService;

    @PostMapping(value = "project/post",
            consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode postProject(@Valid Project project) {
        return proService.addProject(project)
                .map(p -> RespCode.OK).orElse(RespCode.ERROR);
    }

    @PostMapping(value = "project/update",
            consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateProject(
            @RequestParam Integer id,
            @Size(min = 10, message = "项目内容至少 10 个字")
            @RequestParam String content,
            @Size(min = 6, message = "招聘信息最少 6 个字")
            @RequestParam String recruit,
            @Size(min = 11, message = "联系方式最少 11 个字")
            @RequestParam String contact,
            @RequestParam("uid") Integer userId,
            @RequestParam boolean reject) {

        Project project = Project.builder().id(id).userId(userId)
                .content(content).recruit(recruit).contact(contact).build();

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
