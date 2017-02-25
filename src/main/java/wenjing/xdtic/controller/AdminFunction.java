package wenjing.xdtic.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.UserDao;
import wenjing.xdtic.model.RespCode;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller("fn")
public class AdminFunction {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @GetMapping("admin/get/project/uncheck")
    public void getUncheckedProjects(Integer pageNum, Integer size, String keyWords) {
        // 
    }

    @PostMapping(value = "admin/project/operate", consumes = APPLICATION_JSON_VALUE)
    public void operateProject(@RequestBody Map<String, String> params) {
        Integer proId = Integer.valueOf(params.get("proId"));
        String operation = params.get("operation");
        //
    }

    @GetMapping("admin/get/project/accept")
    public void getAcceptedProjects(Integer pageNum, Integer size, String keyWords) {
        // 
    }

    @GetMapping("admin/get/user")
    public void getUsers(Integer pageNum, Integer size, String keyWords) {

    }

    @PostMapping("admin/user/delete")
    public RespCode deleteUser(@RequestParam Integer uid) {
        return RespCode.ERROR;
    }
}
