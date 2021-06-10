package kr.foodie.config.web;

import kr.foodie.config.web.handler.AuthenticatedHandler;
import kr.foodie.config.web.handler.RedirectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
        registry.addViewController("/auth/login").setViewName("login");
        registry.addViewController("/submit").setViewName("signup_done");
        registry.addViewController("/help/idInquiry").setViewName("help-id");
        registry.addViewController("/help/pwInquiry").setViewName("help-pswd");
        registry.addViewController("/reset").setViewName("reset-pw");
    }

    //change to collection
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RedirectHandler())
                .addPathPatterns("/help/reset");

        registry.addInterceptor(new AuthenticatedHandler())
                .addPathPatterns("/")
                .addPathPatterns("/policy")
                .addPathPatterns("/user/**")
                .addPathPatterns("/shop/**")
                .addPathPatterns("/help/*")
                .addPathPatterns("/auth/**")
                .addPathPatterns("/help/pw")
                .addPathPatterns("/help/pw/**")
                .excludePathPatterns("/auth/check/**")
                .excludePathPatterns("/inquiry/**")
                .excludePathPatterns("/user/favorite/shop")
                .excludePathPatterns("/user/favorite/item/**")
                .excludePathPatterns("/user/review/item/**");
    }

    //Async task to be adding with search, gps
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {}

    @Bean
    MappingJackson2JsonView jsonView(){
        return new MappingJackson2JsonView();
    }

}