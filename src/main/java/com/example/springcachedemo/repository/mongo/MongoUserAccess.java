package com.example.springcachedemo.repository.mongo;

import com.example.springcachedemo.service.UserAccess;
import com.example.springcachedemo.service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnProperty(value = "storage.mode", havingValue = "mongo")
@RequiredArgsConstructor
class MongoUserAccess implements UserAccess {

    private final UserMongoRepository userMongoRepository;

    @Override
    public Optional<User> findById(String id) {
        return userMongoRepository.findById(id).map(UserDocument::toUser);
    }

    @Override
    public User create(User user) {
        return save(user);
    }

    @Override
    public User update(User user) {
        return save(user);
    }

    public User save(User user) {
        var document = UserDocument.fromUser(user);
        return userMongoRepository.save(document).toUser();
    }

    @Override
    public void deleteById(String id) {
        userMongoRepository.deleteById(id);
    }
}
