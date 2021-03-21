package kr.foodie.config.security;

import kr.foodie.config.security.auth.AuthUserDetailsService;
import kr.foodie.config.security.handler.AuthFailureHandler;
import kr.foodie.config.security.handler.AuthSuccessHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthUserDetailsService authUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
            web
                .ignoring()
                    .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/account/**").authenticated()
                .anyRequest().permitAll()
                    .and()
                .formLogin()
                .usernameParameter("email")
                .loginProcessingUrl("/login")
                .loginPage("/auth/login")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                    .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                    .and()
                .oauth2Login()
                .loginPage("/auth/login")
                .userInfoEndpoint()
                .userService(authUserDetailsService);
    }

    @Bean
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() { return new AuthFailureHandler(); }

    @Bean
    public AuthenticationSuccessHandler successHandler() { return new AuthSuccessHandler(); }

}

