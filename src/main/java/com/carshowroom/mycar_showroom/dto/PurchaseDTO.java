package com.carshowroom.mycar_showroom.dto;

import jakarta.validation.constraints.*;

public class PurchaseDTO {
    @NotNull(message = "Car ID required")
    @Min(value = 1, message = "Car ID must be positive")
    private Long carId;

    @Size(max = 100, message = "Name too long")
    private String customerName;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number")
    private String phone;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Payment method required")
    private String paymentMethod;

    // Constructors
    public PurchaseDTO() {}

    // Getters/Setters
    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}

