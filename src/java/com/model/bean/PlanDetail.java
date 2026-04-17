package com.model.bean;

/**
 * Backward-compatible alias model used by older DAO code paths.
 * Some environments still reference PlanDetail while newer code uses MealPlanDetail.
 */
public class PlanDetail {
    private int layoutId;
    private int settingId;
    private int mealTypeId;
    private double portionSize;
    private int cookLevel;
    private boolean needCooking;
    private int displayOrder;

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getSettingId() {
        return settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public int getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(int mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public double getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(double portionSize) {
        this.portionSize = portionSize;
    }

    public int getCookLevel() {
        return cookLevel;
    }

    public void setCookLevel(int cookLevel) {
        this.cookLevel = cookLevel;
    }

    public boolean isNeedCooking() {
        return needCooking;
    }

    public void setNeedCooking(boolean needCooking) {
        this.needCooking = needCooking;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
