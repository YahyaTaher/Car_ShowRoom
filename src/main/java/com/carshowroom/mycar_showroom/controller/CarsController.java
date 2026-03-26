package com.carshowroom.mycar_showroom.controller;

import com.carshowroom.mycar_showroom.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CarsController {

    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public List<Map<String, Object>> getCars(@RequestParam(required = false) String company, 
                                           @RequestParam(required = false) String model,
                                           @RequestParam(required = false) String color,
                                           @RequestParam(required = false) String branch) {
        return carService.searchCars(company, model, color, branch);
    }
}
