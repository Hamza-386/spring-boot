package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Home page
    @GetMapping("/") 
    public String index() {
        return "index"; // This will resolve to templates/index.html
    }

    // Products page
    @GetMapping("/products")
    public String products() {
        return "products"; // maps to src/main/resources/templates/products.html
    }

    // Login page (mapped to /connecter)
    @GetMapping("/connecter")
    public String login() {
        return "login"; // maps to src/main/resources/templates/login.html
    }

    // Registration page (mapped to /inscrire)
    @GetMapping("/inscrire")
    public String inscrire() {
        return "register"; // maps to src/main/resources/templates/register.html
    }
    
    @GetMapping("/admin")
    public String admin() {
        return "admin"; // maps to src/main/resources/templates/register.html
    }
}
