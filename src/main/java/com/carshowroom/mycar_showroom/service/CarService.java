package com.carshowroom.mycar_showroom.service;

import com.carshowroom.mycar_showroom.dto.CarDTO;
import com.carshowroom.mycar_showroom.entity.Branch;
import com.carshowroom.mycar_showroom.entity.Car;
import com.carshowroom.mycar_showroom.entity.CarStatus;
import com.carshowroom.mycar_showroom.repository.BranchRepository;
import com.carshowroom.mycar_showroom.repository.CarRepository;
import com.carshowroom.mycar_showroom.service.AuditService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AuditService auditService;

    @Transactional
    public List<Map<String, Object>> searchCars(String company, String model, String color, String branch) {
        List<Car> cars = carRepository.findAvailableCars();
        
        List<Car> filtered = cars.stream().filter(car -> {
            boolean matches = true;
            if (company != null && !company.isBlank() && !car.getBrand().equalsIgnoreCase(company)) {
                matches = false;
            }
            if (model != null && !model.isBlank() && !car.getModel().equalsIgnoreCase(model)) {
                matches = false;
            }
            if (branch != null && !branch.isBlank() && (car.getBranch() == null || !car.getBranch().getName().equalsIgnoreCase(branch))) {
                matches = false;
            }
            return matches;
        }).collect(Collectors.toList());
        
        return filtered.stream().map(car -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", car.getId());
            dto.put("brand", car.getBrand());
            dto.put("model", car.getModel());
            dto.put("year", car.getYear());
            dto.put("price", car.getPrice());
            dto.put("status", car.getStatus());
            dto.put("branchId", car.getBranch() != null ? car.getBranch().getId() : null);
            dto.put("branchName", car.getBranch() != null ? car.getBranch().getName() : "N/A");
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Car> getAvailableCars() {
        return carRepository.findAvailableCars();
    }

    @Transactional
    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addCar(CarDTO dto) {
        Branch branch = branchRepository.findById(dto.getBranchId()).orElseThrow(() -> new IllegalArgumentException("Branch not found"));
        Car car = new Car();
        car.setPlateNumber(dto.getPlateNumber());
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setYear(dto.getYear());
        car.setPrice(dto.getPrice());
        car.setBranch(branch);
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);
        auditService.log("CREATE_CAR", "Car created: " + dto.getBrand() + " " + dto.getModel());
    }

    @Transactional
    public void updateCarPrice(Long id, BigDecimal newPrice) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        car.setPrice(newPrice);
        carRepository.save(car);
        auditService.log("UPDATE_PRICE", "Car ID " + id + " price updated to " + newPrice);
    }

    @Transactional
    public void deleteCar(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        carRepository.delete(car);
        auditService.log("DELETE_CAR", "Car ID " + id + " deleted");
    }

    public void updateCarStatus(Long carId, String status) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null) {
            try {
                car.setStatus(CarStatus.valueOf(status));
                carRepository.save(car);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid car status: " + status);
            }
        }
    }
}

