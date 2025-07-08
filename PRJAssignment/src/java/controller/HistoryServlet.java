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

            System.out.println("==> [DEBUG] Lấy lịch sử từ database, số lượng: " + histories.size());
            for (PageHistory h : histories) {
                String path = h.getPageHistoryPK().getPath();
                System.out.println("==> [DEBUG] DB path: " + path);
                searchHistory.add(path);
            }
            if (searchHistory.size() > 20) {
                searchHistory = searchHistory.subList(0, 20);
            }
        } else {
        // Chưa đăng nhập: lấy từ cookie
            Cookie[] cookies = request.getCookies();
            boolean found = false;
            if (cookies != null) {
                for (Cookie c : cookies) {
                    System.out.println("==> [DEBUG] Cookie name: " + c.getName() + ", value: " + c.getValue());
                    if ("searchHistory".equals(c.getName())) {
                        String value = c.getValue();
                        System.out.println("==> [DEBUG] Cookie value: " + value);
                        if (value != null && !value.isEmpty()) {
                            // SỬA Ở ĐÂY: split theo dấu |
                            String[] arr = value.split("\\|");
                            for (String s : arr) {
                                if (!s.trim().isEmpty()) {
                                    String decoded = java.net.URLDecoder.decode(s.trim(), "UTF-8");
                                    System.out.println("==> [DEBUG] Cookie decoded: " + decoded);
                                    searchHistory.add(decoded);
                                }
                            }
                        }
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("==> [DEBUG] Không tìm thấy cookie searchHistory trong request.");
            }
            System.out.println("==> [DEBUG] Tổng số lịch sử tìm kiếm sẽ render: " + searchHistory.size());
        }

        request.setAttribute("searchHistory", searchHistory);
        request.getRequestDispatcher("/user/userHistory.jsp").forward(request, response);
    }
}
