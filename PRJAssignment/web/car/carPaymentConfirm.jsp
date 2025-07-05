<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Xác nhận thanh toán</title>
    </head>
    <body>
        <jsp:include page="/header.jsp" />
        <h2>${message}</h2>
        <p>Cảm ơn bạn đã mua xe tại hệ thống của chúng tôi!</p>
        <a href="<%= request.getContextPath()%>/home.jsp">Về trang chủ</a>
    </body>
</html>
