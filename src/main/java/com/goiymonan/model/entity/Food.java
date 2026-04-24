package com.goiymonan.model.entity;

public class Food {
    private int food_id;
    private String food_name;
    private String description;
    private String recipe;
    private String image_url;
    private double calories;
    private double protein;
    private double fat;
    private double carbohydrate;
    private double suitabilityScore;
    private int allergyConflictCount;
    private int diseaseMatchCount;

    // --- CÁC TRƯỜNG THÊM MỚI ---
    private boolean customized = false; // Mặc định là false (chưa tùy chỉnh)
    private double customCalories;      // Lưu lượng calo sau khi tùy chỉnh

    public Food() {}

    // Getters and Setters (giữ nguyên các thuộc tính cũ)
    public int getFood_id() { return food_id; }
    public void setFood_id(int food_id) { this.food_id = food_id; }
    public String getFood_name() { return food_name; }
    public void setFood_name(String food_name) { this.food_name = food_name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRecipe() { return recipe; }
    public void setRecipe(String recipe) { this.recipe = recipe; }
    public String getImage_url() { return image_url; }
    public void setImage_url(String image_url) { this.image_url = image_url; }
    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }
    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }
    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }
    public double getCarbohydrate() { return carbohydrate; }
    public void setCarbohydrate(double carbohydrate) { this.carbohydrate = carbohydrate; }
    public double getSuitabilityScore() { return suitabilityScore; }
    public void setSuitabilityScore(double suitabilityScore) { this.suitabilityScore = suitabilityScore; }
    public int getAllergyConflictCount() { return allergyConflictCount; }
    public void setAllergyConflictCount(int allergyConflictCount) { this.allergyConflictCount = allergyConflictCount; }
    public int getDiseaseMatchCount() { return diseaseMatchCount; }
    public void setDiseaseMatchCount(int diseaseMatchCount) { this.diseaseMatchCount = diseaseMatchCount; }
    
    // Getters/Setters cho trường thêm mới
    public boolean isCustomized() { return customized; }
    public void setCustomized(boolean customized) { this.customized = customized; }
    
    public double getCustomCalories() { return customCalories; }
    public void setCustomCalories(double customCalories) { this.customCalories = customCalories; }
    
    /**
     * Hàm tiện ích: Tự động trả về calo tùy chỉnh (nếu có), ngược lại trả về calo gốc
     */
    public double getDisplayCalories() {
        return customized ? customCalories : calories;
    }
}