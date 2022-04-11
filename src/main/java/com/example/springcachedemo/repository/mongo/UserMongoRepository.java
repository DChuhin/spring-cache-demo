package com.example.springcachedemo.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserMongoRepository extends MongoRepository<UserDocument, String> {
}
