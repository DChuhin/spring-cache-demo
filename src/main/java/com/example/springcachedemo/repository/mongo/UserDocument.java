package com.example.springcachedemo.repository.mongo;

import com.example.springcachedemo.service.model.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
class UserDocument {
    @Id
    private String id;
    private String name;

    User toUser() {
        return User.builder()
                .id(id)
                .name(name)
                .build();
    }

    static UserDocument fromUser(User user) {
        var document = new UserDocument();
        document.setId(user.getId());
        document.setName(user.getName());
        return document;
    }

}
