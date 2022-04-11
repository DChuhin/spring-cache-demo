package com.example.springcachedemo.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String id;
    private String name;
}
