package wenjing.xdtic.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.SystemassageDao;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.PagingMessages;
import wenjing.xdtic.model.PagingProjects;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.RespCode;
import wenjing.xdtic.model.Systemassage;
import wenjing.xdtic.model.User;

/**
 * api 功能 <br>
 * 功能包括：用户注册、登录、修改个人信息、修改密码，验证用户是否已经存在，获得用户当前的系统消息
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
@RequestMapping("fn") // 和 "/fn" 作用一样，SpringMVC 会自动在前面添加上 /
public class FunctionController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SystemassageDao systemassageDao;

    @Autowired
    private ProjectDao projectDao;

    /**
     * 根据用户名和密码进行注册（以 Form 提交）
     *
     * @param username 用户名
     * @param password 密码
     * @param passConfirm
     * @return
     */
    @RequestMapping(value = "user/register", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String userRegister(
            @RequestParam String username,
            @RequestParam("pass") String password, @RequestParam String passConfirm) {

        boolean addSucc = userDao.addUser(username, password);
        if (addSucc) {
            return "page/user/login";
        }
        return "page/user/register";
    }

    /**
     * 根据用户名和密码进行登录（以 Form 提交）
     *
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return
     */
    @RequestMapping(value = "user/login", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String userLogin(
            HttpSession session,
            @RequestParam String username, @RequestParam String password) {

        User user = userDao.getUser(username, password);
        if (user != null) {
            int msgCount = systemassageDao.countMessages(user.getId());
            user.setHasMsg(msgCount > 0);
            session.setAttribute("user", user);
            return "page/user/center";
        }

        return "page/user/register";
    }

    /**
     * 修改用户密码
     *
     * @param username 用户名
     * @param passOld 旧密码
     * @param passNew 新密码
     * @param passNewConfirm 第二次输入的新密码
     * @return
     */
    @RequestMapping(value = "user/resetPass", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String updateUserPassword(
            @RequestParam String username, @RequestParam String passOld,
            @RequestParam String passNew, @RequestParam String passNewConfirm) {

        if (passNew.equals(passNewConfirm)) {
            boolean updatePassSucc = userDao.updatePassword(username, passOld, passNew);
            if (updatePassSucc) {
                return "page/user/login";
            }
        }
        return ""; // 更新密码不成功
    }

    /**
     * 修改用户个人信息
     *
     * @param user 提交的个人信息（以 Form 提交）
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update/profile", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode updateUserProfile(HttpSession session, User user) {
        boolean updateSucc = userDao.updateUser(user);
        session.setAttribute("user", user);
        return updateSucc ? RespCode.OK : RespCode.ERROR;
    }

    /**
     * 验证用户名是否可用（以 JSON 提交）
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "valid/username", method = POST, consumes = APPLICATION_JSON_VALUE)
    public RespCode validUsername(@RequestBody Map<String, String> params) {
        String username = params.get("username");

        boolean usernameExisted = userDao.containsUsername(username);
        return usernameExisted ? RespCode.ERROR : RespCode.OK;
    }

    /**
     * 根据用户名和密码验证用户是否可注册（以 JSON 提交）
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "valid/user", method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public RespCode validUserByJsonValue(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        User _user = userDao.getUser(username, password);
        return _user == null ? RespCode.ERROR : RespCode.OK;
    }

    /**
     * 根据用户名和密码验证用户是否可注册（以 Form 提交）
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "valid/user", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode validUserByFormValue(
            @RequestParam String username, @RequestParam String password) {

        User _user = userDao.getUser(username, password);
        return _user == null ? RespCode.ERROR : RespCode.OK;
    }

    /**
     * 根据用户的 id 获取系统消息列表
     *
     * @param uid 用户 id
     * @param pageNum 当前页索引
     * @param size 请求的消息数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get/msg", method = GET)
    public PagingMessages getMessages(
            @RequestParam Integer uid,
            @RequestParam Integer pageNum, @RequestParam Integer size) {

        int offset = pageNum * size;
        List<Systemassage> systemassages = systemassageDao.getSystemassageid(uid, offset, size);

        PagingMessages pagingMsgs = new PagingMessages();
        pagingMsgs.setPageNum(pageNum);
        pagingMsgs.setSize(size); // 设置返回的 size 为本次返回消息的数量

        pagingMsgs.setMsgs(systemassages);
        int count = systemassageDao.countMessages(uid);
        if ((pageNum + 1) * size >= count) {
            pagingMsgs.setHasMore(false);
        } else {
            pagingMsgs.setHasMore(true);
        }

        pagingMsgs.setMsgs(systemassages);

        return pagingMsgs;
    }

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

        PagingProjects pagingProject = new PagingProjects();
        pagingProject.setProjects(projects);

        long count = projectDao.getPostedProjectsCount(uid);
        if ((pageNum + 1) * pageSize >= count) {
            pagingProject.setHasMore(false);
        } else {
            pagingProject.setHasMore(true);
        }

        pagingProject.setPageNum(pageNum);
        pagingProject.setSize(projects.size());

        return pagingProject;
    }
}
