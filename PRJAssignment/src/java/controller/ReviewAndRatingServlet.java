package controller;

import carDao.ReviewCarDAO;
import carDao.CarRatingDAO;
import model.ReviewCar;
import model.CarRating;
import model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ReviewAndRatingServlet", urlPatterns = {"/ReviewAndRatingServlet"})
public class ReviewAndRatingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // ==== 1. Validate & lấy dữ liệu từ request ====
        String globalKey = req.getParameter("globalKey");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String ratingParam = req.getParameter("rating");

        if (globalKey == null || globalKey.isBlank() ||
            title == null || title.isBlank() ||
            content == null || content.isBlank() ||
            ratingParam == null || ratingParam.isBlank()) {

            resp.sendRedirect(req.getContextPath() + "/detail/" + (globalKey != null ? globalKey : ""));
            return;
        }

        int rating;
        try {
            rating = Integer.parseInt(ratingParam);
            if (rating < 1 || rating > 5) {
                throw new NumberFormatException("Rating không hợp lệ");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/detail/" + globalKey);
            return;
        }

        // ==== 2. Lấy user từ session ====
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Integer userID = currentUser.getUserId();

        // ==== 3. Mở EntityManager ====
        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        if (emf == null) {
            throw new ServletException("EntityManagerFactory chưa được khởi tạo trong ServletContext");
        }
        EntityManager em = emf.createEntityManager();

        try {
            // ==== 4. Bắt đầu transaction ====
            em.getTransaction().begin();

            // --- DAO cho Review ---
            ReviewCarDAO reviewDAO = new ReviewCarDAO();
            reviewDAO.setEntityManager(em);

            ReviewCar review = new ReviewCar();
            review.setGlobalKey(globalKey);
            review.setUserID(userID);
            review.setTitle(title.trim());
            review.setContent(content.trim());
            review.setStatus("Hiện");

            reviewDAO.add(review);

            // --- DAO cho Rating ---
            CarRatingDAO ratingDAO = new CarRatingDAO();
            ratingDAO.setEntityManager(em);

            CarRating carRating = new CarRating();
            carRating.setGlobalKey(globalKey);
            carRating.setUserID(userID);
            carRating.setRating(rating);
            carRating.setShortComment(title.trim());
            carRating.setStatus("Hiện");

            ratingDAO.addOrUpdateRating(carRating);

            // ==== 5. Commit transaction ====
            em.getTransaction().commit();

        } catch (Exception e) {
            // Nếu đang có transaction → rollback
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Ghi log + ném ServletException để hiện thông báo lỗi
            e.printStackTrace();
            throw new ServletException("Lỗi khi lưu review/rating: " + e.getMessage(), e);
        } finally {
            // ==== 6. Luôn đóng EntityManager ====
            if (em.isOpen()) {
                em.close();
            }
        }

        // ==== 7. Redirect về trang chi tiết xe ====
        resp.sendRedirect(req.getContextPath() + "/detail/" + globalKey);
    }
}
