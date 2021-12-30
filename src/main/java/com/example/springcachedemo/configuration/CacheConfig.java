package com.example.springcachedemo.configuration;

import com.aerospike.client.AerospikeClient;
import com.example.springcachedemo.cache.CompositeCacheManager;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.aerospike.cache.AerospikeCacheConfiguration;
import org.springframework.data.aerospike.cache.AerospikeCacheManager;
import org.springframework.data.aerospike.convert.MappingAerospikeConverter;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

    private static final Duration longCacheTime = Duration.of(1, ChronoUnit.HOURS);
    private static final Duration shortCacheTime = Duration.of(10, ChronoUnit.MINUTES);

    // TODO define aerospike cache manager in aerospike config and make conditional on property
/*    private final AerospikeClient aerospikeClient;
    @Qualifier("customConverter")
    private final MappingAerospikeConverter mappingAerospikeConverter;*/

    @Primary
    @Bean(name = "longCacheManager")
    public CacheManager longCacheManager() {
        // return new CompositeCacheManager(level1Cache(), level2Cache());
        return level1Cache();
    }

    public CacheManager level1Cache() {
        var caffeine = Caffeine.newBuilder()
                .expireAfterWrite(longCacheTime);

        CaffeineCacheManager l1Cache = new CaffeineCacheManager();
        l1Cache.setCaffeine(caffeine);
        return l1Cache;
    }

/*    public CacheManager level2Cache() {
        AerospikeCacheConfiguration defaultConfiguration = new AerospikeCacheConfiguration("daniil-cache-test");
        return new AerospikeCacheManager(aerospikeClient, mappingAerospikeConverter, defaultConfiguration);
    }*/

    @Bean(name = "shortCacheManager")
    public CacheManager shortCacheManager() {
        var caffeine = Caffeine.newBuilder()
                .expireAfterWrite(shortCacheTime);

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
