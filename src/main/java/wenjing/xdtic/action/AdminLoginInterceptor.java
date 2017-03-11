package wenjing.xdtic.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import wenjing.xdtic.cache.XdticCache;
import wenjing.xdtic.model.Admin;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private XdticCache cache;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null) {
            return true;
        }

        Object admin = cache.get(request.getRemoteAddr());
        if (admin != null && admin instanceof Admin) {
            return true;
        }

        request.getRequestDispatcher("/WEB-INF/views/page/admin/login.jsp").forward(request, response);

        return false;
    }

}
