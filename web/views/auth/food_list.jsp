<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gợi ý món ăn</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/food_list.css">
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
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/foods" class="nav-link active">
                    <i class="fa-solid fa-utensils"></i> Món ăn
                </a>
            </li>
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
        <h1>Gợi ý món ăn phù hợp</h1>
        <p class="sub-text">Khám phá các món ăn phù hợp với sức khỏe của bạn<br><br>Có ${foodList.size()} món ăn phù hợp với bạn hôm nay</p>

        <div class="grid">
            <c:forEach var="food" items="${foodList}">
                <a href="food-detail?id=${food.food_id}" class="card">
                    <div class="card-img-wrapper">
                        <img src="${pageContext.request.contextPath}/assets/images/${food.image_url}" alt="${food.food_name}" onerror="this.onerror=null; this.src='https://via.placeholder.com/300x200?text=No+Image'">
                        <span class="badge-safe">✔ An toàn</span>
                    </div>
                    <div class="card-body">
                        <h3 class="card-title">${food.food_name}</h3>
                        <div class="card-calo">${food.displayCalories} calo</div>
                        <div class="card-rating"><i class="fa-solid fa-star"></i> Phù hợp mục tiêu</div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </main>

    <c:if test="${param.success == 'true'}">
        <div id="toast">
            <i class="fa-solid fa-circle-check"></i> Đã lưu công thức thành công!
        </div>

        <script>
            setTimeout(function() {
                var t = document.getElementById("toast");
                if(t) {
                    t.style.opacity = '0';
                    setTimeout(function(){ t.style.display = 'none'; }, 500);
                }
                // Xóa parameter thành công trên URL cho sạch
                window.history.replaceState(null, null, window.location.pathname);
            }, 3000);
        </script>
    </c:if>
</body>
</html>