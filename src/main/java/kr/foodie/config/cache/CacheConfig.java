package kr.foodie.config.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public EhCacheCacheManager ehCacheManager() {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(getCustomCacheManager());

        return ehCacheCacheManager;
    }

    private CacheManager getCustomCacheManager() {
        CacheManager cacheManager = CacheManager.create(getEhCacheConfiguration());
        cacheManager.addCache(createCache("authentication-code"));
        cacheManager.addCache(createCache("encryption-code"));

        return cacheManager;
    }

    private Cache createCache(String cacheName) {
        CacheConfiguration cacheConfig = new CacheConfiguration(cacheName, 3000)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
                .eternal(false)
                .timeToLiveSeconds(6000)
                .timeToIdleSeconds(6000);
        return new Cache(cacheConfig);
    }

    private net.sf.ehcache.config.Configuration getEhCacheConfiguration() {
        net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration();
        DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
        diskStoreConfiguration.setPath("java.io.tmpdir");
        configuration.addDiskStore(diskStoreConfiguration);
        return configuration;
    }
}
