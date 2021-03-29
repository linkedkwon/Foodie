package kr.foodie.config.security.session;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.config.security.auth.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthUserDetailsService authUserDetailsService;

    public void updateAuthentication(String username){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails userDetails = (AuthUserDetails) authUserDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                userDetails, authentication.getCredentials(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
