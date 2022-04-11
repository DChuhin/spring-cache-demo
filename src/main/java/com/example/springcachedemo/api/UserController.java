package com.example.springcachedemo.api;

import com.example.springcachedemo.service.UserCrud;
import com.example.springcachedemo.service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userCrud.create(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userCrud.delete(id);
    }
}
