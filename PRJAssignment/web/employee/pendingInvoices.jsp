<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Danh sách hóa đơn chờ xử lý</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f8;
            margin: 40px;
            color: #333;
        }

        h2 {
            text-align: center;
            color: #4a90e2;
        }

        table {
            border-collapse: collapse;
            margin: 30px auto;
            width: 90%;
            max-width: 900px;
            background-color: white;
            box-shadow: 0 0 15px rgba(74, 144, 226, 0.3);
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            padding: 14px 20px;
            text-align: left;
        }

        th {
            background-color: #4a90e2;
            color: #ffffff;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        tbody tr:nth-child(even) {
            background-color: #f9fbfd;
        }

        tbody tr:hover {
            background-color: #e6f0fa;
            cursor: pointer;
        }

        a {
            color: #4a90e2;
            text-decoration: none;
            font-weight: 500;
        }

        a:hover {
            text-decoration: underline;
        }

        p {
            text-align: center;
            font-style: italic;
            color: #666;
            margin-top: 50px;
        }
    </style>
</head>
<body>
    <h2>Hóa đơn chờ xử lý</h2>

    <c:if test="${empty pendingInvoices}">
        <p>Không có hóa đơn nào đang chờ xử lý.</p>
    </c:if>

    <c:if test="${not empty pendingInvoices}">
        <table>
            <thead>
                <tr>
                    <th>Mã hóa đơn</th>
                    <th>Ngày lập</th>
                    <th>Khách hàng</th>
                    <th>Ghi chú</th>
                    <th>Chi tiết</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="invoice" items="${pendingInvoices}">
                    <tr onclick="window.location='${pageContext.request.contextPath}/employee/confirmInvoice?invoiceId=${invoice.invoiceId}'">
                        <td>${invoice.invoiceId}</td>
                        <td><fmt:formatDate value="${invoice.invoiceDate}" pattern="dd/MM/yyyy"/></td>
                        <td>
                            <c:choose>
                              <c:when test="${not empty invoice.customer and not empty invoice.customer.fullName}">
                                ${invoice.customer.fullName}
                              </c:when>
                              <c:otherwise>Không có khách hàng</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${invoice.note}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/employee/confirmInvoice?invoiceId=${invoice.invoiceId}">Xem chi tiết</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
