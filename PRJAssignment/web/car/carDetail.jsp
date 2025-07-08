<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết xe - ${carDetail.carName}</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <style>
            :root {
                --primary-color: #007bff;
                --secondary-color: #6c757d;
                --accent-color: #28a745;
                --background-light: #f8f9fa;
                --card-background: #ffffff;
                --text-dark: #343a40;
                --text-muted: #6c757d;
                --border-color: #dee2e6;
            }
            body {
                font-family: 'Poppins', sans-serif;
                background-color: var(--background-light);
                margin: 0;
                padding: 20px;
                color: var(--text-dark);
                line-height: 1.6;
            }
            .container {
                max-width: 1200px;
                margin: 40px auto;
                background: var(--card-background);
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 8px 20px rgba(0,0,0,0.1);
                display: grid;
                grid-template-columns: 1fr 1.5fr;
                gap: 40px;
                align-items: center;
            }
            .car-image {
                flex: 1;
                text-align: center;
            }
            .car-image img {
                max-width: 100%;
                height: auto;
                border-radius: 10px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                object-fit: cover;
            }
            .car-info {
                flex: 2;
            }
            .car-info h1 {
                font-size: 2.2em;
                margin-bottom: 15px;
                color: var(--primary-color);
                font-weight: 700;
            }
            .car-info p {
                margin: 8px 0;
                font-size: 1em;
                display: flex;
                align-items: center;
            }
            .car-info p strong {
                min-width: 120px;
                display: inline-block;
                color: var(--text-muted);
                font-weight: 500;
            }
            .car-info p span {
                color: var(--text-dark);
                font-weight: 400;
            }
            .buttons {
                margin-top: 30px;
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
            }
            .buttons button {
                padding: 12px 25px;
                border: none;
                border-radius: 8px;
                background: linear-gradient(to right, #4facfe, #00f2fe);
                color: white;
                font-weight: 600;
                font-size: 1em;
                cursor: pointer;
                transition: transform 0.3s ease, box-shadow 0.3s ease, background 0.3s ease;
                flex-grow: 1;
                min-width: 150px;
            }
            .buttons button:hover {
                transform: translateY(-3px);
                box-shadow: 0 6px 15px rgba(0,0,0,0.2);
                background: linear-gradient(to right, #00f2fe, #4facfe);
            }
            /* Thêm style cho favorite button */
            .favorite-btn {
                display: flex;
                align-items: center;
                gap: 8px;
                padding: 12px 25px;
                border: none;
                border-radius: 8px;
                background: linear-gradient(to right, #4facfe, #00f2fe);
                color: #ff6b6b;
                font-weight: 600;
                font-size: 1em;
                cursor: pointer;
                transition: background 0.3s, color 0.3s, box-shadow 0.3s;
                min-width: 150px;
                border: 1.5px solid #ff6b6b;
                box-shadow: 0 2px 8px rgba(255,107,107,0.08);
                position: relative;
            }
            .favorite-btn .heart-icon {
                width: 22px;
                height: 22px;
                display: inline-block;
                background: url("data:image/svg+xml;utf8,\
                    <svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24'>\
                    <path fill='white' stroke='%23ff6b6b' stroke-width='2' d='M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 \
                    2 6 3.99 4 6.5 4c1.74 0 3.41 1.01 4.13 2.44h1.74C14.09 5.01 15.76 4 17.5 4 \
                    20.01 4 22 6 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z'/>\
                    </svg>") no-repeat center center;
                background-size: contain;
                transition: background 0.3s;
            }
            .favorite-btn .favorite-text {
                font-size: 1em;
                font-weight: 600;
                color: #ff6b6b;
                transition: color 0.3s;
            }

            /* Trạng thái đã yêu thích */
            .favorite-btn.favorited {
                background: #fff;
                color: #ff6b6b;
                border: 2px solid #ff6b6b;
                box-shadow: 0 2px 8px rgba(255,107,107,0.12);
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
            .favorite-btn.favorited .favorite-text {
                color: #ff6b6b;
            }

            /* Hover hiệu ứng */
            .favorite-btn:hover, .favorite-btn.favorited:hover {
                background: #ff6b6b;
                color: #fff;
            }
            .favorite-btn:hover .favorite-text,
            .favorite-btn.favorited:hover .favorite-text {
                color: #fff;
            }
            .favorite-btn:hover .heart-icon,
            .favorite-btn.favorited:hover .heart-icon {
                background: url("data:image/svg+xml;utf8,\
                    <svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24'>\
                    <path fill='white' d='M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 \
                    2 6 3.99 4 6.5 4c1.74 0 3.41 1.01 4.13 2.44h1.74C14.09 5.01 15.76 4 17.5 4 \
                    20.01 4 22 6 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z'/>\
                    </svg>") no-repeat center center;
                background-size: contain;
            }

            .suggestions {
                max-width: 1200px;
                margin: 50px auto;
                background: var(--card-background);
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            }
            .suggestions h2 {
                font-size: 2em;
                margin-bottom: 25px;
                color: var(--primary-color);
                font-weight: 700;
                text-align: center;
            }
            .suggestion-grid {
                display: grid;
                grid-template-columns: repeat(4, 1fr);
                gap: 25px;
                justify-content: start;
            }
            .car-suggestion {
                background: var(--background-light);
                padding: 20px;
                border-radius: 10px;
                text-align: center;
                box-shadow: 0 4px 10px rgba(0,0,0,0.08);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
                border: 1px solid var(--border-color);
            }
            .car-suggestion:hover {
                transform: translateY(-7px);
                box-shadow: 0 8px 20px rgba(0,0,0,0.15);
            }
            .car-suggestion img {
                width: 100%;
                height: 150px;
                object-fit: cover;
                border-radius: 8px;
                margin-bottom: 15px;
            }
            .car-suggestion a {
                text-decoration: none;
                color: var(--text-dark);
                font-weight: 600;
                font-size: 1.1em;
                display: block;
            }
            .car-suggestion p {
                margin: 5px 0;
                font-weight: 500;
                font-size: 0.95em;
            }
            .car-suggestion p:last-child {
                color: var(--accent-color);
                font-weight: 700;
                font-size: 1em;
            }
            @media (max-width: 992px) {
                .container {
                    grid-template-columns: 1fr;
                    gap: 30px;
                }
                .car-info h1 {
                    font-size: 2em;
                    text-align: center;
                }
                .car-info p {
                    flex-direction: column;
                    align-items: flex-start;
                }
                .car-info p strong {
                    min-width: unset;
                    margin-bottom: 3px;
                }
                .car-image {
                    order: -1;
                }
                .suggestion-grid {
                    grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
                }
            }
            @media (max-width: 768px) {
                body {
                    padding: 15px;
                }
                .container {
                    padding: 20px;
                    margin: 20px auto;
                }
                .car-info h1 {
                    font-size: 1.8em;
                }
                .buttons button {
                    font-size: 0.9em;
                    padding: 10px 20px;
                    min-width: 120px;
                }
                .suggestions {
                    padding: 20px;
                }
                .suggestions h2 {
                    font-size: 1.8em;
                }
                .suggestion-grid {
                    grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
                }
            }
            @media (max-width: 576px) {
                .container {
                    grid-template-columns: 1fr;
                    padding: 15px;
                    gap: 20px;
                }
                .car-info h1 {
                    font-size: 1.5em;
                }
                .buttons {
                    flex-direction: column;
                }
                .buttons button {
                    width: 100%;
                }
                .suggestions {
                    padding: 15px;
                }
                .suggestions h2 {
                    font-size: 1.5em;
                }
                .suggestion-grid {
                    grid-template-columns: 1fr;
                }
                .car-suggestion img {
                    height: 200px;
                }
            }
        </style>
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <div class="container">
            <div class="car-image">
                <c:choose>
                    <c:when test="${not empty carDetail.imageLink}">
                        <img src="${pageContext.request.contextPath}${carDetail.imageLink}" alt="Ảnh xe">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/images/no-image.png" alt="Ảnh xe">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="car-info">
                <h1>${carDetail.carName}</h1>
                <p><strong>Giá bán:</strong>
                    <span>
                        <fmt:formatNumber value="${carDetail.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/>
                    </span>
                </p>
                <p><strong>Tình trạng:</strong> <span>${carDetail.condition}</span></p>
                <p><strong>Động cơ:</strong>
                    <span>
                        <c:out value="${carDetail.engineCapacity}"/>cc -
                        <c:out value="${carDetail.power}"/>HP
                    </span>
                </p>
                <p><strong>Nhiên liệu:</strong> <span>${carDetail.fuelType}</span></p>
                <p><strong>Năm sản xuất:</strong> <span>${carDetail.year}</span></p>
                <p><strong>Màu sắc:</strong> <span>${carDetail.color}</span></p>
                <p><strong>Số chỗ ngồi:</strong> <span>${carDetail.seatCount}</span></p>
                <p><strong>Mô tả:</strong> <span>${carDetail.description}</span></p>
                <div class="buttons">
                    <button onclick="location.href = '${pageContext.request.contextPath}/loanForm?carId=${carDetail.carId}'">Làm khoản vay</button>
                    <button onclick="location.href = '${pageContext.request.contextPath}/payment/${carDetail.globalKey}'">Mua xe</button>
                    <div class="favorite-action">
                        <button class="favorite-btn${favoriteGlobalKeys != null && favoriteGlobalKeys.contains(carDetail.globalKey) ? ' favorited' : ''}"
                                data-globalkey="${carDetail.globalKey}"
                                onclick="toggleFavorite('${carDetail.globalKey}', this)">
                            <span class="heart-icon"></span>
                            <span class="favorite-text">
                                Yêu thích
                            </span>
                        </button>
                    </div>
                </div>

            </div>
        </div>
        <c:if test="${not empty suggestionCars}">
            <section class="suggestions">
                <h2 class="section-title">Xe gợi ý dựa trên sở thích của bạn</h2>
                <div class="suggestion-grid">
                    <c:forEach var="c" items="${suggestionCars}">
                        <div class="car-suggestion">
                            <a href="${pageContext.request.contextPath}/detail/${c.globalKey}">
                                <c:choose>
                                    <c:when test="${not empty c.imageLink}">
                                        <img src="${pageContext.request.contextPath}${c.imageLink}" alt="Xe gợi ý">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/images/no-image.png" alt="Xe gợi ý">
                                    </c:otherwise>
                                </c:choose>
                                <p>${c.carName}</p>
                                <p>
                                    <fmt:formatNumber value="${c.salePrice}" type="currency" currencyCode="VND" pattern="#,###"/>
                                </p>
                            </a>
                            <div class="buttons">
                                <button onclick="location.href = '${pageContext.request.contextPath}/payment/${c.globalKey}'">Mua xe</button>
                                <div class="favorite-action">
                                    <button class="favorite-btn${favoriteGlobalKeys != null && favoriteGlobalKeys.contains(c.globalKey) ? ' favorited' : ''}"
                                            data-globalkey="${c.globalKey}"
                                            onclick="toggleFavorite('${c.globalKey}', this)">
                                        <span class="heart-icon"></span>
                                        <span class="favorite-text">
                                            Yêu thích
                                        </span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </section>
        </c:if>

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

            var currentGlobalKey = '${carDetail.globalKey}';
            document.addEventListener("DOMContentLoaded", function () {
            <c:if test="${empty sessionScope.currentUser}">
                saveViewedCar(currentGlobalKey);
            </c:if>
            <c:if test="${not empty sessionScope.currentUser}">
                saveViewedCarToServer(currentGlobalKey);
            </c:if>
            });
        </script>
    </body>
</html>
