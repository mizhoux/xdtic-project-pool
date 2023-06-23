package xdtic.projpool.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
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
import xdtic.projpool.model.Admin;
import xdtic.projpool.model.PagingModel;
import xdtic.projpool.model.Project;
import xdtic.projpool.model.RespCode;
import xdtic.projpool.model.User;
import xdtic.projpool.service.AdminService;
import xdtic.projpool.service.ProjectService;
import xdtic.projpool.service.UserService;

/**
 * Admin Function
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Controller
@RequestMapping("fn/admin")
public class AdminFunction {

    private final UserService userService;

    private final AdminService adminService;
    private final ProjectService proService;
    
    @Autowired
     public AdminFunction(UserService userService, AdminService adminService, ProjectService proService) {
        this.userService = userService;
        this.adminService = adminService;
        this.proService = proService;
    }
     

    @PostMapping(value = "login", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String login(HttpServletRequest request,
            @RequestParam String username, @RequestParam String password) {
        Optional<Admin> admin = adminService.getAdmin(username, password);

        admin.ifPresent(a -> {
            request.getSession().setAttribute("admin", a);
        });

        if (!admin.isPresent()) {
            request.setAttribute("loginFail", Boolean.TRUE);
        }

        return admin.map(a -> "redirect:/admin").orElse("admin/login");
    }

    @ResponseBody
    @GetMapping("get/project/uncheck")
    public PagingModel<Project> getUncheckedProjects(
            @RequestParam("keyWords") String keyword,
            @RequestParam int pageNum, @RequestParam int size) {

        return proService.getPagingUncheckedProjects(keyword, pageNum, size);
    }

    @ResponseBody
    @PostMapping(value = "project/operate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespCode operateProject(@RequestBody Map<String, String> params) {

        Integer proId = Integer.parseInt(params.get("id"));
        String operation = params.get("operation");
        String comment = params.get("rejectReason");

        boolean success = proService.updateProjectByOperation(proId, operation, comment);
        return success ? RespCode.OK : RespCode.ERROR;
    }

    @ResponseBody
    @GetMapping("get/project/accept")
    public PagingModel<Project> getAcceptedProjects(
            @RequestParam("keyWords") String keyword,
            @RequestParam int pageNum, @RequestParam int size) {

        return proService.getPagingAcceptedProjects(keyword, pageNum, size, null);
    }

    @ResponseBody
    @GetMapping("get/user")
    public PagingModel<User> getUsers(
            @RequestParam("keyWords") String keyword,
            @RequestParam int pageNum, @RequestParam int size) {

        return userService.getPagingUsers(keyword, pageNum, size);
    }

    @ResponseBody
    
    @PostMapping("user/delete")
    @SuppressWarnings("unchecked")
    public RespCode deleteUser(@RequestBody Map<String, Object> params) {
        List<Integer> userIds = (List<Integer>) params.get("uid");

        boolean success = userService.deleteUsers(userIds);
        return success ? RespCode.OK : RespCode.ERROR;
    }
    

}
