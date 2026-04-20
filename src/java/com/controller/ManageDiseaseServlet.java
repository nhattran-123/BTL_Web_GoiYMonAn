package com.controller;

import com.model.bean.DiseaseDetail;
import com.model.dao.DiseaseDAO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ManageDiseaseServlet", urlPatterns = {"/admin/manage-disease"})
public class ManageDiseaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        DiseaseDAO dao = new DiseaseDAO();

        // 1. XỬ LÝ LỆNH XÓA BỆNH LÝ
        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.deleteDisease(id);
            } catch (Exception e) { e.printStackTrace(); }
            response.sendRedirect(request.getContextPath() + "/admin/manage-disease");
            return;
        }

        // 2. XỬ LÝ LỆNH XÓA TƯƠNG THÍCH
        if ("deleteRating".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.deleteRating(id);
            } catch (Exception e) { e.printStackTrace(); }
            response.sendRedirect(request.getContextPath() + "/admin/manage-disease?tab=compatibility");
            return;
        }

        // --- MẶC ĐỊNH: ĐỔ DỮ LIỆU LÊN GIAO DIỆN ---
        // Đảm bảo tab nào cũng có đủ dữ liệu để hiển thị
        request.setAttribute("diseaseList", dao.getAllDiseases());
        request.setAttribute("foodList", dao.getAllFoodsForDropdown());
        request.setAttribute("compatibilityList", dao.getAllCompatibilities());

        request.getRequestDispatcher("/admin/manage_disease.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        DiseaseDAO dao = new DiseaseDAO();

        // A. CHỨC NĂNG THÊM BỆNH LÝ
        if ("add".equals(action)) {
            String name = request.getParameter("diseaseName");
            String desc = request.getParameter("diseaseDescription");
            dao.insertDisease(new DiseaseDetail(name, desc));
            response.sendRedirect(request.getContextPath() + "/admin/manage-disease");
        } 
        
        // B. CHỨC NĂNG CHỈNH SỬA BỆNH LÝ (ĐÃ KHÔI PHỤC VÀ KIỂM TRA)
        else if ("edit".equals(action)) {
            try {
                // Lấy ID từ input hidden trong form sửa
                int id = Integer.parseInt(request.getParameter("diseaseId"));
                String name = request.getParameter("diseaseName");
                String desc = request.getParameter("diseaseDescription");
                
                DiseaseDetail d = new DiseaseDetail(name, desc);
                d.setId(id);
                
                // Gọi hàm update trong DAO mà bạn đã viết
                boolean isUpdated = dao.updateDisease(d);
                
                if(isUpdated) {
                    System.out.println("Cập nhật thành công bệnh lý ID: " + id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/admin/manage-disease");
        }
        
        // C. CHỨC NĂNG THÊM TƯƠNG THÍCH
        else if ("addRating".equals(action)) {
            try {
                int foodId = Integer.parseInt(request.getParameter("foodId"));
                int diseaseId = Integer.parseInt(request.getParameter("diseaseId"));
                int rating = Integer.parseInt(request.getParameter("rating"));

                if (dao.checkRatingExist(foodId, diseaseId)) {
                    response.sendRedirect(request.getContextPath() + "/admin/manage-disease?error=duplicate_rating&tab=compatibility");
                    return;
                }
                dao.insertRating(foodId, diseaseId, rating);
            } catch (Exception e) { e.printStackTrace(); }
            response.sendRedirect(request.getContextPath() + "/admin/manage-disease?tab=compatibility");
        }
    }
}