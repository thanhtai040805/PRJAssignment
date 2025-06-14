package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processLogout(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processLogout(request, response);
    }
    
    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        // Lấy session hiện tại
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Log logout activity
            Object username = session.getAttribute("username");
            if (username != null) {
                System.out.println("User logged out: " + username);
            }
            
            // Hủy session
            session.invalidate();
        }
        
        // Chuyển hướng về trang chủ
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
