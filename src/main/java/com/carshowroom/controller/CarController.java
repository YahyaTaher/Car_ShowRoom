package com.carshowroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carshowroom.entity.Car;
import com.carshowroom.repository.CarRepository;


@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRepository repo;

    @GetMapping
    public List <Car> getCars(){
        return repo.findAll();
    }
    
}
