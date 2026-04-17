<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/profile" class="nav-link">
                    <i class="fa-regular fa-user"></i> Hồ sơ sức khỏe
                </a>
            </li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/foods" class="nav-link"><i class="fa-solid fa-utensils"></i> Món ăn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/meal_plan" class="nav-link"><i class="fa-regular fa-calendar-days"></i> Thực đơn</a></li>
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
                        <h2>1450 <span style="font-size:14px; color:#aaa;">/2000</span></h2>
                    </div>
                    <div class="icon-box"><i class="fa-solid fa-fire-flame-curved"></i></div>
                </div>
                <div class="progress-bar"><div class="progress-fill"></div></div>
                <div class="stat-note">Còn lại 550 calo</div>
            </div>

            <div class="stat-card">
                <div class="stat-card-header">
                    <div>
                        <h3>Chỉ số BMI</h3>
                        <h2>22.5</h2>
                    </div>
                    <div class="icon-box"><i class="fa-solid fa-scale-balanced"></i></div>
                </div>
                <div class="badge">Bình thường</div>
            </div>

            <div class="stat-card">
                <div class="stat-card-header">
                    <div>
                        <h3>BMR</h3>
                        <h2>1650</h2>
                    </div>
                    <div class="icon-box"><i class="fa-solid fa-square-root-variable"></i></div>
                </div>
                <div class="stat-note" style="margin-top: 15px;">Calo cần thiết cơ bản</div>
            </div>

            <div class="stat-card">
                <div class="stat-card-header">
                    <div>
                        <h3>Mục tiêu</h3>
                        <h2>Giảm cân</h2>
                    </div>
                    <div class="icon-box"><i class="fa-solid fa-bullseye"></i></div>
                </div>
                <div class="stat-note" style="margin-top: 15px;">Cập nhật mục tiêu</div>
            </div>
        </section>

        <section>
            <div class="section-header">
                <h2>Bữa ăn hôm nay</h2>
                <a href="${pageContext.request.contextPath}/meal_plan" class="view-all">Xem tất cả ></a>
            </div>
            <div class="meals-grid">
                <div class="meal-card">
                    <div class="meal-time">07</div>
                    <div class="meal-info">
                        <h4>Bữa sáng</h4>
                        <p>450 calo - 2 món</p>
                    </div>
                </div>
                <div class="meal-card">
                    <div class="meal-time">12</div>
                    <div class="meal-info">
                        <h4>Bữa trưa</h4>
                        <p>650 calo - 3 món</p>
                    </div>
                </div>
                <div class="meal-card">
                    <div class="meal-time">18</div>
                    <div class="meal-info">
                        <h4>Bữa tối</h4>
                        <p>150 calo - 1 món</p>
                    </div>
                </div>
            </div>
        </section>

        <section>
            <div class="section-header">
                <h2>Gợi ý cho bạn</h2>
                <a href="${pageContext.request.contextPath}/foods" class="view-all">Xem thêm ></a>
            </div>
            <div class="suggest-grid">
                <div class="food-card">
                    <img src="https://images.unsplash.com/photo-1512621776951-a57141f2eefd?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=60" alt="Salad" class="food-img">
                    <button class="heart-btn"><i class="fa-regular fa-heart"></i></button>
                    <div class="status-badge bg-safe"><i class="fa-solid fa-check"></i> An toàn</div>
                    <div class="food-content">
                        <h4>Salad gà nướng</h4>
                        <p>350 calo</p>
                        <div class="food-footer">
                            <i class="fa-regular fa-star"></i>
                            <span>- Phù hợp mục tiêu giảm cân</span>
                        </div>
                    </div>
                </div>

                <div class="food-card">
                    <img src="https://images.unsplash.com/photo-1547592180-85f173990554?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=60" alt="Soup" class="food-img">
                    <button class="heart-btn"><i class="fa-regular fa-heart"></i></button>
                    <div class="status-badge bg-safe"><i class="fa-solid fa-check"></i> An toàn</div>
                    <div class="food-content">
                        <h4>Súp bí đỏ</h4>
                        <p>180 calo</p>
                        <div class="food-footer">
                            <i class="fa-regular fa-star"></i>
                            <span>- Giàu chất xơ, ít calo</span>
                        </div>
                    </div>
                </div>

                <div class="food-card">
                    <img src="https://images.unsplash.com/photo-1467003909585-2f8a72700288?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=60" alt="Salmon" class="food-img">
                    <button class="heart-btn"><i class="fa-regular fa-heart"></i></button>
                    <div class="status-badge bg-warn"><i class="fa-solid fa-shield-halved"></i> Lưu ý</div>
                    <div class="food-content">
                        <h4>Cá hồi nướng</h4>
                        <p>350 calo</p>
                        <div class="food-footer">
                            <i class="fa-regular fa-star"></i>
                            <span>- Giàu Omega -3</span>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </main>

</body>
</html>