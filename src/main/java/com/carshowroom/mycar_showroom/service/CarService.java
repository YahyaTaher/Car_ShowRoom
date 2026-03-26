package com.carshowroom.mycar_showroom.service;

import com.carshowroom.mycar_showroom.entity.Car;
import com.carshowroom.mycar_showroom.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Map<String, Object>> searchCars(String company, String model, String color, String branch) {
        // Stub: Query with filters, join for companyName, modelName, year, price, branchName
        List<Car> cars = carRepository.findAvailableCars(company, model, color, branch);
        return cars.stream().map(car -> Map.of(
            "id", car.getId(),
            "companyName", "BMW", // From join
            "modelName", "X5",
            "year", 2023,
            "price", 100.0,
            "branchName", "NYC"
        )).collect(Collectors.toList());
    }
}
