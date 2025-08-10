<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>DriveDreams - Showroom Ô Tô Hàng Đầu</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    </head>
    <body>
        <jsp:include page="/header.jsp" />

        <section class="search-section">
            <div class="search-container">
                <h1 class="search-title">Tìm Kiếm Xe Mơ Ước</h1>
                <p class="search-subtitle">Khám phá hàng nghìn mẫu xe chất lượng cao với công nghệ tìm kiếm thông minh</p>
                <form class="search-form" action="${pageContext.request.contextPath}/search" method="get">
                    <input type="text" class="search-input" name="keyword" 
                           placeholder="🔍 Tìm kiếm theo tên xe, hãng, dòng xe, giá cả...">
                    <button type="submit" class="search-btn">Tìm Kiếm</button>
                </form>
                <div class="stats">
                    <div class="stat-item">
                        <span class="stat-icon">🚗</span>
                        <span>1,920 xe có sẵn</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-icon">❤️</span>
                        <span>180 xe được yêu thích</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-icon">⭐</span>
                        <span>4.8/5 đánh giá</span>
                    </div>
                </div>
                <div class="quick-search">
                    <p class="quick-search-title">Tìm kiếm nhanh:</p>
                    <div class="quick-tags">
                        <a href="${pageContext.request.contextPath}/search/result/Maker/Toyota" class="quick-tag">Toyota</a>
                        <a href="${pageContext.request.contextPath}/search/result/Maker/Honda" class="quick-tag">Honda</a>
                        <a href="${pageContext.request.contextPath}/search/result/Maker/BMW" class="quick-tag">BMW</a>
                        <a href="${pageContext.request.contextPath}/search/result/Maker/Mercedes" class="quick-tag">Mercedes</a>
                        <a href="${pageContext.request.contextPath}/search/result/Type/SUV" class="quick-tag">SUV</a>
                        <a href="${pageContext.request.contextPath}/search/result/Type/Sedan" class="quick-tag">Sedan</a>
                    </div>
                </div>
                <div class="advanced-search-toggle">
                    <a href="#filter-section" class="toggle-btn">🔧 Tìm kiếm nâng cao</a>
                </div>
            </div>
        </section>

        <section class="car-showcase">
            <div class="showcase-container">
                <div class="car-gallery">
                    <c:forEach var="car" items="${showcaseCars}" begin="0" end="6">
                        <a href="${pageContext.request.contextPath}/detail/${car.globalKey}" class="car-item">
                            <c:choose>
                                <c:when test="${not empty car.imageLink}">
                                    <img src="${pageContext.request.contextPath}${car.imageLink}" alt="${car.carName}">
                                </c:when>
                                <c:otherwise>
                                    <div class="car-placeholder">
                                        <c:out value="${car.carBrandName}" default="Hãng xe"/><br>
                                        <c:out value="${car.carModelName}" default="Dòng xe"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </section>

        <section class="best-seller">
            <div class="section-container">
                <h2 class="section-title">BEST SELLER</h2>
                <div class="best-seller-grid">
                    <c:forEach var="car" items="${bestSellerCars}" begin="0" end="1">
                        <div class="car-card">
                            <a href="${pageContext.request.contextPath}/detail/${car.globalKey}">
                                <div class="car-image">
                                    <c:choose>
                                        <c:when test="${not empty car.imageLink}">
                                            <img src="${pageContext.request.contextPath}${car.imageLink}" alt="${car.carName}" class="card-img-top">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="car-placeholder">Hình ảnh xe</div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </a>
                            <div class="car-info">
                                <div class="car-header" style="display: flex; align-items: center; justify-content: space-between;">
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
                                    <fmt:formatNumber value="${car.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/>
                                </div>
                                <div class="car-details">
                                    <p><strong>Hãng:</strong> <c:out value="${car.carBrandName}" default=""/></p>
                                    <p><strong>Dòng xe:</strong> <c:out value="${car.carModelName}" default=""/></p>
                                    <p><strong>Năm:</strong> ${car.year}</p>
                                    <p><strong>Màu sắc:</strong> ${car.color}</p>
                                    <p><strong>Tình trạng:</strong> ${car.condition}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="ranking-section">
                    <div class="ranking-title">
                        <span>📊</span>
                        <strong>Ranking Car</strong>
                    </div>
                    <ul class="ranking-list">
                        <c:forEach var="car" items="${rankingCars}" varStatus="status">
                            <a href="${pageContext.request.contextPath}/detail/${car.globalKey}" class="ranking-item-link">
                                <div class="rank-number
                                     <c:choose>
                                         <c:when test="${status.index eq 0}">rank-gold</c:when>
                                         <c:when test="${status.index eq 1}">rank-silver</c:when>
                                         <c:when test="${status.index eq 2}">rank-bronze</c:when>
                                         <c:when test="${status.index eq 3}">rank-purple</c:when>
                                         <c:when test="${status.index eq 4}">rank-purple</c:when>
                                         <c:otherwise>rank-default</c:otherwise>
                                     </c:choose>
                                     ">
                                    ${status.index + 1}
                                </div>
                                <div class="rank-car-info">
                                    <div class="rank-car-name">${car.carName}</div>
                                    <div class="rank-car-detail">Top ${status.index + 1}</div>
                                </div>
                            </a>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </section>


        <%-- Recommend Cars based on favorite --%>
        <c:if test="${not empty suggestionCars}">
            <section class="recommend-section">
                <div class="section-container">
                    <h2 class="section-title">Recommend Car for You</h2>
                    <div class="recommend-grid">
                        <c:forEach var="car" items="${suggestionCars}">
                            <div class="recommend-card">
                                <a href="${pageContext.request.contextPath}/detail/${car.globalKey}">
                                    <div class="recommend-image">
                                        <c:choose>
                                            <c:when test="${not empty car.imageLink}">
                                                <img src="${pageContext.request.contextPath}${car.imageLink}" alt="${car.carName}">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="car-placeholder">Hình ảnh xe</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </a>
                                <div class="recommend-info">
                                    <div class="car-header" style="display: flex; align-items: center; justify-content: space-between;">
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
                                    <div class="recommend-price">
                                        <fmt:formatNumber value="${car.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/>
                                    </div>
                                    <div class="recommend-type">Type: ${car.condition}</div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </section>
        </c:if>


    </section>

    <section class="filter-section" id="filter-section">
        <div class="section-container">
            <h2 class="section-title">🔧 Tìm Kiếm Nâng Cao</h2>
            <form class="filter-form" onsubmit="return buildAdvancedSearchUrl(event)">
                <div class="filter-group">
                    <label for="hangXe">Hãng xe</label>
                    <select id="hangXe" name="maker">
                        <option value="">Chọn hãng xe</option>
                        <option value="Toyota">Toyota</option>
                        <option value="Honda">Honda</option>
                        <option value="BMW">BMW</option>
                        <option value="Mercedes">Mercedes</option>
                        <option value="Audi">Audi</option>
                        <option value="Hyundai">Hyundai</option>
                        <option value="Kia">Kia</option>
                        <option value="Mazda">Mazda</option>
                        <option value="Ford">Ford</option>
                        <option value="Volkswagen">Volkswagen</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label for="dongXe">Loại xe</label>
                    <select id="dongXe" name="type">
                        <option value="">Chọn loại xe</option>
                        <option value="Sedan">Sedan</option>
                        <option value="SUV">SUV</option>
                        <option value="Hatchback">Hatchback</option>
                        <option value="Pickup">Pickup</option>
                        <option value="Coupe">Coupe</option>
                        <option value="Convertible">Convertible</option>
                        <option value="Crossover">Crossover</option>
                        <option value="Wagon">Wagon</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label for="namSanXuat">Năm sản xuất</label>
                    <div class="year-range">
                        <select name="minYear">
                            <option value="">Từ năm</option>
                            <option value="2020">2020</option>
                            <option value="2021">2021</option>
                            <option value="2022">2022</option>
                            <option value="2023">2023</option>
                            <option value="2024">2024</option>
                            <option value="2025">2025</option>
                        </select>
                        <span class="range-separator">-</span>
                        <select name="maxYear">
                            <option value="">Đến năm</option>
                            <option value="2021">2021</option>
                            <option value="2022">2022</option>
                            <option value="2023">2023</option>
                            <option value="2024">2024</option>
                            <option value="2025">2025</option>
                        </select>
                    </div>
                </div>
                <div class="filter-group">
                    <label for="mauSac">Màu sắc</label>
                    <select id="mauSac" name="color">
                        <option value="">Chọn màu sắc</option>
                        <option value="Trắng">Trắng</option>
                        <option value="Trắng ngọc trai">Trắng ngọc trai</option>
                        <option value="Đen">Đen</option>
                        <option value="Bạc">Bạc</option>
                        <option value="Xám">Xám</option>
                        <option value="Đỏ">Đỏ</option>
                        <option value="Xanh">Xanh</option>
                        <option value="Xanh dương">Xanh dương</option>
                        <option value="Xanh rêu">Xanh rêu</option>
                        <option value="Cam">Cam</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label for="hopSo">Hộp số</label>
                    <select id="hopSo" name="transmission">
                        <option value="">Chọn hộp số</option>
                        <option value="Số sàn">Số sàn</option>
                        <option value="Số tự động">Số tự động</option>
                        <option value="CVT">CVT</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label for="tinhTrang">Tình trạng</label>
                    <select id="tinhTrang" name="condition">
                        <option value="">Chọn tình trạng</option>
                        <option value="Mới">Xe mới</option>
                        <option value="Cũ">Xe cũ</option>
                        <option value="Tân trang">Xe tân trang</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label for="dungTichDongCo">Dung tích động cơ (cc)</label>
                    <div class="engine-range">
                        <select name="minEngine">
                            <option value="">Từ</option>
                            <option value="1000">1.0L (1000cc)</option>
                            <option value="1200">1.2L (1200cc)</option>
                            <option value="1400">1.4L (1400cc)</option>
                            <option value="1500">1.5L (1500cc)</option>
                            <option value="1600">1.6L (1600cc)</option>
                            <option value="1800">1.8L (1800cc)</option>
                            <option value="2000">2.0L (2000cc)</option>
                        </select>
                        <span class="range-separator">-</span>
                        <select name="maxEngine">
                            <option value="">Đến</option>
                            <option value="1500">1.5L (1500cc)</option>
                            <option value="2000">2.0L (2000cc)</option>
                            <option value="2500">2.5L (2500cc)</option>
                            <option value="3000">3.0L (3000cc)</option>
                            <option value="4000">4.0L (4000cc)</option>
                        </select>
                    </div>
                </div>
                <div class="filter-group">
                    <label for="congSuat">Công suất (HP)</label>
                    <div class="power-range">
                        <select name="minPower">
                            <option value="">Từ</option>
                            <option value="100">100 HP</option>
                            <option value="150">150 HP</option>
                            <option value="200">200 HP</option>
                            <option value="250">250 HP</option>
                        </select>
                        <span class="range-separator">-</span>
                        <select name="maxPower">
                            <option value="">Đến</option>
                            <option value="200">200 HP</option>
                            <option value="300">300 HP</option>
                            <option value="400">400 HP</option>
                            <option value="500">500+ HP</option>
                        </select>
                    </div>
                </div>
                <div class="filter-group price-group">
                    <label for="giaBan">Khoảng giá bán</label>
                    <div class="price-range">
                        <select name="minPrice">
                            <option value="">Giá từ</option>
                            <option value="400000000">400 triệu</option>
                            <option value="600000000">600 triệu</option>
                            <option value="800000000">800 triệu</option>
                            <option value="1000000000">1 tỷ</option>
                            <option value="1500000000">1.5 tỷ</option>
                            <option value="2000000000">2 tỷ</option>
                            <option value="3000000000">3 tỷ</option>
                        </select>
                        <span class="range-separator">-</span>
                        <select name="maxPrice">
                            <option value="">Giá đến</option>
                            <option value="800000000">800 triệu</option>
                            <option value="1000000000">1 tỷ</option>
                            <option value="1500000000">1.5 tỷ</option>
                            <option value="2000000000">2 tỷ</option>
                            <option value="3000000000">3 tỷ</option>
                            <option value="5000000000">5 tỷ</option>
                            <option value="10000000000">10 tỷ+</option>
                        </select>
                    </div>
                </div>
                <div class="filter-group">
                    <label for="kmDaDi">Số km đã đi</label>
                    <select id="kmDaDi" name="maxKm">
                        <option value="">Chọn số km</option>
                        <option value="0">Xe mới (0 km)</option>
                        <option value="5000">Dưới 5,000 km</option>
                        <option value="10000">Dưới 10,000 km</option>
                        <option value="20000">Dưới 20,000 km</option>
                        <option value="50000">Dưới 50,000 km</option>
                        <option value="100000">Dưới 100,000 km</option>
                    </select>
                </div>
                <div class="filter-actions">
                    <button type="submit" class="filter-btn">
                        <span class="search-icon">🔍</span>
                        Tìm Kiếm
                    </button>
                    <button type="reset" class="reset-btn">
                        <span class="reset-icon">🔄</span>
                        Đặt Lại
                    </button>
                </div>
            </form>
        </div>
    </section>

    <footer class="footer">
        <div class="footer-content">
            <div class="footer-section">
                <div class="logo">
                    <div class="logo-icon">🚗</div>
                    <h3>DriveDreams</h3>
                </div>
                <p>Chúng tôi là showroom ô tô uy tín hàng đầu, cung cấp các dòng xe chất lượng cao với dịch vụ tốt nhất.</p>
            </div>
            <div class="footer-section">
                <h3>Liên hệ</h3>
                <p>Email: drivedreams@gmail.com</p>
                <p>Phone: 0123456789.OK</p>
                <p>Address: 112 Trần Duy Hưng</p>
            </div>
            <div class="footer-section">
                <h3>Giới thiệu</h3>
                <p>Group 4 SE119405</p>
                <p>FPT University</p>
                <p>© 2024 DriveDreams. All rights reserved.</p>
            </div>
        </div>
    </footer>

    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/js/include.js"></script>

    <script>
        function buildAdvancedSearchUrl(event) {
            event.preventDefault(); // Ngăn submit mặc định

            const form = document.querySelector('.filter-form');
            const formData = new FormData(form);

            let urlParts = [];

            const maker = formData.get('maker');
            if (maker) {
                urlParts.push('Maker', encodeURIComponent(maker));
            }

            const type = formData.get('type');
            if (type) {
                urlParts.push('Type', encodeURIComponent(type));
            }

            const color = formData.get('color');
            if (color) {
                urlParts.push('Color', encodeURIComponent(color));
            }

            const fuel = formData.get('fuel'); // Nếu có trường fuel trong form
            if (fuel) {
                urlParts.push('Fuel', encodeURIComponent(fuel));
            }

            const transmission = formData.get('transmission');
            if (transmission) {
                urlParts.push('Transmission', encodeURIComponent(transmission));
            }

            const seat = formData.get('seat'); // Nếu có trường seat trong form
            if (seat) {
                urlParts.push('Seat', encodeURIComponent(seat));
            }

            const condition = formData.get('condition');
            if (condition) {
                urlParts.push('Condition', encodeURIComponent(condition));
            }

            const minYear = formData.get('minYear');
            const maxYear = formData.get('maxYear');
            if (minYear || maxYear) {
                let yearRange = '';
                if (minYear && maxYear) {
                    yearRange = encodeURIComponent(minYear) + '-' + encodeURIComponent(maxYear);
                } else if (minYear) {
                    yearRange = encodeURIComponent(minYear) + '-2025';
                } else if (maxYear) {
                    yearRange = '2020-' + encodeURIComponent(maxYear);
                }
                urlParts.push('Year', yearRange);
            }

            const minEngine = formData.get('minEngine');
            const maxEngine = formData.get('maxEngine');
            if (minEngine || maxEngine) {
                let engineRange = '';
                if (minEngine && maxEngine) {
                    engineRange = encodeURIComponent(minEngine) + '-' + encodeURIComponent(maxEngine);
                } else if (minEngine) {
                    engineRange = encodeURIComponent(minEngine) + '-9999';
                } else if (maxEngine) {
                    engineRange = '0-' + encodeURIComponent(maxEngine);
                }
                urlParts.push('Engine', engineRange);
            }

            const minPower = formData.get('minPower');
            const maxPower = formData.get('maxPower');
            if (minPower || maxPower) {
                let powerRange = '';
                if (minPower && maxPower) {
                    powerRange = encodeURIComponent(minPower) + '-' + encodeURIComponent(maxPower);
                } else if (minPower) {
                    powerRange = encodeURIComponent(minPower) + '-9999';
                } else if (maxPower) {
                    powerRange = '0-' + encodeURIComponent(maxPower);
                }
                urlParts.push('Power', powerRange);
            }

            const minPrice = formData.get('minPrice');
            const maxPrice = formData.get('maxPrice');
            if (minPrice || maxPrice) {
                let priceRange = '';
                if (minPrice && maxPrice) {
                    priceRange = encodeURIComponent(minPrice) + '-' + encodeURIComponent(maxPrice);
                } else if (minPrice) {
                    priceRange = encodeURIComponent(minPrice) + '-10000000000';
                } else if (maxPrice) {
                    priceRange = '0-' + encodeURIComponent(maxPrice);
                }
                urlParts.push('Price', priceRange);
            }

            const maxKm = formData.get('maxKm');
            if (maxKm) {
                urlParts.push('Km', encodeURIComponent(maxKm));
            }

            let searchUrl = contextPath + '/search/result';

            // Luôn thêm dấu '/' ở cuối URL kể cả khi urlParts rỗng
            searchUrl += '/' + (urlParts.length > 0 ? urlParts.join('/') : '');

            window.location.href = searchUrl;
            return false; // Ngăn submit mặc định (dự phòng)
        }




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
