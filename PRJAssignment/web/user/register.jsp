<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng ký tài khoản</title>
</head>
<body>
<h2>Đăng ký tài khoản mới</h2>

<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <p style="color:red;"><%= error %></p>
<% } %>

<form action="<%=request.getContextPath()%>/register" method="post">
    Tên đăng nhập (Username): <input type="text" name="username" required><br/>
    Mật khẩu (Password): <input type="password" name="password" required><br/>

    Vai trò (Role):
    <select name="role" required>
        <option value="khachhang">Khách hàng</option>
        <option value="nhanvien">Nhân viên</option>
        <option value="admin">Admin</option>
    </select><br/>

    Mã Khách hàng (nếu có): <input type="number" name="maKH"><br/>
    Mã Nhân viên (nếu có): <input type="number" name="maNV"><br/>

    <input type="submit" value="Đăng ký">
</form>

<p><a href="login.jsp">Đã có tài khoản? Đăng nhập</a></p>
</body>
</html>
