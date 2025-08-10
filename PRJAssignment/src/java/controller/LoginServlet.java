package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.User;
import model.Customer;
import userDao.UserDao;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");

        if (emf == null) {
            try {
                LOGGER.warning("EntityManagerFactory not found in ServletContext. Creating a new one in LoginServlet.init(). "
                        + "Consider using a ServletContextListener for EMF lifecycle management.");
                emf = Persistence.createEntityManagerFactory("PRJPU");
                getServletContext().setAttribute("emf", emf);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to create EntityManagerFactory in LoginServlet.init().", e);
                throw new ServletException("Failed to initialize LoginServlet due to EMF creation error", e);
            }
        }
        LOGGER.info("LoginServlet initialized. EntityManagerFactory ready.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("currentUser") != null) {
            // Nếu người dùng đã đăng nhập, chuyển hướng đến trang chủ (/)
            User user = (User) session.getAttribute("currentUser");
            LOGGER.log(Level.INFO, "User {0} already logged in. Redirecting to home.", user.getUsername());
            response.sendRedirect(request.getContextPath() + "/"); // Chuyển hướng về root context path
        } else {
            // Nếu chưa đăng nhập, hiển thị trang login JSP
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            request.setAttribute("username", username != null ? username.trim() : "");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            return;
        }

        EntityManager em = null;
        User authenticatedUser = null;

        try {
            em = emf.createEntityManager();
            UserDao userDao = new UserDao();
            userDao.setEntityManager(em);

            em.getTransaction().begin();
            authenticatedUser = userDao.authenticate(username.trim(), password);
            em.getTransaction().commit();

            if (authenticatedUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", authenticatedUser);
                session.setAttribute("loggedInUser", authenticatedUser);
                session.setAttribute("username", authenticatedUser.getUsername());
                session.setAttribute("userRole", authenticatedUser.getRole());
                session.setAttribute("isLoggedIn", true);
                session.setAttribute("userId", authenticatedUser.getUserId());

                if ("khachhang".equalsIgnoreCase(authenticatedUser.getRole())) {
                    Integer maKH = authenticatedUser.getCustomerId();
                    if (maKH != null) {
                        Customer customer = em.find(Customer.class, maKH);
                        if (customer != null) {
                            session.setAttribute("loggedInCustomer", customer);
                            LOGGER.info("Customer info loaded into session for MaKH = " + maKH);
                        } else {
                            LOGGER.warning("Customer not found in DB for MaKH = " + maKH);
                        }
                    } else {
                        LOGGER.warning("Authenticated user has null customerId.");
                    }
                }

                if ("nhanvien".equalsIgnoreCase(authenticatedUser.getRole())) {
                    Integer maNV = authenticatedUser.getEmployeeId(); // Nếu có sẵn employeeId
                    if (maNV != null) {
                        Employee nhanVien = em.find(Employee.class, maNV);
                        if (nhanVien != null) {
                            session.setAttribute("loggedInEmployee", nhanVien);
                            LOGGER.info("Employee info loaded into session for MaNV = " + maNV);
                        } else {
                            LOGGER.warning("Employee not found in DB for MaNV = " + maNV);
                        }
                    } else {
                        LOGGER.warning("Authenticated user has null employeeId.");
                    }
                }

                LOGGER.log(Level.INFO, "User logged in successfully: {0} with role {1}. Redirecting to home.",
                        new Object[]{authenticatedUser.getUsername(), authenticatedUser.getRole()});

                // XÓA COOKIE favoriteCars khi login thành công
                Cookie cookie = new Cookie("favoriteCars", "");
                cookie.setMaxAge(0); // Xóa ngay lập tức
                cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
                response.addCookie(cookie);

                // XÓA COOKIE searchHistory khi login thành công
                cookie = new Cookie("searchHistory", "");
                cookie.setMaxAge(0); // Xóa ngay lập tức
                cookie.setPath("/");
                response.addCookie(cookie);

                // XÓA COOKIE viewedCar khi login thành công
                cookie = new Cookie("viewedCars", "");
                cookie.setMaxAge(0); // Xóa ngay lập tức
                cookie.setPath("/");
                response.addCookie(cookie);

                // Chuyển hướng tất cả về trang chủ
                response.sendRedirect(request.getContextPath() + "/");

            } else {
                request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng hoặc tài khoản không hoạt động!");
                request.setAttribute("username", username);
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                LOGGER.log(Level.WARNING, "Transaction rolled back during login process.", e);
            }
            LOGGER.log(Level.SEVERE, "Login error for user: " + (username != null ? username : "null"), e);
            request.setAttribute("errorMessage", "Lỗi hệ thống khi đăng nhập. Vui lòng thử lại sau.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("LoginServlet destroyed.");
    }
}
