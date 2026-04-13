package com.model.dao;

import com.model.bean.User;
import com.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 1. Kiểm tra Email tồn tại
    public boolean checkEmailExist(String email) {
        String query = "SELECT 1 FROM Users WHERE email = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return true;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 2. Đăng ký User mới
    public int registerUserReturnId(User u) {
        String query = "INSERT INTO Users (name, email, password, gender, age, weight, height, desired_weight, desired_height, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'USER')";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setBoolean(4, "Nam".equals(u.getGender())); 
            ps.setInt(5, u.getAge());
            ps.setFloat(6, u.getWeight());
            ps.setFloat(7, u.getHeight());
            ps.setFloat(8, u.getDesired_weight());
            ps.setFloat(9, u.getDesired_height());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    // 3. Kiểm tra đăng nhập
    public User checkLogin(String email, String password) {
        String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("User_id"));           
                    user.setFullName(rs.getString("name"));    
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("Role"));
                    user.setAge(rs.getInt("age"));
                    user.setWeight(rs.getFloat("weight"));
                    user.setHeight(rs.getFloat("height"));
                    user.setDesired_weight(rs.getFloat("desired_weight"));
                    user.setDesired_height(rs.getFloat("desired_height"));
                    user.setGender(rs.getBoolean("gender") ? "Nam" : "Nữ");
                    return user;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // 4. Cập nhật chỉ số sức khỏe cơ bản
    public boolean updateHealthProfile(User u) {
        String query = "UPDATE Users SET gender=?, age=?, weight=?, height=?, desired_weight=?, desired_height=? WHERE User_id=?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setBoolean(1, "Nam".equals(u.getGender())); 
            ps.setInt(2, u.getAge());
            ps.setFloat(3, u.getWeight());
            ps.setFloat(4, u.getHeight());
            ps.setFloat(5, u.getDesired_weight());
            ps.setFloat(6, u.getDesired_height());
            ps.setInt(7, u.getId()); 
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    // Lấy danh sách ID bệnh của User
    public List<Integer> getDiseaseIdsByUserId(int userId) {
        List<Integer> list = new ArrayList<>();
        String query = "SELECT Disease_id FROM User_disease WHERE User_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rs.getInt(1));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Lấy danh sách ID dị ứng của User
    public List<Integer> getAllergyIdsByUserId(int userId) {
        List<Integer> list = new ArrayList<>();
        String query = "SELECT Ingredient_id FROM User_Allergy WHERE User_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rs.getInt(1));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void addUserDiseases(int userId, String[] diseaseIds) {
        if (diseaseIds == null || diseaseIds.length == 0) return;
        String query = "INSERT INTO User_disease (User_id, Disease_id) VALUES (?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            for (String dId : diseaseIds) {
                if (dId != null && !dId.isEmpty()) {
                    ps.setInt(1, userId);
                    ps.setInt(2, Integer.parseInt(dId));
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void addUserAllergies(int userId, String[] allergyIds) {
        if (allergyIds == null || allergyIds.length == 0) return;
        String query = "INSERT INTO User_Allergy (User_id, Ingredient_id) VALUES (?, ?)";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            for (String aId : allergyIds) {
                if (aId != null && !aId.isEmpty()) {
                    ps.setInt(1, userId);
                    ps.setInt(2, Integer.parseInt(aId));
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void updateDiseases(int userId, String[] diseaseIds) {
        try (Connection conn = new DBContext().getConnection()) {
            PreparedStatement psDel = conn.prepareStatement("DELETE FROM User_disease WHERE User_id = ?");
            psDel.setInt(1, userId);
            psDel.executeUpdate();
            addUserDiseases(userId, diseaseIds);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void updateAllergies(int userId, String[] allergyIds) {
        try (Connection conn = new DBContext().getConnection()) {
            PreparedStatement psDel = conn.prepareStatement("DELETE FROM User_Allergy WHERE User_id = ?");
            psDel.setInt(1, userId);
            psDel.executeUpdate();
            addUserAllergies(userId, allergyIds);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public boolean updateBasicInfo(int userId, String name, String email) {
        String query = "UPDATE Users SET name = ?, email = ? WHERE User_id = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        String checkQuery = "SELECT 1 FROM Users WHERE User_id = ? AND password = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement psCheck = conn.prepareStatement(checkQuery)) {
            psCheck.setInt(1, userId);
            psCheck.setString(2, oldPassword);
            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next()) {
                    String updateQuery = "UPDATE Users SET password = ? WHERE User_id = ?";
                    try (PreparedStatement psUpdate = conn.prepareStatement(updateQuery)) {
                        psUpdate.setString(1, newPassword);
                        psUpdate.setInt(2, userId);
                        return psUpdate.executeUpdate() > 0;
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false; 
    }

    // ================== CÁC HÀM MỚI CHO ADMIN QUẢN LÝ (DÙNG DỮ LIỆU CÓ SẴN) ==================

    // Lấy thông kê đếm số lượng (Trạng thái được fake)
    public int[] getUserStats() {
        int[] stats = new int[4]; // [Tổng, Hoạt động, Không HĐ, Admin]
        String sql = "SELECT COUNT(*) AS total, SUM(CASE WHEN Role = 'ADMIN' THEN 1 ELSE 0 END) AS admins FROM Users";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                stats[0] = rs.getInt("total");
                stats[1] = rs.getInt("total"); // Fake: Đang hoạt động = Tổng số
                stats[2] = 0;                  // Fake: Không hoạt động = 0
                stats[3] = rs.getInt("admins");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return stats;
    }

    // Lấy danh sách kết hợp Tìm kiếm & Lọc (Tự bơm dữ liệu giả cho Status/Date)
    public List<User> searchAndFilterUsers(String keyword, String role) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE 1=1 ";

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND (name LIKE ? OR email LIKE ?) ";
        }
        if (role != null && !role.equals("all") && !role.isEmpty()) {
            sql += " AND Role = ? ";
        }

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword + "%");
                ps.setString(paramIndex++, "%" + keyword + "%");
            }
            if (role != null && !role.equals("all") && !role.isEmpty()) {
                ps.setString(paramIndex++, role.toUpperCase()); 
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("User_id"));
                    user.setFullName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("Role"));
                    
                    // BƠM DỮ LIỆU GIẢ CHO GIAO DIỆN
                    user.setStatus(1); // Luôn là Hoạt động
                    user.setCreatedAt("10-04-2024"); // Ngày tạo cố định
                    
                    list.add(user);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 1. Đếm tổng số người dùng (theo bộ lọc) để tính tổng số trang
    public int getTotalUsers(String keyword, String role) {
        String sql = "SELECT COUNT(*) FROM Users WHERE 1=1 ";
        if (keyword != null && !keyword.isEmpty()) sql += " AND (name LIKE ? OR email LIKE ?) ";
        if (role != null && !role.equals("all") && !role.isEmpty()) sql += " AND Role = ? ";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword + "%");
                ps.setString(paramIndex++, "%" + keyword + "%");
            }
            if (role != null && !role.equals("all") && !role.isEmpty()) {
                ps.setString(paramIndex++, role);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // 2. Lấy danh sách 10 người cho 1 trang cụ thể
    public List<User> getUsersPaged(String keyword, String role, int index) {
        List<User> list = new ArrayList<>();
        // Công thức: LIMIT 10 OFFSET (index-1)*10
        String sql = "SELECT * FROM Users WHERE 1=1 ";
        if (keyword != null && !keyword.isEmpty()) sql += " AND (name LIKE ? OR email LIKE ?) ";
        if (role != null && !role.equals("all") && !role.isEmpty()) sql += " AND Role = ? ";
        sql += " LIMIT 10 OFFSET ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword + "%");
                ps.setString(paramIndex++, "%" + keyword + "%");
            }
            if (role != null && !role.equals("all") && !role.isEmpty()) {
                ps.setString(paramIndex++, role);
            }
            ps.setInt(paramIndex, (index - 1) * 10); // Tính vị trí bắt đầu lấy

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("User_id"));
                user.setFullName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("Role"));
                user.setStatus(1); // Fake data
                user.setCreatedAt("10-04-2024"); // Fake data
                list.add(user);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Vô hiệu hóa = XÓA THẲNG KHỎI CSDL (Kèm xóa dữ liệu liên quan để tránh lỗi khóa ngoại)
    public boolean deleteUser(int userId) {
        String sqlDeleteAllergy = "DELETE FROM User_Allergy WHERE User_id = ?";
        String sqlDeleteDisease = "DELETE FROM User_disease WHERE User_id = ?";
        String sqlDeleteUser = "DELETE FROM Users WHERE User_id = ?";

        try (Connection conn = new DBContext().getConnection()) {
            
            // 1. Xóa Dị ứng trước
            try (PreparedStatement ps1 = conn.prepareStatement(sqlDeleteAllergy)) {
                ps1.setInt(1, userId);
                ps1.executeUpdate();
            }
            
            // 2. Xóa Bệnh lý tiếp theo
            try (PreparedStatement ps2 = conn.prepareStatement(sqlDeleteDisease)) {
                ps2.setInt(1, userId);
                ps2.executeUpdate();
            }
            
            // 3. Cuối cùng mới tiêu diệt User ở bảng chính
            try (PreparedStatement ps3 = conn.prepareStatement(sqlDeleteUser)) {
                ps3.setInt(1, userId);
                return ps3.executeUpdate() > 0;
            }
            
        } catch (Exception e) {
            System.out.println("LỖI KHI XÓA USER: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}