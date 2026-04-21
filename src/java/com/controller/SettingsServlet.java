package com.controller;

import com.model.bean.User;
import com.model.dao.UserDAO;
import com.util.SecurityUtil; // Nhớ import thư viện mã hóa của Lĩnh nhé
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
            return;
        }
         User currentUser = (User) session.getAttribute("currentUser");
        UserDAO dao = new UserDAO();
        request.setAttribute("goalData", dao.getLatestGoalByUserId(currentUser.getId()));
        request.getRequestDispatcher("/views/auth/settings.jsp").forward(request, response);
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

        // Lấy biến action để biết người dùng đang bấm nút Lưu ở thẻ nào
        String action = request.getParameter("action");
        UserDAO dao = new UserDAO();
        request.setAttribute("goalData", dao.getLatestGoalByUserId(currentUser.getId()));

        try {
            // NẾU BẤM LƯU "THÔNG TIN CÁ NHÂN"
            if ("updateInfo".equals(action)) {
                String newName = request.getParameter("fullName");
                String newEmail = request.getParameter("email");

                // Check xem email mới có bị trùng với người khác không (bỏ qua nếu email không đổi)
                if (!newEmail.equals(currentUser.getEmail()) && dao.checkEmailExist(newEmail)) {
                    request.setAttribute("errorInfo", "Email này đã có người sử dụng!");
                } else {
                    if (dao.updateBasicInfo(currentUser.getId(), newName, newEmail)) {
                        currentUser.setFullName(newName);
                        currentUser.setEmail(newEmail);
                        session.setAttribute("currentUser", currentUser);
                        request.setAttribute("successInfo", "Cập nhật thông tin thành công!");
                    } else {
                        request.setAttribute("errorInfo", "Lỗi khi cập nhật!");
                    }
                }
            } 
            // NẾU BẤM LƯU "ĐỔI MẬT KHẨU"
            else if ("changePassword".equals(action)) {
                String oldPass = request.getParameter("oldPassword");
                String newPass = request.getParameter("newPassword");
                String confirmPass = request.getParameter("confirmPassword");

                if (!newPass.equals(confirmPass)) {
                    request.setAttribute("errorPass", "Mật khẩu xác nhận không khớp!");
                } else {
                    // Mã hóa mật khẩu trước khi check và lưu (Nếu Lĩnh dùng SecurityUtil)
                    String hashedOld = SecurityUtil.hashPassword(oldPass);
                    String hashedNew = SecurityUtil.hashPassword(newPass);

                    if (dao.changePassword(currentUser.getId(), hashedOld, hashedNew)) {
                        request.setAttribute("successPass", "Đổi mật khẩu thành công!");
                    } else {
                        request.setAttribute("errorPass", "Mật khẩu hiện tại không đúng!");
                    }
                }
            }
            else if ("updateGoal".equals(action)) {
                String goalType = request.getParameter("goalType");
                double targetCalories = Double.parseDouble(request.getParameter("targetCalories"));
                if (dao.upsertUserGoal(currentUser.getId(), goalType, targetCalories)) {
                    request.setAttribute("successGoal", "Đã cập nhật mục tiêu dinh dưỡng!");
                } else {
                    request.setAttribute("errorGoal", "Không thể cập nhật mục tiêu.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load lại trang cài đặt để hiện thông báo
        request.getRequestDispatcher("/views/auth/settings.jsp").forward(request, response);
    }
}