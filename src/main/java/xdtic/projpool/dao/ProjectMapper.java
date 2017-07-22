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

    long countAcceptedProjects(@Param("condition") String condition);

    List<Project> getHotProjects(@Param("condition") String condition);

    List<Project> getUncheckedProjects(@Param("condition") String condition);

    long countUncheckedProjects(@Param("condition") String condition);

    List<Project> getPostedProjects(Integer userId);

    long countPostedProjects(Integer userId);

    List<Project> getCollectedProjects(Integer userId);

    long countCollectedProjects(Integer userId);

    List<Project> getJoinedProjects(Integer userId);

    long countJoinedProjects(Integer userId);

    int addCollection(Integer userId, Integer proId);

    int deleteCollection(Integer userId, Integer proId);

    long containsCollection(Integer userId, Integer proId);

    int updateProjectWithStatus(Project project);

    int updateProject(Project project);

    int updateProjectStatus(Integer proId, int status);
}
