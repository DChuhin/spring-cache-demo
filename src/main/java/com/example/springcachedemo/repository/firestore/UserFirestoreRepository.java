package com.example.springcachedemo.repository.firestore;

import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

interface UserFirestoreRepository extends FirestoreReactiveRepository<UserDocument> {
}
