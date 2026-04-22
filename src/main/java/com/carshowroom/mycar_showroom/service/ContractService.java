package com.carshowroom.mycar_showroom.service;

import com.carshowroom.mycar_showroom.entity.Contract;
import com.carshowroom.mycar_showroom.entity.Car;
import com.carshowroom.mycar_showroom.entity.Customer;
import com.carshowroom.mycar_showroom.entity.CarStatus;
import com.carshowroom.mycar_showroom.entity.ContractStatus;
import com.carshowroom.mycar_showroom.repository.ContractRepository;
import com.carshowroom.mycar_showroom.repository.CarRepository;
import com.carshowroom.mycar_showroom.dto.PurchaseDTO;
import com.carshowroom.mycar_showroom.entity.User;
import com.carshowroom.mycar_showroom.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    @Transactional
    public List<Map<String, Object>> getMyContracts() {
        String username = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;
        if (username == null || "anonymousUser".equalsIgnoreCase(username)) {
            throw new IllegalArgumentException("Not authenticated");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return contractRepository.findByUserId(user.getId()).stream().map(c -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", c.getId());
            dto.put("status", c.getStatus());
            dto.put("paymentMethod", c.getPaymentMethod());
            dto.put("totalPrice", c.getTotalPrice());
            dto.put("customerName", c.getCustomerNameSnapshot());
            dto.put("customerPhone", c.getCustomerPhoneSnapshot());
            dto.put("customerEmail", c.getCustomerEmailSnapshot());
            if (c.getCar() != null) {
                dto.put("carId", c.getCar().getId());
                dto.put("brand", c.getCar().getBrand());
                dto.put("model", c.getCar().getModel());
                dto.put("year", c.getCar().getYear());
                dto.put("price", c.getCar().getPrice());
                dto.put("branchName", c.getCar().getBranch() != null ? c.getCar().getBranch().getName() : null);
                dto.put("colors", c.getCar().getColors());
                dto.put("imageUrls", c.getCar().getImageUrls());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<Map<String, Object>> getPendingPurchaseContracts() {
        return contractRepository.findByStatus(ContractStatus.PENDING).stream().map(c -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", c.getId());
            dto.put("status", c.getStatus());
            dto.put("paymentMethod", c.getPaymentMethod());
            dto.put("totalPrice", c.getTotalPrice());
            dto.put("userId", c.getUser() != null ? c.getUser().getId() : null);
            dto.put("username", c.getUser() != null ? c.getUser().getUsername() : null);
            dto.put("customerName", c.getCustomerNameSnapshot());
            dto.put("customerPhone", c.getCustomerPhoneSnapshot());
            dto.put("customerEmail", c.getCustomerEmailSnapshot());
            if (c.getCar() != null) {
                dto.put("carId", c.getCar().getId());
                dto.put("brand", c.getCar().getBrand());
                dto.put("model", c.getCar().getModel());
                dto.put("year", c.getCar().getYear());
                dto.put("branchName", c.getCar().getBranch() != null ? c.getCar().getBranch().getName() : null);
                dto.put("quantityAvailable", c.getCar().getQuantityAvailable());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void approvePurchaseContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found"));

        if (contract.getStatus() != ContractStatus.PENDING) {
            throw new IllegalArgumentException("Only PENDING contracts can be approved");
        }

        Car car = contract.getCar();
        if (car == null) throw new IllegalArgumentException("Car not found for contract");

        Integer qty = car.getQuantityAvailable();
        if (car.getStatus() != CarStatus.AVAILABLE || qty == null || qty <= 0) {
            throw new IllegalArgumentException("Car is not available anymore");
        }

        car.setQuantityAvailable(qty - 1);
        if (car.getQuantityAvailable() <= 0) {
            car.setQuantityAvailable(0);
            car.setStatus(CarStatus.UNAVAILABLE);
        }
        carRepository.save(car);

        contract.setStatus(ContractStatus.COMPLETED);
        contractRepository.save(contract);

        auditService.log("APPROVE_PURCHASE", "Contract " + contractId + " approved. Car " + car.getId() + " qty now " + car.getQuantityAvailable());
    }

    @Transactional
    public void rejectPurchaseContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found"));

        if (contract.getStatus() != ContractStatus.PENDING) {
            throw new IllegalArgumentException("Only PENDING contracts can be rejected");
        }

        contract.setStatus(ContractStatus.CANCELLED);
        contractRepository.save(contract);
        auditService.log("REJECT_PURCHASE", "Contract " + contractId + " rejected");
    }

@Transactional
    public void createPurchase(PurchaseDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;
        if (username == null || "anonymousUser".equalsIgnoreCase(username)) {
            throw new IllegalArgumentException("You must be logged in to purchase a car");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new IllegalArgumentException("Customer profile not found");
        }

        // Check car availability
        Car car = carRepository.findById(dto.getCarId()).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        if (car.getStatus() != CarStatus.AVAILABLE || car.getQuantityAvailable() == null || car.getQuantityAvailable() <= 0) {
            throw new IllegalArgumentException("Car is not available for purchase");
        }

        // Create contract
        Contract contract = new Contract();
        contract.setCustomer(customer);
        contract.setCar(car);
        contract.setUser(user);
        contract.setStatus(ContractStatus.PENDING);
        contract.setPaymentMethod(dto.getPaymentMethod());
        contract.setTotalPrice(car.getPrice());
        contract.setCustomerNameSnapshot(dto.getCustomerName() != null && !dto.getCustomerName().isBlank() ? dto.getCustomerName().trim() : customer.getFullName());
        contract.setCustomerPhoneSnapshot(dto.getPhone() != null && !dto.getPhone().isBlank() ? dto.getPhone().trim() : customer.getPhone());
        contract.setCustomerEmailSnapshot(dto.getEmail() != null && !dto.getEmail().isBlank() ? dto.getEmail().trim() : customer.getEmail());
        contractRepository.save(contract);

        auditService.log("CREATE_PURCHASE_REQUEST", "Purchase request created for car " + dto.getCarId() + " by user " + user.getId());
    }


    public List<Contract> getContractsByCustomer(Long customerId) {
        return contractRepository.findByCustomerId(customerId);
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public void cancelContract(Long contractId) throws Exception {
        Optional<Contract> contractOpt = contractRepository.findById(contractId);
        if (!contractOpt.isPresent()) {
            throw new Exception("Contract not found");
        }

        Contract contract = contractOpt.get();
        if (contract.getStatus() == ContractStatus.CANCELLED || contract.getStatus() == ContractStatus.COMPLETED) {
            throw new Exception("Cannot cancel a " + contract.getStatus() + " contract");
        }

        contract.setStatus(ContractStatus.CANCELLED);
        contractRepository.save(contract);

        // Release car back to available status
        Car car = contract.getCar();
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);
    }
}
