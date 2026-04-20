package com.carshowroom.mycar_showroom.controller;

import com.carshowroom.mycar_showroom.dto.*;
import com.carshowroom.mycar_showroom.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
// @CrossOrigin(origins = "*") - Handled by global CORS in SecurityConfig
public class CarsController {

    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public ResponseEntity<ResponseWrapper<List<Map<String, Object>>>> getCars(@RequestParam(required = false) String company, 
                                           @RequestParam(required = false) String model,
                                           @RequestParam(required = false) String color,
                                           @RequestParam(required = false) String branch) {
        List<Map<String, Object>> cars = carService.searchCars(company, model, color, branch);
        return ResponseEntity.ok(ResponseWrapper.success("Cars retrieved successfully", cars));
    }

    @PostMapping("/cars")
    public ResponseEntity<ResponseWrapper<Void>> addCar(@Valid @RequestBody CarDTO carDTO) {
        carService.addCar(carDTO);
        return ResponseEntity.ok(ResponseWrapper.success("Car added successfully"));
    }

    @PutMapping("/cars/{id}/price")
    public ResponseEntity<ResponseWrapper<Void>> updatePrice(@PathVariable Long id, @RequestParam @jakarta.validation.constraints.DecimalMin(value = "0.01", message = "Price must be positive") BigDecimal price) {
        carService.updateCarPrice(id, price);
        return ResponseEntity.ok(ResponseWrapper.success("Price updated successfully"));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok(ResponseWrapper.success("Car deleted successfully"));
    }
}

