package wenjing.xdtic.dao;

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

    public List<Systemassage> getSystemassageid(Integer uid) {
        String SQL = "SELECT * FROM  systemassage  WHERE uid = " + uid;
        
        List<Systemassage> messages = new ArrayList<>();
        
        try {
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(SQL);
            System.out.println("getSystemassageid: maps size: " + maps.size());
            
            for (Map<String, Object> map : maps) {
                //Map数据集返回对象名为string类型的值
                Systemassage systemassage = new Systemassage();
                systemassage.setUid((Integer) map.get("uid"));//将得到的数据赋值，并返回
                systemassage.setMid((Integer) map.get("mid"));
                systemassage.setMassage((String) map.get("massage"));
                //    systemassage.setDate((String) map.get("date"));

                messages.add(systemassage);
            }

        } catch (EmptyResultDataAccessException ex) {
            //    return null;// 捕获异常      spring查询不到输入数据时返回null    
        }
        
        return messages;
    }
    
}
