package com.model.dao;

import com.model.bean.Food;
import com.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

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
                    double caloPerGram = rs.getDouble("caloPerGram");
                    double fatPerGram = rs.getDouble("fatPerGram");
                    double proteinPerGram = rs.getDouble("proteinPerGram");
                    double carbsPerGram = rs.getDouble("carbsPerGram");

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
     public List<Food> getTopSuitableFoodsForUser(int userId, int limit) {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT f.Food_id, f.Food_name, f.description, f.image_url, f.calories, "
                + "COALESCE(fd_stats.avg_rating, 3) AS avg_rating, "
                + "COALESCE(allergy_stats.conflict_count, 0) AS conflict_count, "
                + "COALESCE(fd_stats.match_count, 0) AS match_count "
                + "FROM Food f "
                + "LEFT JOIN ( "
                + "   SELECT fd.Food_id, AVG(fd.Rating) AS avg_rating, COUNT(*) AS match_count "
                + "   FROM Food_disease fd "
                + "   JOIN User_disease ud ON ud.Disease_id = fd.Disease_id "
                + "   WHERE ud.User_id = ? "
                + "   GROUP BY fd.Food_id "
                + ") fd_stats ON f.Food_id = fd_stats.Food_id "
                + "LEFT JOIN ( "
                + "   SELECT fi.Food_id, COUNT(*) AS conflict_count "
                + "   FROM Food_Ingredient fi "
                + "   JOIN User_allergy ua ON ua.Ingredient_id = fi.Ingredient_id "
                + "   WHERE ua.User_id = ? "
                + "   GROUP BY fi.Food_id "
                + ") allergy_stats ON f.Food_id = allergy_stats.Food_id "
                + "ORDER BY conflict_count ASC, avg_rating DESC, match_count DESC, f.calories ASC "
                + "LIMIT ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapSuitabilityFood(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Food> searchSuitableFoodsForUser(int userId, String keyword, int limit) {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT f.Food_id, f.Food_name, f.description, f.image_url, f.calories, "
                + "COALESCE(fd_stats.avg_rating, 3) AS avg_rating, "
                + "COALESCE(allergy_stats.conflict_count, 0) AS conflict_count, "
                + "COALESCE(fd_stats.match_count, 0) AS match_count "
                + "FROM Food f "
                + "LEFT JOIN ( "
                + "   SELECT fd.Food_id, AVG(fd.Rating) AS avg_rating, COUNT(*) AS match_count "
                + "   FROM Food_disease fd "
                + "   JOIN User_disease ud ON ud.Disease_id = fd.Disease_id "
                + "   WHERE ud.User_id = ? "
                + "   GROUP BY fd.Food_id "
                + ") fd_stats ON f.Food_id = fd_stats.Food_id "
                + "LEFT JOIN ( "
                + "   SELECT fi.Food_id, COUNT(*) AS conflict_count "
                + "   FROM Food_Ingredient fi "
                + "   JOIN User_allergy ua ON ua.Ingredient_id = fi.Ingredient_id "
                + "   WHERE ua.User_id = ? "
                + "   GROUP BY fi.Food_id "
                + ") allergy_stats ON f.Food_id = allergy_stats.Food_id "
                + "WHERE (? IS NULL OR ? = '' OR f.Food_name LIKE ?) "
                + "ORDER BY conflict_count ASC, avg_rating DESC, match_count DESC, f.calories ASC "
                + "LIMIT ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setString(3, keyword);
            ps.setString(4, keyword);
            ps.setString(5, "%" + keyword + "%");
            ps.setInt(6, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapSuitabilityFood(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Food mapSuitabilityFood(ResultSet rs) throws Exception {
        Food food = new Food();
        food.setFood_id(rs.getInt("Food_id"));
        food.setFood_name(rs.getString("Food_name"));
        food.setDescription(rs.getString("description"));
        food.setImage_url(rs.getString("image_url"));
        food.setCalories(rs.getDouble("calories"));
        food.setAllergyConflictCount(rs.getInt("conflict_count"));
        food.setDiseaseMatchCount(rs.getInt("match_count"));
        double score = (rs.getDouble("avg_rating") * 20.0) - (food.getAllergyConflictCount() * 25.0);
        if (score < 0) {
            score = 0;
        }
        if (score > 100) {
            score = 100;
        }
        food.setSuitabilityScore(score);
        return food;
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
                f.setImage_url(rs.getString("image_url"));
                f.setCalories(rs.getDouble("calories"));
                f.setProtein(rs.getDouble("protein"));
                f.setFat(rs.getDouble("fat"));
                f.setCarbohydrate(rs.getDouble("carbohydrate"));
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
    
    public boolean insertFoodWithIngredients(Food food, String[] ingredientIds, String[] quantities, String[] units) {
        String sqlFood = "INSERT INTO food (Food_name, description, recipe, image_url, calories, protein, fat, carbohydrate, create_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE())";
        String sqlDetail = "INSERT INTO food_ingredient (Food_id, Ingredient_id, Quantity, Unit) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = new DBContext().getConnection()) {
            conn.setAutoCommit(false); // Tắt auto commit để chạy Transaction
            
            try (PreparedStatement psFood = conn.prepareStatement(sqlFood, Statement.RETURN_GENERATED_KEYS)) {
                psFood.setString(1, food.getFood_name());
                psFood.setString(2, food.getDescription());
                psFood.setString(3, food.getRecipe());
                psFood.setString(4, food.getImage_url());
                psFood.setDouble(5, food.getCalories());
                psFood.setDouble(6, food.getProtein());
                psFood.setDouble(7, food.getFat());
                psFood.setDouble(8, food.getCarbohydrate());
                psFood.executeUpdate();
                
                // Lấy ID của món ăn vừa tạo
                ResultSet rs = psFood.getGeneratedKeys();
                int newFoodId = 0;
                if (rs.next()) {
                    newFoodId = rs.getInt(1);
                }
                
                // Lưu danh sách nguyên liệu
                if (newFoodId > 0 && ingredientIds != null) {
                    try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                        for (int i = 0; i < ingredientIds.length; i++) {
                            psDetail.setInt(1, newFoodId);
                            psDetail.setInt(2, Integer.parseInt(ingredientIds[i]));
                            psDetail.setDouble(3, Double.parseDouble(quantities[i]));
                            psDetail.setString(4, units[i]);
                            psDetail.addBatch(); // Gom lệnh
                        }
                        psDetail.executeBatch(); // Chạy 1 lần nhiều lệnh
                    }
                }
                conn.commit(); // Thành công thì lưu database
                return true;
            } catch (Exception e) {
                conn.rollback(); // Lỗi thì hoàn tác toàn bộ
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    public boolean deleteFood(int foodId) {
        String[] deleteQueries = {
            "DELETE FROM adjusted_recipe WHERE Food_id = ?",
            "DELETE FROM food_disease WHERE Food_id = ?",
            "DELETE FROM food_ingredient WHERE Food_id = ?",
            "DELETE FROM menu_detail WHERE Food_id = ?",
            "DELETE FROM recommendation WHERE food_id = ?",
            "DELETE FROM user_favorite WHERE food_id = ?",
            "DELETE FROM user_history WHERE Food_id = ?",
            "DELETE FROM food WHERE Food_id = ?" 
        };

        try (Connection conn = new DBContext().getConnection()) {
            // Tắt auto-commit để chạy Transaction (Gộp nhiều lệnh thành 1 khối)
            conn.setAutoCommit(false); 
            
            try {
                for (String sql : deleteQueries) {
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setInt(1, foodId);
                        ps.executeUpdate();
                    }
                }
                
                // Nếu tất cả lệnh đều chạy thành công thì lưu vào database
                conn.commit();
                return true;
                
            } catch (Exception e) {
                conn.rollback();
                System.out.println("Lỗi khi xóa món ăn ID " + foodId + ", đã hoàn tác!");
                e.printStackTrace();
            } finally {
                // Bật lại auto-commit cho các hàm khác hoạt động bình thường
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
    // Lấy danh sách món ăn theo từ khóa tìm kiếm 
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
    
    public double getFoodGrowth() {
        double thisMonth = 0;
        double lastMonth = 0;

        String sqlThis = "SELECT COUNT(*) FROM food WHERE MONTH(create_at) = MONTH(CURRENT_DATE()) AND YEAR(create_at) = YEAR(CURRENT_DATE())";
        String sqlLast = "SELECT COUNT(*) FROM food WHERE MONTH(create_at) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) AND YEAR(create_at) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH)";

        try (Connection conn = new DBContext().getConnection()) {

            PreparedStatement ps1 = conn.prepareStatement(sqlThis);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) thisMonth = rs1.getDouble(1);

            PreparedStatement ps2 = conn.prepareStatement(sqlLast);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) lastMonth = rs2.getDouble(1);

        } catch (Exception e) { e.printStackTrace(); }

        if (lastMonth == 0) return thisMonth > 0 ? 100 : 0;
        return ((thisMonth - lastMonth) / lastMonth) * 100;
    }
}