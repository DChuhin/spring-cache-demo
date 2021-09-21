package com.example.springcachedemo.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.NonNull;

import java.util.Collection;

@RequiredArgsConstructor
public class CompositeCacheManager implements CacheManager {

    private final CacheManager l1CacheManager;
    private final CacheManager l2CacheManager;

    @Override
    public Cache getCache(String name) {
        Cache l1Cache = l1CacheManager.getCache(name);
        Cache l2Cache = l2CacheManager.getCache(name);
        return new CompositeCache(l1Cache, l2Cache);
    }

    @Override
    public @NonNull Collection<String> getCacheNames() {
        return l1CacheManager.getCacheNames();
    }
}
