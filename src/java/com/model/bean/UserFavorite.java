/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.bean;

/**
 *
 * @author Nhat0
 */


public class UserFavorite {
    private int id;
    private int userId;
    private int foodId;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
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
}
