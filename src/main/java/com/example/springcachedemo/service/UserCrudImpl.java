package com.example.springcachedemo.service;

import com.example.springcachedemo.repository.User;
import com.example.springcachedemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCrudImpl implements UserCrud {

    private final UserRepository userRepository;

    @Override
    public User read(String id) {
        log.info("Doing db request to get {} user", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(User user) {
        log.info("Updating user {}", user.getId());
        return userRepository.save(user);
    }

    @Override
    public User create(User user) {
        var saved = userRepository.save(user);
        log.info("Created user {}", saved.getId());
        return saved;
    }

    @Override
    public void delete(String id) {
        log.info("Deleting user {}", id);
        userRepository.deleteById(id);
    }
}
