/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.dao;

/**
 *
 * @author dkhai
 */
import com.model.bean.Ingredient;
import com.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    
    // Đếm tổng số lượng nguyên liệu trong CSDL (Không có từ khóa)
    public int getTotalIngredient() {
        String sql = "SELECT COUNT(*) FROM Ingredient";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // [MỚI] Đếm tổng số lượng nguyên liệu CÓ LỌC theo từ khóa (Dùng cho phân trang tìm kiếm)
    public int getTotalIngredientByKeyword(String keyword) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Ingredient WHERE Ingredient_name LIKE ? OR category LIKE ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Lấy danh sách nguyên liệu có Phân Trang (Không có từ khóa)
    public List<Ingredient> getIngredientsByPage(int offset, int limit) {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT Ingredient_id, Ingredient_name, category, calories, Protein, fat, carbohydrate FROM Ingredient LIMIT ? OFFSET ?";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ingredient i = new Ingredient();
                    i.setId(rs.getInt("Ingredient_id"));
                    i.setName(rs.getString("Ingredient_name"));
                    i.setCategory(rs.getString("category"));
                    i.setCalories(rs.getDouble("calories"));
                    i.setProtein(rs.getDouble("protein"));
                    i.setFat(rs.getDouble("fat"));
                    i.setCarbohydrate(rs.getDouble("carbohydrate"));
                    list.add(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // [MỚI] Lấy danh sách nguyên liệu CÓ TÌM KIẾM và CÓ PHÂN TRANG
    public List<Ingredient> searchIngredientsByPage(String keyword, int offset, int limit) {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT * FROM Ingredient WHERE Ingredient_name LIKE ? OR category LIKE ? LIMIT ? OFFSET ?";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ingredient i = new Ingredient();
                    i.setId(rs.getInt("Ingredient_id"));
                    i.setName(rs.getString("Ingredient_name"));
                    i.setCalories(rs.getDouble("calories"));
                    i.setCategory(rs.getString("category"));
                    i.setProtein(rs.getDouble("Protein"));
                    i.setFat(rs.getDouble("fat"));
                    i.setCarbohydrate(rs.getDouble("carbohydrate"));
                    list.add(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
   
    // Xóa nguyên liệu
    public boolean deleteIngredient(int id) {
        String sqlDeleteFoodIngredient = "delete from food_ingredient where Ingredient_id= ?";
        String sqlDeleteAllergy = "delete from user_allergy where Ingredient_id=?";
        String sqlDeleteIngredient = "delete from Ingredient where Ingredient_id = ?";
        try(Connection conn = new DBContext().getConnection()){
            // tắt auto-commit để gộp 3 lệnh xóa thành 1 giao dịch (Transaction)
            conn.setAutoCommit(false); 
            try(PreparedStatement ps1 = conn.prepareStatement(sqlDeleteFoodIngredient);
                PreparedStatement ps2= conn.prepareStatement(sqlDeleteAllergy);
                PreparedStatement ps3 = conn.prepareStatement(sqlDeleteIngredient)){
                
                ps1.setInt(1, id);
                ps1.executeUpdate();
                
                ps2.setInt(1,id);
                ps2.executeUpdate();
                
                ps3.setInt(1, id);
                int result = ps3.executeUpdate();
                
                // Luu thay doi vao database
                conn.commit();
                return result > 0;
            } catch (Exception e){
                // neu co' loi o 3 buoc tren, hoan` tac'
                conn.rollback();
                e.printStackTrace();
            } finally {
                // Bat auto-commit de ko anh huong ham` khac'
                conn.setAutoCommit(true);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
   // Thêm nguyên liệu mới vào Database
    public boolean insertIngredient(Ingredient ing) {
        String sql = "INSERT INTO Ingredient (Ingredient_name, calories, category, Protein, fat, carbohydrate) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, ing.getName());
            ps.setDouble(2, ing.getCalories());
            ps.setString(3, ing.getCategory());
            ps.setDouble(4, ing.getProtein());
            ps.setDouble(5, ing.getFat());
            ps.setDouble(6, ing.getCarbohydrate());
            
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy thông tin 1 nguyên liệu cụ thể để sửa
    public Ingredient getIngredientById(int id) {
        String sql = "SELECT * FROM Ingredient WHERE Ingredient_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ingredient i = new Ingredient();
                    i.setId(rs.getInt("Ingredient_id"));
                    i.setName(rs.getString("Ingredient_name"));
                    i.setCalories(rs.getDouble("calories"));
                    i.setCategory(rs.getString("category"));
                    i.setProtein(rs.getDouble("Protein"));
                    i.setFat(rs.getDouble("fat"));
                    i.setCarbohydrate(rs.getDouble("carbohydrate"));
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin nguyên liệu
    public boolean updateIngredient(Ingredient ing) {
        String sql = "UPDATE Ingredient SET Ingredient_name = ?, calories = ?, category = ?, Protein = ?, fat = ?, carbohydrate = ? WHERE Ingredient_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ing.getName());
            ps.setDouble(2, ing.getCalories());
            ps.setString(3, ing.getCategory());
            ps.setDouble(4, ing.getProtein());
            ps.setDouble(5, ing.getFat());
            ps.setDouble(6, ing.getCarbohydrate());
            ps.setInt(7, ing.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Tìm kiếm nguyên liệu (Hàm cũ không có phân trang)
    public List<Ingredient> searchIngredients(String keyword) {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT * FROM ingredient WHERE Ingredient_name LIKE ? OR category LIKE ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ingredient i = new Ingredient(
                    rs.getInt("Ingredient_id"),
                    rs.getString("Ingredient_name"),
                    rs.getFloat("calories"),
                    rs.getString("category"),
                    rs.getFloat("Protein"),
                    rs.getFloat("fat"),
                    rs.getFloat("carbohydrate")
                );
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}