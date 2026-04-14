<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết: ${food.food_name}</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/food_detail.css">
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
            <li class="nav-item"><a href="${pageContext.request.contextPath}/foods" class="nav-link active"><i class="fa-solid fa-utensils"></i> Món ăn</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-regular fa-calendar-days"></i> Thực đơn</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-chart-line"></i> Tiến trình</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-regular fa-heart"></i> Yêu thích</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/settings" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </aside>

    <main class="main-content">
        <a href="${pageContext.request.contextPath}/foods" style="color: #666; text-decoration: none; margin-bottom: 20px; display: inline-block; font-weight: 500;">
            <i class="fa-solid fa-chevron-left"></i> Quay lại danh sách
        </a>
        
        <img src="${pageContext.request.contextPath}/assets/images/${food.image_url}" class="hero-image" onerror="this.src='https://via.placeholder.com/900x380?text=Food+Image'">
        
        <div class="action-bar">
            <button class="btn btn-like"><i class="fa-regular fa-heart"></i> Thích</button>
            <button class="btn btn-add"><i class="fa-solid fa-plus"></i> Thêm vào thực đơn</button>
        </div>

        <div class="section">
            <h2 style="margin:0; color: #111;">${food.food_name}</h2>
            <p style="color: #555; line-height: 1.7; margin-top: 15px;">${food.description}</p>
        </div>

        <div class="section">
            <h3 class="section-title">Thành phần dinh dưỡng</h3>
            <div class="macro-grid">
                <div class="macro-box" style="background: #f0fdf4;">
                    <span class="macro-value" style="color: #065f46;">${food.calories}</span>
                    <span class="macro-label">Calo</span>
                </div>
                <div class="macro-box" style="background: #eff6ff;">
                    <span class="macro-value" style="color: #1e40af;">${food.protein}g</span>
                    <span class="macro-label">Protein</span>
                </div>
                <div class="macro-box" style="background: #fffbeb;">
                    <span class="macro-value" style="color: #92400e;">${food.fat}g</span>
                    <span class="macro-label">Chất béo</span>
                </div>
                <div class="macro-box" style="background: #fef2f2;">
                    <span class="macro-value" style="color: #991b1b;">${food.carbohydrate}g</span>
                    <span class="macro-label">Carbs</span>
                </div>
            </div>
        </div>

        <div class="section">
            <div class="section-title">
                <span>Nguyên liệu</span>
                <a href="${pageContext.request.contextPath}/customize-recipe?foodId=${food.food_id}" class="btn-small">
                    <i class="fa-solid fa-sliders"></i> Điều chỉnh
                </a>
            </div>
            <div class="ingredient-grid">
                <c:forEach var="ing" items="${ingredients}">
                    <div class="ing-item">
                        <span class="ing-name">${ing.ingredientName}</span>
                        <span class="ing-qty">${ing.quantity} ${ing.unit}</span>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${empty ingredients}">
                <p style="color: #999; font-style: italic; text-align: center;">Dữ liệu nguyên liệu đang được cập nhật...</p>
            </c:if>
        </div>

        <div class="section">
            <h3 class="section-title">Cách làm</h3>
            <p style="white-space: pre-line; line-height: 1.8; color: #333;">${food.recipe}</p>
        </div>
    </main>
</body>
</html>