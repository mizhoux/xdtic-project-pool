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

    private static final String SQL_ADD_SIGN_INFO
            = "INSERT INTO sign_info "
            + "(pro_id, user_id, apply, skill, experience, sign_time) "
            + "VALUES (?, ?, ?, ?, ?, NOW())";

    public boolean addSignInfo(SignInfo signInfo) {

        int result = jdbcTmpl.update(SQL_ADD_SIGN_INFO,
                signInfo.getProId(), signInfo.getUserId(),
                signInfo.getApply(), signInfo.getSkill(), signInfo.getExperience());

        return result == 1;
    }

    private static final String SQL_GET_SIGN_INFO
            = "SELECT u.username, s.* FROM sign_info s, user u "
            + "WHERE s.id = ? AND u.id = s.user_id";

    public SignInfo getSignInfo(Integer id) {
        return jdbcTmpl.query(SQL_GET_SIGN_INFO, rs -> rs.next() ? parseSignInfo(rs, 1) : null, id);
    }

    private static final String SQL_GET_SIGN_INFOS
            = "SELECT u.username, s.* FROM sign_info s, user u "
            + "WHERE pro_id = ? AND u.id = s.user_id";

    public List<SignInfo> getSignInfos(Integer proId) {
        return jdbcTmpl.query(SQL_GET_SIGN_INFOS, this::parseSignInfo, proId);
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
