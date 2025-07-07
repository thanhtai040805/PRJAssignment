// HomeServlet.java
package controller;

import carDao.CarDao;
import model.Car;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.FavoriteCars;
import model.User;
import userDao.FavoriteCarDAO;

@WebServlet(urlPatterns = {"/", "/home"})
public class HomeServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        CarDao carDao = new CarDao();
        carDao.setEntityManager(em);

        List<Car> showcaseCars = carDao.getShowcaseCars();
        List<Car> bestSellerCars = carDao.getBestSellerCars();
        List<Car> rankingCars = carDao.getRankingCars();
        List<Car> recommendCars = carDao.getRecommendCars();
        List<Map<String, Object>> providers = carDao.getActiveProviders();

        request.setAttribute("showcaseCars", showcaseCars);
        request.setAttribute("bestSellerCars", bestSellerCars);
        request.setAttribute("rankingCars", rankingCars);
        request.setAttribute("recommendCars", recommendCars);
        request.setAttribute("providers", providers);

        List<String> favoriteGlobalKeys = new ArrayList<>();
        HttpSession session = request.getSession(false);
        Object currentUser = (session != null) ? session.getAttribute("currentUser") : null;
        if (currentUser != null) {
            // Lấy từ database nếu đã đăng nhập
            User user = (User) currentUser;
            int userId = user.getUserId();
            FavoriteCarDAO favoriteCarDAO = new FavoriteCarDAO();
            favoriteCarDAO.setEntityManager(em);
            List<FavoriteCars> favorites = favoriteCarDAO.findByUserId(userId);
            for (FavoriteCars f : favorites) {
                favoriteGlobalKeys.add(f.getFavoriteCarsPK().getGlobalKey());
            }
            request.setAttribute("favoriteGlobalKeys", favoriteGlobalKeys);
        }
        request.setAttribute("favoriteGlobalKeys", favoriteGlobalKeys);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
        em.close();

    }
}
