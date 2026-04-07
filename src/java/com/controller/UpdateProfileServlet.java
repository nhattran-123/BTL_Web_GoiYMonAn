package com.controller;

import com.model.bean.User;
import com.model.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/update-profile")
public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Đảm bảo không bị lỗi font tiếng Việt khi nhận dữ liệu
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }

        try {
            // 1. Lấy dữ liệu chỉ số cơ thể từ form
            String gender = request.getParameter("gender");
            int age = Integer.parseInt(request.getParameter("age"));
            float weight = Float.parseFloat(request.getParameter("weight"));
            float height = Float.parseFloat(request.getParameter("height"));
            float desiredWeight = Float.parseFloat(request.getParameter("desired_weight"));
            float desiredHeight = Float.parseFloat(request.getParameter("desired_height"));

            // 2. LẤY DỮ LIỆU BỆNH LÝ & DỊ ỨNG (Dạng mảng ID)
            String[] diseaseIds = request.getParameterValues("disease_id");
            String[] allergyIds = request.getParameterValues("allergy_id");

            // Cập nhật thông tin vào đối tượng currentUser trong Session
            currentUser.setGender(gender);
            currentUser.setAge(age);
            currentUser.setWeight(weight);
            currentUser.setHeight(height);
            currentUser.setDesired_weight(desiredWeight);
            currentUser.setDesired_height(desiredHeight);

            // 3. GỌI DAO ĐỂ GHI XUỐNG CƠ SỞ DỮ LIỆU
            UserDAO dao = new UserDAO();
            
            // Cập nhật bảng Users (Thông tin sức khỏe cơ bản)
            boolean success = dao.updateHealthProfile(currentUser);

            if (success) {
                // CẬP NHẬT BẢNG NỐI (Bệnh lý và Dị ứng)
                // Xóa hết cái cũ của User này và chèn cái mới người dùng vừa chọn
                dao.updateDiseases(currentUser.getId(), diseaseIds);
                dao.updateAllergies(currentUser.getId(), allergyIds);
                
                // Cập nhật lại Session để hiển thị dữ liệu mới nhất
                session.setAttribute("currentUser", currentUser);
            }
            
            // Xong xuôi thì load lại trang hồ sơ để xem kết quả
            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (Exception e) {
            System.out.println(">>> LỖI CẬP NHẬT HỒ SƠ: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }
}