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

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdbcTemplate jdbcTmpl;

    /**
     * 获得用户的消息总数
     *
     * @param userId 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long getMessagesCount(Integer userId) {
        String SQL = "SELECT COUNT(*) FROM message WHERE uid = ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    /**
     * 获得用户未读的消息总数
     *
     * @param userId 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long getUnreadMessagesCount(Integer userId) {
        String SQL = "SELECT COUNT(*) FROM message AS m WHERE m.uid = ? AND m.read = FALSE";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    public List<Message> getMessages(Integer userId, Integer offset, Integer size) {
        String SQL = "SELECT * FROM message WHERE uid = ? LIMIT ?, ?";
        return jdbcTmpl.query(SQL, this::parseMessage, userId, offset, size);
    }

    public Message getMessage(Integer id) {
        String SQL = "SELECT * FROM message WHERE mid = ?";
        return jdbcTmpl.query(SQL, rs -> rs.next() ? parseMessage(rs, 0) : null, id);
    }

    private Message parseMessage(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();
        message.setUid(rs.getInt("uid"));
        message.setMid(rs.getInt("mid"));
        message.setMassage(rs.getString("massage"));
        message.setType(rs.getString("type"));
        message.setRead(rs.getBoolean("read"));

        LocalDateTime dateTime = rs.getTimestamp("date").toLocalDateTime();
        String dateStr = dateTime.format(DATE_TIME_FORMATTER);
        message.setDate(dateStr);

        return message;
    }

    public boolean setMessageRead(Integer mid) {
        String SQL = "UPDATE message m SET m.read = TRUE WHERE m.mid = ?";
        return jdbcTmpl.update(SQL, mid) == 1;
    }

}
