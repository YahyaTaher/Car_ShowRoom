package com.carshowroom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "car")
public class Car {
    @Id
    private Integer carId;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "year_id")
    private YearOfManufacture year;

    // getters & setters
    public Integer getCarId() { return carId; }
    public void setCarId(Integer carId) { this.carId = carId; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public Model getModel() { return model; }
    public void setModel(Model model) { this.model = model; }

    public YearOfManufacture getYear() { return year; }
    public void setYear(YearOfManufacture year) { this.year = year; }
}