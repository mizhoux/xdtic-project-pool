package xdtic.projpool.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 登录拦截器
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        String requestURI = request.getRequestURI();
        if (requestURI.contains("/admin")) { // For Admin
            if (session.getAttribute("admin") != null) {
                return true;
            }

            request.getRequestDispatcher("/WEB-INF/views/page/admin/login.jsp")
                    .forward(request, response);

        } else { // For User
            if (session.getAttribute("user") != null) {
                return true;
            }

            request.getRequestDispatcher("/WEB-INF/views/page/user/login.jsp")
                    .forward(request, response);
        }

        return false;
    }

}
