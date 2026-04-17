/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.bean;

public class MealPlanDetail {
    private int layout_id;
    private int Setting_id;
    private int Meal_type_id;
    private double portion_size;
    private int cook_level;
    private boolean need_cooking;
    private int display_oder;

    public void setLayout_id(int layout_id) {
        this.layout_id = layout_id;
    }

    public void setSetting_id(int Setting_id) {
        this.Setting_id = Setting_id;
    }

    public void setMeal_type_id(int Meal_type_id) {
        this.Meal_type_id = Meal_type_id;
    }

    public void setPortion_size(double portion_size) {
        this.portion_size = portion_size;
    }

    public void setCook_level(int cook_level) {
        this.cook_level = cook_level;
    }

    public void setNeed_cooking(boolean need_cooking) {
        this.need_cooking = need_cooking;
    }

    public void setDisplay_oder(int display_oder) {
        this.display_oder = display_oder;
    }

    public int getLayout_id() {
        return layout_id;
    }

    public int getSetting_id() {
        return Setting_id;
    }

    public int getMeal_type_id() {
        return Meal_type_id;
    }

    public double getPortion_size() {
        return portion_size;
    }

    public int getCook_level() {
        return cook_level;
    }

    public boolean isNeed_cooking() {
        return need_cooking;
    }

    public int getDisplay_oder() {
        return display_oder;
    }

    
}