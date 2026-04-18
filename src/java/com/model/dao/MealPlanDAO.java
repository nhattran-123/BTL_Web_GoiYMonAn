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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MealPlanDAO {

    public List<MealType> getMealTypes() {
        List<MealType> list = new ArrayList<>();
        String sql = "SELECT meal_type_id, meal_name, target_calories FROM Meal_type ORDER BY meal_type_id";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MealType m = new MealType();
                m.setMeal_type_id(rs.getInt("meal_type_id"));
                m.setMeal_name(rs.getString("meal_name"));
                m.setTarget_calories(rs.getDouble("target_calories"));
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
            section.put("mealTypeId", type.getMeal_type_id());
            section.put("mealName", type.getMeal_name());
            section.put("targetCalories", type.getTarget_calories());
            section.put("usedCalories", 0.0);
            section.put("foods", new ArrayList<Map<String, Object>>());
            sectionsByType.put(type.getMeal_type_id(), section);
        }

        String sql = "SELECT md.Detail_id, md.Meal_type_id, f.Food_id, f.Food_name, f.image_url, f.calories "
                + "FROM Daily_Menu dm "
                + "JOIN Menu_Detail md ON dm.menu_id = md.Menu_id "
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
                    food.put("imageUrl", rs.getString("image_url"));
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

    public List<Map<String, Object>> getWeekSlider(int userId, LocalDate centerDate) {
        LocalDate start = centerDate.minusDays(3);
        LocalDate end = centerDate.plusDays(3);

        Set<LocalDate> hasMealDates = new HashSet<>();
        String sql = "SELECT DISTINCT Menu_date FROM Daily_Menu WHERE User_id = ? AND Menu_date BETWEEN ? AND ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(start));
            ps.setDate(3, Date.valueOf(end));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date d = rs.getDate("Menu_date");
                    if (d != null) {
                        hasMealDates.add(d.toLocalDate());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = start.plusDays(i);
            Map<String, Object> day = new HashMap<>();
            day.put("date", date.toString());
            day.put("dow", getDowLabel(date.getDayOfWeek().getValue()));
            day.put("day", date.getDayOfMonth());
            day.put("hasMeals", hasMealDates.contains(date));
            day.put("selected", date.equals(centerDate));
            days.add(day);
        }
        return days;
    }

    private String getDowLabel(int dow) {
        switch (dow) {
            case 1: return "T2";
            case 2: return "T3";
            case 3: return "T4";
            case 4: return "T5";
            case 5: return "T6";
            case 6: return "T7";
            default: return "CN";
        }
    }

    public double getTotalCalories(int userId, LocalDate date) {
        String sql = "SELECT COALESCE(SUM(f.calories),0) AS totalCalories "
                + "FROM Daily_Menu dm "
                + "JOIN Menu_Detail md ON dm.menu_id = md.Menu_id "
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
        String sql = "SELECT menu_id FROM Daily_Menu WHERE User_id = ? AND Menu_date = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("menu_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createMenu(int userId, LocalDate date) {
        String sql = "INSERT INTO Daily_Menu(User_id, plan_id, Menu_date, total_calories, Status) VALUES (?, NULL, ?, 0, 'Pending')";
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
        List<Integer> ids = new ArrayList<>();
        ids.add(foodId);
        addMealFoods(userId, date, mealTypeId, ids);
    }

    public void addMealFoods(int userId, LocalDate date, int mealTypeId, List<Integer> foodIds) {
        if (foodIds == null || foodIds.isEmpty()) {
            return;
        }

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
            for (Integer foodId : foodIds) {
                if (foodId != null && foodId > 0) {
                    ps.setInt(1, menuId);
                    ps.setInt(2, foodId);
                    ps.setInt(3, mealTypeId);
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateMenuCalories(menuId);
    }
    public void updateMealFoods(int userId, LocalDate date, int mealTypeId, List<Integer> foodIds) {
        Integer menuId = getMenuIdByDate(userId, date);
        if (menuId == null) {
            menuId = createMenu(userId, date);
        }
        if (menuId == null || menuId <= 0) {
            return;
        }

        String sqlDelete = "DELETE FROM Menu_Detail WHERE Menu_id = ? AND Meal_type_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
            ps.setInt(1, menuId);
            ps.setInt(2, mealTypeId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (foodIds != null && !foodIds.isEmpty()) {
            String sqlInsert = "INSERT INTO Menu_Detail(Menu_id, Food_id, Meal_type_id) VALUES (?, ?, ?)";
            try (Connection conn = new DBContext().getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                for (Integer foodId : foodIds) {
                    if (foodId != null && foodId > 0) {
                        ps.setInt(1, menuId);
                        ps.setInt(2, foodId);
                        ps.setInt(3, mealTypeId);
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    public boolean canEditMealDetail(int userId, int detailId) {
        String sql = "SELECT dm.Menu_date "
                + "FROM Menu_Detail md "
                + "JOIN Daily_Menu dm ON md.Menu_id = dm.menu_id "
                + "WHERE md.Detail_id = ? AND dm.User_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detailId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date menuDate = rs.getDate("Menu_date");
                    return menuDate != null && !menuDate.toLocalDate().isBefore(LocalDate.now());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void archivePastMealsToHistory(int userId) {
        String sql = "INSERT INTO User_History (user_id, food_id, eaten_at) "
                + "SELECT dm.User_id, md.Food_id, dm.Menu_date "
                + "FROM Daily_Menu dm "
                + "JOIN Menu_Detail md ON dm.menu_id = md.Menu_id "
                + "WHERE dm.User_id = ? AND dm.Menu_date < CURRENT_DATE "
                + "AND NOT EXISTS ( "
                + "    SELECT 1 FROM User_History uh "
                + "    WHERE uh.user_id = dm.User_id "
                + "      AND uh.food_id = md.Food_id "
                + "      AND DATE(uh.eaten_at) = dm.Menu_date"
                + ")";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMenuCalories(int menuId) {
        String sql = "UPDATE Daily_Menu dm "
                + "SET dm.total_calories = ("
                + "   SELECT COALESCE(SUM(f.calories),0) "
                + "   FROM Menu_Detail md "
                + "   JOIN Food f ON md.Food_id = f.Food_id "
                + "   WHERE md.Menu_id = dm.menu_id"
                + ") "
                + "WHERE dm.menu_id = ?";

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
        String sql = "SELECT Food_id, Food_name, calories, image_url FROM Food ORDER BY Food_name";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Food f = new Food();
                f.setFood_id(rs.getInt("Food_id"));
                f.setFood_name(rs.getString("Food_name"));
                f.setCalories(rs.getDouble("calories"));
                f.setImage_url(rs.getString("image_url"));
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
