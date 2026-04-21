package com.model.dao;

import com.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RecipeDAO {
    public List<com.model.bean.IngredientItem> getIngredientsByFoodId(int foodId) {
    List<com.model.bean.IngredientItem> list = new java.util.ArrayList<>();
    // Join 2 bảng để lấy Tên, Số lượng, Đơn vị và Calo
    String sql = "SELECT i.Ingredient_id, i.Ingredient_name, fi.Quantity, fi.Unit, i.calories " +
                 "FROM food_ingredient fi " +
                 "JOIN ingredient i ON fi.Ingredient_id = i.Ingredient_id " +
                 "WHERE fi.Food_id = ?";
    try (java.sql.Connection conn = new com.util.DBContext().getConnection();
         java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, foodId);
        try (java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("Ingredient_id");
                String name = rs.getString("Ingredient_name");
                double qty = rs.getDouble("Quantity");
                String unit = rs.getString("Unit");
                // Trong DB dinh dưỡng, 'calories' thường tính trên 100 gram.
                // Do đó calo/1 gram = calories / 100
                double caloPerGram = rs.getDouble("calories") / 100.0;
                double fatPerGram = rs.getDouble("fat");
                double proteinPerGram = rs.getDouble("protein");
                double carbsPerGram = rs.getDouble("carbs");
                list.add(new com.model.bean.IngredientItem(id, name, qty, unit, caloPerGram, fatPerGram, proteinPerGram, carbsPerGram));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    
    public boolean insertAdjustedRecipe(int foodId, int userId, String recipe, double calories, double fat, double protein, double carbs) {
        boolean isSuccess = false;
        
        // Câu lệnh SQL thêm dữ liệu vào bảng adjusted_recipe
        String sql = "INSERT INTO adjusted_recipe (Food_id, user_id, recipe, calories, fat, Protein, carbohydrate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            // Gọi đến file DBContext của bạn để lấy connection
            conn = new DBContext().getConnection(); 
            
            if (conn != null) {
                ps = conn.prepareStatement(sql);
                
                // Truyền các tham số vào câu lệnh SQL
                ps.setInt(1, foodId);
                ps.setInt(2, userId);
                ps.setString(3, recipe);
                ps.setDouble(4, calories);
                ps.setDouble(5, fat);
                ps.setDouble(6, protein);
                ps.setDouble(7, carbs);
                // Thực thi câu lệnh
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    isSuccess = true; // Nếu có dòng được thêm vào thành công
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng các kết nối (rất quan trọng để không bị quá tải Database)
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    public com.model.bean.AdjustedRecipe getAdjustedRecipeByFoodAndUser(int foodId, int userId) {
        String sql = "SELECT * FROM adjusted_recipe WHERE Food_id = ? AND user_id = ? LIMIT 1";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, foodId);
            ps.setInt(2, userId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    com.model.bean.AdjustedRecipe adjusted = new com.model.bean.AdjustedRecipe();
                    try {
                        adjusted.setId(rs.getInt("id"));
                    } catch (Exception ignore) {
                        // một số CSDL có thể đặt tên khóa chính khác, không bắt buộc dùng ở luồng hiển thị
                    }
                    adjusted.setFoodId(rs.getInt("Food_id"));
                    adjusted.setUserId(rs.getInt("user_id"));
                    adjusted.setRecipe(rs.getString("recipe"));
                    adjusted.setCalories(rs.getDouble("calories"));
                    adjusted.setFat(rs.getDouble("fat"));
                    adjusted.setProtein(rs.getDouble("Protein"));
                    adjusted.setCarbohydrate(rs.getDouble("carbohydrate"));
                    return adjusted;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}