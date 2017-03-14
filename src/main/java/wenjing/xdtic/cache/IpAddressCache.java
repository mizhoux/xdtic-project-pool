package wenjing.xdtic.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Component
public class IpAddressCache {

    @Bean("cacheForIpAddress")
    public Cache<Object, Object> getCache() {
        Cache<Object, Object> guavaCache = CacheBuilder.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();

        return guavaCache;
    }

    @Autowired
    @Qualifier("cacheForIpAddress")
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

    @Override
    public String toString() {
        return cache.asMap()
                .entrySet().stream()
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.joining("\n"));
    }

}
