package controller;

import carDao.CarDao;
import employeeDAO.EmployeeDAO;
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
import util.Email;

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
                int employeeId = Integer.parseInt(request.getParameter("employeeId"));
                String appointmentDate = request.getParameter("appointmentDate");

                // Lấy phương thức thanh toán
                PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();
                paymentMethodDAO.setEntityManager(em);
                PaymentMethod paymentMethod = paymentMethodDAO.findById(paymentMethodId);
                if (paymentMethod == null) {
                    throw new RuntimeException("Phương thức thanh toán không tồn tại.");
                }

                // Lấy thông tin nhân viên
                EmployeeDAO employeeDAO = new EmployeeDAO();
                employeeDAO.setEntityManager(em);
                Employee employee = employeeDAO.findById(employeeId);
                if (employee == null) {
                    throw new RuntimeException("Nhân viên không tồn tại.");
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

                // Thêm employee & lịch hẹn
                request.setAttribute("employeeId", employeeId);
                request.setAttribute("employeeName", employee.getFullName()); // đổi thành getTenNhanVien() nếu tên hàm khác
                request.setAttribute("appointmentDate", appointmentDate);

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
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            String appointmentDateStr = request.getParameter("appointmentDate");
            java.util.Date appointmentDate = java.sql.Date.valueOf(appointmentDateStr);

            // DAO
            CarDao carDao = new CarDao();
            carDao.setEntityManager(em);
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.setEntityManager(em);
            InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();
            detailDAO.setEntityManager(em);
            PaymentDAO paymentDAO = new PaymentDAO(em);


            // 1. Giảm số lượng xe
            if (!carDao.updateSoLuongTon(carId, 1)) {
                throw new RuntimeException("Không thể giảm số lượng xe tồn kho.");
            }

            // 2. Tạo hóa đơn
            Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
            if (customer == null) {
                throw new RuntimeException("Khách hàng chưa đăng nhập.");
            }

            Invoice invoice = new Invoice();
            invoice.setCustomerId(customer.getCustomerId());
            invoice.setEmployeeId(employeeId);
            invoice.setInvoiceDate(new java.util.Date());
            invoice.setTotalAmount(price);
            invoice.setDiscountAmount(0L);
            invoice.setFinalAmount(price);
            invoice.setStatus("Chờ xử lý");
            invoice.setNote("Khách hàng: " + customerName);

            Integer invoiceId = invoiceDAO.addInvoice(invoice);
            if (invoiceId == null) {
                throw new RuntimeException("Không thể tạo hóa đơn.");
            }

            // 3. Chi tiết hóa đơn
            InvoiceDetail detail = new InvoiceDetail();
            detail.setCarId(carId);
            detail.setQuantity(1);
            detail.setUnitPrice(price);
            detail.setTotalPrice(price);
            detail.setInvoice(invoice);

            if (!detailDAO.addInvoiceDetail(detail)) {
                throw new RuntimeException("Không thể thêm chi tiết hóa đơn.");
            }

            // 4. Thanh toán
            Payment payment = new Payment();
            payment.setInvoiceId(invoiceId);
            payment.setPaymentMethodId(paymentMethodId);
            payment.setAmount(price);
            payment.setPaymentDate(appointmentDate); // đúng ngày form chọn
            payment.setStatus("Chờ xử lý");
            payment.setNote("Khách: " + customerName + ", Phone: " + phone);

            if (!paymentDAO.add(payment)) {
                throw new RuntimeException("Không thể thêm thanh toán.");
            }

            em.getTransaction().commit();

            // 5. Gửi email xác nhận
            String subject = "Xác nhận thông tin đặt mua xe " + carName;
            String content = "Kính gửi quý khách " + customerName + ",\n\n"
                    + "Chúng tôi đã nhận được đơn đặt mua xe **" + carName + "** với trị giá **" + price + " VND**.\n"
                    + "Thông tin liên hệ của quý khách:\n"
                    + "- Số điện thoại: " + phone + "\n"
                    + "- Email: " + email + "\n\n"
                    + "Nhân viên kinh doanh của chúng tôi sẽ liên hệ trong thời gian sớm nhất để sắp xếp lịch trao đổi và hoàn tất thủ tục hợp đồng.\n"
                    + "Lịch hẹn của quý khách: " + appointmentDateStr + "\n\n"
                    + "Trân trọng cảm ơn quý khách đã tin tưởng và lựa chọn DriveDreams.\n"
                    + "Trân trọng,\n"
                    + "DriveDreams";

            try {
                Email.sendEmail(email, subject, content);
            } catch (Exception e) {
                e.printStackTrace();
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
