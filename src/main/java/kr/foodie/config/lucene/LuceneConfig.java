package kr.foodie.config.lucene;

import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;

public class LuceneConfig {

    @Bean
    public LuceneIndexServiceBean luceneIndexServiceBean(EntityManagerFactory EntityManagerFactory){
        LuceneIndexServiceBean luceneIndexServiceBean = new LuceneIndexServiceBean(EntityManagerFactory);
        luceneIndexServiceBean.triggerIndexing();
        return luceneIndexServiceBean;
    }
}
