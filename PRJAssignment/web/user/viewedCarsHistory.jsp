<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử xe đã xem</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: #f7f8fa;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 1100px;
            margin: 40px auto;
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.10);
            padding: 32px 40px 40px 40px;
        }
        h1 {
            text-align: center;
            color: #007bff;
            margin-bottom: 28px;
            font-weight: 700;
        }
        .top-action {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 18px;
        }
        .search-history-btn {
            background: #fff;
            color: #007bff;
            border: 1.5px solid #007bff;
            border-radius: 22px;
            padding: 7px 22px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s, color 0.2s;
            box-shadow: 0 2px 8px rgba(0,123,255,0.08);
            text-decoration-line: none;
        }
        .search-history-btn:hover {
            background: #007bff;
            color: #fff;
        }
        .viewed-list {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
            gap: 30px;
        }
        .car-card {
            background: #f9fafb;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.07);
            transition: box-shadow 0.2s, transform 0.2s;
            padding: 20px;
            text-align: center;
            position: relative;
        }
        .car-card:hover {
            box-shadow: 0 8px 32px rgba(0,0,0,0.13);
            transform: translateY(-4px) scale(1.01);
        }
        .car-img {
            width: 100%;
            height: 160px;
            object-fit: cover;
            border-radius: 8px;
            margin-bottom: 18px;
            background: #eaeaea;
        }
        .car-name {
            font-size: 1.18em;
            font-weight: 600;
            margin-bottom: 8px;
            color: #343a40;
        }
        .car-brand {
            color: #6c757d;
            font-size: 0.98em;
            margin-bottom: 6px;
        }
        .car-price {
            color: #28a745;
            font-size: 1.08em;
            font-weight: 600;
            margin-bottom: 10px;
        }
        .card-actions {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 18px;
            margin-top: 18px;
        }
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
        .favorite-btn.favorited .heart-icon {
            background: url("data:image/svg+xml;utf8,\
                <svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24'>\
                <path fill='%23ff6b6b' d='M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 \
                2 6 3.99 4 6.5 4c1.74 0 3.41 1.01 4.13 2.44h1.74C14.09 5.01 15.76 4 17.5 4 \
                20.01 4 22 6 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z'/>\
                </svg>") no-repeat center center;
            background-size: contain;
        }
        .detail-btn {
            background: #007bff;
            color: #fff;
            border: none;
            border-radius: 20px;
            padding: 8px 20px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
            text-decoration: none;
            display: inline-block;
        }
        .detail-btn:hover {
            background: #0056b3;
        }
        .no-history {
            text-align: center;
            color: #888;
            font-size: 1.15em;
            margin: 70px 0 40px 0;
        }
        @media (max-width: 700px) {
            .container { padding: 15px 5px 25px 5px; }
            .viewed-list { grid-template-columns: 1fr; }
            .card-actions { flex-direction: column; gap: 10px; }
        }
    </style>
</head>
<body>
    <jsp:include page="/header.jsp" />
    <div class="container">
        <h1>Lịch sử xe đã xem</h1>
        <div class="top-action">
            <a href="${pageContext.request.contextPath}/history" class="search-history-btn" title="Xem lịch sử tìm kiếm">
                <span style="font-size: 17px; vertical-align: middle;">🔍</span> Lịch sử tìm kiếm
            </a>
        </div>
        <c:choose>
            <c:when test="${not empty viewedCarsList}">
                <div class="viewed-list">
                    <c:forEach var="car" items="${viewedCarsList}">
                        <div class="car-card" id="card-${car.globalKey}">
                            <a href="${pageContext.request.contextPath}/detail/${car.globalKey}">
                                <c:choose>
                                    <c:when test="${not empty car.imageLink}">
                                        <img class="car-img" src="${pageContext.request.contextPath}${car.imageLink}" alt="${car.carName}">
                                    </c:when>
                                    <c:otherwise>
                                        <img class="car-img" src="${pageContext.request.contextPath}/images/no-image.png" alt="No image">
                                    </c:otherwise>
                                </c:choose>
                                <div class="car-name">${car.carName}</div>
                            </a>
                            <div class="car-brand">${car.carBrandName} - ${car.carModelName}</div>
                            <div class="car-price">
                                <fmt:formatNumber value="${car.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/> VNĐ
                            </div>
                            <div class="card-actions">
                                <button class="favorite-btn${favoriteGlobalKeys != null && favoriteGlobalKeys.contains(car.globalKey) ? ' favorited' : ''}"
                                        data-globalkey="${car.globalKey}"
                                        onclick="toggleFavorite('${car.globalKey}', this)">
                                    <span class="heart-icon"></span>
                                </button>
                                <a class="detail-btn" href="${pageContext.request.contextPath}/detail/${car.globalKey}">
                                    Xem chi tiết
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="no-history">
                    <p>Bạn chưa xem xe nào.</p>
                    <a href="${pageContext.request.contextPath}/" style="color:#007bff;text-decoration:underline;">Khám phá xe ngay!</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
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
