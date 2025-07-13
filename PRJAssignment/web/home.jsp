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

            /* Search Section - Cải tiến hoàn toàn */
            .search-section {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                padding: 60px 0;
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
                font-size: 42px;
                font-weight: 300;
                margin-bottom: 15px;
                text-shadow: 0 2px 4px rgba(0,0,0,0.3);
                letter-spacing: -1px;
            }

            .search-subtitle {
                color: rgba(255,255,255,0.9);
                font-size: 18px;
                margin-bottom: 40px;
                font-weight: 300;
                line-height: 1.6;
            }

            .search-form {
                display: flex;
                justify-content: center;
                gap: 0;
                margin-bottom: 35px;
                box-shadow: 0 15px 35px rgba(0,0,0,0.2);
                border-radius: 50px;
                overflow: hidden;
                background: white;
                max-width: 650px;
                margin-left: auto;
                margin-right: auto;
                margin-bottom: 35px;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .search-form:hover {
                transform: translateY(-3px);
                box-shadow: 0 20px 40px rgba(0,0,0,0.25);
            }

            .search-input {
                flex: 1;
                padding: 22px 30px;
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
                padding: 22px 45px;
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

            .search-btn:active {
                transform: translateY(0);
            }

            /* Stats Section - Cải tiến */
            .stats {
                display: flex;
                justify-content: center;
                gap: 40px;
                margin-bottom: 30px;
            }

            .stat-item {
                display: flex;
                align-items: center;
                gap: 12px;
                padding: 18px 28px;
                background: rgba(255,255,255,0.15);
                border-radius: 30px;
                backdrop-filter: blur(10px);
                border: 1px solid rgba(255,255,255,0.2);
                transition: all 0.3s ease;
                color: white;
                font-size: 16px;
                font-weight: 500;
            }

            .stat-item:hover {
                background: rgba(255,255,255,0.25);
                transform: translateY(-3px);
                box-shadow: 0 8px 25px rgba(0,0,0,0.15);
            }

            .stat-icon {
                font-size: 22px;
                filter: drop-shadow(0 2px 4px rgba(0,0,0,0.2));
            }

            /* Quick Search Tags */
            .quick-search {
                margin-top: 30px;
            }

            .quick-search-title {
                color: rgba(255,255,255,0.8);
                font-size: 15px;
                margin-bottom: 18px;
                font-weight: 400;
            }

            .quick-tags {
                display: flex;
                justify-content: center;
                gap: 12px;
                flex-wrap: wrap;
            }

            .quick-tag {
                padding: 10px 20px;
                background: rgba(255,255,255,0.15);
                color: white;
                border-radius: 25px;
                text-decoration: none;
                font-size: 14px;
                transition: all 0.3s ease;
                border: 1px solid rgba(255,255,255,0.2);
                font-weight: 500;
                backdrop-filter: blur(5px);
            }

            .quick-tag:hover {
                background: rgba(255,255,255,0.25);
                transform: translateY(-2px);
                color: white;
                text-decoration: none;
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }

            /* Advanced Search Toggle */
            .advanced-search-toggle {
                margin-top: 25px;
            }

            .toggle-btn {
                background: rgba(255,255,255,0.1);
                border: 1px solid rgba(255,255,255,0.3);
                color: rgba(255,255,255,0.9);
                padding: 12px 24px;
                border-radius: 30px;
                cursor: pointer;
                transition: all 0.3s ease;
                font-size: 14px;
                font-weight: 500;
                backdrop-filter: blur(5px);
                text-decoration: none;
            }

            .toggle-btn:hover {
                background: rgba(255,255,255,0.2);
                border-color: rgba(255,255,255,0.5);
                transform: translateY(-2px);
                text-decoration: none;
                color: rgba(255,255,255,0.9);
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
                text-decoration: none;
                color: inherit;
            }

            .car-item:hover {
                transform: translateY(-5px);
                text-decoration: none;
                color: inherit;
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
                text-decoration: none;
                color: inherit;
                display: block;
            }

            .car-card:hover {
                transform: translateY(-10px);
                text-decoration: none;
                color: inherit;
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

            .rank-gold {
                background: linear-gradient(135deg, #FFD700, #FFA500);
            }

            .rank-silver {
                background: linear-gradient(135deg, #C0C0C0, #A9A9A9);
            }

            .rank-bronze {
                background: linear-gradient(135deg, #CD7F32, #B87333);
            }

            .rank-purple {
                background: linear-gradient(135deg, #8A2BE2, #9932CC);
            }

            .ranking-item-link {
                display: flex;
                align-items: center;
                gap: 15px;
                padding: 10px 0;
                border-bottom: 1px solid #e0e0e0;
                text-decoration: none;
                color: inherit;
                transition: background-color 0.2s ease;
            }

            .ranking-item-link:last-child {
                border-bottom: none;
            }

            .ranking-item-link:hover {
                background-color: #f0f0f0;
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
                text-decoration: none;
                color: inherit;
                display: block;
            }

            .recommend-card:hover {
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
                text-decoration: none;
                color: inherit;
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

            /* Filter Section - Cải tiến hoàn toàn */
            .filter-section {
                background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
                padding: 50px 0;
                margin-top: 0;
                border-top: 1px solid #dee2e6;
            }

            .filter-form {
                max-width: 1200px;
                margin: 0 auto;
                padding: 40px;
                background: white;
                border-radius: 20px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.1);
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 25px;
                align-items: start;
            }

            .filter-group {
                display: flex;
                flex-direction: column;
                gap: 8px;
            }

            .filter-group label {
                font-weight: 600;
                color: #333;
                font-size: 14px;
                margin-bottom: 5px;
            }

            .filter-group select {
                padding: 12px 15px;
                border: 2px solid #e9ecef;
                border-radius: 10px;
                font-size: 14px;
                background: white;
                color: #333;
                transition: all 0.3s ease;
                cursor: pointer;
            }

            .filter-group select:focus {
                outline: none;
                border-color: #667eea;
                box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
            }

            .filter-group select:hover {
                border-color: #667eea;
            }

            /* Year Range, Price Range, Engine Range, Power Range */
            .year-range, .price-range, .engine-range, .power-range {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .year-range select, .price-range select, .engine-range select, .power-range select {
                flex: 1;
            }

            .range-separator {
                color: #666;
                font-weight: 500;
                font-size: 16px;
            }

            /* Filter Actions */
            .filter-actions {
                grid-column: 1 / -1;
                display: flex;
                justify-content: center;
                gap: 15px;
                margin-top: 20px;
            }

            .filter-btn {
                padding: 15px 40px;
                background: linear-gradient(135deg, #667eea, #764ba2);
                color: white;
                border: none;
                border-radius: 50px;
                cursor: pointer;
                font-size: 16px;
                font-weight: 600;
                transition: all 0.3s ease;
                display: flex;
                align-items: center;
                gap: 10px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
            }

            .filter-btn:hover {
                background: linear-gradient(135deg, #5a6fd8, #6a4c93);
                transform: translateY(-2px);
                box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
            }

            .reset-btn {
                padding: 15px 40px;
                background: white;
                color: #666;
                border: 2px solid #e9ecef;
                border-radius: 50px;
                cursor: pointer;
                font-size: 16px;
                font-weight: 600;
                transition: all 0.3s ease;
                display: flex;
                align-items: center;
                gap: 10px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .reset-btn:hover {
                background: #f8f9fa;
                border-color: #667eea;
                color: #667eea;
                transform: translateY(-2px);
            }

            .search-icon, .reset-icon {
                font-size: 18px;
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

            /* Responsive cho search section */
            @media (max-width: 768px) {
                .search-section {
                    padding: 40px 0;
                }

                .search-title {
                    font-size: 32px;
                }

                .search-subtitle {
                    font-size: 16px;
                    margin-bottom: 30px;
                }

                .search-form {
                    flex-direction: column;
                    border-radius: 20px;
                    max-width: 95%;
                }

                .search-input {
                    border-radius: 20px 20px 0 0;
                    padding: 18px 25px;
                }

                .search-btn {
                    border-radius: 0 0 20px 20px;
                    padding: 18px 25px;
                }

                .stats {
                    flex-direction: column;
                    gap: 15px;
                    align-items: center;
                }

                .stat-item {
                    padding: 15px 25px;
                }

                .quick-tags {
                    justify-content: center;
                    gap: 8px;
                }

                .quick-tag {
                    padding: 8px 16px;
                    font-size: 13px;
                }

                /* Filter responsive */
                .filter-form {
                    grid-template-columns: 1fr;
                    padding: 25px;
                    margin: 0 15px;
                }

                .filter-actions {
                    flex-direction: column;
                    align-items: center;
                }

                .filter-btn, .reset-btn {
                    width: 100%;
                    justify-content: center;
                }

                .year-range, .price-range, .engine-range, .power-range {
                    flex-direction: column;
                    gap: 10px;
                }

                .range-separator {
                    display: none;
                }
            }

            @media (max-width: 480px) {
                .search-container {
                    padding: 0 15px;
                }

                .search-title {
                    font-size: 28px;
                }

                .search-subtitle {
                    font-size: 15px;
                }

                .stats {
                    gap: 12px;
                }

                .stat-item {
                    padding: 12px 20px;
                    font-size: 14px;
                }

                .filter-section {
                    padding: 30px 0;
                }

                .filter-form {
                    padding: 20px;
                    margin: 0 10px;
                }

                .filter-btn, .reset-btn {
                    padding: 12px 30px;
                    font-size: 14px;
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

        </style>
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <jsp:include page="/agent-chat.jsp" />
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
            <form class="filter-form" action="${pageContext.request.contextPath}/search" method="get">
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
