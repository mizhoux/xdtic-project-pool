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

/**
 *
 * @author wenjing
 */
@Repository
public class ProjectDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    public List<Project> getPostedProjects(Integer userid, Integer offset, Integer pageSize) {
        String SQL = "SELECT * FROM project WHERE userid = ? LIMIT ?, ?";
        List<Project> projects = jdbcTemplate.query(SQL, this::parseProject, userid, offset, pageSize);

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
        long count = jdbcTemplate.query(SQL, rs -> rs.next() ? rs.getLong(1) : 0, userid);
        return count;
    }

    public List<Project> getJoiningProjects(Integer userid, Integer offset, Integer pageSize) {
        String SQL = "SELECT p.* FROM project AS p WHERE p.proId IN "
                + "(SELECT jp.pid FROM join_project AS jp WHERE jp.uid = ?) LIMIT ?, ?";
        List<Project> projects = jdbcTemplate.query(SQL, this::parseProject, userid, offset, pageSize);

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
        long count = jdbcTemplate.query(SQL, rs -> rs.next() ? rs.getLong(1) : 0, userid);
        return count;
    }

    public List<Project> getCollectedProjects(Integer userid, Integer offset, Integer pageSize) {
        String SQL = "SELECT p.* FROM project AS p WHERE p.proId IN "
                + "(SELECT cp.pid FROM collect_project AS cp WHERE cp.uid = ?) LIMIT ?, ?";
        List<Project> projects = jdbcTemplate.query(SQL, this::parseProject, userid, offset, pageSize);

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
        long count = jdbcTemplate.query(SQL, rs -> rs.next() ? rs.getLong(1) : 0, userid);
        return count;
    }

    /**
     * 判断一个项目是否被给定的用户收藏
     *
     * @param uid 用户ID
     * @param pid 项目ID
     * @return
     */
    private boolean isProjectCollected(Integer uid, Integer pid) {
        String SQL = "SELECT COUNT(*) FROM collect_project WHERE uid = ? AND pid = ?";
        long count = jdbcTemplate.query(SQL, rs -> rs.next() ? rs.getLong(1) : 0, uid, pid);
        return count == 1;
    }

    private Project parseProject(ResultSet rs, int row) throws SQLException {
        Project project = new Project();
        project.setUserid(rs.getInt("userid"));
        project.setProId(rs.getInt("proId"));
        project.setProname(rs.getString("proname"));
        project.setPromassage(rs.getString("promassage"));
        project.setProwant(rs.getString("prowant"));
        project.setPhone(rs.getString("phone"));
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
            String promassage, String prowant, String phone) {

        String SQL = "INSERT INTO project "
                + "(userid, proname, promassage, prowant, tag, phone, date) "
                + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        int result = jdbcTemplate.update(SQL,
                userid, proname, promassage, prowant, tag, phone);
        return result == 1;
    }

}
