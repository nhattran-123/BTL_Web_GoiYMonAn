/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.goiymonan.model.entity;

import java.util.Date;

public class DailyMenu {
    private int menuId;
    private int userId;
    private int planId;
    private Date menuDate;
    private double totalCalories;
    private String status;

    public DailyMenu() {}

    // Getter & Setter
    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }

    public Date getMenuDate() { return menuDate; }
    public void setMenuDate(Date menuDate) { this.menuDate = menuDate; }

    public double getTotalCalories() { return totalCalories; }
    public void setTotalCalories(double totalCalories) { this.totalCalories = totalCalories; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}