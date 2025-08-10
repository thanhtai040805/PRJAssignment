<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Kết Quả Tìm Kiếm - DriveDreams</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/car/search.css" />
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

                <!-- Advanced Search Form -->
                <div class="advanced-search-form">
                    <h3 class="advanced-search-title">🔧 Tìm Kiếm Nâng Cao</h3>
                    <form id="advancedSearchForm" autocomplete="off">
                        <div class="filter-grid">
                            <!-- Hãng xe -->
                            <div class="filter-group">
                                <label for="maker">Hãng xe</label>
                                <select id="hangXe" name="maker">
                                    <option value="">Chọn hãng xe</option>
                                    <option value="Toyota" <c:if test="${searchCriteria.maker == 'Toyota'}">selected</c:if>>Toyota</option>
                                    <option value="Honda" <c:if test="${searchCriteria.maker == 'Honda'}">selected</c:if>>Honda</option>
                                    <option value="BMW" <c:if test="${searchCriteria.maker == 'BMW'}">selected</c:if>>BMW</option>
                                    <option value="Mercedes" <c:if test="${searchCriteria.maker == 'Mercedes'}">selected</c:if>>Mercedes</option>
                                    <option value="Audi" <c:if test="${searchCriteria.maker == 'Audi'}">selected</c:if>>Audi</option>
                                    <option value="Hyundai" <c:if test="${searchCriteria.maker == 'Hyundai'}">selected</c:if>>Hyundai</option>
                                    <option value="Kia" <c:if test="${searchCriteria.maker == 'Kia'}">selected</c:if>>Kia</option>
                                    <option value="Mazda" <c:if test="${searchCriteria.maker == 'Mazda'}">selected</c:if>>Mazda</option>
                                    <option value="Ford" <c:if test="${searchCriteria.maker == 'Ford'}">selected</c:if>>Ford</option>
                                    <option value="Volkswagen" <c:if test="${searchCriteria.maker == 'Volkswagen'}">selected</c:if>>Volkswagen</option>
                                    </select>
                                </div>
                                <!-- Dòng xe -->
                                <div class="filter-group">
                                    <label for="model">Dòng xe</label>
                                    <select id="dongXe" name="type">
                                        <option value="">Chọn loại xe</option>
                                        <option value="Sedan" <c:if test="${searchCriteria.carType == 'Sedan'}">selected</c:if>>Sedan</option>
                                    <option value="SUV" <c:if test="${searchCriteria.carType == 'SUV'}">selected</c:if>>SUV</option>
                                    <option value="Hatchback" <c:if test="${searchCriteria.carType == 'Hatchback'}">selected</c:if>>Hatchback</option>
                                    <option value="Pickup" <c:if test="${searchCriteria.carType == 'Pickup'}">selected</c:if>>Pickup</option>
                                    <option value="Coupe" <c:if test="${searchCriteria.carType == 'Coupe'}">selected</c:if>>Coupe</option>
                                    <option value="Convertible" <c:if test="${searchCriteria.carType == 'Convertible'}">selected</c:if>>Convertible</option>
                                    <option value="Crossover" <c:if test="${searchCriteria.carType == 'Crossover'}">selected</c:if>>Crossover</option>
                                    <option value="Wagon" <c:if test="${searchCriteria.carType == 'Wagon'}">selected</c:if>>Wagon</option>
                                    </select>
                                </div>
                                <!-- Số chỗ -->
                                <div class="filter-group">
                                    <label for="seat">Số chỗ</label>
                                    <select name="seat" id="seat">
                                        <option value="">Tất cả</option>
                                        <option value="4" <c:if test="${searchCriteria.seat == 4}">selected</c:if>>4</option>
                                    <option value="5" <c:if test="${searchCriteria.seat == 5}">selected</c:if>>5</option>
                                    <option value="7" <c:if test="${searchCriteria.seat == 7}">selected</c:if>>7</option>
                                    </select>
                                </div>
                                <!-- Nhiên liệu -->
                                <div class="filter-group">
                                    <label for="fuel">Nhiên liệu</label>
                                    <select name="fuel" id="fuel">
                                        <option value="">Tất cả</option>
                                        <option value="Xăng" <c:if test="${searchCriteria.fuel == 'Xăng'}">selected</c:if>>Xăng</option>
                                    <option value="Dầu" <c:if test="${searchCriteria.fuel == 'Dầu'}">selected</c:if>>Dầu</option>
                                    <option value="Hybrid" <c:if test="${searchCriteria.fuel == 'Hybrid'}">selected</c:if>>Hybrid</option>
                                    <option value="Điện" <c:if test="${searchCriteria.fuel == 'Điện'}">selected</c:if>>Điện</option>
                                    <option value="Khác" <c:if test="${searchCriteria.fuel == 'Khác'}">selected</c:if>>Khác</option>
                                    </select>
                                </div>
                                <!-- Năm sản xuất -->
                                <div class="filter-group">
                                    <label>Năm sản xuất</label>
                                    <div class="range-group">
                                        <select name="minYear">
                                            <option value="">Từ</option>
                                        <c:forEach var="y" begin="2015" end="2025">
                                            <option value="${y}" <c:if test="${searchCriteria.minYear == y}">selected</c:if>>${y}</option>
                                        </c:forEach>
                                    </select>
                                    <span class="range-separator">-</span>
                                    <select name="maxYear">
                                        <option value="">Đến</option>
                                        <c:forEach var="y" begin="2015" end="2025">
                                            <option value="${y}" <c:if test="${searchCriteria.maxYear == y}">selected</c:if>>${y}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <!-- Màu sắc -->
                            <div class="filter-group">
                                <label for="color">Màu sắc</label>
                                <select name="color" id="color">
                                    <option value="">Tất cả</option>
                                    <option value="Trắng" <c:if test="${searchCriteria.color == 'Trắng'}">selected</c:if>>Trắng</option>
                                    <option value="Đen" <c:if test="${searchCriteria.color == 'Đen'}">selected</c:if>>Đen</option>
                                    <option value="Đỏ" <c:if test="${searchCriteria.color == 'Đỏ'}">selected</c:if>>Đỏ</option>
                                    <option value="Xám" <c:if test="${searchCriteria.color == 'Xám'}">selected</c:if>>Xám</option>
                                    <option value="Bạc" <c:if test="${searchCriteria.color == 'Bạc'}">selected</c:if>>Bạc</option>
                                    <option value="Xanh" <c:if test="${searchCriteria.color == 'Xanh'}">selected</c:if>>Xanh</option>
                                    <option value="Cam" <c:if test="${searchCriteria.color == 'Cam'}">selected</c:if>>Cam</option>
                                    <option value="Khác" <c:if test="${searchCriteria.color == 'Khác'}">selected</c:if>>Khác</option>
                                    </select>
                                </div>
                                <!-- Hộp số -->
                                <div class="filter-group">
                                    <label for="transmission">Hộp số</label>
                                    <select name="transmission" id="transmission">
                                        <option value="">Tất cả</option>
                                        <option value="Số sàn" <c:if test="${searchCriteria.transmission == 'Số sàn'}">selected</c:if>>Số sàn</option>
                                    <option value="Số tự động" <c:if test="${searchCriteria.transmission == 'Số tự động'}">selected</c:if>>Số tự động</option>
                                    <option value="CVT" <c:if test="${searchCriteria.transmission == 'CVT'}">selected</c:if>>CVT</option>
                                    </select>
                                </div>
                                <!-- Tình trạng -->
                                <div class="filter-group">
                                    <label for="condition">Tình trạng</label>
                                    <select name="condition" id="condition">
                                        <option value="">Tất cả</option>
                                        <option value="Mới" <c:if test="${searchCriteria.condition == 'Mới'}">selected</c:if>>Mới</option>
                                    <option value="Cũ" <c:if test="${searchCriteria.condition == 'Cũ'}">selected</c:if>>Cũ</option>
                                    <option value="Tân trang" <c:if test="${searchCriteria.condition == 'Tân trang'}">selected</c:if>>Tân trang</option>
                                    </select>
                                </div>
                                <!-- Khoảng giá -->
                                <div class="filter-group">
                                    <label>Khoảng giá (VNĐ)</label>
                                    <div class="range-group">
                                        <select name="minPrice">
                                            <option value="">Từ</option>
                                            <option value="200000000" <c:if test="${searchCriteria.minPrice == 200000000}">selected</c:if>>200 triệu</option>
                                        <option value="500000000" <c:if test="${searchCriteria.minPrice == 500000000}">selected</c:if>>500 triệu</option>
                                        <option value="700000000" <c:if test="${searchCriteria.minPrice == 700000000}">selected</c:if>>700 triệu</option>
                                        <option value="1000000000" <c:if test="${searchCriteria.minPrice == 1000000000}">selected</c:if>>1 tỷ</option>
                                        <option value="1500000000" <c:if test="${searchCriteria.minPrice == 1500000000}">selected</c:if>>1.5 tỷ</option>
                                        <option value="2000000000" <c:if test="${searchCriteria.minPrice == 2000000000}">selected</c:if>>2 tỷ</option>
                                        </select>
                                        <span class="range-separator">-</span>
                                        <select name="maxPrice">
                                            <option value="">Đến</option>
                                            <option value="500000000" <c:if test="${searchCriteria.maxPrice == 500000000}">selected</c:if>>500 triệu</option>
                                        <option value="700000000" <c:if test="${searchCriteria.maxPrice == 700000000}">selected</c:if>>700 triệu</option>
                                        <option value="1000000000" <c:if test="${searchCriteria.maxPrice == 1000000000}">selected</c:if>>1 tỷ</option>
                                        <option value="1500000000" <c:if test="${searchCriteria.maxPrice == 1500000000}">selected</c:if>>1.5 tỷ</option>
                                        <option value="2000000000" <c:if test="${searchCriteria.maxPrice == 2000000000}">selected</c:if>>2 tỷ</option>
                                        <option value="5000000000" <c:if test="${searchCriteria.maxPrice == 5000000000}">selected</c:if>>5 tỷ</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                    </div>
                    <div class="filter-actions">
                        <button type="button" class="filter-btn" onclick="buildAdvancedSearchUrl()">🔍 Tìm Kiếm</button>
                        <button type="reset" class="reset-btn" onclick="resetForm()">🔄 Đặt Lại</button>
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
                                            <button type="button" class="favorite-btn${favoriteGlobalKeys != null && favoriteGlobalKeys.contains(car.globalKey) ? ' favorited' : ''}"
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
            if (formData.get('fuel')) {
                urlParts.push('Fuel', encodeURIComponent(formData.get('fuel')));
            }
            if (formData.get('transmission')) {
                urlParts.push('Transmission', encodeURIComponent(formData.get('transmission')));
            }
            if (formData.get('seat')) {
                urlParts.push('Seat', encodeURIComponent(formData.get('seat')));
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
            searchUrl += '/' + (urlParts.length > 0 ? urlParts.join('/') : '');
            window.location.href = searchUrl;
        }


        function resetForm() {
            window.location.href = '${pageContext.request.contextPath}/search/result/';
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