<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="header">
    <div class="header-content">
        <div class="logo">
            <div class="logo-icon">?</div>
            <h1>DriveDreams</h1>
        </div>
        <div class="auth-links">
            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <span>Xin chào, ${sessionScope.currentUser.username}!</span>
                    <a href="${pageContext.request.contextPath}/logout">??ng xu?t</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Login</a>
                    <a href="${pageContext.request.contextPath}/register">Register</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
