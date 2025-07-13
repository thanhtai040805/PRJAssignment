package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.User;
import userDao.UserDao;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        try {
            // Lấy EntityManagerFactory từ ServletContext (đã được tạo bởi Listener nếu có)
            // Hoặc tạo mới nếu không có Listener (ít được khuyến khích trong môi trường sản xuất)
            emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
            if (emf == null) {
                LOGGER.warning("EntityManagerFactory not found in ServletContext. Creating a new one in RegisterServlet.init(). " +
                               "Consider using a ServletContextListener for EMF lifecycle management.");
                emf = Persistence.createEntityManagerFactory("PRJPU");
            }
            LOGGER.info("RegisterServlet initialized. EntityManagerFactory ready.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize RegisterServlet or create EntityManagerFactory.", e);
            throw new ServletException("Failed to initialize RegisterServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String maKHStr = request.getParameter("maKH");
        String maNVStr = request.getParameter("maNV");

        // Input validation and trim
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            role == null || role.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin bắt buộc!");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        Integer maKH = null;
        if (maKHStr != null && !maKHStr.trim().isEmpty()) {
            try {
                maKH = Integer.parseInt(maKHStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Mã khách hàng không hợp lệ. Vui lòng nhập số.");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }
        }

        Integer maNV = null;
        if (maNVStr != null && !maNVStr.trim().isEmpty()) {
            try {
                maNV = Integer.parseInt(maNVStr.trim());
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Mã nhân viên không hợp lệ. Vui lòng nhập số.");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            UserDao userDao = new UserDao();
            userDao.setEntityManager(em);

            // Check if username already exists BEFORE creating new user object to avoid unnecessary hashing
            boolean exists = userDao.isUsernameExists(username.trim());
            if (exists) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại! Vui lòng chọn tên đăng nhập khác.");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                return;
            }

            User user = new User();
            user.setUsername(username.trim());
            user.setPassword(password); // Password will be hashed in UserDao.add()
            user.setRole(role.toUpperCase());
            user.setStatus("Hoạt động");
            user.setCustomerId(maKH);
            user.setEmployeeId(maNV);

            em.getTransaction().begin();
            boolean success = userDao.add(user); // Password hashing happens here
            em.getTransaction().commit();

            if (success) {
                LOGGER.log(Level.INFO, "User registered successfully: {0}", user.getUsername());
                response.sendRedirect(request.getContextPath() + "/user/login.jsp?registered=true");
            } else {
                LOGGER.log(Level.WARNING, "Failed to register user: {0}", user.getUsername());
                request.setAttribute("error", "Đăng ký thất bại! Vui lòng thử lại.");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                LOGGER.log(Level.WARNING, "Transaction rolled back during registration process.", e);
            }
            LOGGER.log(Level.SEVERE, "Registration error for user: " + (username != null ? username : "null"), e);
            request.setAttribute("error", "Lỗi hệ thống khi đăng ký. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/user/register.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.info("RegisterServlet destroyed.");
    }
}