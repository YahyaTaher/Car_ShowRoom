package com.carshowroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carshowroom.entity.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {

}
