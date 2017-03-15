package wenjing.xdtic.service;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private static final String CACHE_NAME = "project";

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private MessageDao messageDao;

    public boolean addProject(Project project) {

        project.setUsername(userDao.getUsername(project.getUserId()));

        boolean success = projectDao.addProject(project);
        if (success) {
            Message message = Message.of(project, Message.Type.POST);
            messageDao.addMessage(message);
        }

        return success;
    }

    @Cacheable(value = CACHE_NAME, key = "#id", unless = "#result == null")
    public Optional<Project> getProject(Integer id) {
        return projectDao.getProject(id).map(this::syncDataForFront);
    }

    /**
     * 从数据中按分页条件获取审核通过的项目
     *
     * @param keyword 搜索关键字，默认为 ""
     * @param pageNum 此时的页数
     * @param pageSize 每页的元素数量
     * @param userId 用来判断项目是否已经被该用户收藏
     * @return
     */
    public List<Project> getAcceptedProjects(
            String keyword, int pageNum, int pageSize, Integer userId) {

        List<Project> projects = getAcceptedProjects(keyword, pageNum, pageSize);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(p -> p.setIsCollected(collectedProIds.contains(p.getId())));

        return projects;
    }

    /**
     * 从数据中按分页条件获取审核通过的项目，不需要判断该项目是否被某个用户收藏
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Project> getAcceptedProjects(String keyword, int pageNum, int pageSize) {
        int offset = pageNum * pageSize;

        List<Project> projects = projectDao.getAcceptedProjects(keyword, offset, pageSize);
        projects.forEach(this::syncDataForFront);

        return projects;
    }

    public long countAcceptedProjects(String keyword) {
        return projectDao.countAcceptedProjects(keyword);
    }

    public Map<String, Object> getHotProjects(String keyword, int hotSize, Integer userId) {

        List<Project> projects = projectDao.getHotProjects(keyword, hotSize, userId);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            syncDataForFront(project);
        });

        return ImmutableMap.of("hotSize", projects.size(), "projects", projects);
    }

    public List<Project> getUncheckedProjects(String keyword, int pageNum, int pageSize) {

        int offset = pageNum * pageSize;
        List<Project> projects = projectDao.getUncheckedProjects(keyword, offset, pageSize);
        projects.forEach(this::syncDataForFront);

        return projects;
    }

    public long countUncheckedProjects(String keyword) {
        return projectDao.countUncheckedProjects(keyword);
    }

    public List<Project> getPostedProjects(Integer userId, int pageNum, int pageSize) {

        int offset = pageNum * pageSize;
        List<Project> projects = projectDao.getPostedProjects(userId, offset, pageSize);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            syncDataForFront(project);
        });

        return projects;
    }

    public long countPostedProjects(Integer userId) {
        return projectDao.countPostedProjects(userId);
    }

    public List<Project> getCollectedProjects(Integer userId, int pageNum, int pageSize) {

        int offset = pageNum * pageSize;
        List<Project> projects = projectDao.getCollectedProjects(userId, offset, pageSize);
        projects.forEach(project -> {
            project.setIsCollected(true);
            syncDataForFront(project);
        });

        return projects;
    }

    public long countCollectedProjects(Integer userId) {
        return projectDao.countCollectedProjects(userId);
    }

    public List<Project> getJoinedProjects(Integer userId, int pageNum, int pageSize) {

        int offset = pageNum * pageSize;
        List<Project> projects = projectDao.getJoinedProjects(userId, offset, pageSize);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            syncDataForFront(project);
        });

        return projects;
    }

    public long countJoinedProjects(Integer userId) {
        return projectDao.countJoinedProjects(userId);
    }

    public boolean addCollection(Integer userId, Integer proId) {
        return projectDao.addCollection(userId, proId);
    }

    public boolean deleteCollection(Integer userId, Integer proId) {
        return projectDao.deleteCollection(userId, proId);
    }

    public boolean containsCollection(Integer userId, Integer proId) {
        return projectDao.containsCollection(userId, proId);
    }

    public boolean containsSignInfo(Integer userId, Integer proId) {
        return projectDao.containsSignInfo(userId, proId);
    }

    public boolean deleteProject(Integer proId) {
        return projectDao.deleteProject(proId);
    }

    public PagingModel<Project> getPagingUncheckedProjects(String keyword, int pageNum, int pageSize) {

        Supplier<Long> totalNumOfProjects = () -> countUncheckedProjects(keyword);
        Supplier<List<Project>> projects = () -> getUncheckedProjects(keyword, pageNum, pageSize);

        return PagingModel.of("projects", projects, totalNumOfProjects, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingAcceptedProjects(
            String keyword, int pageNum, int pageSize, Integer userId) {

        Supplier<Long> totalNumOfProjects = () -> countAcceptedProjects(keyword);
        Supplier<List<Project>> projects = () -> getAcceptedProjects(keyword, pageNum, pageSize, userId);

        return PagingModel.of("projects", projects, totalNumOfProjects, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingCollectedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> totalNumOfProjects = () -> countCollectedProjects(userId);
        Supplier<List<Project>> projects = () -> getCollectedProjects(userId, pageNum, pageSize);

        return PagingModel.of("projects", projects, totalNumOfProjects, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingPostedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> totalNumOfProjects = () -> countPostedProjects(userId);
        Supplier<List<Project>> projects = () -> getPostedProjects(userId, pageNum, pageSize);

        return PagingModel.of("projects", projects, totalNumOfProjects, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingAcceptedProjects(String keyword, int pageNum, int pageSize) {

        Supplier<Long> totalNumOfProjects = () -> countAcceptedProjects(keyword);
        Supplier<List<Project>> projects = () -> getAcceptedProjects(keyword, pageNum, pageSize);

        return PagingModel.of("projects", projects, totalNumOfProjects, pageNum, pageSize);
    }

    public PagingModel<Project> getPagingJoinedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> totalNumOfProjects = () -> countJoinedProjects(userId);
        Supplier<List<Project>> projects = () -> getJoinedProjects(userId, pageNum, pageSize);

        return PagingModel.of("projects", projects, totalNumOfProjects, pageNum, pageSize);
    }

    @CacheEvict(value = CACHE_NAME, key = "#project.id")
    public boolean updateProject(Project project, boolean reject) {
        if (reject) {
            project.setStatus("check");
            return projectDao.updateProjectWithStatus(project);
        }

        return projectDao.updateProject(project);
    }

    @CacheEvict(value = CACHE_NAME, key = "#proId")
    public boolean updateProjectByOperation(Integer proId, String operation, String comment) {
        boolean success = false;

        switch (operation) {
            case "reject":
                success = projectDao.updateProjectStatus(proId, "reject");
                if (success) {
                    getProject(proId)
                            .map(p -> Message.of(p, Message.Type.REJECT, comment))
                            .ifPresent(message -> messageDao.addMessage(message));
                }
                break;
            case "accept":
                success = projectDao.updateProjectStatus(proId, "pass");
                if (success) {
                    getProject(proId)
                            .map(p -> Message.of(p, Message.Type.PASS))
                            .ifPresent(message -> messageDao.addMessage(message));
                }
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

    public Project syncDataForBack(Project project) {

        project.setId(project.getProId());
        project.setUserId(project.getUserid());
        project.setName(project.getProname());
        project.setContent(project.getPromassage());
        project.setRecruit(project.getProwant());
        project.setContact(project.getConcat());
        project.setStatus(project.getStatu());

        return project;
    }

    public Project syncDataForFront(Project project) {

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

        return project;
    }
}
