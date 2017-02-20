package wenjing.xdtic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.model.HotProjects;
import wenjing.xdtic.model.PagingProjects;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.RespCode;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
@RequestMapping("fn")
public class ProjectFunction {

    @Autowired
    private ProjectDao projectDao;

    @ResponseBody
    @RequestMapping(value = "project/post", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode postProject(
            @RequestParam(name = "uid") Integer userid,
            @RequestParam(name = "title") String proname,
            @RequestParam String concat, @RequestParam String tag,
            @RequestParam String promassage, @RequestParam String prowant) {
        if (userid == null) {
            return RespCode.ERROR;
        }

        //调用dao中addproject()方法向数据库中插入数据
        boolean addProjectSucc
                = projectDao.addProject(userid, tag, proname, promassage, prowant, concat);
        return addProjectSucc ? RespCode.OK : RespCode.ERROR;
    }

    @ResponseBody
    @RequestMapping(value = "project/update", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateProject(
            @RequestParam(name = "uid") Integer userid,
            @RequestParam Integer proId, @RequestParam String concat,
            @RequestParam String promassage, @RequestParam String prowant) {
        boolean addProjectSucc
                = projectDao.updateProject(userid, proId, promassage, prowant, concat);
        return addProjectSucc ? RespCode.OK : RespCode.ERROR;
    }

    @ResponseBody
    @RequestMapping("project/collect")
    public RespCode collectProject(@RequestParam Integer userid, @RequestParam Integer proId) {
        boolean success = projectDao.collectProject(userid, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @ResponseBody
    @RequestMapping("project/uncollect")
    public RespCode uncollectProject(@RequestParam Integer userid, @RequestParam Integer proId) {
        boolean success = projectDao.uncollectProject(userid, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @ResponseBody
    @RequestMapping("get/project/myJoin")
    public PagingProjects getMyJoiningProjects(@RequestParam Integer uid,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        int offset = pageNum * pageSize;
        List<Project> projects = projectDao.getJoiningProjects(uid, offset, pageSize);

        PagingProjects pagingProject = new PagingProjects();
        pagingProject.setProjects(projects);

        long count = projectDao.getJoiningProjectsCount(uid);
        if ((pageNum + 1) * pageSize >= count) {
            pagingProject.setHasMore(false);
        } else {
            pagingProject.setHasMore(true);
        }

        pagingProject.setPageNum(pageNum);
        pagingProject.setSize(projects.size());

        return pagingProject;
    }

    @ResponseBody
    @RequestMapping("get/project/myCollect")
    public PagingProjects getMyCollectedProjects(@RequestParam Integer uid,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        int offset = pageNum * pageSize;
        List<Project> projects = projectDao.getCollectedProjects(uid, offset, pageSize);

        PagingProjects pagingProject = new PagingProjects();
        pagingProject.setProjects(projects);

        long count = projectDao.getCollectedProjectsCount(uid);
        if ((pageNum + 1) * pageSize >= count) {
            pagingProject.setHasMore(false);
        } else {
            pagingProject.setHasMore(true);
        }

        pagingProject.setPageNum(pageNum);
        pagingProject.setSize(projects.size());

        return pagingProject;
    }

    @ResponseBody
    @RequestMapping("get/project/myPost")
    public PagingProjects getMyPostedProjects(@RequestParam Integer uid,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        int offset = pageNum * pageSize;
        List<Project> projects = projectDao.getPostedProjects(uid, offset, pageSize);

        PagingProjects pagingProjects = new PagingProjects();
        pagingProjects.setProjects(projects);

        long count = projectDao.getPostedProjectsCount(uid);
        if ((pageNum + 1) * pageSize >= count) {
            pagingProjects.setHasMore(false);
        } else {
            pagingProjects.setHasMore(true);
        }

        pagingProjects.setPageNum(pageNum);
        pagingProjects.setSize(projects.size());

        return pagingProjects;
    }

    @ResponseBody
    @GetMapping("get/project")
    public PagingProjects getProjects(@RequestParam Integer userid,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String keyWords) {
        int offset = pageNum * pageSize;
        List<Project> projects = projectDao.getProjects(userid, offset, pageSize);

        PagingProjects pagingProjects = new PagingProjects();
        pagingProjects.setProjects(projects);

        long count = projectDao.getProjectsCount();
        if ((pageNum + 1) * pageSize >= count) {
            pagingProjects.setHasMore(false);
        } else {
            pagingProjects.setHasMore(true);
        }

        pagingProjects.setPageNum(pageNum);
        pagingProjects.setSize(projects.size());

        return pagingProjects;
    }

    @ResponseBody
    @GetMapping("get/hotProject")
    public HotProjects getHotProjects(@RequestParam Integer userid,
            @RequestParam Integer hotSize, @RequestParam String keyWords) {
        List<Project> projects = projectDao.getHotProjects(userid, hotSize);
        
        HotProjects hotProjects = new HotProjects(projects.size(), projects);
        return hotProjects;
    }

}
