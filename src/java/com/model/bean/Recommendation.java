/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.bean;

import java.util.Date;

public class Recommendation {
    private int id;
    private int userId;
    private int foodId;
    private double score;
    private String reason;
    private Date createdAt;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public double getScore() {
        return score;
    }

    public String getReason() {
        return reason;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}