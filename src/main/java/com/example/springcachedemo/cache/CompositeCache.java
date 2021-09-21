package com.example.springcachedemo.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

@Slf4j
@RequiredArgsConstructor
public class CompositeCache implements Cache {

    private final Cache l1Cache;
    private final Cache l2Cache;

    @Override
    public String getName() {
        return l1Cache.getName();
    }

    @Override
    public Object getNativeCache() {
        return l1Cache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        var l1Value = l1Cache.get(key);
        if (l1Value != null) {
            log.info("Found {} in l1 cache", key);
            return l1Value;
        }
        var l2Value = l2Cache.get(key);
        if (l2Value != null) {
            log.info("Found {} in l2 cache", key);
            l1Cache.put(key, l2Value.get());
            return l2Value;
        }
        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        var l1Value = l1Cache.get(key, type);
        if (l1Value != null) {
            log.info("Found {} in l1 cache", key);
            return l1Value;
        }
        var l2Value = l2Cache.get(key, type);
        if (l2Value != null) {
            log.info("Found {} in l2 cache", key);
            l1Cache.put(key, l2Value);
            return l2Value;
        }
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        var l1Value = l1Cache.get(key, valueLoader);
        if (l1Value != null) {
            log.info("Found {} in l1 cache", key);
            return l1Value;
        }
        var l2Value = l2Cache.get(key, valueLoader);
        if (l2Value != null) {
            log.info("Found {} in l2 cache", key);
            l1Cache.put(key, l2Value);
            return l2Value;
        }
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        log.info("Put {} to l1 cache", key);
        l1Cache.put(key, value);
        if (value != null) {
            log.info("Put {} to l2 cache", key);
            l2Cache.put(key, value);
        }
    }

    @Override
    public void evict(Object key) {
        l1Cache.evict(key);
        l2Cache.evict(key);
    }

    @Override
    public void clear() {
        l1Cache.clear();
        l2Cache.clear();
    }
}
