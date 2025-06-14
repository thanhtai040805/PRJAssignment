package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import userDao.IUserDao;
import userDao.UserDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserDao userDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra nếu đã đăng nhập
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            User user = (User) session.getAttribute("currentUser");
            redirectByRole(user, request, response);
            return;
        }
        
        // Chuyển hướng đến trang login
        request.getRequestDispatcher("/user/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy thông tin từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validate input
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            return;
        }
        
        try {
            // Xác thực người dùng thông qua DAO
            User user = userDao.authenticate(username.trim(), password);
            
            if (user != null) {
                // Đăng nhập thành công
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", user);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userRole", user.getRole());
                session.setAttribute("isLoggedIn", true);
                session.setAttribute("userId", user.getMaUser());
                
                // Log login activity (có thể thêm vào database)
                System.out.println("User logged in: " + user.getUsername() + " - Role: " + user.getRole());
                
                // Chuyển hướng theo role
                redirectByRole(user, request, response);
                
            } else {
                // Đăng nhập thất bại
                request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
                request.setAttribute("username", username); // Giữ lại username để user không phải nhập lại
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình đăng nhập. Vui lòng thử lại!");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
        }
    }
    
    /**
     * Chuyển hướng người dùng dựa trên role
     */
    private void redirectByRole(User user, HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String contextPath = request.getContextPath();
        
        switch (user.getRole()) {
            case "ADMIN":
                response.sendRedirect(contextPath + "/");
                break;
            case "EMPLOYEE":
                response.sendRedirect(contextPath + "/");
                break;
            case "CUSTOMER":
                response.sendRedirect(contextPath + "/");
                break;
            default:
                response.sendRedirect(contextPath + "/home");
        }
    }
}
