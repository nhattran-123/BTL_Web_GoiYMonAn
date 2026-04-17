/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.bean;

public class MealType {
    private int mealTypeId;
    private String mealName;
    private double targetCalories;

    public int getMealTypeId() {
        return mealTypeId;
    }

    public String getMealName() {
        return mealName;
    }

    public double getTargetCalories() {
        return targetCalories;
    }

    // Getter Setter

    public void setMealTypeId(int mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setTargetCalories(double targetCalories) {
        this.targetCalories = targetCalories;
    }
}