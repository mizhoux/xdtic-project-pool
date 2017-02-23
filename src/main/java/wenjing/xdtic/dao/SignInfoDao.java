package wenjing.xdtic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

    public boolean add(SignInfo signInfo) {
        String SQL
                = "INSERT INTO sign_info (proId, uid, apply, profile, pexperice, sign_time) "
                + "VALUES (?, ?, ?, ?, ?, NOW())";
        int result = jdbcTmpl.update(SQL, signInfo.getProId(), signInfo.getUid(),
                signInfo.getApply(), signInfo.getProfile(), signInfo.getPexperice());

        if (result == 1) {
            Project project = projectDao.get(signInfo.getProId());
            messageDao.add(project.getUserid(), project.getProId(), project.getProname(), Message.Type.JOIN);
            return true;
        }

        return false;
    }

    public List<SignInfo> getSignInfos(Integer proId) {
        String SQL = "SELECT u.username, s.* FROM sign_info AS s, user AS u WHERE proId = ? AND u.id = s.uid";
        return jdbcTmpl.query(SQL, this::parseSignInfo, proId);
    }

    public SignInfo getSignInfo(Integer sid) {
        String SQL = "SELECT u.username, s.* FROM sign_info AS s, user AS u WHERE s.sid = ? AND u.id = s.uid";
        return jdbcTmpl.query(SQL, this::extractSignInfo, sid);
    }

    private SignInfo parseSignInfo(ResultSet rs, int rowNum) throws SQLException {
        SignInfo signInfo = new SignInfo();

        signInfo.setApply(rs.getString("apply"));
        signInfo.setPexperice(rs.getString("pexperice"));
        signInfo.setProId(rs.getInt("proId"));
        signInfo.setProfile(rs.getString("profile"));
        signInfo.setSid(rs.getInt("sid"));
        signInfo.setUid(rs.getInt("uid"));
        signInfo.setUsername(rs.getString("username"));

        LocalDateTime dateTime = rs.getTimestamp("sign_time").toLocalDateTime();

        signInfo.setDate(DATE_FORMATTER.format(dateTime));
        signInfo.setTime(TIME_FORMATTER.format(dateTime));

        return signInfo;
    }

    private SignInfo extractSignInfo(ResultSet rs) throws SQLException {
        return rs.next() ? parseSignInfo(rs, 0) : null;
    }

}
