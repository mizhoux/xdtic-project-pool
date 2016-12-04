package wenjing.xdtic.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author admin
 */
public class ProjectDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public boolean  addProject(Integer userid,String tag,String title,String pdesc,
            String prowant,String concat )
    {
        
        //
              String SQL= "INSERT INTO project values(?,?,?,?,?,?)";       
              int result= jdbcTemplate.update(SQL,userid, title, pdesc, prowant, tag,concat);  
              return  result == 1;   
    }

}
