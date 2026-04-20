package com.model.bean;

public class DiseaseDetail {
    private int id;
    private String name;
    private String description;
    private int foodCount; // Số món ăn tương thích

    // Constructors
    public DiseaseDetail() {
    }

    public DiseaseDetail(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getFoodCount() { return foodCount; }
    public void setFoodCount(int foodCount) { this.foodCount = foodCount; }
}