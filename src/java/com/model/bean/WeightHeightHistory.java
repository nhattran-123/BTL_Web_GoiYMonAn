/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.bean;

import java.util.Date;

public class WeightHeightHistory {
    private int id;
    private int userId;
    private double weight;
    private double height;
    private Date recordDate;

    // Getter Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public Date getRecordDate() { return recordDate; }
    public void setRecordDate(Date recordDate) { this.recordDate = recordDate; }
}
