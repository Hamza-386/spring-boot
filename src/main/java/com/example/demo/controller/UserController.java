package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;  
    @Autowired
    private AuthenticationManager authenticationManager;  
    @Autowired
    private UserService userService;  
    @Autowired
    private PasswordEncoder passwordEncoder;  

    @PostMapping("/register")
    public String register(@RequestParam String firstName, 
                           @RequestParam String lastName, 
                           @RequestParam String dob, 
                           @RequestParam String tel, 
                           @RequestParam String password) {
        // Encode password before saving
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(firstName, lastName, dob, tel, encodedPassword);
        userRepository.save(user);  
        return "redirect:/connecter";  
    }
    
    
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Convert User entities to UserResponse DTOs
        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(user.getTel(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(userResponses);
    }

    // Inner class for UserResponse
    public static class UserResponse {
        private String tel;
        private String prenom;
        private String nom;

        // Constructor
        public UserResponse(String tel, String prenom, String nom) {
            this.tel = tel;
            this.prenom = prenom;
            this.nom = nom;
        }

        // Getters and Setters
        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }
    }

    
    // Endpoint to delete a user by ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/seconnecter")  // GET method for login authentication
    public ResponseEntity<?> login(@RequestParam String tel, 
                                   @RequestParam String password) {
        // Retrieve user from database using phone number
        Optional<User> userOptional = userService.findUserByPhoneNumber(tel);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the provided password matches the stored hashed password
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Successful authentication logic here (set security context)
                Authentication authentication = new UsernamePasswordAuthenticationToken(user.getTel(), password);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Return success with only the phone number
                return ResponseEntity.ok().body(new LoginResponse(true, "Login successful", tel));
            } else {
                return ResponseEntity.ok().body(new LoginResponse(false, "Mot de passe incorrect", null));
            }
        } else {
            return ResponseEntity.ok().body(new LoginResponse(false, "Utilisateur introuvable", null));
        }
    }

    // Response DTO for login response
    public static class LoginResponse {
        private boolean success;
        private String message;
        private String tel;

        public LoginResponse(boolean success, String message, String tel) {
            this.success = success;
            this.message = message;
            this.tel = tel;
        }

        // Getters and Setters
        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}