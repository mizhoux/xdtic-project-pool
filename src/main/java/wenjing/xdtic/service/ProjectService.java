package wenjing.xdtic.service;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenjing.xdtic.dao.MessageDao;
import wenjing.xdtic.dao.ProjectDao;
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
    private MessageDao messageDao;

    public boolean addProject(Project project) {
        return projectDao.addProject(project);
    }

    public boolean updateProject(Project project) {
        if (project.getStatus() != null) {
            return projectDao.updateProjectWithStatus(project);
        }
        return projectDao.updateProject(project);
    }

    public Project getProject(Integer id) {
        Project project = projectDao.getProject(id);
        Project.syncDataForFront(project);

        return project;
    }

    public List<Project> getAcceptedProjects(
            String keyword, int pageNum, int size, Integer userId) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getAcceptedProjects(keyword, offset, size, userId);

        Collection<Integer> collectedProIds = projectDao.getCollectedProjectIds(userId);
        projects.forEach(p -> {
            p.setIsCollected(collectedProIds.contains(p.getId()));
            Project.syncDataForFront(p);
        });

        return projects;
    }

    public List<Project> getAcceptedProjects(String keyword, int pageNum, int size) {
        int offset = pageNum * size;

        List<Project> projects = projectDao.getAcceptedProjects(keyword, offset, size);
        projects.forEach(Project::syncDataForFront);

        return projects;
    }

    public long getAcceptedProjectsCount(String keyword) {
        return projectDao.getAcceptedProjectsCount(keyword);
    }

    public List<Project> getHotProjects(String keyword, int hotSize, Integer userId) {

        List<Project> projects = projectDao.getHotProjects(keyword, hotSize, userId);

        Collection<Integer> collectedProIds = projectDao.getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            Project.syncDataForFront(project);
        });

        return projects;
    }

    public List<Project> getUncheckedProjects(String keyword, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getUncheckedProjects(keyword, offset, size);
        projects.forEach(Project::syncDataForFront);

        return projects;
    }

    public long getUncheckedProjectsCount(String keyword) {
        return projectDao.getUncheckedProjectsCount(keyword);
    }

    public List<Project> getPostedProjects(Integer userId, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getPostedProjects(userId, offset, size);

        Collection<Integer> collectedProIds = projectDao.getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            Project.syncDataForFront(project);
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
            Project.syncDataForFront(project);
        });

        return projects;
    }

    public long getCollectedProjectsCount(Integer userId) {
        return projectDao.getCollectedProjectsCount(userId);
    }

    public List<Project> getJoinedProjects(Integer userId, int pageNum, int size) {

        int offset = pageNum * size;
        List<Project> projects = projectDao.getJoinedProjects(userId, offset, size);

        Collection<Integer> collectedProIds = projectDao.getCollectedProjectIds(userId);
        projects.forEach(project -> {
            project.setIsCollected(collectedProIds.contains(project.getId()));
            Project.syncDataForFront(project);
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

    public boolean isUserCollected(Integer userId, Integer proId) {
        return projectDao.isUserCollected(userId, proId);
    }

    public boolean isUserJoined(Integer userId, Integer proId) {
        return projectDao.isUserJoined(userId, proId);
    }

    public boolean rejectProject(Integer proId) {
        return projectDao.updateProjectStatus(proId, "reject");
    }

    public boolean acceptProject(Integer proId) {
        return projectDao.updateProjectStatus(proId, "pass");
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
}
