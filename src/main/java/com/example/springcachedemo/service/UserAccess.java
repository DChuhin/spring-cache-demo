package com.example.springcachedemo.service;


import com.example.springcachedemo.service.model.User;

import java.util.Optional;

public interface UserAccess {
    Optional<User> findById(String id);
    User create(User user);
    User update(User user);
    void deleteById(String id);
}
