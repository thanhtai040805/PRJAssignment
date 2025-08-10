<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<div class="container" style="max-width:1200px;margin:40px auto;background:#fff;padding:30px;border-radius:10px;box-shadow:0 4px 16px rgba(0,0,0,0.08);">
    <h2 style="color:#007bff;">Quản lý Nhân viên</h2>
    <form method="get" action="" style="margin-bottom:20px;display:flex;gap:10px;">
        <input type="text" name="search" placeholder="Tìm kiếm tên, email, SĐT, username..." value="${param.search}" style="flex:1;padding:8px;border-radius:6px;border:1px solid #ccc;"/>
        <button type="submit" style="background:#007bff;color:#fff;border:none;padding:8px 18px;border-radius:6px;font-weight:600;">Tìm kiếm</button>
    </form>
    <table style="width:100%;border-collapse:collapse;">
        <thead>
            <tr style="background:#f8f9fa;">
                <th>Họ tên</th>
                <th>Email</th>
                <th>SĐT</th>
                <th>Username</th>
                <th>Password</th>
                <th>Trạng thái</th>
                <th>Chức năng</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="e" items="${employees}">
                <tr>
                    <td>${e.HoTen}</td>
                    <td>${e.Email}</td>
                    <td>${e.SoDienThoai}</td>
                    <td>${e.Username}</td>

                    <td>${e.Password}</td>
                    <td>${e.TrangThai}</td>
                    <td>
                        <button style="background:#ffc107;color:#fff;border:none;padding:5px 12px;border-radius:5px;">Sửa</button>
                        <button style="background:#ff6b6b;color:#fff;border:none;padding:5px 12px;border-radius:5px;">Xóa</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <button style="margin-top:20px;background:#28a745;color:#fff;border:none;padding:8px 20px;border-radius:6px;font-weight:600;">Thêm nhân viên</button>
</div>
