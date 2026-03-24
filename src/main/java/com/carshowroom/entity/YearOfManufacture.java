package com.carshowroom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "year_of_manufacture")
public class YearOfManufacture {
    @Id
    private Integer yearId;
    private Integer year;

    // getters & setters
    public Integer getYearId() { return yearId; }
    public void setYearId(Integer yearId) { this.yearId = yearId; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}