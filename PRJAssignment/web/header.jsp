<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>x`
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

    .auth-links span {
        color: white;
        font-weight: 300;
        font-size: 14px;
    }

    @media (max-width: 768px) {
        .header-content {
            flex-direction: column;
            gap: 10px;
        }

        .logo h1 {
            font-size: 24px;
        }

        .auth-links {
            flex-direction: column;
            gap: 10px;
        }
    }
</style>

<header class="header">
    <div class="header-content">
        <div class="logo">
            <div class="logo-icon">🚗</div>
            <a href="${pageContext.request.contextPath}"><h1>DriveDreams</h1></a>
        </div>
        <div class="auth-links">
            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <span>Xin chào, ${sessionScope.currentUser.username}!</span>
                    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                    <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
