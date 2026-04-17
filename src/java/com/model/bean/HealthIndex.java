/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.bean;

import java.util.Date;

public class HealthIndex {
    private int id;
    private int userId;
    private double bmi;
    private double bmr;
    private double tdee;
    private Date calculatedAt;

    // Getter Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }

    public double getBmr() { return bmr; }
    public void setBmr(double bmr) { this.bmr = bmr; }

    public double getTdee() { return tdee; }
    public void setTdee(double tdee) { this.tdee = tdee; }

    public Date getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(Date calculatedAt) { this.calculatedAt = calculatedAt; }
}
