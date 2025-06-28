package controller;

import carDao.CarDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Payment;
import paymentDAO.PaymentDAO;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.math.BigDecimal;

import java.io.IOException;

@WebServlet("/confirm-payment")
public class CarPaymentConfirmServlet extends HttpServlet {

    private final CarDao carDao = new CarDao();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String carIdStr = request.getParameter("carId");
        String carName = request.getParameter("carName");
        String customerName = request.getParameter("customerName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String method = request.getParameter("paymentMethod");

        try {
            int carId = Integer.parseInt(carIdStr);

            // Validate dữ liệu (tùy)
            if (customerName == null || phone == null || method == null) {
                throw new IllegalArgumentException("Missing required fields");
            }

            // Trừ số lượng xe
            carDao.updateSoLuongTon(carId, 1);

            // Tạo payment (nên viết constructor đầy đủ hoặc dùng setter)
            Payment payment = new Payment();
            payment.setMaHD(carId); // hoặc set theo hoá đơn thực tế
            payment.setSoTien(new BigDecimal("1000000000")); // test cứng
            payment.setTrangThai("Đã thanh toán");
            payment.setNgayThanhToan(LocalDate.now());
            // ... thêm các trường cần thiết

            paymentDAO.insertPayment(payment);

            request.setAttribute("message", "Bạn đã mua xe " + carName + " thành công!");
            request.getRequestDispatcher("/car/paymentConfirm.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error/500.jsp");
        }
    }

}
