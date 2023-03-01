package fa.training.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor {
    private final HttpSession httpSession;

    public AdminAuthenticationInterceptor(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        System.out.println("pre handle of request: "  + request.getRequestURI());
        if(httpSession.getAttribute("username") != null) {
            return true;
        }
        httpSession.setAttribute("redirect-uri", request.getRequestURI());
        response.sendRedirect("/alogin");
        return false;
    }
}
