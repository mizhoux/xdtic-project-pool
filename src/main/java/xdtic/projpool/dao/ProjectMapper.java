package xdtic.projpool.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xdtic.projpool.model.Project;

/**
 * Project Mapper
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public interface ProjectMapper {

    Project getProject(Integer id);

    int addProject(Project project);

    int deleteProject(Integer id);

    List<Project> getAcceptedProjects(@Param("condition") String condition);

    List<Project> getHotProjects(@Param("condition") String condition);

    List<Project> getUncheckedProjects(@Param("condition") String condition);

    List<Project> getPostedProjects(Integer userId);

    long countPostedProjects(Integer userId);

    List<Project> getCollectedProjects(Integer userId);

    long countCollectedProjects(Integer userId);

    List<Project> getJoinedProjects(Integer userId);

    long countJoinedProjects(Integer userId);

    int addCollection(Integer userId, Integer proId);

    int deleteCollection(Integer userId, Integer proId);

    long containsCollection(Integer userId, Integer proId);

    /**
     * 根据 id 更新 Project，包括 content, contact，recruit
     *
     * @param project 包括 id 的 Project 实体
     * @return 受影响的行数，期待值为 1
     */
    int updateProject(Project project);

    /**
     * 根据 id 更新 Project，包括 content, contact，recruit，status
     *
     * @param project 包括 id 的 Project 实体
     * @return 受影响的行数，期待值为 1
     */
    int updateProjectWithStatus(Project project);

    int updateProjectStatus(Integer proId, int status);
}
