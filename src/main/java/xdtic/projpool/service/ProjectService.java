package xdtic.projpool.service;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xdtic.projpool.dao.MessageMapper;
import xdtic.projpool.dao.ProjectMapper;
import xdtic.projpool.dao.SignInfoMapper;
import xdtic.projpool.dao.UserMapper;
import xdtic.projpool.model.Message;
import xdtic.projpool.model.PagingModel;
import xdtic.projpool.model.Project;

/**
 * Project Service
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class ProjectService {

    private static final String CACHE_NAME = "project";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private SignInfoMapper signInfoMapper;

    // #result.id -> Big bug ???
    //@CachePut(value = CACHE_NAME, key = "#result.id", unless = "#result == null")
    public Optional<Project> addProject(Project project) {

        String username = userMapper.getUsername(project.getUserId());
        if (username == null) {
            return Optional.empty();
        }

        project.setUsername(username);

        int result = projectMapper.addProject(project);
        if (result == 1) {
            int msgResult = messageMapper.addMessage(Message.of(project, Message.Type.POST));
            System.out.println("msgResult: " + msgResult);
            return Optional.of(project);
        }

        return Optional.empty();
    }

    @Cacheable(value = CACHE_NAME, key = "#id", unless = "#result == null")
    public Optional<Project> getProject(Integer id) {
        return Optional.ofNullable(projectMapper.selectByPrimaryKey(id))
                .map(this::makeTagsForFront);
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

        PageHelper.startPage(pageNum + 1, pageSize);

        String condition = getSearchCondition(keyword);
        List<Project> projects = projectMapper.getAcceptedProjects(condition);

        projects.forEach(this::makeTagsForFront);

        if (userId != null) {
            Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
            projects.forEach(p -> p.setIsCollected(collectedProIds.contains(p.getId())));
        }

        return projects;
    }

    public long countAcceptedProjects(String keyword) {
        return projectMapper.countAcceptedProjects(getSearchCondition(keyword));
    }

    public Map<String, Object> getHotProjects(String keyword, int hotSize, Integer userId) {

        PageHelper.startPage(1, hotSize);
        List<Project> projects = projectMapper.getHotProjects(getSearchCondition(keyword));

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(pro -> {
            pro.setIsCollected(collectedProIds.contains(pro.getId()));
            makeTagsForFront(pro);
        });

        return ImmutableMap.of("hotSize", projects.size(), "projects", projects);
    }

    public List<Project> getUncheckedProjects(String keyword, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum + 1, pageSize);
        List<Project> projects = projectMapper.getUncheckedProjects(getSearchCondition(keyword));
        projects.forEach(this::makeTagsForFront);

        return projects;
    }

    public long countUncheckedProjects(String keyword) {
        return projectMapper.countUncheckedProjects(getSearchCondition(keyword));
    }

    public List<Project> getPostedProjects(Integer userId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum + 1, pageSize);
        List<Project> projects = projectMapper.getPostedProjects(userId);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(pro -> {
            pro.setIsCollected(collectedProIds.contains(pro.getId()));
            makeTagsForFront(pro);
        });

        return projects;
    }

    public long countPostedProjects(Integer userId) {
        return projectMapper.countPostedProjects(userId);
    }

    public List<Project> getCollectedProjects(Integer userId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum + 1, pageSize);

        List<Project> projects = projectMapper.getCollectedProjects(userId);
        projects.forEach(pro -> {
            pro.setIsCollected(true);
            makeTagsForFront(pro);
        });

        return projects;
    }

    public long countCollectedProjects(Integer userId) {
        return projectMapper.countCollectedProjects(userId);
    }

    public List<Project> getJoinedProjects(Integer userId, int pageNum, int pageSize) {

        List<Project> projects = projectMapper.getJoinedProjects(userId);

        Collection<Integer> collectedProIds = getCollectedProjectIds(userId);
        projects.forEach(pro -> {
            pro.setIsCollected(collectedProIds.contains(pro.getId()));
            makeTagsForFront(pro);
        });

        return projects;
    }

    public long countJoinedProjects(Integer userId) {
        return projectMapper.countJoinedProjects(userId);
    }

    public boolean addCollection(Integer userId, Integer proId) {
        return projectMapper.addCollection(userId, proId) == 1;
    }

    public boolean deleteCollection(Integer userId, Integer proId) {
        return projectMapper.deleteCollection(userId, proId) == 1;
    }

    public boolean containsCollection(Integer userId, Integer proId) {
        return projectMapper.containsCollection(userId, proId) == 1;
    }

    public boolean containsSignInfo(Integer userId, Integer proId) {
        return signInfoMapper.containsSignInfo(userId, proId) == 1;
    }

    @CacheEvict(value = CACHE_NAME, key = "#proId")
    public boolean deleteProject(Integer proId) {
        return projectMapper.deleteByPrimaryKey(proId) == 1;
    }

    public PagingModel<Project> getPagingUncheckedProjects(String keyword, int pageNum, int pageSize) {

        Supplier<Long> totalNumOfProjects = () -> countUncheckedProjects(keyword);
        Supplier<List<Project>> projects = () -> getUncheckedProjects(keyword, pageNum, pageSize);

        return PagingModel.of("projects", projects, totalNumOfProjects, pageNum, pageSize);
    }

    /**
     * 从数据中按分页条件获取审核通过的项目，封装为一个分页模型
     *
     * @param keyword 搜索关键字，默认为 ""
     * @param pageNum 此时的页数
     * @param pageSize 每页的元素数量
     * @param userId 用来判断项目是否已经被该用户收藏
     * @return 分页模型
     * @see xdtic.projpool.model.PagingModel
     */
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

    public PagingModel<Project> getPagingJoinedProjects(Integer userId, int pageNum, int pageSize) {

        Supplier<Long> totalNumOfProjects = () -> countJoinedProjects(userId);
        Supplier<List<Project>> projects = () -> getJoinedProjects(userId, pageNum, pageSize);

        return PagingModel.of("projects", projects, totalNumOfProjects, pageNum, pageSize);
    }

    @CacheEvict(value = CACHE_NAME, key = "#project.id")
    public boolean updateProject(Project project, boolean reject) {
        if (reject) {
            project.setStatus((byte) 0);
            return projectMapper.updateProjectWithStatus(project) == 1;
        }

        return projectMapper.updateProject(project) == 1;
    }

    @CacheEvict(value = CACHE_NAME, key = "#proId")
    public boolean updateProjectByOperation(Integer proId, String operation, String comment) {
        boolean success = false;

        switch (operation) {
            case "reject":
                success = projectMapper.updateProjectStatus(proId, 2) == 1;
                if (success) {
                    getProject(proId)
                            .map(p -> Message.of(p, Message.Type.REJECT, comment))
                            .ifPresent(messageMapper::addMessage);
                }
                break;
            case "accept":
                success = projectMapper.updateProjectStatus(proId, 1) == 1;
                if (success) {
                    getProject(proId)
                            .map(p -> Message.of(p, Message.Type.PASS))
                            .ifPresent(messageMapper::addMessage);
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
        List<Integer> collectedProjectIds = userMapper.getCollectedProIds(userId);
        if (collectedProjectIds.size() > 5) {
            return new HashSet<>(collectedProjectIds);
        }
        return collectedProjectIds;
    }

    private Project makeTagsForFront(Project project) {

        if (project.getTag() != null) {
            List<String> tags = Arrays.asList(project.getTag().split("&"));
            project.setTags(tags);
        }

        return project;
    }

    private String getSearchCondition(String keywords) {
        StringJoiner columnJoiner = new StringJoiner(",',',", "CONCAT(", ")");
        columnJoiner.add("p.tag").add("p.name").add("p.content").add("p.username");
        String columns = columnJoiner.toString();

        String[] keys = keywords.split("\\s+");
        StringBuilder condition = new StringBuilder(80);
        for (String keyword : keys) {
            keyword = getSearchKeyword(keyword);
            condition.append(columns).append(" LIKE '%")
                    .append(keyword).append("%' AND ");
        }
        condition.delete(condition.length() - 4, condition.length());

        return condition.toString();
    }

    private String getSearchKeyword(String keyword) {
        if (keyword.isEmpty()) {
            return keyword;
        }

        keyword = keyword.toLowerCase();
        if (keyword.equals("android")) {
            return "安卓";
        }

        if (keyword.equals("网站") || keyword.equals("前端") || keyword.equals("后端")) {
            return "web";
        }

        return keyword;
    }

}
