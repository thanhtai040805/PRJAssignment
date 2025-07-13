<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xe Yêu Thích Của Bạn</title>
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
            margin-bottom: 35px;
            font-weight: 700;
        }
        .favorite-list {
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
        .remove-btn {
            background: #fff;
            color: #ff6b6b;
            border: 1px solid #ff6b6b;
            border-radius: 20px;
            padding: 7px 16px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            margin-top: 8px;
            transition: background 0.2s, color 0.2s;
        }
        .remove-btn:disabled {
            opacity: 0.6;
            cursor: not-allowed;
        }
        .remove-btn:hover:enabled {
            background: #ff6b6b;
            color: #fff;
        }
        .no-favorite {
            text-align: center;
            color: #888;
            font-size: 1.15em;
            margin: 70px 0 40px 0;
        }
        @media (max-width: 700px) {
            .container { padding: 15px 5px 25px 5px; }
            .favorite-list { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>
    <jsp:include page="/header.jsp" />
    <div class="container">
        <h1>Xe Yêu Thích Của Bạn</h1>
        <div id="favoriteContent">
            <c:choose>
                <c:when test="${not empty favoriteCars}">
                    <div class="favorite-list" id="favoriteList">
                        <c:forEach var="car" items="${favoriteCars}">
                            <div class="car-card">
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
                                <button class="remove-btn" onclick="removeFavorite('${car.globalKey}', this)">Bỏ yêu thích</button>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="no-favorite" id="noFavoriteMsg">
                        <p>Bạn chưa có xe nào trong danh sách yêu thích.</p>
                        <a href="${pageContext.request.contextPath}/" style="color:#007bff;text-decoration:underline;">Khám phá xe ngay!</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script>
    function removeFavorite(globalKey, btn) {
        btn.disabled = true;
        fetch('${pageContext.request.contextPath}/updateFavorite', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'globalKey=' + encodeURIComponent(globalKey)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Xóa thẻ card khỏi DOM
                const card = btn.closest('.car-card');
                card.remove();
                // Nếu không còn xe nào, hiển thị thông báo
                const list = document.getElementById('favoriteList');
                if (!list || list.children.length === 0) {
                    document.getElementById('favoriteContent').innerHTML =
                        '<div class="no-favorite"><p>Bạn chưa có xe nào trong danh sách yêu thích.</p>' +
                        '<a href="${pageContext.request.contextPath}/" style="color:#007bff;text-decoration:underline;">Khám phá xe ngay!</a></div>';
                }
            } else {
                alert(data.message || 'Bỏ yêu thích thất bại!');
                btn.disabled = false;
            }
        })
        .catch(() => {
            alert('Có lỗi xảy ra!');
            btn.disabled = false;
        });
    }
    </script>
</body>
</html>
