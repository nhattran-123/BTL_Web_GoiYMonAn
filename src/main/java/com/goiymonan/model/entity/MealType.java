/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.goiymonan.model.entity;

public class MealType {
    private int meal_type_id;
    private String meal_name;
    private double target_calories;

    public int getMeal_type_id() {
        return meal_type_id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public double getTarget_calories() {
        return target_calories;
    }

    public void setMeal_type_id(int meal_type_id) {
        this.meal_type_id = meal_type_id;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public void setTarget_calories(double target_calories) {
        this.target_calories = target_calories;
    }

   
}