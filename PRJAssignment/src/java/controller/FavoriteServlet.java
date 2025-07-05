package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/addFavorite")
public class FavoriteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy globalKey từ request
        String globalKey = request.getParameter("globalKey");
        boolean success = false;

        // Lấy thông tin user (nếu có đăng nhập)
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        // Xử lý logic thêm vào yêu thích
        if (globalKey != null && !globalKey.trim().isEmpty() && userId != null) {
            // TODO: Thực hiện lưu vào DB hoặc session (ví dụ)
            // FavoriteService.addFavorite(userId, globalKey);
            // Nếu lưu thành công:
            success = true;
        }

        // Trả về JSON cho AJAX
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"success\":" + success + "}");
        out.flush();
    }
}
