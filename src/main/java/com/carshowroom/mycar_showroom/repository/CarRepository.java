package com.carshowroom.mycar_showroom.repository;

import com.carshowroom.mycar_showroom.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE c.available = true") // Stub for available cars
    List<Car> findAvailableCars(String company, String model, String color, String branch);
}
