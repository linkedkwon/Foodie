package kr.foodie.config.web.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class CommonAuthenticatedHandler implements HandlerInterceptor {

    private static final String principalValue = "anonymousUser";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.getPrincipal().toString().equalsIgnoreCase(principalValue)) {
            modelAndView.addObject("map", getMap(false));
            return;
        }
        modelAndView.addObject("map", getMap(true));
    }

    private static Map<String, String> getMap(boolean authenticated){
        if(authenticated){
            return Map.of(
                    "footer-li-name-1", "로그아웃", "footer-li-name-2","마이페이지",
                    "footer-li-url-1","/logout", "footer-li-url-2","/user/info",
                    "header-li-menu-1","마이페이지", "header-li-menu-2","로그아웃",
                    "header-li-url-1","/user/info", "header-li-url-2","/logout",
                    "img-url","/images/1223.png" //to be adding account image
            );
        }
        return Map.of(
                "footer-li-name-1","로그인", "footer-li-name-2","회원가입",
                "footer-li-url-1","/auth/login", "footer-li-url-2","/auth/join/user1",
                "header-li-menu-1","로그인", "header-li-menu-2","회원가입",
                "header-li-url-1","/auth/login", "header-li-url-2","/auth/join/user1",
                "img-url","/images/ico-login-red.png"
        );
    }
}
