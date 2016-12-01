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
 * @author admin
 */
@Repository
public class SystemassageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获得用户的消息总数
     *
     * @param uid 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public int countMessages(Integer uid) {
        String SQL = "SELECT COUNT(*) FROM systemassage";
        Map<String, Object> map = jdbcTemplate.queryForMap(SQL);
        Long count = (Long) map.get("COUNT(*)");

        return count.intValue();
    }

    public List<Systemassage> getSystemassageByUserId(Integer uid, Integer offset, Integer size) {
        String SQL = "SELECT * FROM  systemassage WHERE uid = ? LIMIT ?, ?";
        //limit 后加限制条件

        List<Systemassage> messages = new ArrayList<>();
        try {
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(SQL, uid, offset, size);

<<<<<<< HEAD
            for (Map<String, Object> map : maps) {
                //Map数据集返回对象名为string类型的值
                Systemassage systemassage = new Systemassage();
                systemassage.setUid((Integer) map.get("uid"));//将得到的数据赋值，并返回
                systemassage.setMid((Integer) map.get("mid"));
                systemassage.setMassage((String) map.get("massage"));
                systemassage.setType((String) map.get("type"));
                
                Timestamp timestamp = (java.sql.Timestamp) map.get("date");    //获取时间
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                String dateStr = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));//设施时间格式
                systemassage.setDate(dateStr); //加入map
                messages.add(systemassage);
=======
            for (Map<String, Object> map : maps) { // 返回的 Map 数据集，键为 String 类型
                messages.add(parseMessage(map));
>>>>>>> d9191a7c32cf56f695a5da1591bb544564ae2e35
            }
        } catch (EmptyResultDataAccessException ex) {// 捕获异常 
            //  Spring 查询不到数据时抛出异常，此时返回 空
        }

        return messages;
    }

    private Systemassage parseMessage(Map<String, Object> map) {
        Systemassage systemassage = new Systemassage();

        systemassage.setUid((Integer) map.get("uid"));//将得到的数据赋值，并返回
        systemassage.setMid((Integer) map.get("mid"));
        systemassage.setMassage((String) map.get("massage"));
        systemassage.setType((String) map.get("type"));

        Timestamp timestamp = (Timestamp) map.get("date");
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        String dateStr = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        systemassage.setDate(dateStr);

        return systemassage;
    }
}
