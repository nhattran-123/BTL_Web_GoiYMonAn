package com.controller;

import com.model.bean.User;
import com.model.dao.UserDAO;
import com.util.SecurityUtil; 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/admin/settings")
public class AdminSettingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Kiểm tra xem Admin đã đăng nhập chưa
        if (session.getAttribute("currentUser") == null) {
          
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }
        
        // Trỏ về đúng giao diện cài đặt của Admin
        request.getRequestDispatcher("/admin/settings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }

       
        String action = request.getParameter("action");
        UserDAO dao = new UserDAO();

        try {
           
            if ("updateInfo".equals(action)) {
                String newName = request.getParameter("fullName");
                String newEmail = request.getParameter("email");

                
                if (!newEmail.equals(currentUser.getEmail()) && dao.checkEmailExist(newEmail)) {
                    request.setAttribute("errorInfo", "Email này đã có người sử dụng!");
                } else {
                    if (dao.updateBasicInfo(currentUser.getId(), newName, newEmail)) {
                       
                        currentUser.setFullName(newName);
                        currentUser.setEmail(newEmail);
                        session.setAttribute("currentUser", currentUser);
                        request.setAttribute("successInfo", "Cập nhật thông tin Admin thành công!");
                    } else {
                        request.setAttribute("errorInfo", "Lỗi khi cập nhật vào cơ sở dữ liệu!");
                    }
                }
            } 
           
            else if ("changePassword".equals(action)) {
                String oldPass = request.getParameter("oldPassword");
                String newPass = request.getParameter("newPassword");
                String confirmPass = request.getParameter("confirmPassword");

                if (!newPass.equals(confirmPass)) {
                    request.setAttribute("errorPass", "Mật khẩu xác nhận không khớp!");
                } else {
                    // Mã hóa mật khẩu
                    String hashedOld = SecurityUtil.hashPassword(oldPass);
                    String hashedNew = SecurityUtil.hashPassword(newPass);

                    if (dao.changePassword(currentUser.getId(), hashedOld, hashedNew)) {
                        request.setAttribute("successPass", "Đổi mật khẩu Admin thành công!");
                    } else {
                        request.setAttribute("errorPass", "Mật khẩu hiện tại không đúng!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        request.getRequestDispatcher("/admin/settings.jsp").forward(request, response);
    }
}