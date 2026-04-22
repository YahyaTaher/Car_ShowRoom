package com.carshowroom.mycar_showroom.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "contracts")
public class Contract {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    private String paymentMethod;
    private BigDecimal totalPrice;

    private String customerNameSnapshot;
    private String customerPhoneSnapshot;
    private String customerEmailSnapshot;

    @Enumerated(EnumType.STRING)
    private ContractStatus status = ContractStatus.PENDING;

    // Constructors
    public Contract() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getCustomerNameSnapshot() { return customerNameSnapshot; }
    public void setCustomerNameSnapshot(String customerNameSnapshot) { this.customerNameSnapshot = customerNameSnapshot; }

    public String getCustomerPhoneSnapshot() { return customerPhoneSnapshot; }
    public void setCustomerPhoneSnapshot(String customerPhoneSnapshot) { this.customerPhoneSnapshot = customerPhoneSnapshot; }

    public String getCustomerEmailSnapshot() { return customerEmailSnapshot; }
    public void setCustomerEmailSnapshot(String customerEmailSnapshot) { this.customerEmailSnapshot = customerEmailSnapshot; }

    public ContractStatus getStatus() { return status; }
    public void setStatus(ContractStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", customerId=" + (customer != null ? customer.getId() : null) +
                ", userId=" + (user != null ? user.getId() : null) +
                ", carId=" + (car != null ? car.getId() : null) +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
