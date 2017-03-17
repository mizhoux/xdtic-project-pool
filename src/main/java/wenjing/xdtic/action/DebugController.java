package wenjing.xdtic.action;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wenjing.xdtic.core.RemoteAddressCache;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@RestController
@RequestMapping("debug")
public class DebugController {

    @Autowired
    private RemoteAddressCache addrCache;

    @GetMapping("addr_cache")
    public Map<Object, Object> viewRemoteAddressCache() {
        return addrCache.toMap();
    }
    
}
