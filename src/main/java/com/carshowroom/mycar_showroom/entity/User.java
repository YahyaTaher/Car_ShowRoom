package com.carshowroom.mycar_showroom.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;

    private String role; // e.g., "ROLE_CUSTOMER" or "ROLE_ADMIN"

    @OneToOne(mappedBy = "user")
    private Customer customer;

    // Constructors
    public User() {}

    public User(String username, String password, Customer customer) {
        this.username = username;
        this.password = password;
        this.customer = customer;
        this.role = "ROLE_CUSTOMER"; // Default role
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}'; 
    }
}
