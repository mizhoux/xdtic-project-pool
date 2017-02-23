package wenjing.xdtic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.SignInfoDao;
import wenjing.xdtic.model.HotProjects;
import wenjing.xdtic.model.PagingProjects;
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
    private SignInfoDao signInfoDao;

    @RequestMapping(value = "project/post", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode postProject(
            @RequestParam(name = "uid") Integer userId,
            @RequestParam(name = "title") String proname,
            @RequestParam String concat, @RequestParam String tag,
            @RequestParam String promassage, @RequestParam String prowant) {

        if (userId == null) {
            return RespCode.ERROR;
        }

        boolean addProjectSucc
                = projectDao.addProject(userId, tag, proname, promassage, prowant, concat);
        return addProjectSucc ? RespCode.OK : RespCode.ERROR;
    }

    @RequestMapping(value = "project/update", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateProject(
            @RequestParam(name = "uid") Integer userId,
            @RequestParam Integer proId, @RequestParam String concat,
            @RequestParam String promassage, @RequestParam String prowant) {
        boolean addProjectSucc
                = projectDao.updateProject(userId, proId, promassage, prowant, concat);
        return addProjectSucc ? RespCode.OK : RespCode.ERROR;
    }

    @RequestMapping("project/collect")
    public RespCode collectProject(@RequestParam Integer userid, @RequestParam Integer proId) {
        boolean success = projectDao.collectProject(userid, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @RequestMapping("project/uncollect")
    public RespCode uncollectProject(@RequestParam Integer userid, @RequestParam Integer proId) {
        boolean success = projectDao.uncollectProject(userid, proId);
        return success ? RespCode.OK : RespCode.ERROR;
    }

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

    @GetMapping("get/project")
    public PagingProjects getProjects(@RequestParam Integer userid,
            @RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String keyWords) {

        int offset = pageNum * pageSize;
        List<Project> projects = projectDao.getProjects(userid, keyWords, offset, pageSize);

        PagingProjects pagingProjects = new PagingProjects();
        pagingProjects.setProjects(projects);

        long count = projectDao.getProjectsCount(keyWords);
        if ((pageNum + 1) * pageSize >= count) {
            pagingProjects.setHasMore(false);
        } else {
            pagingProjects.setHasMore(true);
        }

        pagingProjects.setPageNum(pageNum);
        pagingProjects.setSize(projects.size());

        return pagingProjects;
    }

    @GetMapping("get/hotProject")
    public HotProjects getHotProjects(@RequestParam Integer userid,
            @RequestParam Integer hotSize, @RequestParam String keyWords) {
        
        List<Project> projects = projectDao.getHotProjects(userid, keyWords, hotSize);

        HotProjects hotProjects = new HotProjects(projects.size(), projects);
        return hotProjects;
    }

    @PostMapping("project/toJoin")
    public RespCode toJoinProject(SignInfo signInfo) {
        boolean success = signInfoDao.add(signInfo);
        return success ? RespCode.OK : RespCode.ERROR;
    }

}
