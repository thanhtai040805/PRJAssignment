// CarPaymentConfirmServlet.java
package controller;

import carDao.CarDao;
import invoiceDAO.InvoiceDAO;
import invoicedetailDAO.InvoiceDetailDAO;
import model.Payment;
import paymentDAO.PaymentDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import model.Invoice;
import model.InvoiceDetail;

@WebServlet("/invoice/*")
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

        try {
            em.getTransaction().begin();

            // Lấy thông tin từ form
            int carId = Integer.parseInt(request.getParameter("carId"));
            String carName = request.getParameter("carName");
            long price = Long.parseLong(request.getParameter("price"));
            String customerName = request.getParameter("customerName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String paymentMethodName = request.getParameter("paymentMethod");

            // ===== DAO khởi tạo và gán EntityManager =====
            CarDao carDao = new CarDao();
            carDao.setEntityManager(em);
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.setEntityManager(em);
            InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();
            detailDAO.setEntityManager(em);
            PaymentDAO paymentDAO = new PaymentDAO();
            paymentDAO.setEntityManager(em);

            // 1. Giảm tồn kho xe
            boolean updated = carDao.updateSoLuongTon(carId, 1);
            if (!updated) {
                throw new RuntimeException("Không thể giảm số lượng xe");
            }

            // 2. Tạo hóa đơn
            Invoice invoice = new Invoice();
            invoice.setCustomerId(1); // giả định
            invoice.setEmployeeId(3); // giả định
            invoice.setInvoiceDate(new java.util.Date());
            invoice.setTotalAmount(price);
            invoice.setDiscountAmount(0L);
            invoice.setFinalAmount(price);
            invoice.setStatus("Đã giao");
            invoice.setNote("Khách hàng: " + customerName);

            Integer invoiceId = invoiceDAO.addInvoice(invoice);
            if (invoiceId == null) {
                throw new RuntimeException("Không thể tạo hóa đơn");
            }

            // 3. Tạo chi tiết hóa đơn
            InvoiceDetail detail = new InvoiceDetail();
            detail.setCarId(carId);
            detail.setQuantity(1);
            detail.setUnitPrice(price);
            detail.setTotalPrice(price);
            detail.setInvoice(invoice);

            boolean addedDetail = detailDAO.addInvoiceDetail(detail);
            if (!addedDetail) {
                throw new RuntimeException("Không thể thêm chi tiết hóa đơn");
            }

            // 4. Lấy mã phương thức thanh toán
            int paymentMethodId = getPaymentMethodIdByName(em, paymentMethodName);

            // 5. Tạo thanh toán
            Payment payment = new Payment();
            payment.setInvoiceId(invoiceId);
            payment.setPaymentMethodId(paymentMethodId);
            payment.setAmount(price);
            payment.setPaymentDate(new java.util.Date());
            payment.setStatus("Thành công");
            payment.setNote("Khách: " + customerName + ", Phone: " + phone);

            boolean addedPayment = paymentDAO.add(payment);
            if (!addedPayment) {
                throw new RuntimeException("Không thể thêm thanh toán");
            }

            // ===== Commit =====
            em.getTransaction().commit();

            request.setAttribute("message", "Bạn đã mua xe " + carName + " thành công!");
            request.getRequestDispatcher("/car/paymentConfirm.jsp").forward(request, response);

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error/500.jsp");
        } finally {
            em.close();
        }
    }

    private int getPaymentMethodIdByName(EntityManager em, String methodName) {
        String jpql = "SELECT p.paymentMethodId FROM PaymentMethod p WHERE p.paymentMethodName = :name";
        return em.createQuery(jpql, Integer.class)
                .setParameter("name", methodName)
                .getSingleResult();
    }
}
