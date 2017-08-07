package xdtic.projpool.util;

/**
 * Login Utilities
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class LoginUtil {

    /**
     * 用于通过 IP 地址判断缓存中是否存在该用户
     *
     * @param ip
     * @return
     */
    public static String getUserIPIdentity(String ip) {
        return "U".concat(ip);
    }

    /**
     * 用于通过 IP 地址判断缓存中是否存在该管理员
     *
     * @param ip
     * @return
     */
    public static String getAdminIPIdentity(String ip) {
        return "A".concat(ip);
    }

}
