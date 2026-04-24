<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ - Healthy Food</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
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
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/home" class="nav-link active">
                    <i class="fa-solid fa-house"></i> Trang chủ
                </a>
            </li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/profile" class="nav-link"><i class="fa-regular fa-user"></i> Hồ sơ sức khỏe</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/foods" class="nav-link"><i class="fa-solid fa-utensils"></i> Món ăn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/meal_plan" class="nav-link"><i class="fa-regular fa-calendar-days"></i> Thực đơn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/progress" class="nav-link"><i class="fa-solid fa-chart-line"></i> Tiến trình</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/favorites" class="nav-link"><i class="fa-regular fa-heart"></i> Yêu thích</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/search" class="nav-link"><i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/settings" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </aside>

    <main class="main-content">
        
        <header class="top-header">
            <div class="greeting">
                <h1>Xin chào,${sessionScope.currentUser.fullName}</h1>
                <p>Chúc bạn một ngày tốt lành!</p>
            </div>
            <a class="view-all" href="${pageContext.request.contextPath}/meal_plan">
          <button class="btn-add">
            <i class="fa-solid fa-plus"></i> Thêm bữa ăn
          </button>
        </a>
        </header>

        <section class="stats-grid">
            <div class="stat-card">
                <div class="stat-card-header">
                    <div>
                        <h3>Calo hôm nay</h3>
                        <h2><fmt:formatNumber value="${todayCalories}" maxFractionDigits="0"/> <span style="font-size:14px; color:#aaa;">/<fmt:formatNumber value="${targetCalories}" maxFractionDigits="0"/></span></h2>
                    </div>
                    <div class="icon-box"><i class="fa-solid fa-fire-flame-curved"></i></div>
                </div>
                <div class="progress-bar"><div class="progress-fill"></div></div>
               <div class="stat-note">Còn lại <fmt:formatNumber value="${remainCalories}" maxFractionDigits="0"/> calo</div>
            </div>

            <div class="stat-card">
                <div class="stat-card-header">
                    <div>
                        <h3>Chỉ số BMI</h3>
                         <h2><fmt:formatNumber value="${bmi}" maxFractionDigits="2"/></h2>
                    </div>
                    <div class="icon-box"><i class="fa-solid fa-scale-balanced"></i></div>
                </div>
                <div class="badge">Bình thường</div>
            </div>

            <div class="stat-card">
                <div class="stat-card-header">
                    <div>
                        <h3>BMR</h3>
                       <h2><fmt:formatNumber value="${bmr}" maxFractionDigits="0"/></h2>
                    </div>
                    <div class="icon-box"><i class="fa-solid fa-square-root-variable"></i></div>
                </div>
                <div class="stat-note" style="margin-top: 15px;">Calo cần thiết cơ bản</div>
            </div>

            <div class="stat-card">
                <div class="stat-card-header">
                    <div>
                        <h3>Mục tiêu</h3>
                        <h2>${goalLabel}</h2>
                    </div>
                    <div class="icon-box"><i class="fa-solid fa-bullseye"></i></div>
                </div>
                 <div class="stat-note" style="margin-top: 15px;"><a href="${pageContext.request.contextPath}/settings" style="color: inherit;">Cập nhật mục tiêu</a></div>
            </div>
        </section>

        <section>
            <div class="section-header">
                <h2>Bữa ăn hôm nay</h2>
                <a href="${pageContext.request.contextPath}/meal_plan" class="view-all">Xem tất cả ></a>
            </div>
            <div class="meals-grid">
                 <c:forEach var="meal" items="${todayMeals}" varStatus="st">
                    <div class="meal-card">
                        <div class="meal-time">${st.index + 1}</div>
                        <div class="meal-info">
                            <h4>${meal.mealName}</h4>
                            <p><fmt:formatNumber value="${meal.totalCalories}" maxFractionDigits="0"/> calo - ${meal.totalFoods} món</p>
                    </div>
                </div>
                     </c:forEach>
            </div>
        </section>

        <section>
            <div class="section-header">
                <h2>Gợi ý cho bạn</h2>
                <a href="${pageContext.request.contextPath}/foods" class="view-all">Xem thêm ></a>
            </div>
            <div class="suggest-grid">
                <c:forEach var="food" items="${homeSuggestions}">
                     <a href="${pageContext.request.contextPath}/food-detail?id=${food.food_id}" style="text-decoration:none; color:inherit;">
                        <div class="food-card">
                            <img src="${pageContext.request.contextPath}/assets/images/${food.image_url}" alt="${food.food_name}" class="food-img" onerror="this.onerror=null; this.src='https://via.placeholder.com/300x200?text=No+Image'">
                            <div class="status-badge ${food.allergyConflictCount == 0 ? 'bg-safe' : 'bg-warn'}">
                                <i class="fa-solid ${food.allergyConflictCount == 0 ? 'fa-check' : 'fa-shield-halved'}"></i> ${food.allergyConflictCount == 0 ? 'An toàn' : 'Có dị ứng'}
                            </div>
                       <div class="food-content">
                                <h4>${food.food_name}</h4>
                                <p><fmt:formatNumber value="${food.calories}" maxFractionDigits="0"/> calo</p>
                                <div class="food-footer">
                                    <i class="fa-regular fa-star"></i>
                                    <span><fmt:formatNumber value="${food.suitabilityScore}" maxFractionDigits="0"/>% phù hợp</span>
                        </div>
                    </div>
                     
                </div>
                     </a>
                        </c:forEach>
            </div>
        </section>

    </main>

</body>
</html>