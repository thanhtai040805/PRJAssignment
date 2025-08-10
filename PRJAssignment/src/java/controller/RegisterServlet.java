package controller;

import customerDAO.CustomerDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Customer;
import model.User;
import userDao.UserDao;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());
    private EntityManagerFactory emf;

    @Override
    public void init() {
        try {
            emf = Persistence.createEntityManagerFactory("PRJPU");
            LOGGER.info("RegisterServlet initialized. EntityManagerFactory ready.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Cannot init EntityManagerFactory", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/user/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Lấy thông tin
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String identityCard = request.getParameter("identityCard");
        String address = request.getParameter("address");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate bắt buộc nhập
        if (isEmpty(fullName) || isEmpty(email) || isEmpty(phone) ||
                isEmpty(identityCard) || isEmpty(address) ||
                isEmpty(username) || isEmpty(password) || isEmpty(confirmPassword)) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        // Validate định dạng
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            request.setAttribute("error", "Email không hợp lệ!");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }
        if (!Pattern.matches("\\d{9,15}", phone)) {
            request.setAttribute("error", "Số điện thoại không hợp lệ!");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }
        if (!Pattern.matches("\\d{12}", identityCard)) {
            request.setAttribute("error", "CCCD phải gồm 12 chữ số!");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            // Kiểm tra username trùng
            UserDao userDao = new UserDao();
            userDao.setEntityManager(em);
            if (userDao.isUsernameExists(username.trim().toLowerCase())) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }

            // Tạo DAO cho Customer
            CustomerDAO customerDao = new CustomerDAO();
            customerDao.setEntityManager(em);

            // Kiểm tra trùng email/phone/CCCD
            if (isDuplicateCustomer(em, email, phone, identityCard)) {
                request.setAttribute("error", "Email, Số điện thoại hoặc CCCD đã tồn tại!");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }

            em.getTransaction().begin();

            // Tạo Customer
            Customer customer = new Customer();
            customer.setFullName(fullName);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setIdentityCard(identityCard);
            customer.setAddress(address);
            customer.setCreatedDate(new Date());
            customer.setStatus("Hoạt động");

            customerDao.add(customer);
            em.flush(); // Lấy MaKH

            // Tạo User
            User user = new User();
            user.setUsername(username.trim().toLowerCase());
            user.setPassword(password); // TODO: hash password
            user.setRole("khachhang");
            user.setStatus("Hoạt động");
            user.setCustomerId(customer.getCustomerId());
            user.setEmployeeId(null);

            userDao.add(user);

            em.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/login");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.log(Level.SEVERE, "Lỗi khi đăng ký", e);
            request.setAttribute("error", "Đăng ký thất bại: " + e.getMessage());
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
        } finally {
            if (em != null) em.close();
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isDuplicateCustomer(EntityManager em, String email, String phone, String cccd) {
        Long count = em.createQuery(
                        "SELECT COUNT(c) FROM Customer c WHERE c.email = :email OR c.phone = :phone OR c.identityCard = :cccd",
                        Long.class)
                .setParameter("email", email)
                .setParameter("phone", phone)
                .setParameter("cccd", cccd)
                .getSingleResult();
        return count > 0;
    }
}
