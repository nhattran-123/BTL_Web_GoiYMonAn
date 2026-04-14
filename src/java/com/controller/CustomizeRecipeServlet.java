package com.controller;

import com.model.bean.User;
import com.model.dao.RecipeDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CustomizeRecipeServlet", urlPatterns = {"/customize-recipe"})
public class CustomizeRecipeServlet extends HttpServlet {


protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idRaw = request.getParameter("foodId");
        try {
            int foodId = Integer.parseInt(idRaw);
            // Gọi FoodDAO để lấy thông tin món ăn thực tế
            com.model.dao.FoodDAO dao = new com.model.dao.FoodDAO();
            com.model.bean.Food food = dao.getFoodById(foodId);
            // lấy danh sách nguyên liệu
            List<com.model.bean.IngredientItem> ingredientList = dao.getIngredientsByFoodId(foodId);
            request.setAttribute("ingredientList", ingredientList);
            // Truyền dữ liệu sang giao diện
            request.setAttribute("allIngredients", dao.getAllIngredientsFromDB());
            request.setAttribute("food", food);
            request.getRequestDispatcher("/views/auth/customize_recipe.jsp").forward(request, response);           
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/foods");
        }
    }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // 1. Lấy các dữ liệu từ Form gửi lên
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            double newCalories = Double.parseDouble(request.getParameter("calculatedCalories"));
            double newFat = Double.parseDouble(request.getParameter("calculatedFat"));
            double newProtein = Double.parseDouble(request.getParameter("calculatedProtein"));
            double newCarbs = Double.parseDouble(request.getParameter("calculatedCarbs"));
            String newRecipe = request.getParameter("recipeText");
            
           
            HttpSession session = request.getSession();
            com.model.bean.User currentUser = (com.model.bean.User) session.getAttribute("currentUser");

            // Nếu chưa đăng nhập -> chuyển về trang login
            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return; 
            }
            // Đã đăng nhập, lấy id thực thể
            int userId = currentUser.getId();

            // Khởi tạo các DAO cần thiết
            com.model.dao.FoodDAO foodDao = new com.model.dao.FoodDAO();
            com.model.dao.RecipeDAO recipeDao = new com.model.dao.RecipeDAO();

            // Sửa lỗi logic: Nếu không nhập công thức, lấy công thức gốc thay thế
            if (newRecipe == null || newRecipe.trim().isEmpty()) {
                com.model.bean.Food originalFood = foodDao.getFoodById(foodId);
                if(originalFood != null) {
                    newRecipe = originalFood.getRecipe(); // Lấy cách làm gốc
                }
            }

            // 3. Thực hiện lưu vào bảng adjusted_recipe
            boolean isSaved = recipeDao.insertAdjustedRecipe(foodId, userId, newRecipe, newCalories, newFat, newProtein, newCarbs);

            if (isSaved) {
                request.setAttribute("successMsg", "Đã lưu công thức thành công!");
                response.sendRedirect(request.getContextPath() + "/foods?success=true");
                return;
            } else {
                request.setAttribute("errorMsg", "Lưu thất bại do lỗi hệ thống Database.");
            }
            //Load lại thông tin món ăn trước khi trả về JSP
            com.model.bean.Food food = foodDao.getFoodById(foodId);
            request.setAttribute("food", food);
            
            java.util.List<com.model.bean.IngredientItem> ingredientList = foodDao.getIngredientsByFoodId(foodId);
            request.setAttribute("ingredientList", ingredientList);

            // Đẩy về lại trang giao diện
            request.getRequestDispatcher("/views/auth/customize_recipe.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi parse số liệu, cũng phải load lại data cho đỡ trắng trang (nhưng tạm bỏ qua để code gọn)
            request.setAttribute("errorMsg", "Dữ liệu nhập vào không hợp lệ!");
            request.getRequestDispatcher("/views/auth/customize_recipe.jsp").forward(request, response);
        }
    }
}