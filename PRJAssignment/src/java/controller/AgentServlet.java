package controller;

import agent.CarAgent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import userDao.FavoriteCarDAO;
import userDao.ConversationHistoryDAO;
import userDao.ViewedCarsDAO;
import carDao.CarDao;
import userDao.SearchHistoryDAO;

@WebServlet("/agent")
public class AgentServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        // Chỉ tạo EntityManagerFactory 1 lần khi khởi động Servlet
        emf = Persistence.createEntityManagerFactory("PRJPU");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String prompt = req.getParameter("prompt");
        String sessionId = req.getSession().getId();

        // userId có thể null hoặc rỗng nếu chưa đăng nhập
        Integer userId = null;
        try {
            String userIdStr = req.getParameter("userId");
            if (userIdStr != null && !userIdStr.isEmpty()) {
                userId = Integer.parseInt(userIdStr);
            }
        } catch (Exception e) {
            // Nếu userId không hợp lệ, để null (xử lý trong agent)
        }

        EntityManager em = emf.createEntityManager();
        try {
            // Khởi tạo DAO và set EntityManager
            FavoriteCarDAO favoriteCarDAO = new FavoriteCarDAO();
            favoriteCarDAO.setEntityManager(em);

            CarDao carDao = new CarDao();
            carDao.setEntityManager(em);

            ConversationHistoryDAO conversationHistoryDAO = new ConversationHistoryDAO();
            conversationHistoryDAO.setEntityManager(em);

            ViewedCarsDAO viewedCarsDAO = new ViewedCarsDAO();
            viewedCarsDAO.setEntityManager(em);

            SearchHistoryDAO searchHistoryDAO = new SearchHistoryDAO();
            searchHistoryDAO.setEntityManager(em);
            // Khởi tạo Agent với đủ các DAO cần thiết
            CarAgent agent = new CarAgent(
                    "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",
                    favoriteCarDAO,
                    carDao,
                    conversationHistoryDAO,
                    viewedCarsDAO,
                    searchHistoryDAO
            );

            // Gọi agent xử lý prompt, truyền thêm sessionId, request và response để agent có thể lưu/lấy cookie nếu cần
            String answer = agent.handlePrompt(prompt, userId, sessionId, req, resp);

            resp.setContentType("text/plain; charset=UTF-8");
            resp.getWriter().write(answer);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        // Đóng EntityManagerFactory khi ứng dụng dừng
        if (emf != null) {
            emf.close();
        }
    }
}
