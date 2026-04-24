# Chuyển dự án sang Spring Boot + SQL Server (không dùng Servlet/JSP)

## Công nghệ
- Frontend: HTML, CSS, JavaScript.
- Backend: Java, Spring Boot.
- Database: SQL Server.

## Vì sao chọn bộ này
- Phổ biến.
- Dễ triển khai.
- Dễ tích hợp với Spring Boot.

## Kiến trúc MVC
- Controller -> Service -> Repository -> SQL Server.
- View dùng file `.html` (Thymeleaf), không dùng Servlet/JSP.

> Đã migrate lại **toàn bộ nhóm thực thể (entity) từ hệ cũ** vào `src/main/java/com/goiymonan/model/entity` gồm: `User`, `Food`, `Ingredient`, `Disease`, `MealPlanDetail`, `HealthIndex`, `Recommendation`, ... để bám sát cấu trúc dữ liệu ban đầu.

## Vẽ cấu trúc file dự án (tree)
```text
BTL_Web_GoiYMonAn/
├─ pom.xml
├─ HUONG_DAN_CHUYEN_SANG_SPRING_BOOT.md
├─ src/
│  └─ main/
│     ├─ java/
│     │  └─ com/goiymonan/
│     │     ├─ GoiMonAnApplication.java
│     │     ├─ config/
│     │     │  └─ WebMvcConfig.java
│     │     ├─ controller/
│     │     │  ├─ AuthController.java
│     │     │  ├─ FoodController.java
│     │     │  ├─ UserPageController.java
│     │     │  └─ AdminPageController.java
│     │     ├─ dto/
│     │     │  └─ RegisterRequest.java
│     │     ├─ model/entity/
│     │     │  ├─ User.java
│     │     │  ├─ Food.java
│     │     │  ├─ Ingredient.java
│     │     │  ├─ Disease.java
│     │     │  ├─ MealPlanDetail.java
│     │     │  └─ ... (các entity còn lại)
│     │     ├─ repository/
│     │     │  ├─ UserRepository.java
│     │     │  ├─ FoodRepository.java
│     │     │  └─ jdbc/
│     │     │     ├─ JdbcUserRepository.java
│     │     │     └─ JdbcFoodRepository.java
│     │     └─ service/
│     │        ├─ AuthService.java
│     │        └─ FoodService.java
│     └─ resources/
│        ├─ application.properties
│        ├─ static/
│        │  ├─ css/app.css
│        │  └─ js/app.js
│        └─ templates/
│           ├─ auth/
│           │  ├─ index.html
│           │  ├─ login.html
│           │  ├─ register.html
│           │  ├─ home.html
│           │  ├─ food_list.html
│           │  ├─ food_detail.html
│           │  ├─ search_food.html
│           │  ├─ favorites.html
│           │  ├─ meal_plan.html
│           │  ├─ profile.html
│           │  ├─ progress.html
│           │  ├─ settings.html
│           │  └─ customize_recipe.html
│           └─ admin/
│              ├─ dashboard.html
│              ├─ manage_user.html
│              ├─ manage_food.html
│              ├─ add_food.html
│              ├─ edit_food.html
│              ├─ manage_ingredient.html
│              ├─ add_ingredient.html
│              ├─ edit_ingredient.html
│              ├─ manage_disease.html
│              └─ settings.html
```

## Cấu hình SQL Server
Sửa trong `src/main/resources/application.properties`:
- `spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=goi_y_mon_an;encrypt=true;trustServerCertificate=true`
- `spring.datasource.username=sa`
- `spring.datasource.password=YourStrong@Passw0rd`
- `spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver`

## Chạy trên VS Code
1. Cài extension: Extension Pack for Java, Spring Boot Extension Pack, Maven for Java.
2. Cài: JDK 17, Maven 3.9+, SQL Server.
3. Chạy:
```bash
mvn clean package
mvn spring-boot:run
```
4. Mở: `http://localhost:8080/login`.
