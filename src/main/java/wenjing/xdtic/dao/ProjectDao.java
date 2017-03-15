package wenjing.xdtic.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
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

    private static final String SQL_ADD_PROJECT
            = "INSERT INTO project "
            + "(user_id, name, content, recruit, tag, contact, username, date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

    public boolean addProject(Project project) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTmpl.update(conn -> {

            PreparedStatement pstmt = conn.prepareStatement(SQL_ADD_PROJECT, Statement.RETURN_GENERATED_KEYS);

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

    private static final String SQL_UPDATE_PROJECT
            = "UPDATE project SET content = ?, recruit = ?, contact = ? "
            + "WHERE user_id = ? AND id = ?";

    public boolean updateProject(Project project) {
        return jdbcTmpl.update(SQL_UPDATE_PROJECT, project.getContent(),
                project.getRecruit(), project.getContact(), project.getUserId(), project.getId()) == 1;
    }

    private static final String SQL_UPDATE_PROJECT_WITH_STATUS
            = "UPDATE project SET content = ?, recruit = ?, contact = ?, status = ?, date = NOW()"
            + "WHERE user_id = ? AND id = ?";

    public boolean updateProjectWithStatus(Project project) {
        return jdbcTmpl.update(SQL_UPDATE_PROJECT_WITH_STATUS, project.getContent(), project.getRecruit(),
                project.getContact(), project.getStatus(), project.getUserId(), project.getId()) == 1;
    }

    public Optional<Project> getProject(Integer id) {
        String sql = "SELECT * FROM project WHERE id = ?";
        return jdbcTmpl.query(sql, this::extractProject, id);
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

        return jdbcTmpl.query(SQL, this::mapProject, offset, size);
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

        String sql = "SELECT p.* FROM project AS p, "
                + "(SELECT pc.pro_id, COUNT(*) AS num FROM pro_collection pc GROUP BY pc.pro_id ORDER BY num DESC LIMIT ?) AS t "
                + "WHERE p.status = 'pass' AND p.id = t.pro_id AND " + getSearchCondition(keyword);

        return jdbcTmpl.query(sql, this::mapProject, hotSize);
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
        String sql
                = "SELECT p.* FROM project AS p "
                + "WHERE p.status = 'check' AND " + getSearchCondition(keyword)
                + "ORDER BY date DESC LIMIT ?, ?";

        return jdbcTmpl.query(sql, this::mapProject, offset, size);
    }

    public long countUncheckedProjects(String keyword) {
        String sql = "SELECT COUNT(*) FROM project p "
                + "WHERE p.status = 'check' AND " + getSearchCondition(keyword);

        return jdbcTmpl.queryForObject(sql, Long.class);
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
        String sql = "SELECT p.* FROM project p WHERE p.user_id = ? ORDER BY date DESC LIMIT ?, ?";
        return jdbcTmpl.query(sql, this::mapProject, userId, offset, size);
    }

    /**
     * 获得用户发布的项目数量
     *
     * @param userId 用户 ID
     * @return 用户发布的项目数量
     */
    public long countPostedProjects(Integer userId) {
        String sql = "SELECT COUNT(*) FROM project WHERE user_id = ?";
        return jdbcTmpl.queryForObject(sql, Long.class, userId);
    }

    private static final String SQL_GET_COLLECTED_PROJECTS
            = "SELECT p.* FROM project p "
            + "WHERE p.id IN (SELECT pc.pro_id FROM pro_collection pc WHERE pc.user_id = ?)"
            + "LIMIT ?, ?";

    /**
     * 获得用户收藏的项目
     *
     * @param userId 用户 ID
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getCollectedProjects(Integer userId, int offset, int size) {
        return jdbcTmpl.query(SQL_GET_COLLECTED_PROJECTS, this::mapProject, userId, offset, size);
    }

    private static final String SQL_COUNT_COLLECTED_PROJECTS
            = "SELECT COUNT(*) FROM project p WHERE p.id IN "
            + "(SELECT pc.pro_id FROM pro_collection pc WHERE pc.user_id = ?)";

    /**
     * 获得用户收藏的项目数量
     *
     * @param userId 用户 ID
     * @return 用户收藏的项目数量
     */
    public long countCollectedProjects(Integer userId) {
        return jdbcTmpl.queryForObject(SQL_COUNT_COLLECTED_PROJECTS, Long.class, userId);
    }

    private static final String SQL_GET_JOINED_PROJECTS
            = "SELECT p.* FROM project p "
            + "WHERE p.id IN (SELECT s.pro_id FROM sign_info s WHERE s.user_id = ?) "
            + "ORDER BY date DESC LIMIT ?, ?";

    /**
     * 获得用户参与的项目
     *
     * @param userId 用户 ID
     * @param offset
     * @param size
     * @return
     */
    public List<Project> getJoinedProjects(Integer userId, int offset, int size) {
        return jdbcTmpl.query(SQL_GET_JOINED_PROJECTS, this::mapProject, userId, offset, size);
    }

    private static final String SQL_COUNT_JOINED_PROJECTS
            = "SELECT COUNT(*) FROM project p WHERE p.id IN "
            + "(SELECT s.pro_id FROM sign_info s WHERE s.user_id = ?)";

    /**
     * 获得用户参加的项目数量
     *
     * @param userId 用户 ID
     * @return 用户参加的项目数量
     */
    public long countJoinedProjects(Integer userId) {
        return jdbcTmpl.queryForObject(SQL_COUNT_JOINED_PROJECTS, Long.class, userId);
    }

    /**
     * 收藏项目
     *
     * @param userId
     * @param proId
     * @return
     */
    public boolean addCollection(Integer userId, Integer proId) {
        String sql = "INSERT INTO pro_collection SET user_id = ?, pro_id = ?";
        return jdbcTmpl.update(sql, userId, proId) == 1;
    }

    /**
     * 取消收藏项目
     *
     * @param userId
     * @param proId
     * @return
     */
    public boolean deleteCollection(Integer userId, Integer proId) {
        String sql = "DELETE FROM pro_collection WHERE user_id = ? AND pro_id = ?";
        return jdbcTmpl.update(sql, userId, proId) == 1;
    }

    /**
     * 判断一个项目是否被给定的用户收藏
     *
     * @param userId 用户ID
     * @param proId 项目ID
     * @return
     */
    public boolean containsCollection(Integer userId, Integer proId) {
        String sql = "SELECT COUNT(*) FROM pro_collection WHERE user_id = ? AND pro_id = ?";
        return jdbcTmpl.queryForObject(sql, Long.class, userId, proId) == 1;
    }

    /**
     * 判断用户是否参与了项目
     *
     * @param userId 用户 ID
     * @param proId 项目 ID
     * @return
     */
    public boolean containsSignInfo(Integer userId, Integer proId) {
        String sql = "SELECT COUNT(*) FROM sign_info WHERE user_id = ? AND pro_id = ?";
        return jdbcTmpl.queryForObject(sql, Long.class, userId, proId) == 1;
    }

    public boolean updateProjectStatus(Integer proId, String status) {
        String sql = "UPDATE project SET status = ? WHERE id = ?";
        return jdbcTmpl.update(sql, status, proId) == 1;
    }

    public boolean deleteProject(Integer proId) {
        String sql = "DELETE FROM project WHERE id = ?";
        return jdbcTmpl.update(sql, proId) == 1;
    }

    public List<Integer> getCollectedProjectIds(Integer userId) {
        String sql = "SELECT pc.pro_id FROM pro_collection pc WHERE pc.user_id = ?";
        return jdbcTmpl.queryForList(sql, Integer.class, userId);
    }

    /**
     * 从 ResultSet 提取数据，将其映射为 Project，目标函数为 ResultSetExtractor.extractData
     *
     * @param rs
     * @return
     * @throws SQLException
     * @see org.springframework.jdbc.core.ResultSetExtractor
     */
    private Optional<Project> extractProject(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapProject(rs, 1)) : Optional.empty();
    }

    /**
     * 将 ResultSet 中的数据其映射为 Project，目标函数为 RowMapper.mapRow
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     * @see org.springframework.jdbc.core.RowMapper
     */
    private Project mapProject(ResultSet rs, int rowNum) throws SQLException {
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
                .append(" OR p.content LIKE '%").append(keyword).append("%') ");

        return condition.toString();
    }

}
