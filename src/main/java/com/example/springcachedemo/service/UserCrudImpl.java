package com.example.springcachedemo.service;

import com.example.springcachedemo.service.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "longCacheManager", cacheNames = "userCache")
public class UserCrudImpl implements UserCrud {

    private final UserAccess userAccess;

    @Override
    // @Cacheable(key = "#id")
    public User read(String id) {
        log.info("Doing db request to get {} user", id);
        return userAccess.findById(id).orElse(null);
    }

    @Override
    @CachePut(key = "#user.id")
    public User update(User user) {
        log.info("Updating user {}", user.getId());
        return userAccess.update(user);
    }

    @Override
    @CachePut(key = "#result.id")
    public User create(User user) {
        var saved = userAccess.create(user);
        log.info("Created user {}", saved.getId());
        return saved;
    }

    @Override
    @CacheEvict(key = "#id")
    public void delete(String id) {
        log.info("Deleting user {}", id);
        userAccess.deleteById(id);
    }
}
