package kr.foodie.config.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        request.getSession().setMaxInactiveInterval(3600);

        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //to be changing as json
        if (savedRequest != null) {
            out.print(savedRequest.getRedirectUrl());
            return;
        }
        out.print("/");
    }
}