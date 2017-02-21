package wenjing.xdtic.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @Autowired
    private JdbcTemplate jdbcTmpl;

    @Autowired
    private UserDao userDao;

    public Project getProject(Integer proId) {
        String SQL = "SELECT * FROM project WHERE proId = ?";
        return jdbcTmpl.query(SQL, this::extractProject, proId);
    }

    // userId 用来判断项目是否已经被该用户收藏
    public List<Project> getProjects(Integer userId, Integer offset, Integer size) {
        String SQL = "SELECT u.username, p.* FROM project AS p, USER AS u WHERE p.userid = u.id LIMIT ?, ?";

        List<Project> projects = jdbcTmpl.query(SQL, this::parseProjectWithUsername, offset, size);

        projects.forEach(project -> project.setIsCollected(isProjectCollected(userId, project.getProId())));

        return projects;
    }

    // userId 用来判断项目是否已经被该用户收藏
    public List<Project> getHotProjects(Integer userId, Integer hotSize) {

        String SQL = "SELECT u.username, p.* FROM project AS p, USER AS u,"
                + "(SELECT pid, COUNT(*) AS num FROM collect_project GROUP BY pid ORDER BY num DESC LIMIT ?) AS t "
                + "WHERE p.proId = t.pid AND u.id = p.userid";

        List<Project> projects = jdbcTmpl.query(SQL, this::parseProjectWithUsername, hotSize);

        projects.forEach(project -> project.setIsCollected(isProjectCollected(userId, project.getProId())));

        return projects;
    }

    public List<Project> getPostedProjects(Integer userId, Integer offset, Integer size) {
        String SQL = "SELECT * FROM project WHERE userid = ? LIMIT ?, ?";
        List<Project> projects = jdbcTmpl.query(SQL, this::parseProject, userId, offset, size);

        String username = userDao.getUsername(userId);
        projects.forEach(project -> {
            project.setUsername(username);
            project.setIsCollected(isProjectCollected(userId, project.getProId()));
        });

        return projects;
    }

    /**
     * 获得用户发布的项目数量
     *
     * @param userId 用户 ID
     * @return 用户发布的项目数量
     */
    public long getPostedProjectsCount(Integer userId) {
        String SQL = "SELECT COUNT(*) FROM project WHERE userid = ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    public long getProjectsCount() {
        String SQL = "SELECT COUNT(*) FROM project";
        return jdbcTmpl.queryForObject(SQL, Long.class);
    }

    public List<Project> getJoiningProjects(Integer userId, Integer offset, Integer size) {
        String SQL
                = "SELECT u.username, p.* FROM project AS p, user AS u WHERE p.proId IN "
                + "(SELECT jp.pid FROM join_project AS jp WHERE jp.uid = ?) AND u.id = p.userid "
                + "LIMIT ?, ?";
        List<Project> projects = jdbcTmpl.query(SQL, this::parseProjectWithUsername, userId, offset, size);

        projects.forEach(project -> project.setIsCollected(isProjectCollected(userId, project.getProId())));

        return projects;
    }

    /**
     * 获得用户参加的项目数量
     *
     * @param userId 用户 ID
     * @return 用户参加的项目数量
     */
    public long getJoiningProjectsCount(Integer userId) {
        String SQL
                = "SELECT COUNT(*) FROM project AS p WHERE p.proId IN "
                + "(SELECT jp.pid FROM join_project AS jp WHERE jp.uid = ?)";

        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    public List<Project> getCollectedProjects(Integer userId, Integer offset, Integer size) {
        String SQL
                = "SELECT u.username, p.* FROM project AS p, user AS u WHERE p.proId IN "
                + "(SELECT cp.pid FROM collect_project AS cp WHERE cp.uid = ?) AND u.id = p.userid "
                + "LIMIT ?, ?";

        List<Project> projects = jdbcTmpl.query(SQL, this::parseProjectWithUsername, userId, offset, size);

        projects.forEach(project -> project.setIsCollected(true));

        return projects;
    }

    /**
     * 获得用户收藏的项目数量
     *
     * @param userId 用户 ID
     * @return 用户收藏的项目数量
     */
    public long getCollectedProjectsCount(Integer userId) {
        String SQL
                = "SELECT COUNT(*) FROM project AS p WHERE p.proId IN "
                + "(SELECT cp.pid FROM collect_project AS cp WHERE cp.uid = ?)";

        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
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
        return jdbcTmpl.queryForObject(SQL, Long.class, uid, pid) == 1;
    }

    /**
     * 将 ResultSet 中的数据转化为 Project （用于 ResultSetExtractor）
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private Project extractProject(ResultSet rs) throws SQLException {
        return rs.next() ? parseProject(rs, 1) : null;
    }

    /**
     * 将 ResultSet 中的数据转化为 Project （用于 RowMapper）
     *
     * @param rs ResultSet
     * @param rowNum 数据的行号
     * @return
     * @throws SQLException
     */
    private Project parseProject(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();
        project.setUserid(rs.getInt("userid"));
        project.setProId(rs.getInt("proId"));
        project.setProname(rs.getString("proname"));
        project.setPromassage(rs.getString("promassage"));
        project.setProwant(rs.getString("prowant"));
        project.setConcat(rs.getString("concat"));
        project.setStatu(rs.getString("statu"));

        LocalDateTime creationDateTime = rs.getTimestamp("date").toLocalDateTime();
        project.setDate(creationDateTime.format(DATE_TIME_FORMATTER));

        project.setDesc(project.getPromassage());
        String tag = rs.getString("tag");
        if (tag != null) {
            project.setTag(tag);
            List<String> tags = Arrays.asList(tag.split("&&"));
            project.setTags(tags);
        }

        return project;
    }

    private Project parseProjectWithUsername(ResultSet rs, int rowNum) throws SQLException {
        Project project = parseProject(rs, rowNum);
        project.setUsername(rs.getString("username"));
        return project;
    }

    public boolean addProject(Integer userId, String tag, String proname,
            String promassage, String prowant, String concat) {
        String SQL
                = "INSERT INTO project "
                + "(userid, proname, promassage, prowant, tag, concat, date) "
                + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        return jdbcTmpl.update(SQL, userId, proname, promassage, prowant, tag, concat) == 1;
    }

    public boolean collectProject(Integer userId, Integer proId) {
        String SQL = "INSERT INTO collect_project SET uid = ?, pid = ?";
        return jdbcTmpl.update(SQL, userId, proId) == 1;
    }

    public boolean uncollectProject(Integer userId, Integer proId) {
        String SQL = "DELETE FROM collect_project WHERE uid = ? AND pid = ?";
        return jdbcTmpl.update(SQL, userId, proId) == 1;
    }

    public boolean updateProject(Integer userId, Integer proId,
            String promassage, String prowant, String concat) {
        String SQL
                = "UPDATE project SET promassage = ?, prowant = ?, concat = ? "
                + "WHERE userid = ? AND proId = ?";

        return jdbcTmpl.update(SQL, promassage, prowant, concat, userId, proId) == 1;
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

    private boolean isColumnExists(ResultSet rs, String column) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            if (column.equals(metaData.getColumnName(i))) {
                return true;
            }
        }
        return false;
    }

}
