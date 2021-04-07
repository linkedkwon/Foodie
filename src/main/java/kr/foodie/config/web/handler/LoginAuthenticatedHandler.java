package kr.foodie.config.web.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * only for login page as footer template
 */
@Component
public class LoginAuthenticatedHandler implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("map", getMap());
    }

    private static Map<String, String> getMap(){
        return Map.of(
                "footer-li-name-1","로그인","footer-li-name-2","회원가입",
                "footer-li-url-1","/auth/login","footer-li-url-2","/auth/join/user1"
        );
    }
}
