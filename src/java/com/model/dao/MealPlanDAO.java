package com.model.dao;

import com.model.bean.Food;
import com.model.bean.MealType;
import com.util.DBContext;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MealPlanDAO {

    public List<MealType> getMealTypes() {
        List<MealType> list = new ArrayList<>();
        String sql = "SELECT Meal_type_id, Meal_name, Target_calories FROM Meal_Type ORDER BY Meal_type_id";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MealType m = new MealType();
                m.setMealTypeId(rs.getInt("Meal_type_id"));
                m.setMealName(rs.getString("Meal_name"));
                m.setTargetCalories(rs.getDouble("Target_calories"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Map<String, Object>> getMealSections(int userId, LocalDate date) {
        List<MealType> mealTypes = getMealTypes();
        Map<Integer, Map<String, Object>> sectionsByType = new LinkedHashMap<>();

        for (MealType type : mealTypes) {
            Map<String, Object> section = new HashMap<>();
            section.put("mealTypeId", type.getMealTypeId());
            section.put("mealName", type.getMealName());
            section.put("targetCalories", type.getTargetCalories());
            section.put("usedCalories", 0.0);
            section.put("foods", new ArrayList<Map<String, Object>>());
            sectionsByType.put(type.getMealTypeId(), section);
        }

        String sql = "SELECT md.Detail_id, md.Meal_type_id, f.Food_id, f.Food_name, f.Image_url, f.calories "
                + "FROM Daily_Menu dm "
                + "JOIN Menu_Detail md ON dm.Menu_id = md.Menu_id "
                + "JOIN Food f ON md.Food_id = f.Food_id "
                + "WHERE dm.User_id = ? AND dm.Menu_date = ? "
                + "ORDER BY md.Meal_type_id, md.Detail_id";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int mealTypeId = rs.getInt("Meal_type_id");
                    Map<String, Object> section = sectionsByType.get(mealTypeId);
                    if (section == null) {
                        section = new HashMap<>();
                        section.put("mealTypeId", mealTypeId);
                        section.put("mealName", "Bữa ăn");
                        section.put("targetCalories", 0.0);
                        section.put("usedCalories", 0.0);
                        section.put("foods", new ArrayList<Map<String, Object>>());
                        sectionsByType.put(mealTypeId, section);
                    }

                    double calories = rs.getDouble("calories");
                    double used = ((Number) section.get("usedCalories")).doubleValue();
                    section.put("usedCalories", used + calories);

                    Map<String, Object> food = new HashMap<>();
                    food.put("detailId", rs.getInt("Detail_id"));
                    food.put("foodId", rs.getInt("Food_id"));
                    food.put("foodName", rs.getString("Food_name"));
                    food.put("imageUrl", rs.getString("Image_url"));
                    food.put("calories", calories);
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> foods = (List<Map<String, Object>>) section.get("foods");
                    foods.add(food);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(sectionsByType.values());
    }

    public double getTotalCalories(int userId, LocalDate date) {
        String sql = "SELECT COALESCE(SUM(f.calories),0) AS totalCalories "
                + "FROM Daily_Menu dm "
                + "JOIN Menu_Detail md ON dm.Menu_id = md.Menu_id "
                + "JOIN Food f ON md.Food_id = f.Food_id "
                + "WHERE dm.User_id = ? AND dm.Menu_date = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("totalCalories");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Integer getMenuIdByDate(int userId, LocalDate date) {
        String sql = "SELECT Menu_id FROM Daily_Menu WHERE User_id = ? AND Menu_date = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Menu_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createMenu(int userId, LocalDate date) {
        String sql = "INSERT INTO Daily_Menu(User_id, Plan_id, Menu_date, Total_calories, Status) VALUES (?, NULL, ?, 0, 'ACTIVE')";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(date));
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addMealFood(int userId, LocalDate date, int mealTypeId, int foodId) {
        Integer menuId = getMenuIdByDate(userId, date);
        if (menuId == null) {
            menuId = createMenu(userId, date);
        }
        if (menuId == null || menuId <= 0) {
            return;
        }

        String sql = "INSERT INTO Menu_Detail(Menu_id, Food_id, Meal_type_id) VALUES (?, ?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            ps.setInt(2, foodId);
            ps.setInt(3, mealTypeId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateMenuCalories(menuId);
    }

    public void deleteMealDetail(int detailId) {
        String findSql = "SELECT Menu_id FROM Menu_Detail WHERE Detail_id = ?";
        Integer menuId = null;

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(findSql)) {
            ps.setInt(1, detailId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    menuId = rs.getInt("Menu_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String deleteSql = "DELETE FROM Menu_Detail WHERE Detail_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(deleteSql)) {
            ps.setInt(1, detailId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (menuId != null) {
            updateMenuCalories(menuId);
        }
    }

    private void updateMenuCalories(int menuId) {
        String sql = "UPDATE Daily_Menu dm "
                + "SET dm.Total_calories = ("
                + "  SELECT COALESCE(SUM(f.calories),0)"
                + "  FROM Menu_Detail md JOIN Food f ON md.Food_id = f.Food_id"
                + "  WHERE md.Menu_id = dm.Menu_id"
                + ") WHERE dm.Menu_id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Food> getAllFoods() {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT Food_id, Food_name, calories, Image_url FROM Food ORDER BY Food_name";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Food f = new Food();
                f.setFood_id(rs.getInt("Food_id"));
                f.setFood_name(rs.getString("Food_name"));
                f.setCalories(rs.getDouble("calories"));
                f.setImage_url(rs.getString("Image_url"));
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
