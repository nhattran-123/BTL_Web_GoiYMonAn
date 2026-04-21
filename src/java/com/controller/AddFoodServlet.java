package com.controller;

import com.model.bean.Food;
import com.model.dao.FoodDAO;
import com.model.dao.IngredientDAO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

// CẤU HÌNH ĐỂ NHẬN FILE UPLOAD TỪ FORM
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB (Kích thước bộ nhớ đệm)
    maxFileSize = 1024 * 1024 * 10,       // 10MB (Kích thước tối đa 1 file)
    maxRequestSize = 1024 * 1024 * 50     // 50MB (Kích thước tối đa toàn request)
)
@WebServlet(name = "AddFoodServlet", urlPatterns = {"/admin/add_food"})
public class AddFoodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        IngredientDAO ingDao = new IngredientDAO();
        request.setAttribute("ingredients", ingDao.searchIngredients("")); 
        request.getRequestDispatcher("/admin/add_food.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        Food food = new Food();
        food.setFood_name(request.getParameter("name"));
        food.setDescription(request.getParameter("description"));
        food.setRecipe(request.getParameter("recipe"));
        
        // --- XỬ LÝ UPLOAD ẢNH ---
        Part filePart = request.getPart("imageFile"); // Lấy file từ input name="imageFile"
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Lấy tên file gốc
        
        if (fileName != null && !fileName.isEmpty()) {
            // Lấy đường dẫn tuyệt đối đến thư mục assets/images trên Server (Tomcat)
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + "assets" + File.separator + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Tạo thư mục nếu chưa có
            }
            
            // Có thể thêm System.currentTimeMillis() vào trước tên file để tránh trùng lặp tên ảnh
            String finalFileName = System.currentTimeMillis() + "_" + fileName;
            filePart.write(uploadPath + File.separator + finalFileName); // Lưu file vào ổ cứng
            
            food.setImage_url(finalFileName); // Lưu tên file vào CSDL
        } else {
            food.setImage_url("default.jpg"); // Nếu không upload ảnh, để ảnh mặc định
        }

        try {
            food.setCalories(Double.parseDouble(request.getParameter("calories")));
            food.setProtein(Double.parseDouble(request.getParameter("protein")));
            food.setFat(Double.parseDouble(request.getParameter("fat")));
            food.setCarbohydrate(Double.parseDouble(request.getParameter("carbohydrate")));
        } catch (NumberFormatException e) {
            food.setCalories(0); food.setProtein(0); food.setFat(0); food.setCarbohydrate(0);
        }
        
        String[] ingredientIds = request.getParameterValues("ingredientId[]");
        String[] quantities = request.getParameterValues("quantity[]");
        String[] units = request.getParameterValues("unit[]");
        
        FoodDAO foodDao = new FoodDAO();
        boolean isSuccess = foodDao.insertFoodWithIngredients(food, ingredientIds, quantities, units);
        
        if (isSuccess) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_food?success=added");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/add_food?error=true");
        }
    }
}