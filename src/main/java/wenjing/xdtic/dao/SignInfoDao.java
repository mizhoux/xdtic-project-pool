package wenjing.xdtic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.SignInfo;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Repository
public class SignInfoDao {

    @Autowired
    private JdbcTemplate jdbcTmpl;

    public boolean addSignInfo(SignInfo signInfo) {
        String SQL
                = "INSERT INTO sign_info (pro_id, user_id, apply, skill, experience, sign_time) "
                + "VALUES (?, ?, ?, ?, ?, NOW())";

        int result = jdbcTmpl.update(SQL, signInfo.getProId(), signInfo.getUserId(),
                signInfo.getApply(), signInfo.getSkill(), signInfo.getExperience());

        return result == 1;
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

        return signInfo;
    }

}
