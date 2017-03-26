package wenjing.xdtic.action;

import com.google.common.cache.Cache;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wenjing.xdtic.core.RemoteAddressCache;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.RespCode;
import wenjing.xdtic.model.User;
import wenjing.xdtic.service.ProjectService;
import wenjing.xdtic.service.UserService;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Validated
@RestController
@RequestMapping("debug")
public class DebugController {

    @Autowired
    private RemoteAddressCache addrCache;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService proService;

    @Autowired
    private CacheManager cacheManager;

    @GetMapping("user/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id).orElse(new User());
    }

    @GetMapping("pro/{id}")
    public Project getProject(@PathVariable Integer id) {
        return proService.getProject(id).orElse(new Project());
    }

    @PostMapping(value = "user/register", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode register(
            @Size(min = 2, max = 20, message = "用户名长度需要在应该在 2~20 之间")
            @RequestParam String username,
            @Size(min = 6, max = 30, message = "密码长度需要在 6~30 之间")
            @RequestParam("pass") String password,
            @RequestParam("passConfirm") String passwordConfirm) {

        if (password.equals(passwordConfirm)) {
            return RespCode.OK;
        }

        return RespCode.ERROR;
    }

    @PostMapping(value = "project/post", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public RespCode postProject(@Valid Project project) {
        return proService.addProject(project)
                .map(p -> RespCode.OK).orElse(RespCode.ERROR);
    }

    @GetMapping("cache")
    @SuppressWarnings("unchecked")
    public ConcurrentMap<Object, Object> viewCache() {
        Cache<Object, Object> cache
                = (Cache<Object, Object>) cacheManager.getCache("project").getNativeCache();

        return cache.asMap();
    }

    @GetMapping("addr_cache")
    public Map<Object, Object> viewRemoteAddressCache() {
        return addrCache.toMap();
    }

}
