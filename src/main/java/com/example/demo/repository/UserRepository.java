package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByTel(String tel); // Query method to find user by phone number (tel)

}
