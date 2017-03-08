package wenjing.xdtic.action;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.model.Admin;
import wenjing.xdtic.model.PagingModel;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.RespCode;
import wenjing.xdtic.model.User;
import wenjing.xdtic.service.AdminService;
import wenjing.xdtic.service.ProjectService;
import wenjing.xdtic.service.UserService;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
@RequestMapping("fn")
public class AdminFunction {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProjectService proService;

    @PostMapping(value = "admin/login", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String login(
            HttpServletRequest request, HttpSession session,
            @RequestParam String username, @RequestParam String password) {

        Admin admin = adminService.getAdmin(username, password);

        if (admin == null) {
            request.setAttribute("loginFail", Boolean.TRUE);
            return "admin/login";
        }

        session.setAttribute("admin", admin);
        return "redirect:/admin";
    }

    @ResponseBody
    @GetMapping("admin/get/project/uncheck")
    public PagingModel<Project> getUncheckedProjects(
            @RequestParam("keyWords") String keyword,
            @RequestParam int pageNum, @RequestParam int size) {

        return proService.getPagingUncheckedProjects(keyword, pageNum, size);
    }

    @ResponseBody
    @PostMapping(value = "admin/project/operate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespCode operateProject(@RequestBody Map<String, String> params) {

        Integer proId = Integer.parseInt(params.get("proId"));
        String operation = params.get("operation");
        String comment = params.get("rejectReason");

        boolean success = proService.updateProjectByOperation(proId, operation, comment);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @ResponseBody
    @GetMapping("admin/get/project/accept")
    public PagingModel<Project> getAcceptedProjects(
            @RequestParam("keyWords") String keyword,
            @RequestParam int pageNum, @RequestParam int size) {

        return proService.getPagingAcceptedProjects(keyword, pageNum, size);
    }

    @ResponseBody
    @GetMapping("admin/get/user")
    public PagingModel<User> getUsers(
            @RequestParam("keyWords") String keyword,
            @RequestParam int pageNum, @RequestParam int size) {

        return userService.getPagingUsers(keyword, pageNum, size);
    }

    @ResponseBody
    @PostMapping("admin/user/delete")
    @SuppressWarnings("unchecked")
    public RespCode deleteUser(@RequestBody Map<String, Object> params) {
        List<Integer> userIds = (List<Integer>) params.get("uid");

        boolean success = userService.deleteUsers(userIds);
        return success ? RespCode.OK : RespCode.ERROR;
    }

}
