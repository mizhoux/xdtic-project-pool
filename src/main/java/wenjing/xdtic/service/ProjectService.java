package wenjing.xdtic.service;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    private ProjectDao proDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    public boolean addProject(Project project) {
        syncDataForBack(project);
        String username = userDao.getUsername(project.getUserId());
        project.setUsername(username);

        boolean success = proDao.addProject(project);
        if (success) {
            Message message = Message.of(project, Message.Type.POST);
            messageDao.addMessage(message);
        }
        return success;
    }

    @Cacheable(value = "project", key = "#id")
    public Project getProject(Integer id) {
        Project project = proDao.getProject(id);
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

        List<Project> projects = proDao.getAcceptedProjects(keyword, offset, size);
        projects.forEach(this::syncDataForFront);

        return projects;
    }

    public long countAcceptedProjects(String keyword) {
        return proDao.countAcceptedProjects(keyword);
    }

    public Map<String, Object> getHotProjects(String keyword, int hotSize, Integer userId) {

        List<Project> projects = proDao.getHotProjects(keyword, hotSize, userId);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            syncDataForFront(project);
        });

        return ImmutableMap.of("hotSize", projects.size(), "projects", projects);
    }

    public List<Project> getUncheckedProjects(String keyword, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = proDao.getUncheckedProjects(keyword, offset, size);
        projects.forEach(this::syncDataForFront);

        return projects;
    }

    public long countUncheckedProjects(String keyword) {
        return proDao.countUncheckedProjects(keyword);
    }

    public List<Project> getPostedProjects(Integer userId, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = proDao.getPostedProjects(userId, offset, size);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            syncDataForFront(project);
        });

        return projects;
    }

    public long countPostedProjects(Integer userId) {
        return proDao.countPostedProjects(userId);
    }

    public List<Project> getCollectedProjects(Integer userId, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = proDao.getCollectedProjects(userId, offset, size);
        projects.forEach(project -> {
            project.setIsCollected(true);
            syncDataForFront(project);
        });

        return projects;
    }

    public long countCollectedProjects(Integer userId) {
        return proDao.countCollectedProjects(userId);
    }

    public List<Project> getJoinedProjects(Integer userId, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = proDao.getJoinedProjects(userId, offset, size);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            syncDataForFront(project);
        });

        return projects;
    }

    public long countJoinedProjects(Integer userId) {
        return proDao.countJoinedProjects(userId);
    }

    /**
     * 添加收藏
     *
     * @param userId
     * @param proId
     * @return
     */
    public boolean addCollection(Integer userId, Integer proId) {
        return proDao.addCollection(userId, proId);
    }

    public boolean deleteCollection(Integer userId, Integer proId) {
        return proDao.deleteCollection(userId, proId);
    }

    public boolean containsCollection(Integer userId, Integer proId) {
        return proDao.containsCollection(userId, proId);
    }

    public boolean containsSignInfo(Integer userId, Integer proId) {
        return proDao.containsSignInfo(userId, proId);
    }

    public boolean deleteProject(Integer proId) {
        return proDao.deleteProject(proId);
    }

    public PagingModel<Project> getPagingUncheckedProjects(String keyword, int pageNum, int size) {

        Supplier<Long> count = () -> countUncheckedProjects(keyword);
        Supplier<List<Project>> projects = () -> getUncheckedProjects(keyword, pageNum, size);

        return PagingModel.of(projects, "projects", count, pageNum, size);
    }

    public PagingModel<Project> getPagingAcceptedProjects(String keyword, int pageNum, int size, Integer userId) {
        Supplier<Long> count = () -> countAcceptedProjects(keyword);
        Supplier<List<Project>> projects = () -> getAcceptedProjects(keyword, pageNum, size, userId);

        return PagingModel.of(projects, "projects", count, pageNum, size);
    }

    public PagingModel<Project> getPagingCollectedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> count = () -> countCollectedProjects(userId);
        Supplier<List<Project>> projects = () -> getCollectedProjects(userId, pageNum, pageSize);

        return PagingModel.of(projects, "projects", count, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingPostedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> count = () -> countPostedProjects(userId);
        Supplier<List<Project>> projects = () -> getPostedProjects(userId, pageNum, pageSize);

        return PagingModel.of(projects, "projects", count, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingAcceptedProjects(String keyword, int pageNum, int pageSize) {

        Supplier<Long> count = () -> countAcceptedProjects(keyword);
        Supplier<List<Project>> projects = () -> getAcceptedProjects(keyword, pageNum, pageSize);

        return PagingModel.of(projects, "projects", count, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingJoinedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> count = () -> countJoinedProjects(userId);
        Supplier<List<Project>> projects = () -> getJoinedProjects(userId, pageNum, pageSize);

        return PagingModel.of(projects, "projects", count, pageNum, pageSize);
    }

    @CacheEvict(value = "project", key = "#project.id")
    public boolean updateProject(Project project, boolean reject) {
        syncDataForBack(project);

        if (reject) {
            project.setStatus("check");
            return proDao.updateProjectWithStatus(project);
        }

        return proDao.updateProject(project);
    }

    @CacheEvict(value = "project", key = "@proId")
    public boolean updateProjectByOperation(Integer proId, String operation, String comment) {
        boolean success = false;

        switch (operation) {
            case "reject":
                success = proDao.updateProjectStatus(proId, "reject");
                Message reject = Message.of(getProject(proId), Message.Type.REJECT, comment);
                messageDao.addMessage(reject);
                break;
            case "accept":
                success = proDao.updateProjectStatus(proId, "pass");
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
        List<Integer> collectedProjectIds = proDao.getCollectedProjectIds(userId);
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
