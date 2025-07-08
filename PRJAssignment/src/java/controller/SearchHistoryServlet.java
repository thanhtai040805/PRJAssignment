package controller;

import userDao.SearchHistoryDAO;
import model.PageHistory;
import model.PageHistoryPK;
import model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/saveSearchHistory"})
public class SearchHistoryServlet extends HttpServlet {

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

        // Lấy path tìm kiếm từ client gửi lên (có thể đã encodeURIComponent)
        String path = request.getParameter("path");
        String contextPath = request.getContextPath();

        response.setContentType("application/json");

        if (currentUser == null || path == null || path.trim().isEmpty()) {
            response.getWriter().write("{\"success\":false, \"error\":\"User chưa đăng nhập hoặc path rỗng\"}");
            return;
        }

        // GIẢI MÃ PATH nếu client gửi encodeURIComponent
        path = java.net.URLDecoder.decode(path, "UTF-8");

        // Xử lý path để chỉ lưu phần sau contextPath, bắt đầu bằng 1 dấu /
        if (path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        // Đảm bảo path chỉ có 1 dấu / ở đầu
        while (path.startsWith("//")) {
            path = path.substring(1);
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        User user = (User) currentUser;
        int userId = user.getUserId();

        EntityManager em = emf.createEntityManager();
        SearchHistoryDAO searchHistoryDAO = new SearchHistoryDAO();
        searchHistoryDAO.setEntityManager(em);

        PageHistoryPK pk = new PageHistoryPK(userId, path);

        try {
            em.getTransaction().begin();

            // Xoá bản ghi cũ nếu đã có để luôn mới nhất lên đầu
            PageHistory existing = searchHistoryDAO.getById(pk);
            if (existing != null) {
                searchHistoryDAO.remove(pk);
                em.flush(); // Đảm bảo xóa ngay
            }

            PageHistory history = new PageHistory(pk);
            history.setUser(user);
            history.setCreatedAt(new Date()); // Gán thời gian hiện tại
            searchHistoryDAO.add(history);

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
