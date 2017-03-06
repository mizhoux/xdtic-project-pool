package wenjing.xdtic.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenjing.xdtic.dao.MessageDao;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.Message;
import wenjing.xdtic.model.PagingModel;
import wenjing.xdtic.model.Project;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    public boolean addProject(Project project) {
        syncDataForBack(project);
        String username = userDao.getUsername(project.getUserId());
        project.setUsername(username);

        boolean success = projectDao.addProject(project);
        if (success) {
            Message message = Message.of(project, Message.Type.POST);
            messageDao.addMessage(message);
        }
        return success;
    }

    public boolean updateProject(Project project, boolean reject) {
        syncDataForBack(project);

        if (reject) {
            project.setStatus("check");
            return projectDao.updateProjectWithStatus(project);
        }
        
        return projectDao.updateProject(project);
    }

    public Project getProject(Integer id) {
        Project project = projectDao.getProject(id);
        syncDataForFront(project);

        return project;
    }

    /**
     * 从数据中按分页条件获取审核通过的项目
     *
     * @param keyword 搜索关键字，默认为 ""
     * @param pageNum 此时的页数
     * @param size 每页的元素数量
     * @param userId 用来判断项目是否已经被该用户收藏
     * @return
     */
    public List<Project> getAcceptedProjects(
            String keyword, int pageNum, int size, Integer userId) {

        List<Project> projects = getAcceptedProjects(keyword, pageNum, size);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(p -> p.setIsCollected(collectedProIds.contains(p.getId())));

        return projects;
    }

    /**
     * 从数据中按分页条件获取审核通过的项目，不需要判断该项目是否被某个用户收藏
     *
     * @param keyword
     * @param pageNum
     * @param size
     * @return
     */
    public List<Project> getAcceptedProjects(String keyword, int pageNum, int size) {
        int offset = pageNum * size;

        List<Project> projects = projectDao.getAcceptedProjects(keyword, offset, size);
        projects.forEach(this::syncDataForFront);

        return projects;
    }

    public long getAcceptedProjectsCount(String keyword) {
        return projectDao.getAcceptedProjectsCount(keyword);
    }

    public HashMap<String, Object> getHotProjects(String keyword, int hotSize, Integer userId) {

        List<Project> projects = projectDao.getHotProjects(keyword, hotSize, userId);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            syncDataForFront(project);
        });

        HashMap<String, Object> hotProjects = new HashMap<>(2);
        hotProjects.put("hotSize", projects.size());
        hotProjects.put("projects", projects);

        return hotProjects;
    }

    public List<Project> getUncheckedProjects(String keyword, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getUncheckedProjects(keyword, offset, size);
        projects.forEach(this::syncDataForFront);

        return projects;
    }

    public long getUncheckedProjectsCount(String keyword) {
        return projectDao.getUncheckedProjectsCount(keyword);
    }

    public List<Project> getPostedProjects(Integer userId, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getPostedProjects(userId, offset, size);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            syncDataForFront(project);
        });

        return projects;
    }

    public long getPostedProjectsCount(Integer userId) {
        return projectDao.getPostedProjectsCount(userId);
    }

    public List<Project> getCollectedProjects(Integer userId, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getCollectedProjects(userId, offset, size);
        projects.forEach(project -> {
            project.setIsCollected(true);
            syncDataForFront(project);
        });

        return projects;
    }

    public long getCollectedProjectsCount(Integer userId) {
        return projectDao.getCollectedProjectsCount(userId);
    }

    public List<Project> getJoinedProjects(Integer userId, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getJoinedProjects(userId, offset, size);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            syncDataForFront(project);
        });

        return projects;
    }

    public long getJoinedProjectsCount(Integer userId) {
        return projectDao.getJoinedProjectsCount(userId);
    }

    public boolean collectProject(Integer userId, Integer proId) {
        return projectDao.collectProject(userId, proId);
    }

    public boolean uncollectProject(Integer userId, Integer proId) {
        return projectDao.uncollectProject(userId, proId);
    }

    public boolean isCollected(Integer userId, Integer proId) {
        return projectDao.isCollected(userId, proId);
    }

    public boolean isJoined(Integer userId, Integer proId) {
        return projectDao.isJoined(userId, proId);
    }

    public boolean acceptProject(Integer proId) {
        return projectDao.updateProjectStatus(proId, "pass");
    }

    public boolean rejectProject(Integer proId) {
        return projectDao.updateProjectStatus(proId, "reject");
    }

    public boolean deleteProject(Integer proId) {
        return projectDao.deleteProject(proId);
    }

    public PagingModel<Project> getPagingUncheckedProjects(String keyword, int pageNum, int size) {

        Supplier<Long> count = () -> getUncheckedProjectsCount(keyword);
        Supplier<List<Project>> projects = () -> getUncheckedProjects(keyword, pageNum, size);

        return PagingModel.of(projects, "projects", count, pageNum, size);
    }

    public PagingModel<Project> getPagingAcceptedProjects(String keyword, int pageNum, int size, Integer userId) {
        Supplier<Long> count = () -> getAcceptedProjectsCount(keyword);
        Supplier<List<Project>> projects = () -> getAcceptedProjects(keyword, pageNum, size, userId);

        return PagingModel.of(projects, "projects", count, pageNum, size);
    }

    public PagingModel<Project> getPagingCollectedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> count = () -> getCollectedProjectsCount(userId);
        Supplier<List<Project>> projects = () -> getCollectedProjects(userId, pageNum, pageSize);

        return PagingModel.of(projects, "projects", count, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingPostedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> count = () -> getPostedProjectsCount(userId);
        Supplier<List<Project>> projects = () -> getPostedProjects(userId, pageNum, pageSize);

        return PagingModel.of(projects, "projects", count, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingAcceptedProjects(String keyword, int pageNum, int pageSize) {

        Supplier<Long> count = () -> getAcceptedProjectsCount(keyword);
        Supplier<List<Project>> projects = () -> getAcceptedProjects(keyword, pageNum, pageSize);

        return PagingModel.of(projects, "projects", count, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingJoinedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> count = () -> getJoinedProjectsCount(userId);
        Supplier<List<Project>> projects = () -> getJoinedProjects(userId, pageNum, pageSize);

        return PagingModel.of(projects, "projects", count, pageNum, pageSize);
    }

    public boolean operateProject(Integer proId, String operation, String comment) {
        boolean success = false;

        switch (operation) {
            case "reject":
                success = rejectProject(proId);
                Message reject = Message.of(getProject(proId), Message.Type.REJECT, comment);
                messageDao.addMessage(reject);
                break;
            case "accept":
                success = acceptProject(proId);
                Message pass = Message.of(getProject(proId), Message.Type.PASS);
                messageDao.addMessage(pass);
                break;
            case "delete":
                success = deleteProject(proId);
                break;
        }

        return success;
    }

    /**
     * 获得所有被用户收藏的项目的 id
     *
     * @param userId 用户 id
     * @return
     */
    private Collection<Integer> getCollectedProjectIds(Integer userId) {
        List<Integer> collectedProjectIds = projectDao.getCollectedProjectIds(userId);
        if (collectedProjectIds.size() > 5) {
            return new HashSet<>(collectedProjectIds);
        }
        return collectedProjectIds;
    }

    public void syncDataForBack(Project project) {
        if (project == null) {
            return;
        }
        project.setId(project.getProId());
        project.setUserId(project.getUserid());
        project.setName(project.getProname());
        project.setContent(project.getPromassage());
        project.setRecruit(project.getProwant());
        project.setContact(project.getConcat());
        project.setStatus(project.getStatu());
    }

    public void syncDataForFront(Project project) {
        if (project == null) {
            return;
        }

        project.setProId(project.getId());
        project.setUserid(project.getUserId());
        project.setProname(project.getName());
        project.setDesc(project.getContent());
        project.setPromassage(project.getContent());
        project.setProwant(project.getRecruit());
        project.setConcat(project.getContact());
        project.setStatu(project.getStatus());

        if (project.getTag() != null) {
            List<String> tags = Arrays.asList(project.getTag().split("&+"));
            project.setTags(tags);
        }
    }
}
