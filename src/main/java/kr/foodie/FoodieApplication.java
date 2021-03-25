package kr.foodie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableAsync
@EnableCaching
@SpringBootApplication
public class FoodieApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(FoodieApplication.class, args);
    }

}
