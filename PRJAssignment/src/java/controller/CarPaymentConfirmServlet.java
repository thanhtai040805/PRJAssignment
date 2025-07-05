// CarPaymentConfirmServlet.java
package controller;

import carDao.CarDao;
import model.Payment;
import paymentDAO.PaymentDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/confirm-payment")
public class CarPaymentConfirmServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        CarDao carDao = new CarDao();
        carDao.setEntityManager(em);
        PaymentDAO paymentDAO = new PaymentDAO();
        paymentDAO.setEntityManager(em);

        String carIdStr = request.getParameter("carId");
        String carName = request.getParameter("carName");
        String customerName = request.getParameter("customerName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String method = request.getParameter("paymentMethod");

        try {
            int carId = Integer.parseInt(carIdStr);
            if (customerName == null || phone == null || method == null) {
                throw new IllegalArgumentException("Missing required fields");
            }
            carDao.updateSoLuongTon(carId, 1);

            Payment payment = new Payment();
            payment.setInvoiceId(carId);
            payment.setAmount(Long.valueOf("1000000000"));
            payment.setStatus("Đã thanh toán");
            payment.setPaymentDate(java.sql.Date.valueOf(LocalDate.now()));
            paymentDAO.add(payment);

            request.setAttribute("message", "Bạn đã mua xe " + carName + " thành công!");
            em.close();
            request.getRequestDispatcher("/car/paymentConfirm.jsp").forward(request, response);

        } catch (Exception e) {
            em.close();
            response.sendRedirect(request.getContextPath() + "/error/500.jsp");
        }
    }
}
