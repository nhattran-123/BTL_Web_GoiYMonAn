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
                    //  Lấy id từ ResultSet
                    int id = rs.getInt("Ingredient_id");
                    String name = rs.getString("Ingredient_name");
                    double qty = rs.getDouble("Quantity");
                    String unit = rs.getString("Unit");
                    
                    //Lấy chuẩn xác theo tên AS trong câu SQL
                    double caloPerGram = rs.getDouble("caloPerGram");
                    double fatPerGram = rs.getDouble("fatPerGram");
                    double proteinPerGram = rs.getDouble("proteinPerGram");
                    double carbsPerGram = rs.getDouble("carbsPerGram");
                    
                    //  Truyền biến 'id' vào vị trí đầu tiên của Constructor
                    list.add(new com.model.bean.IngredientItem(id, name, qty, unit, caloPerGram, fatPerGram, proteinPerGram, carbsPerGram));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // Lấy danh sách món ăn an toàn (không chứa nguyên liệu dị ứng)
    public List<Food> getSafeFoodsForUser(int userId) {
        List<Food> safeFoodList = new ArrayList<>();
        
        String query = "SELECT f.* FROM Food f " +
                       "WHERE f.Food_id NOT IN (" +
                       "    SELECT fi.Food_id " +
                       "    FROM Food_Ingredient fi " +
                       "    INNER JOIN User_Allergy ua ON fi.Ingredient_id = ua.Ingredient_id " +
                       "    WHERE ua.User_id = ?" +
                       ")";
                       
        try (Connection conn =new  DBContext().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Food food = new Food();
                    food.setFood_id(rs.getInt("Food_id"));
                    food.setFood_name(rs.getString("Food_name"));
                    food.setDescription(rs.getString("description"));
                    food.setImage_url(rs.getString("image_url"));
                    food.setCalories(rs.getDouble("calories"));
                    safeFoodList.add(food);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return safeFoodList;
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
    // Lấy danh sách món ăn theo từ khóa tìm kiếm (Use Case của tôi)
    public List<Food> searchFoodByName(String keyword) {
        List<Food> list = new ArrayList<>();
        // Lưu ý: Tùy database của nhóm mà 'Food' có phân biệt hoa thường hay không.
        // Bạn cùng nhóm dùng 'food' ở getAllFoods và 'Food' ở getSafeFoods.
        String sql = "SELECT * FROM Food WHERE Food_name LIKE ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Food f = new Food();
                    f.setFood_id(rs.getInt("Food_id"));
                    f.setFood_name(rs.getString("Food_name"));
                    f.setDescription(rs.getString("description"));
                    f.setImage_url(rs.getString("image_url"));
                    f.setCalories(rs.getDouble("calories"));
                    
                    // Nếu Model Food của bạn có các trường này thì set, không thì có thể bỏ qua
                    // vì danh sách tìm kiếm thường chỉ cần Tên, Ảnh, và Calo
                    // f.setProtein(rs.getDouble("protein"));
                    // f.setFat(rs.getDouble("fat"));
                    // f.setCarbohydrate(rs.getDouble("carbohydrate"));

                    list.add(f);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi tại searchFoodByName: " + e.getMessage());
        }
        return list;
    }
    // Lay tonh so mon an
    public int getTotalFoods() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM Food";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }
}