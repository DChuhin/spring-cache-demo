package com.example.springcachedemo.repository.firestore;


import com.example.springcachedemo.service.model.User;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;
import org.springframework.cloud.gcp.data.firestore.Document;

@Data
@Document(collectionName = "users")
class UserDocument {
    @DocumentId
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
