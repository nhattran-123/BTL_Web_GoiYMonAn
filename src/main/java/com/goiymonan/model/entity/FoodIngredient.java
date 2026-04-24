/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.goiymonan.model.entity;

public class FoodIngredient {
    private int id;
    private int foodId;
    private int ingredientId;
    private double quantity;
    private String unit;

    public void setId(int id) {
        this.id = id;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Getter Setter

    public int getId() {
        return id;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
