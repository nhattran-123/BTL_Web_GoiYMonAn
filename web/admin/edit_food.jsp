<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa món ăn - Healthy Food</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <style>
        .form-card { background: white; padding: 40px; border-radius: 12px; box-shadow: 0 2px 10px rgba(0,0,0,0.02); margin-top: 10px;}
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; margin-bottom: 25px;}
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; margin-bottom: 10px; font-weight: 500; color: #374151; font-size: 14px;}
        .form-control { width: 100%; padding: 12px 15px; border: 1px solid #e5e7eb; border-radius: 8px; box-sizing: border-box; outline: none; font-family: 'Inter', sans-serif;}
        .form-control:focus { border-color: #10b981; }

        .macro-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin-bottom: 25px;}
        .macro-input { background: #f9fafb; color: #111827; font-weight: bold; pointer-events: none;}

        .ingredient-row { display: grid; grid-template-columns: 2fr 1fr 1fr auto; gap: 15px; margin-bottom: 15px; align-items: center;}
        .btn-remove-row { background: #fee2e2; color: #dc2626; border: none; width: 42px; height: 42px; border-radius: 8px; cursor: pointer; display: flex; justify-content: center; align-items: center; font-size: 16px;}

        .select2-container .select2-selection--single { height: 42px; padding: 6px; border: 1px solid #e5e7eb; border-radius: 8px; }

        .upload-container { display: flex; align-items: center; gap: 15px; flex-wrap: wrap; }
        .btn-upload { background: #f3f4f6; border: 1px solid #e5e7eb; padding: 10px 15px; border-radius: 8px; cursor: pointer; font-size: 13px; font-weight: 500; color: #4b5563; display: inline-flex; align-items: center; gap: 8px;}
        .btn-upload:hover { background: #e5e7eb; }
        #fileNameDisplay { font-size: 13px; color: #10b981; font-style: italic; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 250px;}
        .preview-image { width: 60px; height: 60px; border-radius: 10px; object-fit: cover; border: 1px solid #e5e7eb; }

        .error-border { border: 1px solid #dc2626 !important; }
    </style>
</head>
<body>

    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon"><i class="fa-solid fa-leaf text-white"></i></div>
            <div class="brand-text"><h2>Healthy Food</h2><span>Dinh dưỡng thông minh</span></div>
        </div>
        <ul class="nav-menu">
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link"><i class="fa-solid fa-table-cells-large"></i> Tổng quan</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/users" class="nav-link"><i class="fa-regular fa-address-book"></i> Quản lý người dùng</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage_food" class="nav-link active"><i class="fa-solid fa-utensils"></i> Quản lý món ăn</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage_ingredient" class="nav-link"><i class="fa-solid fa-apple-whole"></i> Quản lý nguyên liệu</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/manage-disease" class="nav-link"><i class="fa-solid fa-heart-pulse"></i> Quản lý bệnh lý</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/admin/settings.jsp" class="nav-link"><i class="fa-solid fa-gear"></i> Cài đặt</a></li>
        </ul>
        <div class="logout-box"><a href="${pageContext.request.contextPath}/logout" class="nav-link"><i class="fa-solid fa-arrow-right-from-bracket"></i> Đăng xuất</a></div>
    </aside>

    <main class="main-content">

        <form action="${pageContext.request.contextPath}/admin/edit_food" method="POST" id="editFoodForm" enctype="multipart/form-data">
            <input type="hidden" name="foodId" value="${food.food_id}">

            <div class="form-card">
                <h2 style="margin: 0 0 5px 0; font-size: 20px; color: #111827;">Sửa món ăn</h2>
                <p style="color: #6b7280; font-size: 14px; margin-bottom: 30px;">Cập nhật thông tin chi tiết của món ăn</p>

                <c:if test="${param.error == 'true'}">
                    <div style="background: #fee2e2; color: #dc2626; padding: 12px 15px; border-radius: 8px; margin-bottom: 20px;">
                        <i class="fa-solid fa-circle-exclamation"></i> Cập nhật thất bại, vui lòng thử lại.
                    </div>
                </c:if>

                <div class="form-grid">
                    <div class="form-group" style="margin-bottom:0;">
                        <label>Tên món ăn <span style="color:red">*</span></label>
                        <input type="text" name="name" class="form-control" value="${food.food_name}" required>
                    </div>

                    <div class="form-group" style="margin-bottom:0;">
                        <label>Hình ảnh mới (không bắt buộc)</label>
                        <div class="upload-container">
                            <c:if test="${not empty food.image_url}">
                                <img class="preview-image" src="${pageContext.request.contextPath}/assets/images/${food.image_url}" alt="${food.food_name}">
                            </c:if>
                            <label class="btn-upload">
                                <i class="fa-solid fa-upload"></i> Tải file lên
                                <input type="file" name="imageFile" accept="image/*" style="display: none;" onchange="document.getElementById('fileNameDisplay').innerText = this.files.length ? this.files[0].name : 'Giữ ảnh hiện tại'; document.getElementById('fileNameDisplay').style.color = '#10b981';">
                            </label>
                            <span id="fileNameDisplay">Giữ ảnh hiện tại</span>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label>Mô tả <span style="color:red">*</span></label>
                    <textarea name="description" class="form-control" rows="3" required>${food.description}</textarea>
                </div>

                <div class="macro-grid">
                    <div class="form-group" style="margin:0;">
                        <label>Calo</label>
                        <input type="number" step="0.1" name="calories" id="totalCal" class="form-control macro-input" readonly value="${food.calories}">
                    </div>
                    <div class="form-group" style="margin:0;">
                        <label>Protein (g)</label>
                        <input type="number" step="0.1" name="protein" id="totalPro" class="form-control macro-input" readonly value="${food.protein}">
                    </div>
                    <div class="form-group" style="margin:0;">
                        <label>Chất béo (g)</label>
                        <input type="number" step="0.1" name="fat" id="totalFat" class="form-control macro-input" readonly value="${food.fat}">
                    </div>
                    <div class="form-group" style="margin:0;">
                        <label>Carbs (g)</label>
                        <input type="number" step="0.1" name="carbohydrate" id="totalCarb" class="form-control macro-input" readonly value="${food.carbohydrate}">
                    </div>
                </div>

                <div class="form-group">
                    <label>Công thức <span style="color:red">*</span></label>
                    <textarea name="recipe" class="form-control" rows="4" required>${food.recipe}</textarea>
                </div>

                <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 40px; margin-bottom: 20px;">
                    <h3 style="margin: 0; font-size: 16px;">Nguyên liệu <span style="color:red">*</span></h3>
                    <button type="button" id="btnAddRow" style="background: white; border: 1px solid #e5e7eb; color: #374151; padding: 8px 15px; border-radius: 8px; cursor: pointer; font-size: 13px; font-weight: 500;">
                        <i class="fa-solid fa-plus"></i> Thêm nguyên liệu
                    </button>
                </div>

                <div id="ingredient-container">
                    <div class="ingredient-row template-row" style="display:none;">
                        <select name="ingredientId[]" class="form-control ing-select" disabled>
                            <option value="">Tên nguyên liệu</option>
                            <c:forEach items="${ingredients}" var="ing">
                                <option value="${ing.id}" data-cal="${ing.calories}" data-pro="${ing.protein}" data-fat="${ing.fat}" data-carb="${ing.carbohydrate}">
                                    ${ing.name} (${ing.category})
                                </option>
                            </c:forEach>
                        </select>
                        <input type="number" name="quantity[]" class="form-control ing-qty" placeholder="Số lượng" step="0.1" min="0.1" disabled>
                        <select name="unit[]" class="form-control" disabled>
                            <option value="g">g</option>
                            <option value="ml">ml</option>
                        </select>
                        <button type="button" class="btn-remove-row"><i class="fa-solid fa-trash-can"></i></button>
                    </div>

                    <c:forEach items="${foodIngredients}" var="fi">
                        <div class="ingredient-row">
                            <select name="ingredientId[]" class="form-control ing-select">
                                <option value="">Tên nguyên liệu</option>
                                <c:forEach items="${ingredients}" var="ing">
                                    <option value="${ing.id}" data-cal="${ing.calories}" data-pro="${ing.protein}" data-fat="${ing.fat}" data-carb="${ing.carbohydrate}" ${ing.id == fi.ingredientId ? 'selected' : ''}>
                                        ${ing.name} (${ing.category})
                                    </option>
                                </c:forEach>
                            </select>
                            <input type="number" name="quantity[]" class="form-control ing-qty" placeholder="Số lượng" step="0.1" min="0.1" value="${fi.quantity}">
                            <select name="unit[]" class="form-control">
                                <option value="g" ${fi.unit == 'g' ? 'selected' : ''}>g</option>
                                <option value="ml" ${fi.unit == 'ml' ? 'selected' : ''}>ml</option>
                            </select>
                            <button type="button" class="btn-remove-row"><i class="fa-solid fa-trash-can"></i></button>
                        </div>
                    </c:forEach>
                </div>

                <div style="text-align: right; margin-top: 40px; border-top: 1px solid #f3f4f6; padding-top: 20px;">
                    <a href="${pageContext.request.contextPath}/admin/manage_food" style="padding: 10px 20px; color: #4b5563; text-decoration: none; margin-right: 10px; font-weight: 500;">Hủy</a>
                    <button type="submit" style="background: #10b981; color: white; padding: 12px 30px; border: none; border-radius: 8px; font-size: 14px; font-weight: 600; cursor: pointer;">
                        Lưu cập nhật
                    </button>
                </div>
            </div>
        </form>
    </main>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

    <script>
        $(document).ready(function() {
            function calculateMacros() {
                let totalCal = 0, totalPro = 0, totalFat = 0, totalCarb = 0;
                $('.ingredient-row:not(.template-row)').each(function() {
                    let $selectedOption = $(this).find('.ing-select option:selected');
                    let qty = parseFloat($(this).find('.ing-qty').val()) || 0;
                    if($selectedOption.val()) {
                        let ratio = qty / 100;
                        totalCal += ratio * parseFloat($selectedOption.data('cal') || 0);
                        totalPro += ratio * parseFloat($selectedOption.data('pro') || 0);
                        totalFat += ratio * parseFloat($selectedOption.data('fat') || 0);
                        totalCarb += ratio * parseFloat($selectedOption.data('carb') || 0);
                    }
                });
                $('#totalCal').val(totalCal.toFixed(1));
                $('#totalPro').val(totalPro.toFixed(1));
                $('#totalFat').val(totalFat.toFixed(1));
                $('#totalCarb').val(totalCarb.toFixed(1));
            }

            $('.ing-select').select2({ placeholder: "Tìm tên nguyên liệu...", width: '100%' });

            $('#btnAddRow').click(function() {
                let $newRow = $('.template-row').clone().removeClass('template-row').show();
                $newRow.find('input, select').removeAttr('disabled');
                $('#ingredient-container').append($newRow);
                $newRow.find('.ing-select').select2({ placeholder: "Tìm tên nguyên liệu...", width: '100%' });
            });

            $('#ingredient-container').on('change', '.ing-select', function() {
                $(this).next('.select2-container').find('.select2-selection').removeClass('error-border');
                calculateMacros();
            });
            $('#ingredient-container').on('input', '.ing-qty', function() {
                $(this).removeClass('error-border');
                calculateMacros();
            });

            $('#ingredient-container').on('click', '.btn-remove-row', function() {
                $(this).closest('.ingredient-row').remove();
                calculateMacros();
            });

            if ($('.ingredient-row:not(.template-row)').length === 0) {
                $('#btnAddRow').click();
            }

            calculateMacros();

            $('#editFoodForm').on('submit', function(e) {
                let isValid = true;
                let activeRows = $('.ingredient-row:not(.template-row)');

                if (activeRows.length === 0) {
                    alert('Vui lòng thêm ít nhất 1 nguyên liệu cho món ăn!');
                    isValid = false;
                } else {
                    activeRows.each(function() {
                        let selectVal = $(this).find('.ing-select').val();
                        let qtyVal = $(this).find('.ing-qty').val();

                        if (!selectVal) {
                            $(this).find('.select2-selection').addClass('error-border');
                            isValid = false;
                        }
                        if (!qtyVal || qtyVal <= 0) {
                            $(this).find('.ing-qty').addClass('error-border');
                            isValid = false;
                        }
                    });

                    if (!isValid) {
                        alert('Vui lòng điền đầy đủ tên và số lượng nguyên liệu!');
                    }
                }

                if (!isValid) {
                    e.preventDefault();
                }
            });
        });
    </script>
</body>
</html>
