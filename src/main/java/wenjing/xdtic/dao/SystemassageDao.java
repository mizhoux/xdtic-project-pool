package wenjing.xdtic.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.Systemassage;

/**
 *
 * @author wenjing
 */
@Repository
public class SystemassageDao {

    private static final String SQL_GET_USER_MESSAGE_COUNT
            = "SELECT COUNT(*) FROM systemassage WHERE uid = ?";

    private static final String SQL_GET_USER_MESSAGES
            = "SELECT * FROM  systemassage WHERE uid = ? LIMIT ?, ?";

    @Autowired
    private JdbcTemplate jdbcTmpl;

    /**
     * 获得用户的消息总数
     *
     * @param uid 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long getUserMessagesCount(Integer uid) {
        return jdbcTmpl.query(SQL_GET_USER_MESSAGE_COUNT,
                rs -> rs.next() ? rs.getLong(1) : 0, uid);
    }

    public List<Systemassage> getUserMessages(Integer uid, Integer offset, Integer size) {

        List<Systemassage> messages = new ArrayList<>();
        try {
            List<Map<String, Object>> maps
                    = jdbcTmpl.queryForList(SQL_GET_USER_MESSAGES, uid, offset, size);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Map<String, Object> map : maps) {
                Systemassage systemassage = new Systemassage();
                systemassage.setUid((Integer) map.get("uid"));//将得到的数据赋值，并返回
                systemassage.setMid((Integer) map.get("mid"));
                systemassage.setMassage((String) map.get("massage"));
                systemassage.setType((String) map.get("type"));

                Timestamp timestamp = (java.sql.Timestamp) map.get("date");    //获取时间
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                String dateStr = dateTime.format(formatter);//设施时间格式
                systemassage.setDate(dateStr); //加入map
                messages.add(systemassage);
            }

        } catch (EmptyResultDataAccessException ex) {// 捕获异常 
            //  Spring 查询不到数据时抛出异常，此时返回 空
            // return  null;
        }

        return messages;
    }

}
