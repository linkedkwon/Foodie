package kr.foodie.config.cache;

import lombok.RequiredArgsConstructor;

import net.sf.ehcache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CacheService {

    private static final String authCodeCache = "authentication-code";
    private static final String encryptedCodeCache = "encryption-code";

    private final EhCacheCacheManager ehCacheManager;


    @Cacheable(cacheNames = authCodeCache, key="#email")
    public String saveAuthCode(String email) {
        int left = 65, right = 90, len = 6;

        return  new Random().ints(left, right + 1)
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Cacheable(cacheNames = encryptedCodeCache, key = "#qs")
    public String saveEncryptedCodeWithEmail(String qs, String email){
        return email;
    }

    @CacheEvict(cacheNames = authCodeCache, key="#email")
    public String findByAuthenticationCode(String email, String code){

        Cache cache = initCacheByCacheName(authCodeCache);

        return cache.getKeys()
                    .stream()
                    .filter(o -> o.toString().equalsIgnoreCase(email))
                    .filter(o -> cache.get(o.toString()).getObjectValue().toString().equalsIgnoreCase(code))
                    .count() > 0 ? "1" : "0";
    }

    @CacheEvict(cacheNames = encryptedCodeCache, key = "#qs")
    public String findByEncryptedCode(String code){

        Cache cache = initCacheByCacheName(encryptedCodeCache);
        List keys = cache.getKeys();

        for(Object obj : keys){
            if(keys.contains(code))
                return cache.get(obj).getObjectValue().toString();
        }
        return "1";
    }

    public String getRandomEncryptedCode() {
        int left = 65, right = 90, len = 20;

        return  new Random().ints(left, right + 1)
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private Cache initCacheByCacheName(String cacheName){
        return ehCacheManager.getCacheManager().getCache(cacheName);
    }

}
