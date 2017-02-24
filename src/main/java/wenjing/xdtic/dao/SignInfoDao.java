package wenjing.xdtic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.Message;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.SignInfo;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Repository
public class SignInfoDao {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private JdbcTemplate jdbcTmpl;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ProjectDao projectDao;

    public boolean addSignInfo(SignInfo signInfo) {
        String SQL
                = "INSERT INTO sign_info (pro_id, user_id, apply, skill, experience, sign_time) "
                + "VALUES (?, ?, ?, ?, ?, NOW())";

        int result = jdbcTmpl.update(SQL, signInfo.getProId(), signInfo.getUid(),
                signInfo.getApply(), signInfo.getProfile(), signInfo.getPexperice());

        if (result == 1) { // 添加 signInfo 成功
            Project project = projectDao.getProject(signInfo.getProId());
            messageDao.addMessage(project.getUserid(), project.getProId(), project.getProname(), Message.Type.JOIN);
            return true;
        }

        return false;
    }

    public SignInfo getSignInfo(Integer id) {
        String SQL
                = "SELECT u.username, s.* FROM sign_info s, user u "
                + "WHERE s.id = ? AND u.id = s.user_id";
        return jdbcTmpl.query(SQL, rs -> rs.next() ? parseSignInfo(rs, 1) : null, id);
    }

    public List<SignInfo> getSignInfos(Integer proId) {
        String SQL
                = "SELECT u.username, s.* FROM sign_info s, user u "
                + "WHERE pro_id = ? AND u.id = s.user_id";
        return jdbcTmpl.query(SQL, this::parseSignInfo, proId);
    }

    private SignInfo parseSignInfo(ResultSet rs, int rowNum) throws SQLException {
        SignInfo signInfo = new SignInfo();

        signInfo.setId(rs.getInt("id"));
        signInfo.setProId(rs.getInt("pro_id"));
        signInfo.setUserId(rs.getInt("user_id"));
        signInfo.setApply(rs.getString("apply"));
        signInfo.setExperience(rs.getString("experience"));
        signInfo.setSkill(rs.getString("skill"));
        signInfo.setSignTime(rs.getTimestamp("sign_time"));
        signInfo.setUsername(rs.getString("username"));

        // 兼容前端
        signInfo.setSid(signInfo.getId());
        signInfo.setUid(signInfo.getUserId());
        signInfo.setProfile(signInfo.getSkill());
        signInfo.setPexperice(signInfo.getExperience());
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                signInfo.getSignTime().toInstant(), ZoneId.systemDefault());
        signInfo.setDate(DATE_FORMATTER.format(dateTime));
        signInfo.setTime(TIME_FORMATTER.format(dateTime));

        return signInfo;
    }

}
