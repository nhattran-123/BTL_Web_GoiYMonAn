package com.controller;

import com.model.bean.Disease;
import com.model.bean.Ingredient;
import com.model.bean.User;
import com.model.dao.UserDAO;
import com.model.dao.DataDAO;
import com.util.SecurityUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // --- CHUẨN BỊ DỮ LIỆU ĐỔ RA GIAO DIỆN ĐĂNG KÝ ---
        DataDAO dataDao = new DataDAO();
        
        
        
List<Disease> ds = dataDao.getAllDiseases();
List<Ingredient> ig = dataDao.getAllIngredients();

// Dòng kiểm tra (Debug) - Lĩnh nhìn xuống cửa sổ Output của NetBeans nhé
System.out.println(">>> So luong benh ly: " + ds.size());
System.out.println(">>> So luong nguyen lieu: " + ig.size());

request.setAttribute("listDisease", ds);
request.setAttribute("listIngredient", ig);
        
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        // 1. Lấy dữ liệu CƠ BẢN & SỨC KHỎE
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String rawPassword = request.getParameter("password");
        
        String gender = request.getParameter("gender");
        String ageStr = request.getParameter("age");
        String weightStr = request.getParameter("weight");
        String heightStr = request.getParameter("height");
        String dWeightStr = request.getParameter("desired_weight");
        String dHeightStr = request.getParameter("desired_height");
        String goalType = request.getParameter("goal");
        
        // 2. LẤY MẢNG BỆNH LÝ & DỊ ỨNG MÀ NGƯỜI DÙNG CHỌN
        String[] diseaseIds = request.getParameterValues("disease_id");
        String[] allergyIds = request.getParameterValues("allergy_id");

        int age = (ageStr == null || ageStr.isEmpty()) ? 0 : Integer.parseInt(ageStr);
        float weight = (weightStr == null || weightStr.isEmpty()) ? 0 : Float.parseFloat(weightStr);
        float height = (heightStr == null || heightStr.isEmpty()) ? 0 : Float.parseFloat(heightStr);
        float dWeight = (dWeightStr == null || dWeightStr.isEmpty()) ? 0 : Float.parseFloat(dWeightStr);
        float dHeight = (dHeightStr == null || dHeightStr.isEmpty()) ? 0 : Float.parseFloat(dHeightStr);

        UserDAO dao = new UserDAO();

        if (dao.checkEmailExist(email)) {
            request.setAttribute("error", "Email này đã được sử dụng!");
            // Gọi lại DataDAO để trang không bị mất danh sách khi báo lỗi
            DataDAO dataDao = new DataDAO();
            request.setAttribute("listDisease", dataDao.getAllDiseases());
            request.setAttribute("listIngredient", dataDao.getAllIngredients());
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return; 
        }

        String hashedPassword = SecurityUtil.hashPassword(rawPassword);

        User newUser = new User(email, hashedPassword, fullName);
        newUser.setGender(gender);
        newUser.setAge(age);
        newUser.setWeight(weight);
        newUser.setHeight(height);
        newUser.setDesired_weight(dWeight);
        newUser.setDesired_height(dHeight);
        
        // 3. LƯU USER VÀ LẤY ID MỚI
        int newUserId = dao.registerUserReturnId(newUser);
        
        if (newUserId > 0) {
            // 4. LƯU TIẾP BỆNH VÀ DỊ ỨNG VỚI CÁI ID VỪA LẤY
            dao.addUserDiseases(newUserId, diseaseIds);
            dao.addUserAllergies(newUserId, allergyIds);
            dao.addUserGoal(newUserId, goalType, estimateTargetCalories(gender, age, weight, height, goalType));
            
            request.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Lỗi hệ thống khi đăng ký!");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
        }
    }
private double estimateTargetCalories(String gender, int age, float weight, float height, String goalType) {
        if (age <= 0 || weight <= 0 || height <= 0) {
            return 2000;
        }
        double bmr;
        if ("Nam".equals(gender)) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }
        double tdee = bmr * 1.4;
        if ("Giảm cân".equalsIgnoreCase(goalType)) {
            return Math.max(1200, tdee - 300);
        }
        if ("Tăng cân".equalsIgnoreCase(goalType)) {
            return tdee + 300;
        }
        return tdee;
    }
}