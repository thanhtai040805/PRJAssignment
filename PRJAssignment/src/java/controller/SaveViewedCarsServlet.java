package controller;

import userDao.ViewedCarsDAO;
import model.ViewedCars;
import model.ViewedCarsPK;
import model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "SaveViewedCarsServlet", urlPatterns = {"/saveViewedCars"})
public class SaveViewedCarsServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy user đăng nhập
        HttpSession session = request.getSession(false);
        Object currentUser = (session != null) ? session.getAttribute("currentUser") : null;

        // Lấy globalKey từ client gửi lên (mã xe đã xem)
        String globalKey = request.getParameter("globalKey");

        response.setContentType("application/json");

        if (currentUser == null || globalKey == null || globalKey.trim().isEmpty()) {
            response.getWriter().write("{\"success\":false, \"error\":\"User chưa đăng nhập hoặc globalKey rỗng\"}");
            return;
        }

        User user = (User) currentUser;
        int userId = user.getUserId();

        EntityManager em = emf.createEntityManager();
        ViewedCarsDAO viewedCarsDAO = new ViewedCarsDAO();
        viewedCarsDAO.setEntityManager(em);

        ViewedCarsPK pk = new ViewedCarsPK(userId, globalKey);

        try {
            em.getTransaction().begin();

            // Xoá bản ghi cũ nếu đã có để luôn mới nhất lên đầu
            ViewedCars existing = viewedCarsDAO.getById(pk);
            if (existing != null) {
                viewedCarsDAO.remove(pk);
                em.flush(); // Đảm bảo xóa ngay
            }

            ViewedCars viewed = new ViewedCars(pk);
            viewed.setUser(user);
            viewed.setViewedAt(new Date()); // Gán thời gian hiện tại
            viewedCarsDAO.add(viewed);

            em.getTransaction().commit();
            response.getWriter().write("{\"success\":true}");
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.getWriter().write("{\"success\":false, \"error\":\"" + ex.getMessage() + "\"}");
        } finally {
            em.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
