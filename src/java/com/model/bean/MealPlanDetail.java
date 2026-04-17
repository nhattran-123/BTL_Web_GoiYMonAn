/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.bean;

public class MealPlanDetail {
    private int layoutId;
    private int settingId;
    private int mealTypeId;
    private double portionSize;
    private int cookLevel;
    private boolean needCooking;
    private int displayOrder;

    public int getLayoutId() {
        return layoutId;
    }

    public int getSettingId() {
        return settingId;
    }

    public int getMealTypeId() {
        return mealTypeId;
    }

    public double getPortionSize() {
        return portionSize;
    }

    public int getCookLevel() {
        return cookLevel;
    }

    public boolean isNeedCooking() {
        return needCooking;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    // Getter Setter

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public void setMealTypeId(int mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public void setPortionSize(double portionSize) {
        this.portionSize = portionSize;
    }

    public void setCookLevel(int cookLevel) {
        this.cookLevel = cookLevel;
    }

    public void setNeedCooking(boolean needCooking) {
        this.needCooking = needCooking;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}