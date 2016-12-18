package wenjing.xdtic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.User;

/**
 *
 * @author wenjing
 */
@Repository
public class ProjectDao {

    private static final String SQL_GET_PROJECT_BY_ID
            = "SELECT * FROM project WHERE proId = ?";

    private static final String SQL_GET_POSTED_PROJECTS
            = "SELECT * FROM project WHERE userid = ? LIMIT ?, ?";

    private static final String SQL_GET_POSTED_PROJECTS_COUNT
            = "SELECT COUNT(*) FROM project WHERE userid = ?";

    private static final String SQL_GET_JOINING_PROJECTS
            = "SELECT p.* FROM project AS p WHERE p.proId IN "
            + "(SELECT jp.pid FROM join_project AS jp WHERE jp.uid = ?) LIMIT ?, ?";

    private static final String SQL_GET_JOINING_PORJECTS_COUNT
            = "SELECT COUNT(*) FROM project AS p WHERE p.proId IN "
            + "(SELECT jp.pid FROM join_project AS jp WHERE jp.uid = ?)";

    private static final String SQL_GET_COLLECTED_PROJECTS
            = "SELECT p.* FROM project AS p WHERE p.proId IN "
            + "(SELECT cp.pid FROM collect_project AS cp WHERE cp.uid = ?) LIMIT ?, ?";

    private static final String SQL_GET_COLLECTED_PROJECTS_COUNT
            = "SELECT COUNT(*) FROM project AS p WHERE p.proId IN "
            + "(SELECT cp.pid FROM collect_project AS cp WHERE cp.uid = ?)";

    private static final String SQL_IS_PROJECT_COLLECTED
            = "SELECT COUNT(*) FROM collect_project WHERE uid = ? AND pid = ?";

    private static final String SQL_ADD_PROJECT
            = "INSERT INTO project "
            + "(userid, proname, promassage, prowant, tag, concat, date) "
            + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

    private static final String SQL_COLLECT_PROJECT
            = "INSERT INTO collect_project SET uid = ?, pid = ?";

    private static final String SQL_UNCOLLECT_PROJECT
            = "DELETE FROM collect_project WHERE uid = ? AND pid = ?";

    private static final String SQL_UPDATE_PROJECT
            = "UPDATE project SET promassage = ?, prowant = ?, concat = ? WHERE userid = ? AND proId = ?";

    @Autowired
    private JdbcTemplate jdbcTmpl;

    @Autowired
    private UserDao userDao;

    public Project getProject(Integer proId) {
        Project project = jdbcTmpl.query(SQL_GET_PROJECT_BY_ID,
                rs -> rs.next() ? parseProject(rs, 0) : null, proId);
        return project;
    }

    public List<Project> getPostedProjects(Integer userid, Integer offset, Integer pageSize) {

        List<Project> projects = jdbcTmpl.query(
                SQL_GET_POSTED_PROJECTS, this::parseProject, userid, offset, pageSize);

        String username = userDao.getUsername(userid);
        projects.forEach(project -> {
            project.setUsername(username);
            boolean isCollected = isProjectCollected(userid, project.getProId());
            project.setIsCollected(isCollected);
        });

        return projects;
    }

    /**
     * 获得用户发布的项目数量
     *
     * @param userid 用户 ID
     * @return 用户发布的项目数量
     */
    public long getPostedProjectsCount(Integer userid) {
        return jdbcTmpl.queryForObject(SQL_GET_POSTED_PROJECTS_COUNT, Long.class, userid);
    }

    public List<Project> getJoiningProjects(Integer userid, Integer offset, Integer pageSize) {

        List<Project> projects = jdbcTmpl.query(
                SQL_GET_JOINING_PROJECTS, this::parseProject, userid, offset, pageSize);

        String username = userDao.getUsername(userid);
        projects.forEach(project -> {
            project.setUsername(username);
            boolean isCollected = isProjectCollected(userid, project.getProId());
            project.setIsCollected(isCollected);
        });
        return projects;
    }

    /**
     * 获得用户参加的项目数量
     *
     * @param userid 用户 ID
     * @return 用户参加的项目数量
     */
    public long getJoiningProjectsCount(Integer userid) {
        return jdbcTmpl.queryForObject(SQL_GET_JOINING_PORJECTS_COUNT, Long.class, userid);
    }

    public List<Project> getCollectedProjects(Integer userid, Integer offset, Integer pageSize) {

        List<Project> projects = jdbcTmpl.query(
                SQL_GET_COLLECTED_PROJECTS, this::parseProject, userid, offset, pageSize);

        String username = userDao.getUsername(userid);
        projects.forEach(project -> {
            project.setUsername(username);
            project.setIsCollected(true);
        });

        return projects;
    }

    /**
     * 获得用户收藏的项目数量
     *
     * @param userid 用户 ID
     * @return 用户收藏的项目数量
     */
    public long getCollectedProjectsCount(Integer userid) {
        return jdbcTmpl.queryForObject(SQL_GET_COLLECTED_PROJECTS_COUNT, Long.class, userid);
    }

    /**
     * 判断一个项目是否被给定的用户收藏
     *
     * @param uid 用户ID
     * @param pid 项目ID
     * @return
     */
    public boolean isProjectCollected(Integer uid, Integer pid) {
        return jdbcTmpl.queryForObject(SQL_IS_PROJECT_COLLECTED, Long.class, uid, pid) == 1;
    }

    private Project parseProject(ResultSet rs, int row) throws SQLException {
        Project project = new Project();
        project.setUserid(rs.getInt("userid"));
        project.setProId(rs.getInt("proId"));
        project.setProname(rs.getString("proname"));
        project.setPromassage(rs.getString("promassage"));
        project.setProwant(rs.getString("prowant"));
        project.setConcat(rs.getString("concat"));
        project.setStatu(rs.getString("statu"));

        LocalDateTime creationDateTime = rs.getTimestamp("date").toLocalDateTime();
        project.setDate(creationDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));

        project.setDesc(project.getPromassage());
        String tag = rs.getString("tag");
        if (tag != null) {
            project.setTag(tag);
            List<String> tags = Arrays.asList(tag.split("&&"));
            project.setTags(tags);
        }

        return project;
    }

    public boolean addProject(Integer userid, String tag, String proname,
            String promassage, String prowant, String concat) {
        return jdbcTmpl.update(SQL_ADD_PROJECT,
                userid, proname, promassage, prowant, tag, concat) == 1;
    }

    public boolean collectProject(Integer userid, Integer proId) {
        return jdbcTmpl.update(SQL_COLLECT_PROJECT, userid, proId) == 1;
    }

    public boolean uncollectProject(Integer userid, Integer proId) {
        return jdbcTmpl.update(SQL_UNCOLLECT_PROJECT, userid, proId) == 1;
    }

    public boolean updateProject(Integer userid, Integer proId,
            String promassage, String prowant, String concat) {
        return jdbcTmpl.update(SQL_UPDATE_PROJECT, promassage, prowant, concat, userid, proId) == 1;
    }

    public User getCreator(Project project) {
        Integer userId = project.getUserid();
        if (userId != null) {
            return userDao.getUser(userId);
        }
        return null;
    }

    public boolean isUserJoined(User user, Project project) {
        return false;
    }

}
