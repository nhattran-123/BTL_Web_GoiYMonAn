<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tổng quan Admin - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>

</head>
<body>

    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon"><i class="fa-solid fa-leaf text-white"></i></div>
            <div class="brand-text"><h2>Healthy Food</h2><span>Dinh dưỡng thông minh</span></div>
        </div>
        <ul class="nav-menu">
            <li class="nav-item"><a href="#" class="nav-link active"><i class="fa-solid fa-table-cells-large"></i> Tổng quan</a></li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/users" 
                   class="nav-link ${param.active == 'users' ? 'active' : ''}">
                    <i class="fa-regular fa-address-book"></i> Quản lý người dùng
                </a>
            </li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage_food" class="nav-link"><i class="fa-solid fa-utensils"></i> Quản lý món ăn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage_ingredient" class="nav-link"><i class="fa-solid fa-apple-whole"></i> Quản lý nguyên liệu</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage-disease" class="nav-link"><i class="fa-solid fa-heart-pulse"></i> Quản lý bệnh lý</a></li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/settings.jsp" class="nav-link">
                    <i class="fa-solid fa-gear"></i> Cài đặt
                </a>
            </li>
        </ul>
        <div class="logout-box"><a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a></div>
    </aside>

    <main class="main-content">
        <div class="page-title" style="margin-bottom: 30px;">
            <h1>Tổng quan</h1>
            <p>Chào mừng trở lại, ${sessionScope.currentUser.fullName != null ? sessionScope.currentUser.fullName : 'Admin'}!</p>
        </div>

        <div class="admin-grid">
            <div class="stat-card">
                <div class="stat-info">
                    <h4>Tổng số người dùng</h4>
                    <h2><fmt:formatNumber value="${totalUsers != null ? totalUsers : 0}" type="number"/></h2>
                    <p style="color: ${userGrowth >= 0 ? '#10b981' : '#dc2626'}">
                        <c:if test="${userGrowth >= 0}">+</c:if>
                        <fmt:formatNumber value="${userGrowth}" maxFractionDigits="1"/>% 
                        <span>So với tháng trước</span>
                    </p>
                </div>
                <div class="stat-icon bg-blue"><i class="fa-solid fa-users"></i></div>
            </div>
            
            <div class="stat-card">
                <div class="stat-info">
                    <h4>Tổng món ăn</h4>
                    <h2><fmt:formatNumber value="${totalFoods != null ? totalFoods : 0}" type="number"/></h2>
                    <p style="color: ${foodGrowth >= 0 ? '#10b981' : '#dc2626'}">
                        <c:if test="${foodGrowth >= 0}">+</c:if>
                        <fmt:formatNumber value="${foodGrowth}" maxFractionDigits="1"/>% 
                        <span>So với tháng trước</span>
                    </p>
                </div>
                <div class="stat-icon bg-green"><i class="fa-solid fa-utensils"></i></div>
            </div>
            
            <div class="stat-card">
                <div class="stat-info">
                    <h4>Tổng thực đơn được tạo</h4>
                    <h2><fmt:formatNumber value="${totalMenus != null ? totalMenus : 0}" type="number"/></h2>
                    <p style="color: ${menuGrowth >= 0 ? '#10b981' : '#dc2626'}">
                        <c:if test="${menuGrowth >= 0}">+</c:if>
                        <fmt:formatNumber value="${menuGrowth}" maxFractionDigits="1"/>% 
                        <span>So với tháng trước</span>
                    </p>
                </div>
                <div class="stat-icon bg-green"><i class="fa-regular fa-calendar-check"></i></div>
            </div>
            
            <div class="stat-card">
                <div class="stat-info">
                    <h4>Hoạt động/ngày</h4>
                    <h2><fmt:formatNumber value="${todayActivities != null ? todayActivities : 0}" type="number"/></h2>
                   
                </div>
                <div class="stat-icon bg-red"><i class="fa-solid fa-chart-line"></i></div>
            </div>
        </div>

                    
        <div class="chart-grid">
            <div class="chart-card">
                <h3 style="text-align: center; color: #111827; font-size: 16px; margin-bottom: 20px;">Thống kê đăng nhập 10 ngày qua</h3>
                <div style="height: 300px; width: 100%;">
                    <canvas id="loginChart"></canvas>
                </div>
            </div>

            <div class="chart-card">
                <h3 style="text-align: center; color: #111827; font-size: 16px; margin-bottom: 20px;">Người dùng đăng ký mới (Năm nay)</h3>
                <div style="height: 300px; width: 100%;">
                    <canvas id="registerChart"></canvas>
                </div>
            </div>
        </div>
                    
                    
        <div class="bottom-grid">
            <div class="list-card">
                <h3>Top món ăn được yêu thích</h3>
                
                <c:forEach items="${topFoods}" var="food">
                    <div class="food-item">
                        <span>${food.key}</span>
                        <span class="badge">${food.value} Lượt</span>
                    </div>
                </c:forEach>
                
                <c:if test="${empty topFoods}">
                    <div class="food-item" style="justify-content: center; border: none; color: #9ca3af;">
                        Chưa có dữ liệu thống kê
                    </div>
                </c:if>
            </div>

            <div class="list-card">
                <h3>Mục tiêu phổ biến</h3>
                
                <c:forEach items="${popularGoals}" var="goal">
                    <div class="progress-item">
                        <div class="progress-label">
                            <span>${goal.key}</span>
                            <strong><fmt:formatNumber value="${goal.value}" maxFractionDigits="0"/>%</strong>
                        </div>
                        <div class="progress-bg">
                            <div class="progress-fill" style="width: ${goal.value}%;"></div>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty popularGoals}">
                    <div style="text-align: center; color: #9ca3af; padding-top: 15px;">
                        Chưa có dữ liệu thống kê
                    </div>
                </c:if>
            </div>
            
        </div>
    </main>
  <script>
        Chart.register(ChartDataLabels);

        // ĐĂNG NHẬP 10 NGÀY QUA (BIỂU ĐỒ ĐƯỜNG - BÊN TRÁI)
        const loginLabels = ${chartLabels != null ? chartLabels : "[]"};
        const loginData = ${chartData != null ? chartData : "[]"};

        const ctxLogin = document.getElementById('loginChart').getContext('2d');
        let gradientLogin = ctxLogin.createLinearGradient(0, 0, 0, 300);
        gradientLogin.addColorStop(0, 'rgba(16, 185, 129, 0.4)'); 
        gradientLogin.addColorStop(1, 'rgba(16, 185, 129, 0.0)'); 

        new Chart(ctxLogin, {
            type: 'line', 
            data: {
                labels: loginLabels, 
                datasets: [{
                    label: 'Lượt đăng nhập',
                    data: loginData, 
                    borderColor: '#10b981', 
                    backgroundColor: gradientLogin, 
                    borderWidth: 3,
                    pointBackgroundColor: '#ffffff',
                    pointBorderColor: '#10b981',
                    pointBorderWidth: 2,
                    pointRadius: 4,
                    fill: true, 
                    tension: 0.3 
                }]
            },
            options: {
                responsive: true, maintainAspectRatio: false,
                plugins: {
                    legend: { display: false },
                    datalabels: {
                        anchor: 'end', align: 'top', color: '#10b981',
                        font: { weight: 'bold', size: 12 },
                        formatter: function(value) { return value > 0 ? value : ''; }
                    }
                },
                scales: {
                    x: { grid: { display: false, drawBorder: false }, ticks: { color: '#9ca3af', font: { weight: '500' } } },
                    y: { display: false, beginAtZero: true, suggestedMax: Math.max(...(loginData.length ? loginData : [0])) * 1.2 }
                },
                layout: { padding: { top: 20 } }
            }
        });

        // NGƯỜI DÙNG ĐĂNG KÝ MỚI (BIỂU ĐỒ CỘT - BÊN PHẢI)
        const rawRegisterData = ${userChartData != null ? userChartData : '[0,0,0,0,0,0,0,0,0,0,0,0]'};
        const currentMonth = new Date().getMonth() + 1; 

        // Tạo nhãn T1, T2...
        const registerLabels = [];
        for (let i = 1; i <= currentMonth; i++) {
            registerLabels.push('T' + i);
        }
        const registerData = rawRegisterData.slice(0, currentMonth);

        const ctxRegister = document.getElementById('registerChart').getContext('2d');
        new Chart(ctxRegister, {
            type: 'bar',
            data: {
                labels: registerLabels, 
                datasets: [{
                    label: 'Người dùng mới',
                    data: registerData, 
                    backgroundColor: '#0ea5e9', // Đổi màu xanh dương cho khác biệt biểu đồ 1
                    borderRadius: 6,
                    borderSkipped: false,
                    barThickness: 30 // Thu nhỏ cột lại một chút vì chia 2 khung
                }]
            },
            options: {
                responsive: true, maintainAspectRatio: false,
                plugins: {
                    legend: { display: false },
                    datalabels: {
                        anchor: 'end', align: 'top', color: '#6b7280',
                        font: { weight: 'bold', size: 12 },
                        formatter: function(value) { return value > 0 ? value : ''; }
                    }
                },
                scales: {
                    x: { grid: { display: false, drawBorder: false }, ticks: { color: '#9ca3af', font: { weight: '500' } } },
                    y: { display: false, beginAtZero: true, suggestedMax: Math.max(...(registerData.length ? registerData : [0])) * 1.2 }
                },
                layout: { padding: { top: 20 } }
            }
        });
    </script>
</body>


</html>