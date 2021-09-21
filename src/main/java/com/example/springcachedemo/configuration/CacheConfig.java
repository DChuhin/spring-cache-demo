package com.example.springcachedemo.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

    private static final Duration longCacheTime = Duration.of(1, ChronoUnit.HOURS);
    private static final Duration shortCacheTime = Duration.of(10, ChronoUnit.MINUTES);

    @Primary
    @Bean(name = "longCacheManager")
    public CacheManager longCacheManager() {
        var caffeine = Caffeine.newBuilder()
                .expireAfterWrite(longCacheTime);

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

    @Bean(name = "shortCacheManager")
    public CacheManager shortCacheManager() {
        var caffeine = Caffeine.newBuilder()
                .expireAfterWrite(shortCacheTime);

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
