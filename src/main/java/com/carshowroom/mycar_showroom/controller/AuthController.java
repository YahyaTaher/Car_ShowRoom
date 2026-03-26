package com.carshowroom.mycar_showroom.controller;

import com.carshowroom.mycar_showroom.entity.User;
import com.carshowroom.mycar_showroom.entity.Customer;
import com.carshowroom.mycar_showroom.entity.Role;
import com.carshowroom.mycar_showroom.security.JwtUtil; // Assume exists based on config
import com.carshowroom.mycar_showroom.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtUtil.generateToken(authentication);
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new LoginResponse(token, user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Create Customer
        Customer customer = new Customer();
        customer.setSsn(request.getSsn());
        customer.setName(request.getName());
        // ... set other fields
        authService.saveCustomer(customer);

        // Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCustSsn(customer.getSsn());
        Role customerRole = authService.getRoleByName("CUSTOMER");
        user.setRole(customerRole);
        authService.saveUser(user);

        return ResponseEntity.ok("Registration successful");
    }
}

// DTOs
class LoginRequest {
    private String username;
    private String password;
    // getters/setters
}

class RegisterRequest {
    private String ssn, name, username, password /* + other customer fields */;
    // getters/setters
}

class LoginResponse {
    private String token;
    private User user;
    public LoginResponse(String token, User user) { this.token = token; this.user = user; }
    // getters
}
