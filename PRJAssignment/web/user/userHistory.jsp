<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lịch sử tìm kiếm của bạn</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Poppins', Arial, sans-serif; margin: 0; background: #f7f8fa; }
        .container {
            max-width: 700px;
            margin: 40px auto;
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.10);
            padding: 35px 40px 40px 40px;
        }
        h2 { color: #007bff; text-align: center; margin-bottom: 30px; }
        .history-list { list-style: none; padding: 0; margin: 0; }
        .history-list li {
            margin-bottom: 15px;
            background: #f4f8fb;
            padding: 14px 22px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            transition: box-shadow 0.2s;
            box-shadow: 0 2px 8px rgba(0,123,255,0.04);
        }
        .history-list li a {
            color: #007bff;
            font-weight: 600;
            font-size: 1.05em;
            text-decoration: none;
            flex: 1;
            overflow-wrap: anywhere;
            transition: color 0.2s;
        }
        .history-list li a:hover {
            color: #0056b3;
            text-decoration: underline;
        }
        .empty-msg {
            color: #888;
            font-style: italic;
            text-align: center;
            margin: 60px 0 40px 0;
        }
        .back-btn {
            display: inline-block;
            margin-bottom: 22px;
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
            text-decoration: none;
        }
        .back-btn:hover {
            background: #007bff;
            color: #fff;
        }
    </style>
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
