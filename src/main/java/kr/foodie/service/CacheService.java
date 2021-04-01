package kr.foodie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

//to be changing loop
@Service
public class CacheService {

    private static final String userCode = "userCode";
    private static final String emailCode = "emailCode";

    @Autowired
    private EhCacheCacheManager ehCacheManager;

    @Cacheable(cacheNames = "userCode", key="#email")
    public String getRandomUserCode(String email) {
        int left = 65, right = 90, len = 6;
        return  new Random().ints(left, right + 1)
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @CacheEvict(cacheNames = "userCode", key="#email")
    public String checkCode(String email, String code){
        String data = "1";
        List strArr = ehCacheManager.getCacheManager().getCache(userCode).getKeys();
        for (Object string : strArr) {
            if(strArr.contains(email)) {
                if(ehCacheManager.getCacheManager().getCache(userCode).get(string).getObjectValue().toString().contains(code)){
                    data = email;
                }
            }
        }
        return data;
    }

    @Cacheable(cacheNames = "emailCode", key = "#qs")
    public String setEmailQs(String qs, String email){
        return email;
    }

    @CacheEvict(cacheNames = "emailCode", key = "#qs")
    public String getEmailByQs(String qs){
        String email="";
        List strArr = ehCacheManager.getCacheManager().getCache(emailCode).getKeys();
        for(Object string : strArr){
            if(strArr.contains(qs)){
                email = ehCacheManager.getCacheManager().getCache(emailCode).get(string).getObjectValue().toString();
            }
        }
        return email;
    }

    public String getRandomEmailCode() {
        int left = 65, right = 90, len = 20;
        return  new Random().ints(left, right + 1)
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
