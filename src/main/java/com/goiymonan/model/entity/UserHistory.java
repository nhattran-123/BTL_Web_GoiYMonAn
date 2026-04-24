/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.goiymonan.model.entity;

/**
 *
 * @author Nhat0
 */

import java.util.Date;

public class UserHistory {
    private int id;
    private int userId;
    private int foodId;
    private Date eatenAt;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setEatenAt(Date eatenAt) {
        this.eatenAt = eatenAt;
    }

    // Getter Setter

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public Date getEatenAt() {
        return eatenAt;
    }
}