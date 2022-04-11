package com.example.springcachedemo.repository.firestore;

import com.example.springcachedemo.service.UserAccess;
import com.example.springcachedemo.service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnProperty(value = "storage.mode", havingValue = "firestore")
@RequiredArgsConstructor
class FirestoreUserAccess implements UserAccess {

    private final UserFirestoreRepository userFirestoreRepository;

    @Override
    public Optional<User> findById(String id) {
        return userFirestoreRepository.findById(id).blockOptional().map(UserDocument::toUser);
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
        return userFirestoreRepository.save(document).blockOptional().map(UserDocument::toUser).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        userFirestoreRepository.deleteById(id);
    }
}
