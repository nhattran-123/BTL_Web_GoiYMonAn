package com.model.bean;

public class Ingredient {
    private int id;
    private String name;
    private double calories;
    private String category;
    private double protein;
    private double fat;
    private double carbohydrate;

    public Ingredient() {
    }

    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient(int id, String name, double calories, String category) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.category = category;
    }
    
    public Ingredient(int id, String name, double calories, String category, double protein, double fat, double carbohydrate) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.category = category;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }


}