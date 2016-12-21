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

/**
 *
 * @author wenjing
 */
@Repository
public class MessageDao {

    private static final DateTimeFormatter 
            DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdbcTemplate jdbcTmpl;

    /**
     * 获得用户的消息总数
     *
     * @param uid 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long getUserMessagesCount(Integer uid) {
        String SQL = "SELECT COUNT(*) FROM systemassage WHERE uid = ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, uid);
    }

    public List<Message> getUserMessages(Integer uid, Integer offset, Integer size) {
        String SQL = "SELECT * FROM systemassage WHERE uid = ? LIMIT ?, ?";
        return jdbcTmpl.query(SQL, this::parseMessage, uid, offset, size);
    }

    private Message parseMessage(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();
        message.setUid(rs.getInt("uid"));
        message.setMid(rs.getInt("mid"));
        message.setMassage(rs.getString("massage"));
        message.setType(rs.getString("type"));

        LocalDateTime dateTime = rs.getTimestamp("date").toLocalDateTime(); //获取时间
        String dateStr = dateTime.format(DATE_TIME_FORMATTER);//设施时间格式
        message.setDate(dateStr);

        return message;
    }

}
