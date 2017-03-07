package wenjing.xdtic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
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

    @Autowired
    private JdbcTemplate jdbcTmpl;

    public boolean addMessage(Message msg) {
        String SQL
                = "INSERT INTO message (user_id, pro_id, content, type, date) "
                + "VALUES (?, ?, ?, ?, NOW())";

        return jdbcTmpl.update(SQL, msg.getUserId(), msg.getProId(), msg.getContent(), msg.getType()) == 1;
    }

    public Message getMessage(Integer id) {
        String SQL = "SELECT * FROM message WHERE id = ?";
        return jdbcTmpl.query(SQL, rs -> rs.next() ? parseMessage(rs, 1) : null, id);
    }

    public List<Message> getMessages(Integer userId, int offset, int size) {
        String SQL = "SELECT * FROM message WHERE user_id = ? ORDER BY date DESC LIMIT ?, ?";
        return jdbcTmpl.query(SQL, this::parseMessage, userId, offset, size);
    }

    /**
     * 获得用户的消息总数
     *
     * @param userId 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long countMessages(Integer userId) {
        String SQL = "SELECT COUNT(*) FROM message WHERE user_id = ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    /**
     * 将消息标记为已读
     *
     * @param id 消息 ID
     * @return
     */
    public boolean setMessageRead(Integer id) {
        String SQL = "UPDATE message m SET m.read = TRUE WHERE m.id = ?";
        return jdbcTmpl.update(SQL, id) == 1;
    }

    public boolean setMessagesRead(List<Integer> ids) {
        String SQL = "UPDATE message m SET m.read = TRUE WHERE m.id IN "
                + ids.stream().map(String::valueOf).collect(Collectors.joining(",", "(", ")"));
        return jdbcTmpl.update(SQL) == ids.size();
    }

    /**
     * 获得用户未读的消息总数
     *
     * @param userId 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long countUnreadMessages(Integer userId) {
        String SQL = "SELECT COUNT(*) FROM message m WHERE m.user_id = ? AND m.read = FALSE";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    private Message parseMessage(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();

        message.setId(rs.getInt("id"));
        message.setUserId(rs.getInt("user_id"));
        message.setContent(rs.getString("content"));
        message.setProId(rs.getInt("pro_id"));
        message.setType(rs.getString("type"));
        message.setRead(rs.getBoolean("read"));
        message.setDate(rs.getTimestamp("date"));

        return message;
    }

}
