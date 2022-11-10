package com.enrique.project1back.repository;

import com.enrique.project1back.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findUserByEmail(final String email);
    int deleteUserById(final String id);
    boolean existsByEmail(final String email);
}
