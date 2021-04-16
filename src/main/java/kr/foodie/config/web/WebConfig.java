package kr.foodie.config.web;

import kr.foodie.config.web.handler.AuthenticatedHandler;
import kr.foodie.config.web.handler.RedirectHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.time.Duration;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("resources/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)));
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //Base view mapping
        registry.addViewController("/policy").setViewName("policy");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/submit").setViewName("signup_done");
        registry.addViewController("/help/idInquiry").setViewName("help-id");
        registry.addViewController("/help/pwInquiry").setViewName("help-pswd");

        /**
        registry.addRedirectViewController("/done","/");

        //Handling any pages not given url
        registry.addViewController("^(?!(\\/api\\/+|\\/auth\\/+)).*$")
                .setViewName("error");

        //Handling error for http status
        registry.addStatusController("/error", HttpStatus.valueOf("404"));
         */
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RedirectHandler())
                .addPathPatterns("/help/reset");

        registry.addInterceptor(new AuthenticatedHandler())
                .addPathPatterns("/*")
                .addPathPatterns("/auth/login")
                .addPathPatterns("/user/**")
                .addPathPatterns("/shop/**")
                .addPathPatterns("/help/**")
                .addPathPatterns("/auth/join/user/*")
                .excludePathPatterns("/auth/validate/**")
                .excludePathPatterns("/help/inquiry/**")
                .excludePathPatterns("/login");
    }

    //Async task to be adding with search, gps
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {}

}