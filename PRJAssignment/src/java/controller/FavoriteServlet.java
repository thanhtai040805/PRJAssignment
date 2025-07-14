package controller;

import userDao.FavoriteCarDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import model.FavoriteCars;
import model.FavoriteCarsPK;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@WebServlet("/updateFavorite")
public class FavoriteServlet extends HttpServlet {
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String globalKey = request.getParameter("globalKey");
        boolean success = false;
        String message = "";

        HttpSession session = request.getSession(false);
        Object currentUser = (session != null) ? session.getAttribute("currentUser") : null;

        if (globalKey == null || globalKey.trim().isEmpty()) {
            sendJson(response, false, "Thiếu thông tin xe.");
            return;
        }

        if (currentUser == null) {
            // Chưa đăng nhập: cookie
            List<String> favoriteList = new ArrayList<>();
            String cookieValue = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("favoriteCars".equals(c.getName())) {
                        cookieValue = URLDecoder.decode(c.getValue(), "UTF-8");
                        break;
                    }
                }
            }
            if (cookieValue != null && !cookieValue.isEmpty()) {
                favoriteList.addAll(Arrays.asList(cookieValue.split(",")));
            }
            if (!favoriteList.contains(globalKey)) {
                favoriteList.add(globalKey);
                message = "Đã thêm vào yêu thích!";
            } else {
                favoriteList.remove(globalKey);
                message = "Đã bỏ khỏi yêu thích!";
            }
            String newValue = URLEncoder.encode(String.join(",", favoriteList), "UTF-8");
            Cookie cookie = new Cookie("favoriteCars", newValue);
            cookie.setMaxAge(COOKIE_MAX_AGE);
            cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
            response.addCookie(cookie);
            success = true;
        } else {
            // Đã đăng nhập: lưu vào database
            User user = (User) currentUser;
            int userId = user.getUserId();
            EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
            EntityManager em = emf.createEntityManager();

            FavoriteCarDAO favoriteCarDAO = new FavoriteCarDAO();
            favoriteCarDAO.setEntityManager(em);

            FavoriteCarsPK pk = new FavoriteCarsPK(userId, globalKey);

            try {
                em.getTransaction().begin();

                FavoriteCars existing = favoriteCarDAO.getById(pk);
                if (existing == null) {
                    // Thêm mới
                    FavoriteCars favorite = new FavoriteCars();
                    favorite.setFavoriteCarsPK(pk);
                    favorite.setCreatedAt(new Date());
                    // Nếu entity của bạn cần setUser, hãy set luôn:
                    favorite.setUser(user);
                    favoriteCarDAO.add(favorite);
                    message = "Đã thêm vào yêu thích!";
                } else {
                    // Đã có thì xóa (bỏ khỏi yêu thích)
                    favoriteCarDAO.remove(pk);
                    message = "Đã bỏ khỏi yêu thích!";
                }

                em.getTransaction().commit();
                success = true;
            } catch (Exception e) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                message = "Có lỗi khi cập nhật yêu thích!";
                success = false;
            } finally {
                em.close();
            }
        }

        sendJson(response, success, message);
    }

    private void sendJson(HttpServletResponse response, boolean success, String message) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"success\":" + success + ",\"message\":\"" + message + "\"}");
        out.flush();
    }
}
