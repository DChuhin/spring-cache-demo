package com.example.springcachedemo.api;

import com.example.springcachedemo.repository.User;
import com.example.springcachedemo.service.UserCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserCrud userCrud;

    @GetMapping("/{id}")
    public User get(@PathVariable String id) {
        return userCrud.read(id);
    }

    @PostMapping("/")
    public User update(@RequestBody User user) {
        return userCrud.update(user);
    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userCrud.create(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userCrud.delete(id);
    }
}
