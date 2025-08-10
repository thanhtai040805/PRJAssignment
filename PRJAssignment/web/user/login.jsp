<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập - CarShop</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/login.css" />
    </head>
    <body>
        <div class="login-container">
            <form class="login-form" action="<c:url value='/login'/>" method="post">
                <h2>Đăng nhập hệ thống</h2>

                <div class="form-group">
                    <label for="username">Tên đăng nhập:</label>
                    <input type="text" id="username" name="username" value="<c:out value='${username}'/>" required>
                </div>

                <div class="form-group">
                    <label for="password">Mật khẩu:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="login-btn">Đăng nhập</button>

                <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        <c:out value="${errorMessage}"/>
                    </div>
                </c:if>

                <c:if test="${param.registered eq 'true'}">
                    <div class="success-message">
                        Đăng ký tài khoản thành công! Vui lòng đăng nhập.
                    </div>
                </c:if>

                <p style="text-align: center; margin-top: 20px;">
                    Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký ngay</a>
                </p>
            </form>

            <!-- Social login -->
            <div class="social-login">
                <p style="text-align:center;margin-top:15px;font-weight:600;">Hoặc đăng nhập bằng</p>
                <button onclick="window.location.href = '${pageContext.request.contextPath}/login-google'"
                        class="social-btn google">
                    <svg xmlns="http://www.w3.org/2000/svg" height="22" viewBox="0 0 24 24" width="22" style="vertical-align:middle;margin-right:8px;">
                    <g>
                    <path fill="#4285F4" d="M21.6 12.227c0-.81-.072-1.597-.21-2.36H12v4.469h5.443a4.635 4.635 0 01-2.01 3.044v2.514h3.243c1.899-1.75 2.924-4.324 2.924-7.667z"/>
                    <path fill="#34A853" d="M12 22c2.43 0 4.467-.805 5.956-2.19l-3.243-2.514c-.902.604-2.059.967-3.355.967-2.577 0-4.764-1.741-5.549-4.076H2.766v2.561A10 10 0 0012 22z"/>
                    <path fill="#FBBC05" d="M6.451 13.187a5.994 5.994 0 010-3.814V6.813H2.766a10 10 0 000 9.374l3.685-2.561z"/>
                    <path fill="#EA4335" d="M12 6.213c1.324 0 2.517.456 3.456 1.35l2.589-2.508C16.462 3.573 14.425 2.767 12 2.767a10 10 0 00-7.234 2.87l3.685 2.561C7.236 7.07 9.423 6.213 12 6.213z"/>
                    </g>
                    </svg>
                    Google
                </button>
                <button onclick="window.location.href = '${pageContext.request.contextPath}/login-facebook'"
                        class="social-btn facebook">
                    <svg xmlns="http://www.w3.org/2000/svg" height="22" viewBox="0 0 24 24" width="22" style="vertical-align:middle;margin-right:8px;">
                    <path fill="#1877F3" d="M24 12a12 12 0 1 0-13.875 11.844v-8.382h-2.463V12h2.463V9.797c0-2.434 1.448-3.777 3.668-3.777.755 0 1.547.133 1.547.133v1.7h-.871c-.858 0-1.124.531-1.124 1.077V12h2.711l-.433 3.462h-2.278v8.382A12.006 12.006 0 0 0 24 12z"/>
                    </svg>
                    Facebook
                </button>
            </div>

        </div>
    </body>
</html>
