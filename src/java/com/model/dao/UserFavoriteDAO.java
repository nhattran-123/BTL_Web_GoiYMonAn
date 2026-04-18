/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.dao;

/**
 *
 * @author Nhat0
 */
import com.model.bean.Food;
import com.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserFavoriteDAO {

    public boolean isFavorite(int userId, int foodId) {
        String sql = "SELECT 1 FROM User_Favorite WHERE User_id = ? AND Food_id = ? LIMIT 1";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, foodId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addFavorite(int userId, int foodId) {
        if (isFavorite(userId, foodId)) {
            return false;
        }

        String sql = "INSERT INTO User_Favorite(User_id, Food_id) VALUES (?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, foodId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Food> getFavoritesByUser(int userId) {
        List<Food> list = new ArrayList<>();

        String sql = "SELECT f.* FROM User_Favorite uf "
                + "JOIN Food f ON f.Food_id = uf.Food_id "
                + "WHERE uf.User_id = ? "
                + "ORDER BY uf.id DESC";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Food f = new Food();
                    f.setFood_id(rs.getInt("Food_id"));
                    f.setFood_name(rs.getString("Food_name"));
                    f.setDescription(rs.getString("description"));
                    f.setImage_url(rs.getString("image_url"));
                    f.setCalories(rs.getDouble("calories"));
                    list.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

