package controller;

import carDao.CarDao;
import invoiceDAO.InvoiceDAO;
import invoicedetailDAO.InvoiceDetailDAO;
import model.*;
import paymentDAO.PaymentDAO;
import paymentmethodDAO.PaymentMethodDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import utils.Email;

@WebServlet("/invoice")
public class CarPaymentConfirmServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String confirm = request.getParameter("confirm");

        if (confirm == null) {
            // ===== BƯỚC 1: HIỂN THỊ TRANG XÁC NHẬN carPaymentConfirm.jsp =====
            EntityManager em = emf.createEntityManager();
            try {
                int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));
                PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();
                paymentMethodDAO.setEntityManager(em);
                PaymentMethod paymentMethod = paymentMethodDAO.findById(paymentMethodId);
                if (paymentMethod == null) {
                    throw new RuntimeException("Phương thức thanh toán không tồn tại.");
                }

                // Truyền thông tin sang trang xác nhận
                request.setAttribute("carId", request.getParameter("carId"));
                request.setAttribute("carName", request.getParameter("carName"));
                request.setAttribute("price", request.getParameter("price"));
                request.setAttribute("customerName", request.getParameter("customerName"));
                request.setAttribute("phone", request.getParameter("phone"));
                request.setAttribute("email", request.getParameter("email"));
                request.setAttribute("address", request.getParameter("address"));
                request.setAttribute("paymentMethodId", paymentMethodId);
                request.setAttribute("paymentMethodName", paymentMethod.getPaymentMethodName());

                request.getRequestDispatcher("/car/carPaymentConfirm.jsp").forward(request, response);
            } finally {
                em.close();
            }
            return;
        }

        // ===== BƯỚC 2: XỬ LÝ & LƯU DB =====
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            int carId = Integer.parseInt(request.getParameter("carId"));
            String carName = request.getParameter("carName");
            long price = Long.parseLong(request.getParameter("price"));
            String customerName = request.getParameter("customerName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId"));

            // ===== DAO khởi tạo =====
            CarDao carDao = new CarDao();
            carDao.setEntityManager(em);
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.setEntityManager(em);
            InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();
            detailDAO.setEntityManager(em);
            PaymentDAO paymentDAO = new PaymentDAO();
            paymentDAO.setEntityManager(em);
            PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();
            paymentMethodDAO.setEntityManager(em);

            // 1. Giảm số lượng xe
            boolean updated = carDao.updateSoLuongTon(carId, 1);
            if (!updated) {
                throw new RuntimeException("Không thể giảm số lượng xe tồn kho.");
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
                throw new RuntimeException("Không thể tạo hóa đơn.");
            }

            // 3. Thêm chi tiết hóa đơn
            InvoiceDetail detail = new InvoiceDetail();
            detail.setCarId(carId);
            detail.setQuantity(1);
            detail.setUnitPrice(price);
            detail.setTotalPrice(price);
            detail.setInvoice(invoice);

            boolean addedDetail = detailDAO.addInvoiceDetail(detail);
            if (!addedDetail) {
                throw new RuntimeException("Không thể thêm chi tiết hóa đơn.");
            }

            // 4. Thêm thanh toán
            Payment payment = new Payment();
            payment.setInvoiceId(invoiceId);
            payment.setPaymentMethodId(paymentMethodId);
            payment.setAmount(price);
            payment.setPaymentDate(new java.util.Date());
            payment.setStatus("Thành công");
            payment.setNote("Khách: " + customerName + ", Phone: " + phone);

            boolean addedPayment = paymentDAO.add(payment);
            if (!addedPayment) {
                throw new RuntimeException("Không thể thêm thanh toán.");
            }

            em.getTransaction().commit();

            // 5. Gửi email xác nhận
            String subject = "Xác nhận mua xe " + carName;
            String content = "Cảm ơn quý khách " + customerName + " đã đặt mua xe " + carName + " trị giá " + price + " VND.\n"
                    + "Chúng tôi sẽ sớm liên hệ để hoàn tất thủ tục.\n"
                    + "Thông tin liên hệ: " + phone + " | " + email + "\n\n"
                    + "Trân trọng,\nShowroom ABC";

            try {
                Email.sendEmail(email, subject, content);
            } catch (Exception e) {
                e.printStackTrace(); // không rollback nếu chỉ lỗi gửi email
            }

            request.setAttribute("carName", carName);
            request.setAttribute("customerName", customerName);
            request.getRequestDispatcher("/car/success.jsp").forward(request, response);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error/500.jsp");
        } finally {
            em.close();
        }
    }
}
