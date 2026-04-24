/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.goiymonan.model.entity;

import java.util.Date;

public class UserPlan {
    private int settingId;
    private int userId;
    private Date weekStartDay;
    private boolean sameScheduleDaily;
    private String nutritionGoal;

    public int getSettingId() {
        return settingId;
    }

    public int getUserId() {
        return userId;
    }

    public Date getWeekStartDay() {
        return weekStartDay;
    }

    public boolean isSameScheduleDaily() {
        return sameScheduleDaily;
    }

    public String getNutritionGoal() {
        return nutritionGoal;
    }

    // Getter Setter

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setWeekStartDay(Date weekStartDay) {
        this.weekStartDay = weekStartDay;
    }

    public void setSameScheduleDaily(boolean sameScheduleDaily) {
        this.sameScheduleDaily = sameScheduleDaily;
    }

    public void setNutritionGoal(String nutritionGoal) {
        this.nutritionGoal = nutritionGoal;
    }
}
