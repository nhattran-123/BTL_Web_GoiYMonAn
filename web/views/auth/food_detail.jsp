<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết món ăn</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
    
    <style>
        /* 2. Chỉ giữ lại CSS cho phần nội dung chính bên phải, xóa CSS sidebar cũ đi */
        .main-content { flex: 1; padding: 40px; max-width: 900px; margin: 0 auto; background-color: #f4f7f6; min-height: 100vh;}
        .btn-back { color: #666; text-decoration: none; margin-bottom: 20px; display: inline-block; font-weight: bold;}
        
        .hero-image { width: 100%; height: 350px; object-fit: cover; border-radius: 12px; margin-bottom: 20px; }
        
        .action-bar { display: flex; gap: 15px; margin-bottom: 30px; }
        .btn { flex: 1; padding: 15px; border-radius: 8px; font-size: 16px; font-weight: bold; text-align: center; cursor: pointer; border: none; }
        .btn-like { background: white; color: #333; border: 1px solid #ddd; }
        .btn-add { background: #1cc865; color: white; }
        
        .section { background: white; padding: 25px; border-radius: 12px; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.03); }
        .section-title { margin-top: 0; border-bottom: 1px solid #eee; padding-bottom: 10px; margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center;}
        
        .macro-grid { display: flex; gap: 20px; margin-bottom: 20px;}
        .macro-box { flex: 1; background: #eefaf3; padding: 15px; border-radius: 8px; text-align: center;}
        .macro-value { font-size: 20px; font-weight: bold; color: #2e4a3b;}
        .macro-label { font-size: 14px; color: #666;}
        
        .btn-small { background: #1cc865; color: white; padding: 6px 15px; text-decoration: none; border-radius: 6px; font-size: 14px;}
    </style>
</head>
<body style="display: flex; margin: 0;">

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
                <a href="${pageContext.request.contextPath}/home" class="nav-link">
                    <i class="fa-solid fa-house"></i> Trang chủ
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/profile" class="nav-link">
                    <i class="fa-regular fa-user"></i> Hồ sơ sức khỏe
                </a>
            </li>
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
        <a href="${pageContext.request.contextPath}/foods" class="btn-back">← Quay lại danh sách</a>
        
        <img src="${pageContext.request.contextPath}/assets/images/${food.image_url}" alt="${food.food_name}" class="hero-image" onerror="this.src='https://via.placeholder.com/900x350?text=No+Image'">
        
        <div class="action-bar">
            <button class="btn btn-like">♡ Thích</button>
            <button class="btn btn-add">+ Thêm vào thực đơn</button>
        </div>

        <div class="section">
            <h2 style="margin-top:0">${food.food_name}</h2>
            <p style="color: #555; line-height: 1.6;">${food.description}</p>
        </div>

        <div class="section">
            <h3 class="section-title">Thành phần dinh dưỡng</h3>
            <div class="macro-grid">
                 <div class="macro-box">
                    <div class="macro-value" style="color: ${food.customized ? '#059669' : '#2e4a3b'};">${food.displayCalories}
                 </div>
                    <div class="macro-label">Calo <c:if test="${food.customized}"><i></i></c:if></div>
                </div>
                <div class="macro-box"><div class="macro-value">${food.protein}g</div><div class="macro-label">Protein</div></div>
                <div class="macro-box"><div class="macro-value">${food.fat}g</div><div class="macro-label">Chất béo</div></div>
                <div class="macro-box"><div class="macro-value">${food.carbohydrate}g</div><div class="macro-label">Carbs</div></div>
            </div>
        </div>

        <div class="section">
            <h3 class="section-title">
                Nguyên liệu
                <a href="${pageContext.request.contextPath}/customize-recipe?foodId=${food.food_id}" class="btn-small">Điều chỉnh</a>
            </h3>
            <p><i>(Phần này bạn có thể gọi thêm IngredientDAO để list nguyên liệu ra. Tạm thời mình ẩn để tập trung giao diện)</i></p>
        </div>

        <div class="section">
            <h3 class="section-title">Cách làm</h3>
            <p style="white-space: pre-line; line-height: 1.8; color: #444;">${food.recipe}</p>
        </div>
    </main>
</body>
</html>