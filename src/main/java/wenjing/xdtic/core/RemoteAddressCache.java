package wenjing.xdtic.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Component
public class RemoteAddressCache {

    @Bean("guavaCacheForRemoteAddress")
    public Cache<Object, Object> getCache() {
        // 因为缓存策略不同，所以此处并不使用 Spring 的 GuavaCacheManager
        Cache<Object, Object> guavaCache = CacheBuilder.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();

        return guavaCache;
    }

    @Autowired
    @Qualifier("guavaCacheForRemoteAddress")
    private Cache<Object, Object> cache;

    public Object get(Object key) {
        return cache.getIfPresent(key);
    }

    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    public boolean containsKey(Object key) {
        return cache.getIfPresent(key) != null;
    }

    public void remove(Object key) {
        cache.invalidate(key);
    }

    public Map<Object, Object> toMap() {
        return cache.asMap();
    }

}
