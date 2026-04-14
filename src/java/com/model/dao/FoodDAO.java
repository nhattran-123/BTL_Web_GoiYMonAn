package com.model.dao;

import com.model.bean.Food;
import com.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    public List<com.model.bean.IngredientItem> getIngredientsByFoodId(int foodId) {
        List<com.model.bean.IngredientItem> list = new java.util.ArrayList<>();
        
        String sql = "SELECT i.Ingredient_id, i.Ingredient_name, fi.Quantity, fi.Unit, " +
                     "(i.calories / 100.0) AS caloPerGram, " +
                     "(i.fat / 100.0) AS fatPerGram, " +
                     "(i.Protein / 100.0) AS proteinPerGram, " +
                     "(i.carbohydrate / 100.0) AS carbsPerGram " +
                     "FROM Food_Ingredient fi " +
                     "JOIN Ingredient i ON fi.Ingredient_id = i.Ingredient_id " +
                     "WHERE fi.Food_id = ?";
                     
        try (java.sql.Connection conn = new com.util.DBContext().getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, foodId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // 2. ĐÃ SỬA: Lấy id từ ResultSet
                    int id = rs.getInt("Ingredient_id");
                    String name = rs.getString("Ingredient_name");
                    double qty = rs.getDouble("Quantity");
                    String unit = rs.getString("Unit");
                    
                    // 3. ĐÃ SỬA: Lấy chuẩn xác theo tên AS trong câu SQL
                    double caloPerGram = rs.getDouble("caloPerGram");
                    double fatPerGram = rs.getDouble("fatPerGram");
                    double proteinPerGram = rs.getDouble("proteinPerGram");
                    double carbsPerGram = rs.getDouble("carbsPerGram");
                    
                    // 4. ĐÃ SỬA: Truyền biến 'id' vào vị trí đầu tiên của Constructor
                    list.add(new com.model.bean.IngredientItem(id, name, qty, unit, caloPerGram, fatPerGram, proteinPerGram, carbsPerGram));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy danh sách tất cả món ăn
    public List<Food> getAllFoods() {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM food";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                Food f = new Food();
                f.setFood_id(rs.getInt("Food_id"));
                f.setFood_name(rs.getString("Food_name"));
                f.setDescription(rs.getString("description"));
                f.setImage_url(rs.getString("image_url"));
                f.setCalories(rs.getDouble("calories"));
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy chi tiết 1 món ăn theo ID
    public Food getFoodById(int id) {
        String sql = "SELECT * FROM food WHERE Food_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Food f = new Food();
                    f.setFood_id(rs.getInt("Food_id"));
                    f.setFood_name(rs.getString("Food_name"));
                    f.setDescription(rs.getString("description"));
                    f.setRecipe(rs.getString("recipe"));
                    f.setImage_url(rs.getString("image_url"));
                    f.setCalories(rs.getDouble("calories"));
                    f.setProtein(rs.getDouble("protein"));
                    f.setFat(rs.getDouble("fat"));
                    f.setCarbohydrate(rs.getDouble("carbohydrate"));
                    return f;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public java.util.List<com.model.bean.IngredientItem> getAllIngredientsFromDB() {
        java.util.List<com.model.bean.IngredientItem> list = new java.util.ArrayList<>();
        
        // Truy vấn bảng Ingredient gốc 
        String sql = "SELECT Ingredient_id, Ingredient_name, " +
                     "(calories / 100.0) AS caloPerGram, " +
                     "(fat / 100.0) AS fatPerGram, " +
                     "(Protein / 100.0) AS proteinPerGram, " +
                     "(carbohydrate / 100.0) AS carbsPerGram " +
                     "FROM Ingredient";

        try (java.sql.Connection conn = new com.util.DBContext().getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("Ingredient_id");
                String name = rs.getString("Ingredient_name");
                // Vì lấy từ kho gốc (chưa nằm trong món ăn nào) nên số lượng mặc định là 0
                double qty = 0; 
                String unit = "gram";
                
                double caloPerGram = rs.getDouble("caloPerGram");
                double fatPerGram = rs.getDouble("fatPerGram");
                double proteinPerGram = rs.getDouble("proteinPerGram");
                double carbsPerGram = rs.getDouble("carbsPerGram");

                // Dùng Constructor 1 dòng giống hệt hàm getIngredientsByFoodId
                list.add(new com.model.bean.IngredientItem(id, name, qty, unit, caloPerGram, fatPerGram, proteinPerGram, carbsPerGram));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}