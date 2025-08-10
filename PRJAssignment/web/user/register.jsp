<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>Đăng ký tài khoản</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/register.css" />
    </head>
    <body>
        <h2>Đăng ký tài khoản mới</h2>

        <c:if test="${not empty error}">
            <p style="color:red;"><c:out value="${error}"/></p>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/register"
              style="max-width:400px;margin:40px auto;background:#fff;padding:30px;border-radius:10px;
              box-shadow:0 4px 16px rgba(0,0,0,0.08);">

            <h2 style="color:#007bff;text-align:center;">Đăng ký tài khoản</h2>

            <div style="margin-bottom:18px;">
                <label for="fullName">Họ và tên:</label>
                <input type="text" id="fullName" name="fullName" required style="width:100%;padding:8px;"/>
            </div>

            <div style="margin-bottom:18px;">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required style="width:100%;padding:8px;"/>
            </div>

            <div style="margin-bottom:18px;">
                <label for="phone">Số điện thoại:</label>
                <input type="text" id="phone" name="phone" required style="width:100%;padding:8px;"/>
            </div>

            <div style="margin-bottom:18px;">
                <label for="identityCard">CCCD:</label>
                <input type="text" id="identityCard" name="identityCard" required style="width:100%;padding:8px;"/>
            </div>

            <div style="margin-bottom:18px;">
                <label for="address">Địa chỉ:</label>
                <input type="text" id="address" name="address" required style="width:100%;padding:8px;"/>
            </div>

            <div style="margin-bottom:18px;">
                <label for="username">Tài khoản:</label>
                <input type="text" id="username" name="username" required style="width:100%;padding:8px;"/>
            </div>

            <div style="margin-bottom:18px;">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required style="width:100%;padding:8px;"/>
            </div>

            <div style="margin-bottom:18px;">
                <label for="confirmPassword">Xác nhận mật khẩu:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required style="width:100%;padding:8px;"/>
            </div>

            <div style="margin-bottom:18px;">
                <input type="checkbox" id="terms" name="terms" required />
                <label for="terms">Tôi đồng ý với <a href="#">Điều khoản sử dụng</a></label>
            </div>

            <button type="submit"
                    style="width:100%;background:#007bff;color:#fff;border:none;padding:10px;
                    border-radius:6px;font-weight:600;font-size:1.1em;">
                Đăng ký
            </button>

            <p style="margin-top:18px;text-align:center;">
                Đã có tài khoản?
                <a href="${pageContext.request.contextPath}/login" style="color:#007bff;">Đăng nhập</a>
            </p>
        </form>

    </body>
</html>