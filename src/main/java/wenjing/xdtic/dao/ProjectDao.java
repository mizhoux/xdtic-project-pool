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

    @Autowired
    private JdbcTemplate jdbcTmpl;

    @Autowired
    private UserDao userDao;

    public Project getProject(Integer proId) {
        String SQL = "SELECT * FROM project WHERE proId = ?";
        Project project = jdbcTmpl.query(SQL, rs -> rs.next() ? parseProject(rs, 0) : null, proId);
        return project;
    }

    public List<Project> getPostedProjects(Integer userid, Integer offset, Integer pageSize) {
        String SQL = "SELECT * FROM project WHERE userid = ? LIMIT ?, ?";
        List<Project> projects = jdbcTmpl.query(SQL, this::parseProject, userid, offset, pageSize);

        String username = userDao.getUsername(userid);
        projects.forEach(project -> {
            project.setUsername(username);
            boolean isCollected = isProjectCollected(userid, project.getProId());
            project.setIsCollected(isCollected);
        });

        return projects;
    }

    public long getPostedProjectsCount(Integer userid) {
        String SQL = "SELECT COUNT(*) FROM project WHERE userid = ?";
        long count = jdbcTmpl.query(SQL, rs -> rs.next() ? rs.getLong(1) : 0, userid);
        return count;
    }

    public List<Project> getJoiningProjects(Integer userid, Integer offset, Integer pageSize) {
        String SQL = "SELECT p.* FROM project AS p WHERE p.proId IN "
                + "(SELECT jp.pid FROM join_project AS jp WHERE jp.uid = ?) LIMIT ?, ?";
        List<Project> projects = jdbcTmpl.query(SQL, this::parseProject, userid, offset, pageSize);

        String username = userDao.getUsername(userid);
        projects.forEach(project -> {
            project.setUsername(username);
            boolean isCollected = isProjectCollected(userid, project.getProId());
            project.setIsCollected(isCollected);
        });
        return projects;
    }

    public long getJoiningProjectsCount(Integer userid) {
        String SQL = "SELECT COUNT(*) FROM project AS p WHERE p.proId IN "
                + "(SELECT jp.pid FROM join_project AS jp WHERE jp.uid = ?)";
        long count = jdbcTmpl.query(SQL, rs -> rs.next() ? rs.getLong(1) : 0, userid);
        return count;
    }

    public List<Project> getCollectedProjects(Integer userid, Integer offset, Integer pageSize) {
        String SQL = "SELECT p.* FROM project AS p WHERE p.proId IN "
                + "(SELECT cp.pid FROM collect_project AS cp WHERE cp.uid = ?) LIMIT ?, ?";
        List<Project> projects = jdbcTmpl.query(SQL, this::parseProject, userid, offset, pageSize);

        String username = userDao.getUsername(userid);
        projects.forEach(project -> {
            project.setUsername(username);
            project.setIsCollected(true);
        });

        return projects;
    }

    public long getCollectedProjectsCount(Integer userid) {
        String SQL = "SELECT COUNT(*) FROM project AS p WHERE p.proId IN "
                + "(SELECT cp.pid FROM collect_project AS cp WHERE cp.uid = ?)";
        long count = jdbcTmpl.query(SQL, rs -> rs.next() ? rs.getLong(1) : 0, userid);
        return count;
    }

    /**
     * 判断一个项目是否被给定的用户收藏
     *
     * @param uid 用户ID
     * @param pid 项目ID
     * @return
     */
    public boolean isProjectCollected(Integer uid, Integer pid) {
        String SQL = "SELECT COUNT(*) FROM collect_project WHERE uid = ? AND pid = ?";
        long count = jdbcTmpl.query(SQL, rs -> rs.next() ? rs.getLong(1) : 0, uid, pid);
        return count == 1;
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

        String SQL = "INSERT INTO project "
                + "(userid, proname, promassage, prowant, tag, concat, date) "
                + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        int result = jdbcTmpl.update(SQL,
                userid, proname, promassage, prowant, tag, concat);
        return result == 1;
    }

    public boolean collectProject(Integer userid, Integer proId) {
        String SQL = "INSERT INTO collect_project SET uid = ?, pid = ?";
        return jdbcTmpl.update(SQL, userid, proId) == 1;
    }

    public boolean uncollectProject(Integer userid, Integer proId) {
        String SQL = "DELETE FROM collect_project WHERE uid = ? AND pid = ?";
        return jdbcTmpl.update(SQL, userid, proId) == 1;
    }

    public boolean updateProject(Integer userid, Integer proId,
            String promassage, String prowant, String concat) {
        String SQL = "UPDATE project SET promassage = ?, prowant = ?, concat = ? WHERE userid = ? AND proId = ?";
        return jdbcTmpl.update(SQL, promassage, prowant, concat, userid, proId) == 1;
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
