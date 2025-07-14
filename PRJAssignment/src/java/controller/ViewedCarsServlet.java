package controller;

import carDao.CarDao;
import userDao.ViewedCarsDAO;
import model.ViewedCars;
import model.Car;
import model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.FavoriteCars;
import userDao.FavoriteCarDAO;
import util.SmartSuggestion;

@WebServlet(name = "ViewedCarsHistoryServlet", urlPatterns = {"/viewedCars"})
public class ViewedCarsServlet extends HttpServlet {

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

        List<String> globalKeys = new ArrayList<>();
        List<Car> viewedCarsList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();

        try {
            if (currentUser != null) {
                // Đã đăng nhập: lấy từ database
                User user = (User) currentUser;
                int userId = user.getUserId();

                ViewedCarsDAO viewedCarsDAO = new ViewedCarsDAO();
                viewedCarsDAO.setEntityManager(em);

                List<ViewedCars> histories = viewedCarsDAO.findByUserId(userId);
                for (ViewedCars v : histories) {
                    globalKeys.add(v.getViewedCarsPK().getGlobalKey());
                }
            } else {
                // Chưa đăng nhập: lấy từ cookie
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if ("viewedCars".equals(c.getName())) {
                            String value = java.net.URLDecoder.decode(c.getValue(), "UTF-8");
                            if (value != null && !value.isEmpty()) {
                                String[] arr = value.split(",");
                                for (String s : arr) {
                                    if (!s.trim().isEmpty()) {
                                        globalKeys.add(s.trim());
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }

            // Truy vấn Car theo GlobalKey
            if (!globalKeys.isEmpty()) {
                CarDao carDao = new CarDao();
                carDao.setEntityManager(em);
                for (String key : globalKeys) {
                    Car car = carDao.getCarByGlobalKey(key);
                    if (car != null) {
                        viewedCarsList.add(car);
                    }
                }
            }

            List<String> favoriteGlobalKeys = new ArrayList<>();
            session = request.getSession(false);
            currentUser = (session != null) ? session.getAttribute("currentUser") : null;
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

            List<Car> availableViewed = viewedCarsList.stream()
                    .filter(car -> car.getStockQuantity() > 0)
                    .toList();

            CarDao carDao = new CarDao();
            carDao.setEntityManager(em);
            List<Car> allCars = carDao.getAllCarsAvailable(); // Chỉ những xe còn hàng

            List<Car> suggestions = SmartSuggestion.suggestFromViewed(availableViewed, allCars, 4);
            request.setAttribute("suggestedCars", suggestions);
        } finally {
            em.close();
        }

        request.setAttribute("viewedCarsList", viewedCarsList);
        request.getRequestDispatcher("/user/viewedCarsHistory.jsp").forward(request, response);
    }
}
