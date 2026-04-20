<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<head>
    <meta charset="UTF-8">
    <title>Tổng quan Admin - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        /* CSS DÀNH RIÊNG CHO CÁC THẺ CARD ADMIN */
        .admin-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            background: #fff;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.02);
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
        }
        .stat-info h4 { margin: 0; font-size: 13px; color: #6b7280; font-weight: 500;}
        .stat-info h2 { margin: 10px 0; font-size: 24px; color: #111827; }
        .stat-info p { margin: 0; font-size: 12px; color: #10b981; font-weight: bold;}
        .stat-info p span { color: #9ca3af; font-weight: normal; }
        
        .stat-icon {
            width: 40px; height: 40px;
            border-radius: 8px;
            display: flex; justify-content: center; align-items: center;
            font-size: 18px;
        }
        .bg-blue { background: #e0f2fe; color: #0284c7; }
        .bg-green { background: #d1fae5; color: #059669; }
        .bg-red { background: #fee2e2; color: #dc2626; }

        .chart-card {
            background: #fff;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.02);
            margin-bottom: 30px;
        }

        .bottom-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .list-card {
            background: #fff;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.02);
        }
        .list-card h3 { margin-top: 0; font-size: 16px; color: #111827; margin-bottom: 20px;}
        
        .food-item {
            display: flex; justify-content: space-between; align-items: center;
            padding: 12px 0; border-bottom: 1px solid #f3f4f6;
            color: #4b5563; font-size: 14px;
        }
        .badge {
            background: #d1fae5; color: #059669;
            padding: 4px 10px; border-radius: 20px; font-size: 12px; font-weight: 600;
        }

        /* Thanh Progress Bar */
        .progress-item { margin-bottom: 15px; }
        .progress-label { display: flex; justify-content: space-between; font-size: 14px; color: #4b5563; margin-bottom: 5px; }
        .progress-bg { width: 100%; height: 8px; background: #d1fae5; border-radius: 5px; overflow: hidden;}
        .progress-fill { height: 100%; background: #10b981; border-radius: 5px; }
    </style>
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
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-utensils"></i> Quản lý món ăn</a></li>
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
            <p>Chào mừng trở lại, ${sessionScope.currentUser.fullName}!</p>
        </div>

        <div class="admin-grid">
            <div class="stat-card">
                <div class="stat-info">
                    <h4>Tổng số người dùng</h4><h2>1,650</h2><p>+12% <span>So với tháng trước</span></p>
                </div>
                <div class="stat-icon bg-blue"><i class="fa-solid fa-users"></i></div>
            </div>
            <div class="stat-card">
                <div class="stat-info">
                    <h4>Tổng món ăn</h4><h2>350</h2><p>+12% <span>So với tháng trước</span></p>
                </div>
                <div class="stat-icon bg-green"><i class="fa-solid fa-utensils"></i></div>
            </div>
            <div class="stat-card">
                <div class="stat-info">
                    <h4>Tổng thực đơn được tạo</h4><h2>4,115</h2><p>+12% <span>So với tháng trước</span></p>
                </div>
                <div class="stat-icon bg-green"><i class="fa-regular fa-calendar-check"></i></div>
            </div>
            <div class="stat-card">
                <div class="stat-info">
                    <h4>Hoạt động/ngày</h4><h2>171</h2><p style="color:#dc2626;">-12% <span>So với tháng trước</span></p>
                </div>
                <div class="stat-icon bg-red"><i class="fa-solid fa-chart-line"></i></div>
            </div>
        </div>


        <div class="bottom-grid">
            <div class="list-card">
                <h3>Top món ăn được yêu thích</h3>
                <div class="food-item"><span>Salad Gà Nướng</span><span class="badge">150 Lượt</span></div>
                <div class="food-item"><span>Trứng luộc</span><span class="badge">120 Lượt</span></div>
                <div class="food-item" style="border:none;"><span>Phở bò</span><span class="badge">90 Lượt</span></div>
            </div>

            <div class="list-card">
                <h3>Mục tiêu phổ biến</h3>
                
                <div class="progress-item">
                    <div class="progress-label"><span>Giảm cân</span><strong>45%</strong></div>
                    <div class="progress-bg"><div class="progress-fill" style="width: 45%;"></div></div>
                </div>
                <div class="progress-item">
                    <div class="progress-label"><span>Tăng cơ</span><strong>30%</strong></div>
                    <div class="progress-bg"><div class="progress-fill" style="width: 30%;"></div></div>
                </div>
                <div class="progress-item">
                    <div class="progress-label"><span>Duy trì</span><strong>25%</strong></div>
                    <div class="progress-bg"><div class="progress-fill" style="width: 25%;"></div></div>
                </div>
            </div>
        </div>
    </main>

 
</body>
</html>