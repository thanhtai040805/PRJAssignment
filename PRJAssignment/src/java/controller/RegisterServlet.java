package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import model.User;
import userDao.UserDao;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Lấy thông tin từ form
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String role = request.getParameter("role"); // admin, nhanvien, khachhang
            String maKHStr = request.getParameter("maKH");
            String maNVStr = request.getParameter("maNV");

            // Gán giá trị MaKH/MaNV nếu có
            Integer maKH = (maKHStr != null && !maKHStr.isEmpty()) ? Integer.parseInt(maKHStr) : null;
            Integer maNV = (maNVStr != null && !maNVStr.isEmpty()) ? Integer.parseInt(maNVStr) : null;

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setTrangThai("Hoạt động"); // mặc định
            user.setMaKH(maKH);
            user.setMaNV(maNV);

            boolean success = userDao.registerUser(user);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            } else {
                request.setAttribute("error", "Tài khoản đã tồn tại hoặc lỗi đăng ký!");
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống hoặc dữ liệu không hợp lệ.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/user/register.jsp");
    }
}
