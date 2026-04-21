package com.model.bean;

public class AdjustedRecipe {
    private int id;
    private int foodId;
    private int userId;
    private String recipe;
    private double calories;
     private double fat;
    private double protein;
    private double carbohydrate;

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
    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }
    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }
    public double getCarbohydrate() { return carbohydrate; }
    public void setCarbohydrate(double carbohydrate) { this.carbohydrate = carbohydrate; }
    
}