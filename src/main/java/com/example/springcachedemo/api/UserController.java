package com.example.springcachedemo.api;

import com.example.springcachedemo.repository.User;
import com.example.springcachedemo.service.UserCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserCrud userService;

    @GetMapping("/{id}")
    public User get(@PathVariable String id) {
        return userService.read(id);
    }

}
