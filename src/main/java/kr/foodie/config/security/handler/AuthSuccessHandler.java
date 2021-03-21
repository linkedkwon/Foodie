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

        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //to be changing as json
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            out.print(targetUrl);
            return;
        }
        out.print("/");
    }
}