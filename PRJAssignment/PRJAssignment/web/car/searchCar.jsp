<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Kết Quả Tìm Kiếm - DriveDreams</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f5f5f5;
            }

            /* Header Styles */
            .header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                padding: 15px 0;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }

            .header-content {
                max-width: 1200px;
                margin: 0 auto;
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 0 20px;
            }

            .logo {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .logo h1 {
                color: white;
                font-size: 28px;
                font-weight: 300;
                font-style: italic;
            }

            .logo-icon {
                width: 40px;
                height: 40px;
                background: rgba(255,255,255,0.2);
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 20px;
            }

            .auth-links {
                display: flex;
                gap: 15px;
                align-items: center;
            }

            .auth-links a {
                color: white;
                text-decoration: none;
                padding: 8px 16px;
                border-radius: 20px;
                transition: background-color 0.3s;
            }

            .auth-links a:hover {
                background-color: rgba(255,255,255,0.2);
            }

            /* Search Section - Tương tự home.jsp */
            .search-section {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                padding: 40px 0;
                position: relative;
                overflow: hidden;
            }

            .search-section::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: radial-gradient(circle at 30% 20%, rgba(255,255,255,0.1) 0%, transparent 50%),
                    radial-gradient(circle at 70% 80%, rgba(255,255,255,0.05) 0%, transparent 50%);
                pointer-events: none;
            }

            .search-container {
                max-width: 900px;
                margin: 0 auto;
                text-align: center;
                position: relative;
                z-index: 2;
                padding: 0 20px;
            }

            .search-title {
                color: white;
                font-size: 32px;
                font-weight: 300;
                margin-bottom: 10px;
                text-shadow: 0 2px 4px rgba(0,0,0,0.3);
                letter-spacing: -1px;
            }

            .search-subtitle {
                color: rgba(255,255,255,0.9);
                font-size: 16px;
                margin-bottom: 30px;
                font-weight: 300;
                line-height: 1.6;
            }

            .search-form {
                display: flex;
                justify-content: center;
                gap: 0;
                margin-bottom: 25px;
                box-shadow: 0 15px 35px rgba(0,0,0,0.2);
                border-radius: 50px;
                overflow: hidden;
                background: white;
                max-width: 650px;
                margin-left: auto;
                margin-right: auto;
                margin-bottom: 25px;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .search-form:hover {
                transform: translateY(-3px);
                box-shadow: 0 20px 40px rgba(0,0,0,0.25);
            }

            .search-input {
                flex: 1;
                padding: 18px 25px;
                border: none;
                font-size: 16px;
                outline: none;
                background: transparent;
                color: #333;
                font-weight: 400;
            }

            .search-input::placeholder {
                color: #999;
                font-style: italic;
            }

            .search-btn {
                padding: 18px 35px;
                background: linear-gradient(135deg, #ff6b6b, #ee5a24);
                color: white;
                border: none;
                font-size: 16px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
                position: relative;
                overflow: hidden;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .search-btn::before {
                content: '';
                position: absolute;
                top: 0;
                left: -100%;
                width: 100%;
                height: 100%;
                background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
                transition: left 0.6s;
            }

            .search-btn:hover::before {
                left: 100%;
            }

            .search-btn:hover {
                background: linear-gradient(135deg, #ff5252, #d84315);
                transform: translateY(-1px);
            }

            /* Advanced Search Form */
            .advanced-search-form {
                background: rgba(255,255,255,0.1);
                border-radius: 15px;
                padding: 25px;
                backdrop-filter: blur(10px);
                border: 1px solid rgba(255,255,255,0.2);
                margin-top: 20px;
            }

            .advanced-search-title {
                color: white;
                font-size: 18px;
                font-weight: 500;
                margin-bottom: 20px;
                text-align: center;
            }

            .filter-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 15px;
                margin-bottom: 20px;
            }

            .filter-group {
                display: flex;
                flex-direction: column;
                gap: 5px;
            }

            .filter-group label {
                color: rgba(255,255,255,0.9);
                font-size: 14px;
                font-weight: 500;
            }

            .filter-group select {
                padding: 10px 12px;
                border: 1px solid rgba(255,255,255,0.3);
                border-radius: 8px;
                background: rgba(255,255,255,0.9);
                color: #333;
                font-size: 14px;
                transition: all 0.3s ease;
            }

            .filter-group select:focus {
                outline: none;
                border-color: #ff6b6b;
                box-shadow: 0 0 0 2px rgba(255, 107, 107, 0.2);
            }

            .range-group {
                display: flex;
                gap: 8px;
                align-items: center;
            }

            .range-group select {
                flex: 1;
            }

            .range-separator {
                color: rgba(255,255,255,0.9);
                font-weight: 500;
            }

            .filter-actions {
                display: flex;
                justify-content: center;
                gap: 15px;
            }

            .filter-btn, .reset-btn {
                padding: 12px 25px;
                border: none;
                border-radius: 25px;
                font-size: 14px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .filter-btn {
                background: linear-gradient(135deg, #ff6b6b, #ee5a24);
                color: white;
            }

            .filter-btn:hover {
                background: linear-gradient(135deg, #ff5252, #d84315);
                transform: translateY(-2px);
            }

            .reset-btn {
                background: rgba(255,255,255,0.2);
                color: white;
                border: 1px solid rgba(255,255,255,0.3);
            }

            .reset-btn:hover {
                background: rgba(255,255,255,0.3);
                transform: translateY(-2px);
            }

            /* Search Results Section */
            .search-results-section {
                padding: 40px 0;
                background: white;
            }

            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px;
            }

            .search-header {
                margin-bottom: 30px;
                text-align: center;
            }

            .search-info {
                color: #666;
                font-size: 16px;
            }

            /* Results Grid */
            .results-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
                gap: 30px;
                margin-bottom: 40px;
            }

            .car-card {
                background: white;
                border-radius: 15px;
                box-shadow: 0 8px 25px rgba(0,0,0,0.1);
                overflow: hidden;
                transition: transform 0.3s, box-shadow 0.3s;
                text-decoration: none;
                color: inherit;
                display: block;
            }

            .car-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 35px rgba(0,0,0,0.15);
                text-decoration: none;
                color: inherit;
            }

            .car-image {
                width: 100%;
                height: 220px;
                background: #f8f9fa;
                display: flex;
                align-items: center;
                justify-content: center;
                overflow: hidden;
                position: relative;
            }

            .car-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            .car-placeholder {
                color: #999;
                font-size: 14px;
                text-align: center;
            }

            .car-badge {
                position: absolute;
                top: 15px;
                right: 15px;
                background: #ff6b6b;
                color: white;
                padding: 5px 12px;
                border-radius: 15px;
                font-size: 12px;
                font-weight: 600;
            }

            .car-badge.new {
                background: #51cf66;
            }

            .car-badge.used {
                background: #ffd43b;
                color: #333;
            }

            .car-info {
                padding: 25px;
            }

            .car-name {
                font-size: 20px;
                font-weight: 600;
                color: #333;
                margin-bottom: 10px;
            }

            .car-price {
                font-size: 24px;
                font-weight: bold;
                color: #667eea;
                margin-bottom: 15px;
            }

            .car-details {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 10px;
                margin-bottom: 15px;
            }

            .car-detail-item {
                font-size: 14px;
                color: #666;
            }

            .car-detail-label {
                font-weight: 600;
                color: #333;
            }

            .car-specs {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-top: 15px;
                border-top: 1px solid #eee;
            }

            .spec-item {
                text-align: center;
                flex: 1;
            }

            .spec-value {
                font-weight: 600;
                color: #333;
                font-size: 14px;
            }

            .spec-label {
                font-size: 12px;
                color: #666;
                margin-top: 2px;
            }

            /* No Results */
            .no-results {
                text-align: center;
                padding: 60px 20px;
            }

            .no-results-icon {
                font-size: 64px;
                color: #ddd;
                margin-bottom: 20px;
            }

            .no-results-title {
                font-size: 24px;
                color: #333;
                margin-bottom: 10px;
            }

            .no-results-text {
                color: #666;
                font-size: 16px;
                margin-bottom: 30px;
            }

            .back-btn {
                display: inline-block;
                background: #667eea;
                color: white;
                padding: 12px 30px;
                border-radius: 25px;
                text-decoration: none;
                font-weight: 600;
                transition: background-color 0.3s;
            }

            .back-btn:hover {
                background: #5a6fd8;
                text-decoration: none;
                color: white;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .search-form {
                    flex-direction: column;
                    border-radius: 15px;
                    max-width: 95%;
                }

                .search-input {
                    border-radius: 15px 15px 0 0;
                    padding: 15px 20px;
                }

                .search-btn {
                    border-radius: 0 0 15px 15px;
                    padding: 15px 20px;
                }

                .filter-grid {
                    grid-template-columns: 1fr;
                }

                .range-group {
                    flex-direction: column;
                    gap: 10px;
                }

                .range-separator {
                    display: none;
                }

                .results-grid {
                    grid-template-columns: 1fr;
                    gap: 20px;
                }

                .car-details {
                    grid-template-columns: 1fr;
                }
            }
            .favorite-action {
                text-align: center;
                margin-top: 15px;
            }

            /* Nút hình tròn */
            .favorite-btn {
                background: white;
                border: none;
                border-radius: 50%;
                width: 48px;
                height: 48px;
                cursor: pointer;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 0;
                transition: all 0.3s ease;
                box-shadow: 0 0 5px rgba(0,0,0,0.05);
            }

            /* Trái tim trắng viền đen */
            .heart-icon {
                width: 22px;
                height: 22px;
                background: url("data:image/svg+xml;utf8,\
                    <svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24'>\
                    <path fill='white' stroke='black' stroke-width='2' d='M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 \
                    2 6 3.99 4 6.5 4c1.74 0 3.41 1.01 4.13 2.44h1.74C14.09 5.01 15.76 4 17.5 4 \
                    20.01 4 22 6 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z'/>\
                    </svg>") no-repeat center center;
                background-size: contain;
                transition: all 0.3s ease;
            }

            /* Trái tim hồng đặc khi đã yêu thích */
            .favorite-btn.favorited .heart-icon {
                background: url("data:image/svg+xml;utf8,\
                    <svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24'>\
                    <path fill='%23ff6b6b' d='M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 \
                    2 6 3.99 4 6.5 4c1.74 0 3.41 1.01 4.13 2.44h1.74C14.09 5.01 15.76 4 17.5 4 \
                    20.01 4 22 6 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z'/>\
                    </svg>") no-repeat center center;
                background-size: contain;
            }
            .car-name-row {
                display: flex;
                align-items: center;
                justify-content: space-between;
                gap: 10px;
            }

            .car-name {
                font-size: 20px;
                font-weight: 600;
                color: #333;
                margin: 0;
            }

        </style>
    </head>
    <body>
        <jsp:include page="/header.jsp" />

        <section class="search-section">
            <div class="search-container">
                <h1 class="search-title">Tìm Kiếm Xe</h1>
                <p class="search-subtitle">
                    <c:choose>
                        <c:when test="${not empty searchCriteria.keyword}">
                            Tìm kiếm cho: "<strong>${searchCriteria.keyword}</strong>" - Tìm thấy <strong>${totalResults}</strong> kết quả
                        </c:when>
                        <c:when test="${not empty keyword}">
                            Tìm kiếm cho: "<strong>${keyword}</strong>" - Tìm thấy <strong>${totalResults}</strong> kết quả
                        </c:when>
                        <c:otherwise>
                            Tìm kiếm nâng cao - Tìm thấy <strong>${totalResults}</strong> kết quả
                        </c:otherwise>
                    </c:choose>
                </p>
                <form class="search-form" onsubmit="return handleKeywordSearch(event)">
                    <input type="text" class="search-input" name="keyword" id="keywordInput"
                           value="${searchCriteria.keyword != null ? searchCriteria.keyword : keyword}"
                           placeholder="🔍 Tìm kiếm theo tên xe, hãng, dòng xe, giá cả...">
                    <button type="submit" class="search-btn">Tìm Kiếm</button>
                </form>
                <div class="advanced-search-form">
                    <h3 class="advanced-search-title">🔧 Tìm Kiếm Nâng Cao</h3>
                    <form id="advancedSearchForm">
                        <!-- ... giữ nguyên phần filter ... -->
                        <div class="filter-actions">
                            <button type="button" class="filter-btn" onclick="buildAdvancedSearchUrl()">
                                🔍 Tìm Kiếm
                            </button>
                            <button type="reset" class="reset-btn" onclick="resetForm()">
                                🔄 Đặt Lại
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </section>

        <section class="search-results-section">
            <div class="container">
                <c:choose>
                    <c:when test="${not empty searchResults and totalResults > 0}">
                        <div class="results-grid">
                            <c:forEach var="car" items="${searchResults}">
                                <div class="car-card">
                                    <!-- Link chỉ bọc phần hình ảnh -->
                                    <a href="${pageContext.request.contextPath}/detail/${car.globalKey}">
                                        <div class="car-image">
                                            <c:choose>
                                                <c:when test="${not empty car.imageLink}">
                                                    <img src="${pageContext.request.contextPath}${car.imageLink}" alt="${car.carName}">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="car-placeholder">Hình ảnh xe</div>
                                                </c:otherwise>
                                            </c:choose>
                                            <span class="car-badge <c:if test='${car.condition eq "Mới"}'>new</c:if><c:if test='${car.condition eq "Cũ"}'>used</c:if>">
                                                ${car.condition}
                                            </span>
                                        </div>
                                    </a>

                                    <div class="car-info">
                                        <div class="car-name-row" style="display: flex; justify-content: space-between; align-items: center;">
                                            <h3 class="car-name" style="margin: 0;">
                                                <a href="${pageContext.request.contextPath}/detail/${car.globalKey}" style="text-decoration: none; color: inherit;">
                                                    ${car.carName}
                                                </a>
                                            </h3>
                                            <div class="favorite-action">
                                                <button class="favorite-btn${favoriteGlobalKeys != null && favoriteGlobalKeys.contains(car.globalKey) ? ' favorited' : ''}"
                                                        data-globalkey="${car.globalKey}"
                                                        onclick="toggleFavorite('${car.globalKey}', this)">
                                                    <span class="heart-icon"></span>
                                                </button>
                                            </div>

                                        </div>

                                        <div class="car-price">
                                            <fmt:formatNumber value="${car.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/> VNĐ
                                        </div>

                                        <div class="car-details">
                                            <div class="car-detail-item"><span class="car-detail-label">Hãng:</span> ${car.carBrandName}</div>
                                            <div class="car-detail-item"><span class="car-detail-label">Dòng:</span> ${car.carModelName}</div>
                                            <div class="car-detail-item"><span class="car-detail-label">Năm:</span> ${car.year}</div>
                                            <div class="car-detail-item"><span class="car-detail-label">Màu:</span> ${car.color}</div>
                                            <div class="car-detail-item"><span class="car-detail-label">Hộp số:</span> ${car.transmission}</div>
                                            <div class="car-detail-item"><span class="car-detail-label">Km:</span> <fmt:formatNumber value="${car.mileage}" pattern="#,###"/> km</div>
                                        </div>

                                        <div class="car-specs">
                                            <div class="spec-item">
                                                <div class="spec-value">${car.engineCapacity}cc</div>
                                                <div class="spec-label">Động cơ</div>
                                            </div>
                                            <div class="spec-item">
                                                <div class="spec-value">${car.power}HP</div>
                                                <div class="spec-label">Công suất</div>
                                            </div>
                                            <div class="spec-item">
                                                <div class="spec-value">${car.seatCount}</div>
                                                <div class="spec-label">Chỗ ngồi</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="no-results">
                            <div class="no-results-icon">🔍</div>
                            <h2 class="no-results-title">Không tìm thấy kết quả</h2>
                            <p class="no-results-text">
                                Xin lỗi, chúng tôi không tìm thấy xe nào phù hợp với tiêu chí tìm kiếm của bạn.<br>
                                Vui lòng thử lại với các tiêu chí khác.
                            </p>
                            <a href="${pageContext.request.contextPath}/" class="back-btn">Về trang chủ</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>

        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
        <script src="${pageContext.request.contextPath}/js/include.js"></script>                   

        <script>
            function handleKeywordSearch(event) {
                event.preventDefault();
                const keyword = document.getElementById('keywordInput').value.trim();
                if (keyword) {
                    const searchUrl = '${pageContext.request.contextPath}/search/result/Keyword/' + encodeURIComponent(keyword);
                    window.location.href = searchUrl;
                } else {
                    window.location.href = '${pageContext.request.contextPath}/';
                }
                return false;
            }
            function buildAdvancedSearchUrl() {
                const form = document.getElementById('advancedSearchForm');
                const formData = new FormData(form);
                let urlParts = [];
                const currentKeyword = document.getElementById('keywordInput').value.trim();
                if (currentKeyword) {
                    urlParts.push('Keyword', encodeURIComponent(currentKeyword));
                }
                if (formData.get('maker')) {
                    urlParts.push('Maker', encodeURIComponent(formData.get('maker')));
                }
                if (formData.get('type')) {
                    urlParts.push('Type', encodeURIComponent(formData.get('type')));
                }
                if (formData.get('color')) {
                    urlParts.push('Color', encodeURIComponent(formData.get('color')));
                }
                const minYear = formData.get('minYear');
                const maxYear = formData.get('maxYear');
                if (minYear || maxYear) {
                    if (minYear && maxYear) {
                        urlParts.push('Year', minYear + '-' + maxYear);
                    } else if (minYear) {
                        urlParts.push('Year', minYear + '-2025');
                    } else if (maxYear) {
                        urlParts.push('Year', '2020-' + maxYear);
                    }
                }
                const minPrice = formData.get('minPrice');
                const maxPrice = formData.get('maxPrice');
                if (minPrice || maxPrice) {
                    if (minPrice && maxPrice) {
                        urlParts.push('Price', minPrice + '-' + maxPrice);
                    } else if (minPrice) {
                        urlParts.push('Price', minPrice + '-10000000000');
                    } else if (maxPrice) {
                        urlParts.push('Price', '0-' + maxPrice);
                    }
                }
                if (formData.get('condition')) {
                    urlParts.push('Condition', encodeURIComponent(formData.get('condition')));
                }
                let searchUrl = '${pageContext.request.contextPath}/search/result';
                if (urlParts.length > 0) {
                    searchUrl += '/' + urlParts.join('/');
                }
                window.location.href = searchUrl;
            }
            function resetForm() {
                document.getElementById('advancedSearchForm').reset();
                document.getElementById('keywordInput').value = '';
                window.location.href = '${pageContext.request.contextPath}/';
            }

            // Chỉ lưu lịch sử bằng cookie khi chưa đăng nhập
            <c:if test="${empty sessionScope.currentUser}">
            saveSearchHistory(window.location.pathname + window.location.search);
            </c:if>

            <c:if test="${not empty sessionScope.currentUser}">
            saveSearchHistoryToServer(window.location.pathname + window.location.search);
            </c:if>

            // AJAX yêu thích
            function toggleFavorite(globalKey, btn) {
                fetch('${pageContext.request.contextPath}/updateFavorite', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'globalKey=' + encodeURIComponent(globalKey)
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                btn.classList.toggle('favorited');
                            } else {
                                alert(data.message || 'Có lỗi xảy ra!');
                            }
                        })
                        .catch(() => {
                            alert('Có lỗi xảy ra!');
                        });
            }

            document.addEventListener("DOMContentLoaded", function () {
            <c:if test="${empty sessionScope.currentUser}">
                syncFavoriteFromCookie();
            </c:if>
            });
        </script>
    </body>
</html>
