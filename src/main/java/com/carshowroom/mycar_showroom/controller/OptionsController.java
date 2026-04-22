package com.carshowroom.mycar_showroom.controller;

import com.carshowroom.mycar_showroom.dto.ResponseWrapper;
import com.carshowroom.mycar_showroom.repository.BranchRepository;
import com.carshowroom.mycar_showroom.repository.CarRepository;
import com.carshowroom.mycar_showroom.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/options")
public class OptionsController {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> getDynamicOptions() {
        List<Map<String, Object>> branches = branchRepository.findAllByOrderByNameAsc().stream().map(b -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", b.getId());
            dto.put("name", b.getName());
            dto.put("city", b.getCity());
            return dto;
        }).collect(Collectors.toList());

        List<String> companies = carRepository.findDistinctBrands();

        List<String> paymentMethods = paymentRepository.findDistinctPaymentMethods();
        if (paymentMethods.isEmpty()) {
            paymentMethods = List.of("credit_card", "bank_transfer", "crypto");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("branches", branches);
        data.put("companies", companies);
        data.put("paymentMethods", paymentMethods);

        return ResponseEntity.ok(ResponseWrapper.success("Dynamic options loaded", data));
    }
}

