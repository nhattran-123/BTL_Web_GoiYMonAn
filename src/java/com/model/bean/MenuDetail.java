package com.model.bean;

public class MenuDetail {
    private int detailId;
    private int menuId;
    private int foodId;
    private int mealTypeId;

    public int getDetailId() {
        return detailId;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getMealTypeId() {
        return mealTypeId;
    }

    // Getter Setter

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setMealTypeId(int mealTypeId) {
        this.mealTypeId = mealTypeId;
    }
}