package kr.foodie;

import kr.foodie.config.lucene.LuceneConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableAsync
@EnableCaching
@SpringBootApplication
//@Import(LuceneConfig.class)
public class FoodieApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(FoodieApplication.class, args);
    }

}
