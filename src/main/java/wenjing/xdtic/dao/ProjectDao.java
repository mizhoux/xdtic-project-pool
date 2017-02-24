package wenjing.xdtic.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.Message;
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

    @Autowired
    private MessageDao messageDao;

    public boolean addProject(Integer userId, String tag,
            String proname, String promassage, String prowant, String concat) {

        String SQL = "INSERT INTO project "
                + "(user_id, name, content, recruit, tag, contact, date) "
                + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTmpl.update(conn -> {

            PreparedStatement pstmt = conn.prepareStatement(SQL, new String[]{"id"});
            pstmt.setInt(1, userId);
            pstmt.setString(2, proname);
            pstmt.setString(3, promassage);
            pstmt.setString(4, prowant);
            pstmt.setString(5, tag);
            pstmt.setString(6, concat);

            return pstmt;

        }, keyHolder);

        if (result == 1) { // 添加项目成功
            Integer proId = keyHolder.getKey().intValue();
            messageDao.addMessage(userId, proId, proname, Message.Type.POST);
            return true;
        }
        return false;
    }

    public boolean updateProject(Integer userId, Integer proId,
            String promassage, String prowant, String concat) {
        String SQL
                = "UPDATE project SET content = ?, recruit = ?, contact = ? "
                + "WHERE user_id = ? AND id = ?";

        return jdbcTmpl.update(SQL, promassage, prowant, concat, userId, proId) == 1;
    }

    public Project getProject(Integer id) {
        String SQL = "SELECT * FROM project WHERE id = ?";
        return jdbcTmpl.query(SQL, this::extractProject, id);
    }

    // userId 用来判断项目是否已经被该用户收藏
    public List<Project> getProjects(Integer userId, String keywords, Integer offset, Integer size) {
        String SQL
                = "SELECT u.username, p.* FROM project AS p, user AS u "
                + "WHERE p.user_id = u.id AND p.tag LIKE ? LIMIT ?, ?";

        List<Project> projects = jdbcTmpl.query(
                SQL, this::parseProjectWithUsername, getMysqlLikeKey(keywords), offset, size);

        projects.forEach(project
                -> project.setIsCollected(isProjectCollected(userId, project.getProId())));

        return projects;
    }

    public List<Project> getHotProjects(Integer userId, String keywords, Integer hotSize) {

        String SQL = "SELECT u.username, p.* FROM project AS p, user AS u,"
                + "(SELECT pc.pro_id, COUNT(*) AS num FROM pro_collection pc GROUP BY pc.pro_id ORDER BY num DESC LIMIT ?) AS t "
                + "WHERE p.id = t.pro_id AND p.tag LIKE ? AND u.id = p.user_id";

        List<Project> projects = jdbcTmpl.query(
                SQL, this::parseProjectWithUsername, hotSize, getMysqlLikeKey(keywords));

        projects.forEach(project -> project.setIsCollected(isProjectCollected(userId, project.getProId())));

        return projects;
    }

    /**
     * 获得用户发布的项目
     *
     * @param userId 用户 ID
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getPostedProjects(Integer userId, Integer offset, Integer size) {
        String SQL = "SELECT * FROM project WHERE user_id = ? LIMIT ?, ?";
        List<Project> projects = jdbcTmpl.query(SQL, this::parseProject, userId, offset, size);

        String username = userDao.getUsername(userId);
        projects.forEach(project -> {
            project.setUsername(username);
            project.setIsCollected(isProjectCollected(userId, project.getProId()));
        });

        return projects;
    }

    /**
     * 获得用户收藏的项目
     *
     * @param userId 用户 ID
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getCollectedProjects(Integer userId, Integer offset, Integer size) {
        String SQL
                = "SELECT u.username, p.* FROM project AS p, user AS u WHERE p.id IN "
                + "(SELECT pc.pro_id FROM pro_collection AS pc WHERE pc.user_id = ?) AND u.id = p.user_id "
                + "LIMIT ?, ?";

        List<Project> projects = jdbcTmpl.query(SQL, this::parseProjectWithUsername, userId, offset, size);

        projects.forEach(project -> project.setIsCollected(true));

        return projects;
    }

    /**
     * 获得用户参与的项目
     *
     * @param userId 用户 ID
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getJoiningProjects(Integer userId, Integer offset, Integer size) {
        String SQL
                = "SELECT u.username, p.* FROM project AS p, user AS u WHERE p.id IN "
                + "(SELECT s.pro_id FROM sign_info AS s WHERE s.user_id = ?) AND u.id = p.user_id "
                + "LIMIT ?, ?";
        List<Project> projects = jdbcTmpl.query(SQL, this::parseProjectWithUsername, userId, offset, size);

        projects.forEach(project -> project.setIsCollected(isProjectCollected(userId, project.getProId())));

        return projects;
    }

    public long getProjectsCount(String keywords) {
        String SQL = "SELECT COUNT(*) FROM project WHERE tag LIKE ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, getMysqlLikeKey(keywords));
    }

    /**
     * 获得用户发布的项目数量
     *
     * @param userId 用户 ID
     * @return 用户发布的项目数量
     */
    public long getPostedProjectsCount(Integer userId) {
        String SQL = "SELECT COUNT(*) FROM project WHERE user_id = ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    /**
     * 获得用户参加的项目数量
     *
     * @param userId 用户 ID
     * @return 用户参加的项目数量
     */
    public long getJoiningProjectsCount(Integer userId) {
        String SQL
                = "SELECT COUNT(*) FROM project AS p WHERE p.id IN "
                + "(SELECT s.pro_id FROM sign_info AS s WHERE s.user_id = ?)";

        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    /**
     * 获得用户收藏的项目数量
     *
     * @param userId 用户 ID
     * @return 用户收藏的项目数量
     */
    public long getCollectedProjectsCount(Integer userId) {
        String SQL
                = "SELECT COUNT(*) FROM project AS p WHERE p.id IN "
                + "(SELECT pc.pro_id FROM pro_collection AS pc WHERE pc.user_id = ?)";

        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    /**
     * 判断一个项目是否被给定的用户收藏
     *
     * @param userId 用户ID
     * @param proId 项目ID
     * @return
     */
    public boolean isProjectCollected(Integer userId, Integer proId) {
        String SQL = "SELECT COUNT(*) FROM pro_collection WHERE user_id = ? AND pro_id = ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId, proId) == 1;
    }

    /**
     * 收藏项目
     *
     * @param userId
     * @param proId
     * @return
     */
    public boolean collectProject(Integer userId, Integer proId) {
        String SQL = "INSERT INTO pro_collection SET user_id = ?, pro_id = ?";
        return jdbcTmpl.update(SQL, userId, proId) == 1;
    }

    /**
     * 取消收藏项目
     *
     * @param userId
     * @param proId
     * @return
     */
    public boolean uncollectProject(Integer userId, Integer proId) {
        String SQL = "DELETE FROM pro_collection WHERE user_id = ? AND pro_id = ?";
        return jdbcTmpl.update(SQL, userId, proId) == 1;
    }

    public User getCreator(Project project) {
        Integer userId = project.getUserid();
        return userId == null ? null : userDao.getUser(userId);
    }

    public boolean isUserJoined(User user, Project project) {
        String SQL = "SELECT COUNT(*) FROM sign_info WHERE user_id = ? AND pro_id = ?";
        long result = jdbcTmpl.queryForObject(SQL, Long.class, user.getId(), project.getProId());
        return result == 1;
    }

    private String getMysqlLikeKey(String keyword) {
        return "%" + keyword + "%";
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

        project.setId(rs.getInt("id"));
        project.setUserId(rs.getInt("user_id"));
        project.setName(rs.getString("name"));
        project.setContent(rs.getString("content"));
        project.setRecruit(rs.getString("recruit"));
        project.setContact(rs.getString("contact"));
        project.setStatus(rs.getString("status"));
        project.setDate(rs.getTimestamp("date"));

        String tag = rs.getString("tag");
        if (tag != null) {
            project.setTag(tag);
            List<String> tags = Arrays.asList(tag.split("&+"));
            project.setTags(tags);
        }

        // 兼容前端
        project.setProId(project.getId());
        project.setUserid(project.getUserId());
        project.setProname(project.getName());
        project.setDesc(project.getContent());
        project.setProwant(project.getRecruit());
        project.setConcat(project.getContact());
        project.setStatu(project.getStatus());
        project.setPromassage(project.getContent());

        return project;
    }

    private Project parseProjectWithUsername(ResultSet rs, int rowNum) throws SQLException {
        Project project = parseProject(rs, rowNum);
        project.setUsername(rs.getString("username"));
        return project;
    }
}
