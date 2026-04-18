<%-- 
    Document   : favorites
    Created on : Apr 18, 2026, 12:37:28 PM
    Author     : Nhat0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Món ăn yêu thích</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/favorites.css">
</head>
<body>
    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon"><i class="fa-solid fa-leaf text-white"></i></div>
            <div class="brand-text">
                <h2>Healthy Food</h2>
                <span>Dinh dưỡng thông minh</span>
            </div>
        </div>
        <ul class="nav-menu">
            <li class="nav-item"><a href="${pageContext.request.contextPath}/home" class="nav-link"><i class="fa-solid fa-house"></i> Trang chủ</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/profile" class="nav-link"><i class="fa-regular fa-user"></i> Hồ sơ sức khỏe</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/foods" class="nav-link"><i class="fa-solid fa-utensils"></i> Món ăn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/meal_plan" class="nav-link"><i class="fa-regular fa-calendar-days"></i> Thực đơn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/progress" class="nav-link"><i class="fa-solid fa-chart-line"></i> Tiến trình</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/favorites" class="nav-link active"><i class="fa-solid fa-heart"></i> Yêu thích</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/settings" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </aside>

    <main class="main-content">
        <h1>Món ăn yêu thích</h1>
        <p class="sub-text">Danh sách món ăn bạn đã bấm tim.</p>

        <c:if test="${empty favoriteFoods}">
            <div class="empty-state">
                <i class="fa-regular fa-heart"></i>
                <p>Bạn chưa có món ăn yêu thích nào.</p>
                <a href="${pageContext.request.contextPath}/foods" class="btn-go-foods">Khám phá món ăn</a>
            </div>
        </c:if>

        <div class="grid">
            <c:forEach var="food" items="${favoriteFoods}">
                <a href="${pageContext.request.contextPath}/food-detail?id=${food.food_id}" class="card">
                    <div class="card-img-wrapper">
                        <img src="${pageContext.request.contextPath}/assets/images/${food.image_url}" alt="${food.food_name}" onerror="this.onerror=null; this.src='https://via.placeholder.com/300x200?text=No+Image'">
                        <span class="badge-favorite"><i class="fa-solid fa-heart"></i> Đã thích</span>
                    </div>
                    <div class="card-body">
                        <h3 class="card-title">${food.food_name}</h3>
                        <div class="card-calo">${food.displayCalories} calo</div>
                        <div class="card-rating"><i class="fa-solid fa-star"></i> Ưu tiên hiển thị</div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </main>
</body>
</html>

