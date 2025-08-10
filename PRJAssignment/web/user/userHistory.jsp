<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lịch sử tìm kiếm của bạn</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/searchHistory.css" />
</head>
<body>
    <jsp:include page="/header.jsp" />
    <div class="container">
        <a href="${pageContext.request.contextPath}/viewed-cars-history" class="back-btn">← Lịch sử xe đã xem</a>
        <h2>Lịch sử tìm kiếm gần đây của bạn</h2>
        <c:choose>
            <c:when test="${not empty searchHistory}">
                <ul class="history-list">
                    <c:forEach var="item" items="${searchHistory}">
                        <li>
                            <a href="${pageContext.request.contextPath}${item}">
                                <span style="vertical-align:middle;">🔍</span>
                                <span style="margin-left:8px;">${item}</span>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <div class="empty-msg">Bạn chưa có lịch sử tìm kiếm nào.</div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
