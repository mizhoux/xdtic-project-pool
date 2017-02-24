package wenjing.xdtic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Autowired
    private JdbcTemplate jdbcTmpl;

    public boolean addMessage(Integer userId, Integer proId, String proName, Message.Type type) {
        String SQL = "INSERT INTO message (user_id, content, pro_id, type, date) VALUES (?, ?, ?, ?, NOW())";

        return jdbcTmpl.update(SQL,
                userId, getMessageContent(type, proName), proId, type.name().toLowerCase()) == 1;
    }

    public Message getMessage(Integer id) {
        String SQL = "SELECT * FROM message WHERE id = ?";
        return jdbcTmpl.query(SQL, rs -> rs.next() ? parseMessage(rs, 1) : null, id);
    }

    public List<Message> getMessages(Integer userId, Integer offset, Integer size) {
        String SQL = "SELECT * FROM message WHERE user_id = ? LIMIT ?, ?";
        return jdbcTmpl.query(SQL, this::parseMessage, userId, offset, size);
    }

    public boolean setMessageRead(Integer id) {
        String SQL = "UPDATE message m SET m.read = TRUE WHERE m.id = ?";
        return jdbcTmpl.update(SQL, id) == 1;
    }

    /**
     * 获得用户的消息总数
     *
     * @param userId 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long getMessagesCount(Integer userId) {
        String SQL = "SELECT COUNT(*) FROM message WHERE user_id = ?";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    /**
     * 获得用户未读的消息总数
     *
     * @param userId 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long getUnreadMessagesCount(Integer userId) {
        String SQL = "SELECT COUNT(*) FROM message m WHERE m.user_id = ? AND m.read = FALSE";
        return jdbcTmpl.queryForObject(SQL, Long.class, userId);
    }

    private String getMessageContent(Message.Type type, String proName) {
        String content = "";
        switch (type) {
            case POST:
                content = "恭喜~ 成功发布项目【" + proName + "】";
                break;
            case PASS:
                content = "厉害了~ 项目【" + proName + "】通过了审核";
                break;
            case JOIN:
                content = "开心~ 有用户报名了项目【" + proName + "】";
                break;
        }

        return content;
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

        // 兼容前端
        message.setMid(message.getId());
        message.setUid(message.getUserId());
        message.setMassage(message.getContent());

        return message;
    }

}
