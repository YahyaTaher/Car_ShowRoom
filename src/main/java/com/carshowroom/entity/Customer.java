package com.carshowroom.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "customer")
public class Customer {
    @Id
    private int  SSN;
    private String fname;
    private String lname;
    private String street;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city_id;

    private int building_number;
    private long phone_1;
    private long phone_2;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender_id;

    private Date birthdate;

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int sSN) {
        SSN = sSN;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public City getCity_id() {
        return city_id;
    }

    public void setCity_id(City city_id) {
        this.city_id = city_id;
    }

    public int getBuilding_number() {
        return building_number;
    }

    public void setBuilding_number(int building_number) {
        this.building_number = building_number;
    }

    public long getPhone_1() {
        return phone_1;
    }

    public void setPhone_1(long phone_1) {
        this.phone_1 = phone_1;
    }

    public long getPhone_2() {
        return phone_2;
    }

    public void setPhone_2(long phone_2) {
        this.phone_2 = phone_2;
    }

    public Gender getGender_id() {
        return gender_id;
    }

    public void setGender_id(Gender gender_id) {
        this.gender_id = gender_id;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
