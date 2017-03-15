package wenjing.xdtic.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import wenjing.xdtic.cache.IpAddressCache;
import wenjing.xdtic.model.Admin;
import wenjing.xdtic.model.User;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private IpAddressCache ipCache;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        String requestURI = request.getRequestURI();
        if (requestURI.contains("/admin")) { // For Admin
            if (session.getAttribute("admin") != null) {
                return true;
            }

            Object admin = ipCache.get(request.getRemoteAddr());
            if (admin != null && admin instanceof Admin) {
                return true;
            }

            request.getRequestDispatcher(
                    "/WEB-INF/views/page/admin/login.jsp").forward(request, response);

        } else { // For User
            if (session.getAttribute("user") != null) {
                return true;
            }

            Object user = ipCache.get(request.getRemoteAddr());
            if (user != null && user instanceof User) {
                return true;
            }

            request.getRequestDispatcher(
                    "/WEB-INF/views/page/user/login.jsp").forward(request, response);
        }

        return false;
    }

}
