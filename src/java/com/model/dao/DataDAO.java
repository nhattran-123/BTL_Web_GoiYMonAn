package com.model.dao;

import com.model.bean.Disease;
import com.model.bean.Ingredient;
import com.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {
    
    // Lấy 20 Bệnh lý
    public List<Disease> getAllDiseases() {
        List<Disease> list = new ArrayList<>();
        String query = "SELECT Disease_id, Disease_name FROM Disease";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Disease(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Lấy 69 Nguyên liệu dị ứng
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        String query = "SELECT Ingredient_id, Ingredient_name FROM Ingredient";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Ingredient(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
   

    // lưu lịch sử đăng nhập 
    public void saveLoginRecord(int userId) {
        String query = "INSERT INTO user_login (id, login_date) VALUES (?, CURDATE())";
        try (Connection conn =new  DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // đếm số người dùng đăng nhập 
    public int getUserActivities() {
        int count = 0;
        String query = "SELECT COUNT(DISTINCT id) FROM user_login WHERE DATE(login_date) = CURDATE()";
        try (Connection conn =  new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}