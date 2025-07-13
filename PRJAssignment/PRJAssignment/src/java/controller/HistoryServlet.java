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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HistoryServlet", urlPatterns = {"/history"})
public class HistoryServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object currentUser = (session != null) ? session.getAttribute("currentUser") : null;

        List<String> searchHistory = new ArrayList<>();

        if (currentUser != null) {
            // Đã đăng nhập: lấy từ DB
            User user = (User) currentUser;
            int userId = user.getUserId();

            EntityManager em = emf.createEntityManager();
            SearchHistoryDAO searchHistoryDAO = new SearchHistoryDAO();
            searchHistoryDAO.setEntityManager(em);

            List<PageHistory> histories = searchHistoryDAO.findByUserId(userId);
            em.close();

            for (PageHistory h : histories) {
                searchHistory.add(h.getPageHistoryPK().getPath());
            }
            if (searchHistory.size() > 20) {
                searchHistory = searchHistory.subList(0, 20);
            }
        } else {
            // Chưa đăng nhập: lấy từ cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("searchHistory".equals(c.getName())) {
                        String value = java.net.URLDecoder.decode(c.getValue(), "UTF-8");
                        if (value != null && !value.isEmpty()) {
                            String[] arr = value.split(",");
                            for (String s : arr) {
                                if (!s.trim().isEmpty()) {
                                    searchHistory.add(s.trim());
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }

        request.setAttribute("searchHistory", searchHistory);
        request.getRequestDispatcher("/user/userHistory.jsp").forward(request, response);
    }
}


