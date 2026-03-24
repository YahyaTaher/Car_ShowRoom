package com.carshowroom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="gender")
public class Gender {
    @Id
    private int gender_id;
    private String gender;
    public int getGender_id() {
        return gender_id;
    }
    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}
