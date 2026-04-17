<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thực đơn - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/meal_plan.css">
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
            <li class="nav-item"><a href="${pageContext.request.contextPath}/meal_plan" class="nav-link active"><i class="fa-regular fa-calendar-days"></i> Thực đơn</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-chart-line"></i> Tiến trình</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-regular fa-heart"></i> Yêu thích</a></li>
            <li class="nav-item"><a href="#" class="nav-link"><i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/settings" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </aside>

    <main class="main-content meal-page">
        <header class="meal-header">
            <div>
                <h1>Thực đơn</h1>
                <p>Dữ liệu hiển thị theo ngày bạn chọn từ cơ sở dữ liệu.</p>
            </div>
            <div class="calo-badge"><i class="fa-solid fa-fire-flame-curved"></i> <fmt:formatNumber value="${totalCalories}" minFractionDigits="2" maxFractionDigits="2" /> calo</div>
        </header>

        <section class="card slider-card">
            <a class="slider-nav" href="${pageContext.request.contextPath}/meal_plan?date=${weekSlider[0].date}">&lt;</a>
            <div class="week-track">
                <c:forEach var="d" items="${weekSlider}">
                    <a href="${pageContext.request.contextPath}/meal_plan?date=${d.date}" class="day-pill ${d.selected ? 'active' : ''}">
                        <span class="dow">${d.dow}</span>
                        <strong>${d.day}</strong>
                        <span class="dot ${d.hasMeals ? 'has-meal' : ''}"></span>
                    </a>
                </c:forEach>
            </div>
            <a class="slider-nav" href="${pageContext.request.contextPath}/meal_plan?date=${weekSlider[6].date}">&gt;</a>
        </section>

        <section class="card filter-card">
            <form method="get" action="${pageContext.request.contextPath}/meal_plan" class="date-form">
                <label for="date">Ngày thực đơn</label>
                <input id="date" type="date" name="date" value="${selectedDate}">
                <button type="submit" class="btn-secondary">Xem</button>
            </form>
        </section>

        <section class="meal-list">
            <c:forEach var="section" items="${mealSections}">
                <article class="card meal-card-item"
                         data-meal-id="${section.mealTypeId}"
                         data-meal-name="${section.mealName}"
                         data-target-calories="${section.targetCalories}">
                    <div class="meal-row-head">
                        <div class="meal-title"><i class="fa-regular fa-sun"></i> ${section.mealName}</div>
                        <div class="meal-meta">
                            <fmt:formatNumber value="${section.usedCalories}" minFractionDigits="2" maxFractionDigits="2" />
                            /
                            <fmt:formatNumber value="${section.targetCalories}" minFractionDigits="2" maxFractionDigits="2" /> calo
                            <button type="button" class="text-btn open-adjust">Điều chỉnh</button>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${empty section.foods}">
                            <p class="empty-text">Chưa có món nào trong bữa này.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="food" items="${section.foods}">
                                <div class="food-pill" data-food-id="${food.foodId}" data-food-calories="${food.calories}" data-food-image="${food.imageUrl}">
                                    <img src="${pageContext.request.contextPath}/assets/images/${food.imageUrl}" alt="${food.foodName}">
                                    <div>
                                        <h4>${food.foodName}</h4>
                                        <p><fmt:formatNumber value="${food.calories}" minFractionDigits="2" maxFractionDigits="2" /> calo</p>
                                    </div>
                                    <form method="post" action="${pageContext.request.contextPath}/meal_plan" class="remove-form">
                                        <input type="hidden" name="action" value="removeDetail">
                                        <input type="hidden" name="selectedDate" value="${selectedDate}">
                                        <input type="hidden" name="detailId" value="${food.detailId}">
                                        <button type="submit" class="remove-btn" title="Xóa món">x</button>
                                    </form>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </article>
            </c:forEach>
        </section>

        <div class="add-wrapper">
            <button id="openAddMeal" class="btn-add"><i class="fa-solid fa-plus"></i> Thêm bữa ăn</button>
        </div>
    </main>

    <div class="modal" id="adjustModal">
        <div class="modal-card adjust-modal-card">
            <button type="button" class="close-modal"><i class="fa-solid fa-xmark"></i></button>
            <h3 id="adjustTitle">Điều chỉnh món ăn</h3>
            <form method="post" action="${pageContext.request.contextPath}/meal_plan">
                <input type="hidden" name="action" value="addMealFoods">
                <input type="hidden" name="selectedDate" value="${selectedDate}">
                <input type="hidden" id="adjustMealTypeId" name="mealTypeId">
                <div class="adjust-search">
                    <input id="adjustSearchInput" type="text" placeholder="Tìm kiếm món ăn...">
                    <button id="adjustSearchBtn" type="button" aria-label="Tìm kiếm món ăn"><i class="fa-solid fa-magnifying-glass"></i></button>
                </div>
                <div id="adjustSearchResult" class="adjust-search-result" hidden></div>
                <div class="adjust-selected-header">
                    <h4>Món ăn đã chọn</h4>
                    <span id="adjustCaloriesMeta">0 / 0 calo</span>
                </div>
                <div id="adjustSelectedList" class="adjust-selected-list"></div>
                <div id="adjustHiddenInputs"></div>
                <select id="adjustFoodSource" hidden>
                    <c:forEach var="food" items="${allFoods}">
                        <option value="${food.food_id}"
                                data-name="<c:out value='${food.food_name}'/>"
                                data-calories="${food.calories}"
                                data-image="<c:out value='${food.imageUrl}'/>"></option>
                    </c:forEach>
                </select>
                <button class="btn-save" type="submit"><i class="fa-regular fa-floppy-disk"></i> Lưu thay đổi</button>
            </form>
        </div>
    </div>

    <div class="modal" id="addModal">
        <div class="modal-card">
            <button type="button" class="close-modal"><i class="fa-solid fa-xmark"></i></button>
            <h3>Thêm món vào bữa ăn</h3>
            <form method="post" action="${pageContext.request.contextPath}/meal_plan">
                <input type="hidden" name="action" value="addMealFoods">
                <input type="hidden" name="selectedDate" value="${selectedDate}">

                <div class="form-group">
                    <label>Bữa ăn</label>
                    <select name="mealTypeId" required>
                        <option value="">-- Chọn bữa ăn --</option>
                        <c:forEach var="section" items="${mealSections}">
                            <option value="${section.mealTypeId}">${section.mealName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Món ăn (có thể chọn nhiều)</label>
                    <select name="foodIds" multiple required size="8" class="multi-select">
                        <c:forEach var="food" items="${allFoods}">
                            <option value="${food.food_id}">${food.food_name} (<fmt:formatNumber value="${food.calories}" minFractionDigits="2" maxFractionDigits="2" /> calo)</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="actions-row">
                    <button class="btn-save" type="submit">Lưu bữa ăn</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        const adjustModal = document.getElementById('adjustModal');
        const addModal = document.getElementById('addModal');
        const openAddMealBtn = document.getElementById('openAddMeal');
        const adjustSearchBtn = document.getElementById('adjustSearchBtn');
        const adjustTitle = document.getElementById('adjustTitle');
        const adjustMealTypeId = document.getElementById('adjustMealTypeId');
        const adjustSearchInput = document.getElementById('adjustSearchInput');
        const adjustSearchResult = document.getElementById('adjustSearchResult');
        const adjustSelectedList = document.getElementById('adjustSelectedList');
        const adjustCaloriesMeta = document.getElementById('adjustCaloriesMeta');
        const adjustHiddenInputs = document.getElementById('adjustHiddenInputs');
        const adjustForm = adjustModal ? adjustModal.querySelector('form') : null;

        const allFoods = Array.from(document.querySelectorAll('#adjustFoodSource option')).map((option) => ({
            id: Number(option.value),
            name: option.dataset.name || '',
            calories: Number(option.dataset.calories || 0),
            image: option.dataset.image || ''
        }));

        let selectedFoods = [];
        let currentTargetCalories = 0;

        const renderSelectedFoods = () => {
            adjustSelectedList.innerHTML = '';

            if (selectedFoods.length === 0) {
                adjustSelectedList.innerHTML = '<p class="empty-text">Chưa có món nào được chọn.</p>';
            } else {
                selectedFoods.forEach((food) => {
                    const item = document.createElement('div');
                    item.className = 'food-pill';
                    item.innerHTML =
                        '<img src="${pageContext.request.contextPath}/assets/images/' + food.image + '" alt="' + food.name + '">' +
                        '<div>' +
                            '<h4>' + food.name + '</h4>' +
                            '<p>' + Number(food.calories).toFixed(2) + ' calo</p>' +
                        '</div>' +
                        '<button type="button" class="remove-btn" data-remove-id="' + food.id + '" title="Xóa món">x</button>';
                    adjustSelectedList.appendChild(item);
                });
            }

            const totalCalories = selectedFoods.reduce((sum, food) => sum + Number(food.calories), 0);
            adjustCaloriesMeta.textContent = totalCalories.toFixed(2) + ' / ' + Number(currentTargetCalories).toFixed(2) + ' calo';

            adjustHiddenInputs.innerHTML = selectedFoods
                .map((food) => '<input type="hidden" name="foodIds" value="' + food.id + '">')
                .join('');
        };

        const renderSearchResult = (keyword = '') => {
            const normalized = keyword.trim().toLowerCase();
            const selectedIds = new Set(selectedFoods.map((food) => Number(food.id)));
            const matchedFoods = allFoods
                .filter((food) => {
                    const notSelected = !selectedIds.has(Number(food.id));
                    if (!notSelected) return false;
                    if (!normalized) return true;
                    return food.name.toLowerCase().includes(normalized);
                })
                .slice(0, 6);

            if (matchedFoods.length === 0) {
                adjustSearchResult.hidden = false;
                adjustSearchResult.innerHTML = '<p class="empty-text">Không tìm thấy món phù hợp.</p>';
                return;
            }

            adjustSearchResult.hidden = false;
            adjustSearchResult.innerHTML = matchedFoods.map((food) =>
                '<button type="button" class="search-food-item" data-food-id="' + food.id + '">' +
                    '<span>' + food.name + '</span>' +
                    '<small>' + Number(food.calories).toFixed(2) + ' calo</small>' +
                '</button>'
            ).join('');
        };

        document.querySelectorAll('.open-adjust').forEach((btn) => {
            btn.addEventListener('click', (e) => {
                if (!adjustModal) return;
                const card = e.target.closest('.meal-card-item');
                adjustMealTypeId.value = card.dataset.mealId;
                adjustTitle.textContent = 'Điều chỉnh món ăn cho ' + card.dataset.mealName.toLowerCase();
                currentTargetCalories = card.dataset.targetCalories || 0;

                selectedFoods = Array.from(card.querySelectorAll('.food-pill')).map((pill) => {
                    const id = Number(pill.dataset.foodId);
                    return {
                        id,
                        name: pill.querySelector('h4')?.textContent?.trim() || '',
                        calories: Number(pill.dataset.foodCalories || 0),
                        image: pill.dataset.foodImage || ''
                    };
                });

                adjustSearchInput.value = '';
                adjustSearchResult.hidden = true;
                adjustSearchResult.innerHTML = '';
                renderSelectedFoods();
                renderSearchResult();
                adjustModal.classList.add('show');
            });
        });

        if (openAddMealBtn && addModal) {
            openAddMealBtn.addEventListener('click', () => addModal.classList.add('show'));
        }
        document.querySelectorAll('.close-modal').forEach((btn) => {
            btn.addEventListener('click', () => btn.closest('.modal').classList.remove('show'));
        });

        [adjustModal, addModal].filter(Boolean).forEach((modal) => {
            modal.addEventListener('click', (e) => {
                if (e.target === modal) modal.classList.remove('show');
            });
        });

        if (adjustSearchBtn) {
            adjustSearchBtn.addEventListener('click', () => {
                renderSearchResult(adjustSearchInput.value);
            });
        }

        if (adjustSearchInput) {
            adjustSearchInput.addEventListener('input', () => {
                renderSearchResult(adjustSearchInput.value);
            });
            adjustSearchInput.addEventListener('focus', () => {
                renderSearchResult(adjustSearchInput.value);
            });
        }

        if (adjustSearchResult) {
            adjustSearchResult.addEventListener('click', (e) => {
                const btn = e.target.closest('.search-food-item');
                if (!btn) return;
                const selectedId = Number(btn.dataset.foodId);
                const food = allFoods.find((item) => Number(item.id) === selectedId);
                if (!food) return;
                if (!selectedFoods.some((item) => Number(item.id) === selectedId)) {
                    selectedFoods.push(food);
                    renderSelectedFoods();
                }
                adjustSearchInput.value = '';
                adjustSearchResult.hidden = true;
                adjustSearchResult.innerHTML = '';
            });
        }

        if (adjustSelectedList) {
            adjustSelectedList.addEventListener('click', (e) => {
                const removeBtn = e.target.closest('[data-remove-id]');
                if (!removeBtn) return;
                const removeId = Number(removeBtn.dataset.removeId);
                selectedFoods = selectedFoods.filter((food) => Number(food.id) !== removeId);
                renderSelectedFoods();
            });
        }

        if (adjustForm) {
            adjustForm.addEventListener('submit', (e) => {
                if (selectedFoods.length === 0) {
                    e.preventDefault();
                    alert('Vui lòng chọn ít nhất một món ăn.');
                }
            });
        }
    </script>
</body>
</html>
