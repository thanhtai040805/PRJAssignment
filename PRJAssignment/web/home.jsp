<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DriveDreams - Showroom Ô Tô Hàng Đầu</title>
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
        
        /* Search Section */
        .search-section {
            background: white;
            padding: 30px 0;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .search-container {
            max-width: 800px;
            margin: 0 auto;
            text-align: center;
        }
        
        .search-form {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-bottom: 20px;
        }
        
        .search-input {
            flex: 1;
            padding: 15px 20px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            max-width: 500px;
        }
        
        .search-btn {
            padding: 15px 30px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        
        .search-btn:hover {
            background: #5a6fd8;
        }
        
        .stats {
            display: flex;
            justify-content: center;
            gap: 40px;
            color: #666;
            font-size: 14px;
        }
        
        /* Car Showcase */
        .car-showcase {
            padding: 40px 0;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }
        
        .showcase-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }
        
        .car-gallery {
            display: flex;
            justify-content: center;
            gap: 15px;
            flex-wrap: wrap;
            margin-bottom: 30px;
        }
        
        .car-item {
            width: 150px;
            height: 100px;
            background: white;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            transition: transform 0.3s;
            overflow: hidden;
        }
        
        .car-item:hover {
            transform: translateY(-5px);
        }
        
        .car-item img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        
        .car-placeholder {
            color: #999;
            font-size: 12px;
            text-align: center;
        }
        
        /* Best Seller Section */
        .best-seller {
            padding: 50px 0;
            background: white;
        }
        
        .section-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }
        
        .section-title {
            text-align: center;
            font-size: 32px;
            color: #333;
            margin-bottom: 40px;
        }
        
        .best-seller-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            margin-bottom: 40px;
        }
        
        .car-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: transform 0.3s;
        }
        
        .car-card:hover {
            transform: translateY(-10px);
        }
        
        .car-image {
            width: 100%;
            height: 200px;
            background: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }
        
        .car-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        
        .car-info {
            padding: 20px;
        }
        
        .car-info h3 {
            color: #333;
            font-size: 20px;
            margin-bottom: 10px;
        }
        
        .car-price {
            color: #667eea;
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        
        .car-details {
            color: #666;
            font-size: 14px;
            line-height: 1.5;
        }
        
        /* Ranking Section */
        .ranking-section {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 15px;
            margin-top: 30px;
        }
        
        .ranking-title {
            display: flex;
            align-items: center;
            gap: 10px;
            font-size: 18px;
            color: #333;
            margin-bottom: 20px;
        }
        
        .ranking-list {
            list-style: none;
        }
        
        .ranking-item {
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 10px 0;
            border-bottom: 1px solid #e0e0e0;
        }
        
        .ranking-item:last-child {
            border-bottom: none;
        }
        
        .rank-number {
            background: #667eea;
            color: white;
            width: 25px;
            height: 25px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            font-weight: bold;
        }
        
        .rank-car-info {
            flex: 1;
        }
        
        .rank-car-name {
            font-weight: bold;
            color: #333;
        }
        
        .rank-car-detail {
            font-size: 12px;
            color: #666;
        }
        
        /* Recommend Section */
        .recommend-section {
            padding: 50px 0;
            background: white;
        }
        
        .recommend-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 20px;
        }
        
        .recommend-card {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 10px;
            overflow: hidden;
            transition: box-shadow 0.3s;
        }
        
        .recommend-card:hover {
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        
        .recommend-image {
            width: 100%;
            height: 120px;
            background: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .recommend-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        
        .recommend-info {
            padding: 15px;
        }
        
        .recommend-name {
            font-weight: bold;
            color: #333;
            margin-bottom: 5px;
        }
        
        .recommend-price {
            color: #667eea;
            font-weight: bold;
            margin-bottom: 5px;
        }
        
        .recommend-type {
            color: #666;
            font-size: 12px;
        }
        
        /* Filter Section */
        .filter-section {
            background: #f8f9fa;
            padding: 30px 0;
            margin-top: 30px;
        }
        
        .filter-form {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            align-items: end;
        }
        
        .filter-group {
            display: flex;
            flex-direction: column;
        }
        
        .filter-group label {
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        
        .filter-group select {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        
        .filter-btn {
            padding: 12px 30px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        
        .filter-btn:hover {
            background: #5a6fd8;
        }
        
        /* Footer */
        .footer {
            background: #2c3e50;
            color: white;
            padding: 30px 0;
            margin-top: 50px;
        }
        
        .footer-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 30px;
        }
        
        .footer-section h3 {
            margin-bottom: 15px;
            color: #3498db;
        }
        
        .footer-section p {
            line-height: 1.6;
            color: #bdc3c7;
        }
        
        /* Responsive */
        @media (max-width: 768px) {
            .header-content {
                flex-direction: column;
                gap: 15px;
            }
            
            .search-form {
                flex-direction: column;
                align-items: center;
            }
            
            .search-input {
                max-width: 100%;
            }
            
            .stats {
                flex-direction: column;
                gap: 10px;
            }
            
            .car-gallery {
                grid-template-columns: repeat(2, 1fr);
            }
            
            .best-seller-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <!-- Header -->
    <header class="header">
        <div class="header-content">
            <div class="logo">
                <div class="logo-icon">🚗</div>
                <h1>DriveDreams</h1>
            </div>
            <div class="auth-links">
                <c:choose>
                    <c:when test="${sessionScope.isLoggedIn}">
                        <span>Xin chào, ${sessionScope.username}!</span>
                        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">Login</a>
                        <a href="${pageContext.request.contextPath}/register">Register</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </header>

    <!-- Search Section -->
    <section class="search-section">
        <div class="search-container">
            <form class="search-form" action="${pageContext.request.contextPath}/search" method="get">
                <input type="text" class="search-input" name="keyword" placeholder="Tìm kiếm xe theo tên, hãng, dòng xe...">
                <button type="submit" class="search-btn">Search</button>
            </form>
            <div class="stats">
                <span>📊 1920 xe đang có sẵn</span>
                <span>📈 180 xe được quan tâm</span>
            </div>
        </div>
    </section>

    <!-- Car Showcase -->
    <section class="car-showcase">
        <div class="showcase-container">
            <div class="car-gallery">
                <c:forEach var="car" items="${showcaseCars}" begin="0" end="6">
                    <div class="car-item">
                        <c:choose>
                            <c:when test="${not empty car.hinhAnh}">
                                <img src="${pageContext.request.contextPath}${car.hinhAnh}" alt="${car.tenXe}">
                            </c:when>
                            <c:otherwise>
                                <div class="car-placeholder">${car.tenHang}<br>${car.tenDong}</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- Best Seller Section -->
    <section class="best-seller">
        <div class="section-container">
            <h2 class="section-title">BEST SELLER</h2>
            <div class="best-seller-grid">
                <c:forEach var="car" items="${bestSellerCars}" begin="0" end="1">
                    <div class="car-card">
                        <div class="car-image">
                            <c:choose>
                                <c:when test="${not empty car.hinhAnh}">
                                    <img src="${pageContext.request.contextPath}${car.hinhAnh}" alt="${car.tenXe}">
                                </c:when>
                                <c:otherwise>
                                    <div class="car-placeholder">Hình ảnh xe</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="car-info">
                            <h3>${car.tenXe}</h3>
                            <div class="car-price">
                                <fmt:formatNumber value="${car.giaBan}" type="currency" currencyCode="VND" pattern="#,###"/>
                            </div>
                            <div class="car-details">
                                <p><strong>Hãng:</strong> ${car.tenHang}</p>
                                <p><strong>Dòng xe:</strong> ${car.tenDong}</p>
                                <p><strong>Năm:</strong> ${car.namSanXuat}</p>
                                <p><strong>Màu sắc:</strong> ${car.mauSac}</p>
                                <p><strong>Tình trạng:</strong> ${car.tinhTrang}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            
            <!-- Ranking Section -->
            <div class="ranking-section">
                <div class="ranking-title">
                    <span>📊</span>
                    <strong>Ranking Car</strong>
                </div>
                <ul class="ranking-list">
                    <c:forEach var="car" items="${rankingCars}" varStatus="status">
                        <li class="ranking-item">
                            <div class="rank-number">${status.index + 1}</div>
                            <div class="rank-car-info">
                                <div class="rank-car-name">${car.tenXe}</div>
                                <div class="rank-car-detail">Rank ${status.index + 1}</div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </section>

    <!-- Recommend Section -->
    <section class="recommend-section">
        <div class="section-container">
            <h2 class="section-title">Recommend Car</h2>
            <div class="recommend-grid">
                <c:forEach var="car" items="${recommendCars}">
                    <div class="recommend-card">
                        <div class="recommend-image">
                            <c:choose>
                                <c:when test="${not empty car.hinhAnh}">
                                    <img src="${pageContext.request.contextPath}${car.hinhAnh}" alt="${car.tenXe}">
                                </c:when>
                                <c:otherwise>
                                    <div class="car-placeholder">Hình ảnh xe</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="recommend-info">
                            <div class="recommend-name">${car.tenXe}</div>
                            <div class="recommend-price">
                                <fmt:formatNumber value="${car.giaBan}" type="currency" currencyCode="VND" pattern="#,###"/>
                            </div>
                            <div class="recommend-type">Type: ${car.tinhTrang}</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- Filter Section -->
    <section class="filter-section">
        <form class="filter-form" action="${pageContext.request.contextPath}/filter" method="get">
            <div class="filter-group">
                <label for="type">Type</label>
                <select id="type" name="type">
                    <option value="">Select your option</option>
                    <option value="Sedan">Sedan</option>
                    <option value="SUV">SUV</option>
                    <option value="Hatchback">Hatchback</option>
                    <option value="Pickup">Pickup</option>
                </select>
            </div>
            
            <div class="filter-group">
                <label for="engine">Engine</label>
                <select id="engine" name="engine">
                    <option value="">Select your option</option>
                    <option value="Xăng">Xăng</option>
                    <option value="Dầu">Dầu</option>
                    <option value="Hybrid">Hybrid</option>
                    <option value="Điện">Điện</option>
                </select>
            </div>
            
            <div class="filter-group">
                <label for="provider">Provider</label>
                <select id="provider" name="provider">
                    <option value="">Select your option</option>
                    <c:forEach var="provider" items="${providers}">
                        <option value="${provider.maNCC}">${provider.tenNCC}</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="filter-group">
                <label for="city">City</label>
                <select id="city" name="city">
                    <option value="">Select your option</option>
                    <option value="TP.HCM">TP. Hồ Chí Minh</option>
                    <option value="Hà Nội">Hà Nội</option>
                    <option value="Đà Nẵng">Đà Nẵng</option>
                </select>
            </div>
            
            <div class="filter-group">
                <label for="body">Body</label>
                <select id="body" name="body">
                    <option value="">Select your option</option>
                    <option value="Mới">Mới</option>
                    <option value="Cũ">Cũ</option>
                    <option value="Tân trang">Tân trang</option>
                </select>
            </div>
            
            <div class="filter-group">
                <label for="price">Price</label>
                <div style="display: flex; gap: 10px;">
                    <select name="minPrice">
                        <option value="">Min</option>
                        <option value="500000000">500 triệu</option>
                        <option value="1000000000">1 tỷ</option>
                        <option value="1500000000">1.5 tỷ</option>
                    </select>
                    <select name="maxPrice">
                        <option value="">Max</option>
                        <option value="1000000000">1 tỷ</option>
                        <option value="2000000000">2 tỷ</option>
                        <option value="3000000000">3 tỷ</option>
                    </select>
                </div>
            </div>
            
            <button type="submit" class="filter-btn">Search</button>
        </form>
    </section>

    <!-- Footer -->
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
</body>
</html>
