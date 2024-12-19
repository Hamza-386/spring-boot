package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        System.out.println("User saved: " + user.getFirstName());
    }
    public Optional<User> findUserByPhoneNumber(String tel) {
        return userRepository.findByTel(tel); // Retrieve user by phone number
    }
}
