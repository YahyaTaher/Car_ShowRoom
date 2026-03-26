package com.carshowroom.mycar_showroom.controller;

import com.carshowroom.mycar_showroom.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ContractsController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/contracts")
    public ResponseEntity<?> createContract(@RequestBody ContractRequest request) {
        try {
            contractService.createRentalContract(request);
            return ResponseEntity.ok("Contract created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

class ContractRequest {
    String customerSsn, carId, branchId, paymentMethod;
    // getters/setters stub
}
