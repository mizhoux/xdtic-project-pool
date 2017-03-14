package wenjing.xdtic.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.Project;

/**
 *
 * @author wenjing
 */
@Repository
public class ProjectDao {

    @Autowired
    private JdbcTemplate jdbcTmpl;

    public boolean addProject(Project project) {
        String SQL = "INSERT INTO project "
                + "(user_id, name, content, recruit, tag, contact, username, date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTmpl.update(conn -> {

            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, project.getUserId());
            pstmt.setString(2, project.getName());
            pstmt.setString(3, project.getContent());
            pstmt.setString(4, project.getRecruit());
            pstmt.setString(5, project.getTag());
            pstmt.setString(6, project.getContact());
            pstmt.setString(7, project.getUsername());

            return pstmt;

        }, keyHolder);

        if (result == 1) { // 添加项目成功
            project.setId(keyHolder.getKey().intValue());
            return true;
        }

        return false;
    }

    public boolean updateProject(Project project) {
        String SQL
                = "UPDATE project SET content = ?, recruit = ?, contact = ? "
                + "WHERE user_id = ? AND id = ?";

        return jdbcTmpl.update(SQL, project.getContent(), project.getRecruit(),
                project.getContact(), project.getUserId(), project.getId()) == 1;
    }

    public boolean updateProjectWithStatus(Project project) {
        String SQL
                = "UPDATE project SET content = ?, recruit = ?, contact = ?, status = ?, date = NOW()"
                + "WHERE user_id = ? AND id = ?";

        return jdbcTmpl.update(SQL, project.getContent(), project.getRecruit(),
                project.getContact(), project.getStatus(), project.getUserId(), project.getId()) == 1;
    }

    public Project getProject(Integer id) {
        String SQL = "SELECT * FROM project WHERE id = ?";
        return jdbcTmpl.query(SQL, this::extractProject, id);
    }

    /**
     * 从数据中按分页条件获取审核通过的项目
     *
     * @param keyword
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getAcceptedProjects(String keyword, int offset, int size) {
        String SQL
                = "SELECT p.* FROM project AS p "
                + "WHERE p.status = 'pass' AND  " + getSearchCondition(keyword)
                + "ORDER BY date DESC LIMIT ?, ?";

        return jdbcTmpl.query(SQL, this::parseProject, offset, size);
    }

    public long countAcceptedProjects(String keyword) {
        String SQL
                = "SELECT COUNT(*) FROM project p "
                + "WHERE p.status = 'pass' AND " + getSearchCondition(keyword);

        return jdbcTmpl.queryForObject(SQL, Long.class);
    }

    /**
     * 获得当前的 hot 项目，根据收藏量来判断
     *
     * @param keyword
     * @param hotSize
     * @param userId 当前 session 中的用户，用来判断项目是否已经被该用户收藏
     * @return
     */
    public List<Project> getHotProjects(String keyword, int hotSize, Integer userId) {

        String SQL = "SELECT p.* FROM project AS p, "
                + "(SELECT pc.pro_id, COUNT(*) AS num FROM pro_collection pc GROUP BY pc.pro_id ORDER BY num DESC LIMIT ?) AS t "
                + "WHERE p.status = 'pass' AND p.id = t.pro_id AND " + getSearchCondition(keyword);

        return jdbcTmpl.query(SQL, this::parseProject, hotSize);
    }

    /**
     * 获得没有审核的项目
     *
     * @param keyword
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getUncheckedProjects(String keyword, int offset, int size) {
        String SQL
                = "SELECT p.* FROM project AS p "
                + "WHERE p.status = 'check' AND " + getSearchCondition(keyword)
                + "ORDER BY date DESC LIMIT ?, ?";

        return jdbcTmpl.query(SQL, this::parseProject, offset, size);
    }

    public long countUncheckedProjects(String keyword) {
        String SQL = "SELECT COUNT(*) FROM project p "
                + "WHERE p.status = 'check' AND " + getSearchCondition(keyword);

        return jdbcTmpl.queryForObject(SQL, Long.class);
    }

    /**
     * 获得用户发布的项目
     *
     * @param userId 用户 ID
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getPostedProjects(Integer userId, int offset, int size) {
        String SQL = "SELECT p.* FROM project p WHERE p.user_id = ? ORDER BY date DESC LIMIT ?, ?";
        return jdbcTmpl.query(SQL, this::parseProject, userId, offset, size);
    }

    /**
     * 获得用户发布的项目数量
     *
     * @param userId 用户 ID
     * @return 用户发布的项目数量
     */
    public long countPostedProjects(Integer userId) {
        String SQL = "SELECT COUNT(*) FROM project WHERE user_id = ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    /**
     * 获得用户收藏的项目
     *
     * @param userId 用户 ID
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getCollectedProjects(Integer userId, int offset, int size) {
        String SQL
                = "SELECT p.* FROM project p "
                + "WHERE p.id IN (SELECT pc.pro_id FROM pro_collection pc WHERE pc.user_id = ?)"
                + "LIMIT ?, ?";

        return jdbcTmpl.query(SQL, this::parseProject, userId, offset, size);
    }

    /**
     * 获得用户收藏的项目数量
     *
     * @param userId 用户 ID
     * @return 用户收藏的项目数量
     */
    public long countCollectedProjects(Integer userId) {
        String SQL
                = "SELECT COUNT(*) FROM project p WHERE p.id IN "
                + "(SELECT pc.pro_id FROM pro_collection pc WHERE pc.user_id = ?)";

        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    /**
     * 获得用户参与的项目
     *
     * @param userId 用户 ID
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getJoinedProjects(Integer userId, int offset, int size) {
        String SQL
                = "SELECT p.* FROM project p "
                + "WHERE p.id IN (SELECT s.pro_id FROM sign_info s WHERE s.user_id = ?) "
                + "ORDER BY date DESC LIMIT ?, ?";

        return jdbcTmpl.query(SQL, this::parseProject, userId, offset, size);
    }

    /**
     * 获得用户参加的项目数量
     *
     * @param userId 用户 ID
     * @return 用户参加的项目数量
     */
    public long countJoinedProjects(Integer userId) {
        String SQL
                = "SELECT COUNT(*) FROM project p WHERE p.id IN "
                + "(SELECT s.pro_id FROM sign_info s WHERE s.user_id = ?)";

        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    /**
     * 收藏项目
     *
     * @param userId
     * @param proId
     * @return
     */
    public boolean addCollection(Integer userId, Integer proId) {
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
    public boolean deleteCollection(Integer userId, Integer proId) {
        String SQL = "DELETE FROM pro_collection WHERE user_id = ? AND pro_id = ?";
        return jdbcTmpl.update(SQL, userId, proId) == 1;
    }

    /**
     * 判断一个项目是否被给定的用户收藏
     *
     * @param userId 用户ID
     * @param proId 项目ID
     * @return
     */
    public boolean containsCollection(Integer userId, Integer proId) {
        String SQL = "SELECT COUNT(*) FROM pro_collection WHERE user_id = ? AND pro_id = ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId, proId) == 1;
    }

    /**
     * 判断用户是否参与了项目
     *
     * @param userId 用户 ID
     * @param proId 项目 ID
     * @return
     */
    public boolean containsSignInfo(Integer userId, Integer proId) {
        String SQL = "SELECT COUNT(*) FROM sign_info WHERE user_id = ? AND pro_id = ?";
        long result = jdbcTmpl.queryForObject(SQL, Long.class, userId, proId);
        return result == 1;
    }

    public boolean updateProjectStatus(Integer proId, String status) {
        String SQL = "UPDATE project SET status = ? WHERE id = ?";
        return jdbcTmpl.update(SQL, status, proId) == 1;
    }

    public boolean deleteProject(Integer proId) {
        String SQL = "DELETE FROM project WHERE id = ?";
        return jdbcTmpl.update(SQL, proId) == 1;
    }

    public List<Integer> getCollectedProjectIds(Integer userId) {
        String SQL = "SELECT pc.pro_id FROM pro_collection pc WHERE pc.user_id = ?";
        return jdbcTmpl.queryForList(SQL, Integer.class, userId);
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
        project.setUsername(rs.getString("username"));
        project.setName(rs.getString("name"));
        project.setContent(rs.getString("content"));
        project.setRecruit(rs.getString("recruit"));
        project.setContact(rs.getString("contact"));
        project.setStatus(rs.getString("status"));
        project.setDate(rs.getTimestamp("date"));
        project.setTag(rs.getString("tag"));

        return project;
    }

    private String getSearchCondition(String keyword) {

        StringBuilder condition = new StringBuilder(80);

        condition.append("(p.tag LIKE '%").append(keyword).append("%'")
                .append(" OR p.name LIKE '%").append(keyword).append("%'")
                .append(" OR p.content LIKE '%").append(keyword).append("%')");

        return condition.toString();
    }

}
