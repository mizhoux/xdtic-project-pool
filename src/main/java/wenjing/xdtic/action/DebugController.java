package wenjing.xdtic.action;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wenjing.xdtic.core.RemoteAddressCache;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.User;
import wenjing.xdtic.service.ProjectService;
import wenjing.xdtic.service.UserService;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@RestController
@RequestMapping("debug")
public class DebugController {

    @Autowired
    private RemoteAddressCache addrCache;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService proService;

    @GetMapping("user/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id).orElse(new User());
    }

    @GetMapping("pro/{id}")
    public Project getProject(@PathVariable Integer id) {
        return proService.getProject(id).orElse(new Project());
    }

    @GetMapping("addr_cache")
    public Map<Object, Object> viewRemoteAddressCache() {
        return addrCache.toMap();
    }

}
