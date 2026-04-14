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

    <style>
        /* CSS riêng cho phần thẻ món ăn (Card) bên phải */
        .main-content { flex: 1; padding: 40px; background-color: #f4f7f6; min-height: 100vh;}
        h1 { color: #1a1a1a; margin-top: 0;}
        .sub-text { color: #666; margin-bottom: 30px; }
        
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 30px; }
        .card { background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.05); cursor: pointer; transition: 0.3s; text-decoration: none; color: inherit; display: block; }
        .card:hover { transform: translateY(-5px); box-shadow: 0 8px 15px rgba(0,0,0,0.1); }
        .card-img-wrapper { position: relative; height: 180px; }
        .card-img-wrapper img { width: 100%; height: 100%; object-fit: cover; }
        .badge-safe { position: absolute; top: 10px; right: 10px; background: #1cc865; color: white; padding: 4px 10px; border-radius: 12px; font-size: 12px; }
        
        .card-body { padding: 20px; }
        .card-title { font-size: 18px; font-weight: bold; margin: 0 0 5px; }
        .card-calo { color: #888; font-size: 14px; margin-bottom: 15px;}
        .card-rating { font-size: 14px; color: #1cc865; display: flex; align-items: center; gap: 5px; }
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
                       <div class="card-calo">${food.displayCalories} calo
                    </div>
                        <div class="card-rating"><i class="fa-solid fa-star"></i> Phù hợp mục tiêu</div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </main>
    <c:if test="${param.success == 'true'}">
        <style>
            #toast {
                min-width: 250px; background-color: #10b981; color: #fff;
                text-align: center; border-radius: 8px; padding: 16px;
                position: fixed; z-index: 1000; right: 30px; top: 30px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.2);
                transition: opacity 0.5s ease-out; 
            }
        </style>

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
                
                window.history.replaceState(null, null, window.location.pathname);
            }, 3000);
        </script>
    </c:if>
</body>
</html>