package com.controller;

import com.model.bean.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin/users")
public class ManageUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        // KIỂM TRA BẢO MẬT: Chỉ Admin mới được vào
        if (currentUser == null || !"ADMIN".equalsIgnoreCase(currentUser.getRole().trim())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Đẩy sang trang giao diện Lĩnh vừa tạo
        request.getRequestDispatcher("/admin/manage-user.jsp").forward(request, response);
    }
}