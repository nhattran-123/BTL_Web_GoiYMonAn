package com.model.bean;

public class AdjustedRecipe {
    private int id;
    private int foodId;
    private int userId;
    private String recipe;
    private double calories;

    public AdjustedRecipe() {}

    public AdjustedRecipe(int foodId, int userId, String recipe, double calories) {
        this.foodId = foodId;
        this.userId = userId;
        this.recipe = recipe;
        this.calories = calories;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getRecipe() { return recipe; }
    public void setRecipe(String recipe) { this.recipe = recipe; }
    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }
    
}