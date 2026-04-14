package com.model.bean;

public class IngredientItem {
    // Đã chuẩn hóa lại tên biến: chữ cái đầu phải viết thường
    private int ingredientId; 
    private String ingredientName; 
    private double quantity;
    private String unit;
    private double caloPerGram;
    private double fatPerGram;
    private double proteinPerGram;
    private double carbsPerGram;

    // Constructor đầy đủ
    public IngredientItem(int ingredientId, String ingredientName, double quantity, String unit, double caloPerGram, double fatPerGram, double proteinPerGram, double carbsPerGram) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.caloPerGram = caloPerGram;
        this.fatPerGram = fatPerGram;
        this.proteinPerGram = proteinPerGram;
        this.carbsPerGram = carbsPerGram;
    }

    // Constructor rỗng
    public IngredientItem() {
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getCaloPerGram() {
        return caloPerGram;
    }

    public void setCaloPerGram(double caloPerGram) {
        this.caloPerGram = caloPerGram;
    }

    public double getFatPerGram() {
        return fatPerGram;
    }

    public void setFatPerGram(double fatPerGram) {
        this.fatPerGram = fatPerGram;
    }

    public double getProteinPerGram() {
        return proteinPerGram;
    }

    public void setProteinPerGram(double proteinPerGram) {
        this.proteinPerGram = proteinPerGram;
    }

    public double getCarbsPerGram() {
        return carbsPerGram;
    }

    public void setCarbsPerGram(double carbsPerGram) {
        this.carbsPerGram = carbsPerGram;
    }
}