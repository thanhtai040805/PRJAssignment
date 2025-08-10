<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Xác nhận hóa đơn</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

    body {
      font-family: 'Roboto', sans-serif;
      background: #f5f7fa;
      margin: 0;
      padding: 40px 20px;
      color: #333;
    }

    .invoice-box {
      max-width: 600px;
      background: #fff;
      margin: 0 auto;
      border-radius: 12px;
      box-shadow: 0 16px 40px rgba(0,0,0,0.12);
      padding: 30px 40px;
      box-sizing: border-box;
      animation: fadeIn 0.5s ease-in;
    }

    h2 {
      text-align: center;
      margin-bottom: 30px;
      color: #2c3e50;
      font-weight: 700;
      letter-spacing: 1.2px;
    }

    p {
      font-size: 16px;
      margin: 12px 0;
    }

    p strong {
      color: #34495e;
      width: 130px;
      display: inline-block;
    }

    .actions {
      text-align: center;
      margin-top: 30px;
    }

    .btn {
      padding: 12px 36px;
      margin: 0 10px;
      border: none;
      border-radius: 30px;
      font-weight: 600;
      font-size: 16px;
      cursor: pointer;
      min-width: 140px;
      box-shadow: 0 6px 14px rgba(0,0,0,0.15);
      transition: all 0.3s ease;
      color: #fff;
    }

    .btn-confirm {
      background: linear-gradient(45deg, #27ae60, #2ecc71);
    }

    .btn-confirm:hover {
      background: linear-gradient(45deg, #2ecc71, #27ae60);
      box-shadow: 0 10px 20px rgba(46,204,113,0.6);
    }

    .btn-deliver {
      background: linear-gradient(45deg, #2980b9, #3498db);
    }

    .btn-deliver:hover {
      background: linear-gradient(45deg, #3498db, #2980b9);
      box-shadow: 0 10px 20px rgba(52,152,219,0.6);
    }

    .btn-cancel {
      background: linear-gradient(45deg, #c0392b, #e74c3c);
    }

    .btn-cancel:hover {
      background: linear-gradient(45deg, #e74c3c, #c0392b);
      box-shadow: 0 10px 20px rgba(231,76,60,0.6);
    }

    /* Responsive */
    @media (max-width: 480px) {
      .invoice-box {
        padding: 20px;
      }

      .btn {
        min-width: 100px;
        padding: 10px 20px;
        font-size: 14px;
        margin: 8px 5px;
      }
    }

    @keyframes fadeIn {
      from {opacity: 0; transform: translateY(20px);}
      to {opacity:1; transform: translateY(0);}
    }
  </style>
</head>
<body>

<jsp:include page="/header.jsp" />

<div class="invoice-box">
  <h2>Chi tiết hóa đơn</h2>

  <p><strong>Mã hóa đơn:</strong> ${invoice.invoiceId}</p>
  <p>
    <strong>Khách hàng:</strong>
    <c:choose>
      <c:when test="${not empty invoice.customer and not empty invoice.customer.fullName}">
        ${invoice.customer.fullName}
      </c:when>
      <c:otherwise>Không có khách hàng</c:otherwise>
    </c:choose>
  </p>
  <%-- <p><strong>Nhân viên phụ trách:</strong> ${invoice.nhanVien.fullName}</p> --%>
  <p><strong>Trạng thái:</strong> ${invoice.status}</p>
  <%-- <p><strong>Phương thức thanh toán:</strong> ${invoice.thanhToan.phuongThucThanhToan.tenPhuongThuc}</p> --%>

  <c:if test="${invoice.status eq 'Chờ xử lý'}">
    <form method="post" action="${pageContext.request.contextPath}/employee/confirmInvoice">
      <input type="hidden" name="action" value="confirm" />
      <input type="hidden" name="invoiceId" value="${invoice.invoiceId}" />
      <div class="actions">
        <button type="submit" class="btn btn-confirm">Xác nhận</button>
      </div>
    </form>
  </c:if>

  <c:if test="${invoice.status eq 'Đã xác nhận'}">
    <form method="post" action="${pageContext.request.contextPath}/employee/confirmInvoice">
      <input type="hidden" name="invoiceId" value="${invoice.invoiceId}" />
      <div class="actions">
        <button type="submit" name="action" value="delivered" class="btn btn-deliver">Đã giao</button>
        <button type="submit" name="action" value="cancelled" class="btn btn-cancel">Đã hủy</button>
      </div>
    </form>
  </c:if>
</div>

</body>
</html>
