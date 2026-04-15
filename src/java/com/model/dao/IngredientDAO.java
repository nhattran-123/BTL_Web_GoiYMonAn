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
    // 1. Đếm tổng số lượng nguyên liệu trong CSDL
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

    // 2. Lấy danh sách nguyên liệu có Phân Trang
    public List<Ingredient> getIngredientsByPage(int offset, int limit) {
        List<Ingredient> list = new ArrayList<>();
        // MySQL sử dụng LIMIT [số lượng] OFFSET [vị trí bắt đầu]
        String sql = "SELECT Ingredient_id, Ingredient_name, category, calories FROM Ingredient LIMIT ? OFFSET ?";
        
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
                    list.add(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // Lấy toàn bộ nguyên liệu
    public List<Ingredient> getAllIngredient(){
        List<Ingredient> list = new ArrayList<>();
        String sql = "Select Ingredient_id, Ingredient_name, calories, category from Ingredient";
        
        try(Connection conn= new DBContext().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                    Ingredient i = new Ingredient();
                    i.setId(rs.getInt("Ingredient_id"));
                    i.setName(rs.getString("Ingredient_name"));
                    i.setCalories(rs.getDouble("calories"));
                    i.setCategory(rs.getString("category"));
                    
                    list.add(i);
                }
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return list;
    }

    // Xoa' nguyen lieu
    public boolean deleteIngredient(int id) {
        String sqlDeleteFoodIngredient = "delete from food_ingredient where Ingredient_id= ?";
        String sqlDeleteAllergy = "delete from user_allergy where Ingredient_id=?";
        String sqlDeleteIngredient = "delete from Ingredient where Ingredient_id = ?";
        try(Connection conn = new DBContext().getConnection()){
         //  tắt auto-commit để gộp 3 lệnh xóa thành 1 giao dịch (Transaction)
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
    
}
