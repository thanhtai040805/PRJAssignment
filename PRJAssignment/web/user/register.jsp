<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>Đăng ký tài khoản</title>
    </head>
    <body>
        <h2>Đăng ký tài khoản mới</h2>

    <c:if test="${not empty error}">
        <p style="color:red;"><c:out value="${error}"/></p>
</c:if>

<form action="<c:url value='/register'/>" method="post">
    Tên đăng nhập (Username): <input type="text" name="username" value="<c:out value="${param.username}"/>" required><br/>
    Mật khẩu (Password): <input type="password" name="password" required><br/>

    Vai trò (Role):
    <select name="role" required>
        <option value="CUSTOMER" <c:if test="${param.role eq 'CUSTOMER'}">selected</c:if>>Khách hàng</option>
        <option value="EMPLOYEE" <c:if test="${param.role eq 'EMPLOYEE'}">selected</c:if>>Nhân viên</option>
        <option value="ADMIN" <c:if test="${param.role eq 'ADMIN'}">selected</c:if>>Admin</option>
    </select><br/>

    Mã Khách hàng (nếu có): <input type="number" name="maKH" value="<c:out value="${param.maKH}"/>"><br/>
    Mã Nhân viên (nếu có): <input type="number" name="maNV" value="<c:out value="${param.maNV}"/>"><br/>

    <input type="submit" value="Đăng ký">
</form>

<p><a href="<c:url value='/user/login.jsp'/>">Đã có tài khoản? Đăng nhập</a></p>
</body>
</html>