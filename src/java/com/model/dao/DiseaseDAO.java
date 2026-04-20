package com.model.dao;

import com.model.bean.DiseaseDetail;
import com.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiseaseDAO {

    // [GIỮ NGUYÊN] Hàm Thêm mới
    public boolean insertDisease(DiseaseDetail disease) {
        String sql = "INSERT INTO Disease (Disease_name, disease_description) VALUES (?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, disease.getName());
            ps.setString(2, disease.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // [GIỮ NGUYÊN] Hàm Lấy danh sách
    public List<DiseaseDetail> getAllDiseases() {
        List<DiseaseDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM Disease ORDER BY Disease_id DESC";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DiseaseDetail d = new DiseaseDetail();
                d.setId(rs.getInt("Disease_id"));
                d.setName(rs.getString("Disease_name"));
                d.setDescription(rs.getString("disease_description"));
                d.setFoodCount(0); 
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // [MỚI THÊM] Hàm Cập nhật (Sửa) Bệnh lý
    public boolean updateDisease(DiseaseDetail disease) {
        String sql = "UPDATE Disease SET Disease_name = ?, disease_description = ? WHERE Disease_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, disease.getName());
            ps.setString(2, disease.getDescription());
            ps.setInt(3, disease.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi updateDisease: " + e.getMessage());
        }
        return false;
    }

    // [CẬP NHẬT CUỐI CÙNG] Hàm xóa bất tử, chống mọi lỗi thiếu bảng
    public String deleteDisease(int diseaseId) {
        try (Connection conn = new DBContext().getConnection()) {
            
            // 1. Dọn dẹp bảng Food_disease (Dùng try-catch ẩn để nếu không có bảng này thì tự động lờ đi)
            try {
                conn.prepareStatement("DELETE FROM Food_disease WHERE Disease_id = " + diseaseId).executeUpdate();
            } catch (Exception e) {
                System.out.println("Bỏ qua lỗi Food_disease: " + e.getMessage());
            }
            
            // 2. Dọn dẹp bảng User_disease (Tương tự, không có bảng thì bỏ qua)
            try {
                conn.prepareStatement("DELETE FROM User_disease WHERE Disease_id = " + diseaseId).executeUpdate();
            } catch (Exception e) {
                System.out.println("Bỏ qua lỗi User_disease: " + e.getMessage());
            }
            
            // 3. XÓA BỆNH LÝ CHÍNH (Mục tiêu quan trọng nhất)
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Disease WHERE Disease_id = ?");
            ps.setInt(1, diseaseId);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                return "OK"; // Trả về chữ OK để Servlet tự động quay lại trang danh sách
            } else {
                return "LỖI: Không tìm thấy ID " + diseaseId;
            }
            
        } catch (Exception e) {
            return "LỖI SQL: " + e.getMessage(); 
        }
    }
    // 1. Hàm kiểm tra xem món ăn và bệnh lý đã được đánh giá chưa
    public boolean checkRatingExist(int foodId, int diseaseId) {
        String sql = "SELECT 1 FROM Food_disease WHERE Food_id = ? AND Disease_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, foodId);
            ps.setInt(2, diseaseId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Trả về true nếu đã tồn tại
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Hàm thêm mới đánh giá (Chỉ chạy khi hàm trên trả về false)
    public boolean insertRating(int foodId, int diseaseId, int rating) {
        String sql = "INSERT INTO Food_disease (Food_id, Disease_id, Rating) VALUES (?, ?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, foodId);
            ps.setInt(2, diseaseId);
            ps.setInt(3, rating);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // 1. Lấy danh sách TẤT CẢ món ăn để nạp vào thanh Dropdown
    public List<Map<String, Object>> getAllFoodsForDropdown() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT Food_id, Food_name FROM Food";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt("Food_id"));
                map.put("name", rs.getString("Food_name"));
                list.add(map);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Lấy danh sách Đánh giá tương thích để in ra cái Bảng
    public List<Map<String, Object>> getAllCompatibilities() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT fd.Id, f.Food_name, d.Disease_name, fd.Rating " +
                     "FROM Food_disease fd " +
                     "JOIN Food f ON fd.Food_id = f.Food_id " +
                     "JOIN Disease d ON fd.Disease_id = d.Disease_id";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt("Id"));
                map.put("foodName", rs.getString("Food_name"));
                map.put("diseaseName", rs.getString("Disease_name"));
                map.put("rating", rs.getInt("Rating"));
                list.add(map);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    // Hàm xóa một bản ghi tương thích món ăn - bệnh lý
    public boolean deleteRating(int id) {
        String sql = "DELETE FROM Food_disease WHERE Id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}