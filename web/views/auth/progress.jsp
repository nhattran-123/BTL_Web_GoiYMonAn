<%-- 
    Document   : progress
    Created on : Apr 18, 2026, 8:06:46 AM
    Author     : Nhat0
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tiến trình - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/progress.css">
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
            <li class="nav-item"><a href="${pageContext.request.contextPath}/progress" class="nav-link active"><i class="fa-solid fa-chart-line"></i> Tiến trình</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/favorites" class="nav-link"><i class="fa-regular fa-heart"></i> Yêu thích</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/search" class="nav-link"><i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/settings" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </aside>

    <main class="main-content progress-page">
        <header class="progress-header">
            <div>
                <h1>Tiến trình</h1>
                <p>Theo dõi quá trình sức khỏe của bạn.</p>
            </div>
           <button class="btn-update" id="openUpdateModalBtn" type="button">Cập nhật chỉ số</button>
        </header>

        <section class="stats-grid">
            <article class="stat-card">
                <p class="stat-title">Calo hôm nay</p>
                <h3><fmt:formatNumber value="${todayCalories}" maxFractionDigits="0" /> kcal</h3>
            </article>
            <article class="stat-card">
                <p class="stat-title">Cân nặng hiện tại</p>
                <h3><fmt:formatNumber value="${sessionScope.currentUser.weight}" maxFractionDigits="1" /> kg</h3>
            </article>
            <article class="stat-card">
                <p class="stat-title">Số ngày hoạt động</p>
                <h3>${totalDaysFollowed}</h3>
            </article>
            <article class="stat-card">
                <p class="stat-title">Mục tiêu</p>
                <h3>${goalLabel}</h3>
            </article>
        </section>

        <section class="card">
            <div class="section-head">
                <h2>Tiến độ đạt mục tiêu</h2>
                <span><fmt:formatNumber value="${progressPercent}" maxFractionDigits="0" />%</span>
            </div>
            <div class="progress-track">
                <div class="progress-fill" style="width: <fmt:formatNumber value='${progressPercent}' maxFractionDigits='0'/>%;"></div>
            </div>
            <div class="goal-meta">
                <span>Hiện tại: <fmt:formatNumber value="${sessionScope.currentUser.weight}" maxFractionDigits="1" /> kg</span>
                <span>Mục tiêu: <fmt:formatNumber value="${sessionScope.currentUser.desired_weight}" maxFractionDigits="1" /> kg</span>
            </div>
        </section>

         <section class="card">
            <div class="section-head">
                <h2>Tiến độ mục tiêu chiều cao</h2>
                <span><fmt:formatNumber value="${heightProgressPercent}" maxFractionDigits="0" />%</span>
            </div>
            <div class="progress-track">
                <div class="progress-fill height-progress-fill" style="width: <fmt:formatNumber value='${heightProgressPercent}' maxFractionDigits='0'/>%;"></div>
            </div>
            <div class="goal-meta">
                <span>Hiện tại: <fmt:formatNumber value="${sessionScope.currentUser.height}" maxFractionDigits="1" /> cm</span>
                <span>Mục tiêu: <fmt:formatNumber value="${sessionScope.currentUser.desired_height}" maxFractionDigits="1" /> cm</span>
            </div>
        </section>
            
        <section class="card">
            <div class="section-head">
                <h2>Lịch sử ăn uống</h2>
                <a href="${pageContext.request.contextPath}/meal_plan" class="view-all">Xem tất cả</a>
            </div>

            <c:choose>
                <c:when test="${empty recentMealHistory}">
                    <p class="empty-text">Chưa có lịch sử ăn uống.</p>
                </c:when>
                <c:otherwise>
                    <div class="history-list">
                        <c:forEach var="dayHistory" items="${recentMealHistory}">
                            <article class="history-day">
                                <h4><fmt:formatDate value="${dayHistory.date}" pattern="dd/MM/yyyy"/></h4>
                                <c:forEach var="food" items="${dayHistory.foods}">
                                    <div class="history-item">
                                        <span>${food.foodName}</span>
                                        <span><fmt:formatNumber value="${food.calories}" maxFractionDigits="0"/> calo</span>
                                    </div>
                                </c:forEach>
                            </article>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>

        <section class="card simple-chart">
            <div class="section-head">
                <h2>Chỉ số BMI hiện tại</h2>
            </div>
            <p class="bmi-number"><fmt:formatNumber value="${bmi}" maxFractionDigits="2" /></p>
        </section>
    </main>
        
        <div class="modal-overlay" id="updateModalOverlay">
        <div class="modal-card">
            <div class="modal-head">
                <h3>Cập nhật chỉ số hiện tại</h3>
                <button type="button" class="modal-close" id="closeUpdateModalBtn">X</button>
            </div>
            <form action="${pageContext.request.contextPath}/progress" method="post">
                <div class="form-group">
                    <label for="currentHeight">Chiều cao hiện tại(cm):</label>
                    <input id="currentHeight" type="number" min="1" step="0.1" name="current_height" value="${sessionScope.currentUser.height}" required>
                </div>
                <div class="form-group">
                    <label for="currentWeight">Cân nặng hiện tại(kg):</label>
                    <input id="currentWeight" type="number" min="1" step="0.1" name="current_weight" value="${sessionScope.currentUser.weight}" required>
                </div>
                <button class="btn-save" type="submit">
                    <i class="fa-regular fa-floppy-disk"></i> Lưu thay đổi
                </button>
            </form>
        </div>
    </div>

    <script>
        const openUpdateModalBtn = document.getElementById("openUpdateModalBtn");
        const closeUpdateModalBtn = document.getElementById("closeUpdateModalBtn");
        const updateModalOverlay = document.getElementById("updateModalOverlay");

        function openUpdateModal() {
            updateModalOverlay.classList.add("active");
        }

        function closeUpdateModal() {
            updateModalOverlay.classList.remove("active");
        }

        openUpdateModalBtn.addEventListener("click", openUpdateModal);
        closeUpdateModalBtn.addEventListener("click", closeUpdateModal);

        updateModalOverlay.addEventListener("click", function (event) {
            if (event.target === updateModalOverlay) {
                closeUpdateModal();
            }
        });
    </script>
</body>
</html>

