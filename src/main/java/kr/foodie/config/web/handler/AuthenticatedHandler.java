package kr.foodie.config.web.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthenticatedHandler implements HandlerInterceptor {

    private static final String principalValue = "anonymousUser";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal().toString().equalsIgnoreCase(principalValue)) {
            modelAndView.addObject("authenticated", "0");
            modelAndView.addObject("iconRed","/images/ico-login-red.png");
            modelAndView.addObject("iconNormal","/images/ico-login.png");

            return;
        }
        modelAndView.addObject("authenticated","1");
        modelAndView.addObject("iconRed","/images/1223.png");
        modelAndView.addObject("iconNormal","/images/1224.png");
    }
}
