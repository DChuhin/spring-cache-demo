package com.example.springcachedemo.service;

import com.example.springcachedemo.service.model.User;

public interface UserCrud {
    User create(User user);
    User read(String id);
    User update(User user);
    void delete(String id);
}
