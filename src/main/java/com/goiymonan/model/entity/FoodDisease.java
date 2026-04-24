/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.goiymonan.model.entity;

public class FoodDisease {
    private int id;
    private int foodId;
    private int diseaseId;
    private int rating;

    public int getId() {
        return id;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public int getRating() {
        return rating;
    }

    // Getter Setter

    public void setId(int id) {
        this.id = id;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
