package com.carshowroom.mycar_showroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController - Serves frontend pages for the monolithic Spring Boot application
 * Maps URLs to Thymeleaf templates in src/main/resources/templates
 */
@Controller
public class HomeController {

    /**
     * Home page
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * User Login page (UC-02)
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * User Registration page (UC-01)
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Car Search & Browse page (UC-07)
     */
    @GetMapping("/cars")
    public String cars() {
        return "cars";
    }

    /**
     * Admin Dashboard page (UC-03, UC-04, UC-05, UC-06)
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    /**
     * Car Search page (UC-07)
     */
    @GetMapping("/search")
    public String search() {
        return "search";
    }

    /**
     * Purchase page
     */
    @GetMapping("/purchase")
    public String purchase() {
        return "purchase";
    }

    @GetMapping("/car-details")
    public String carDetails() {
        return "car-details";
    }
}
