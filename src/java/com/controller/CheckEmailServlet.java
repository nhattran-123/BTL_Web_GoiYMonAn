package com.controller;

import com.model.dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CheckEmailServlet", urlPatterns = {"/check-email"})
public class CheckEmailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Cấu hình trả về kiểu JSON (cho JavaScript dễ đọc)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String email = request.getParameter("email");
        UserDAO dao = new UserDAO();
        
        // Hỏi Database xem email tồn tại chưa
        boolean exists = dao.checkEmailExist(email);
        
        // Trả kết quả về cho Giao diện
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"exists\": " + exists + "}");
        }
    }
}