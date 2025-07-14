package controller;

import carDao.CarDao;
import model.Car;
import model.FavoriteCars;
import model.User;
import model.ViewedCars;
import model.ViewedCarsPK;
import userDao.FavoriteCarDAO;
import userDao.ViewedCarsDAO;
import util.SmartSuggestion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

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

        try {
            // ==== Section: Hiển thị chính ====
            request.setAttribute("showcaseCars", carDao.getShowcaseCars());
            request.setAttribute("bestSellerCars", carDao.getBestSellerCars());
            request.setAttribute("rankingCars", carDao.getRankingCars());
            request.setAttribute("recommendCars", carDao.getRecommendCars());
            request.setAttribute("providers", carDao.getActiveProviders());

            List<String> favoriteGlobalKeys = new ArrayList<>();
            List<Car> suggestionCars = new ArrayList<>();
            List<Car> referenceCars = new ArrayList<>();  // dùng để gợi ý

            HttpSession session = request.getSession(false);
            Object currentUser = (session != null) ? session.getAttribute("currentUser") : null;
            List<Car> allCars = carDao.getAllCarsAvailable(); // chỉ xe còn hàng

            if (currentUser != null) {
                // ĐÃ ĐĂNG NHẬP
                User user = (User) currentUser;
                int userId = user.getUserId();

                // Lấy xe yêu thích
                FavoriteCarDAO favoriteCarDAO = new FavoriteCarDAO();
                favoriteCarDAO.setEntityManager(em);
                List<FavoriteCars> favorites = favoriteCarDAO.findByUserId(userId);
                for (FavoriteCars f : favorites) {
                    Car car = f.getCar();
                    if (car != null && car.getStockQuantity() > 0) {
                        referenceCars.add(car);
                        favoriteGlobalKeys.add(f.getFavoriteCarsPK().getGlobalKey());
                    }
                }

                // Lấy xe đã xem
                ViewedCarsDAO viewedCarsDAO = new ViewedCarsDAO();
                viewedCarsDAO.setEntityManager(em);
                List<ViewedCars> viewedList = viewedCarsDAO.findByUserId(userId);
                for (ViewedCars v : viewedList) {
                    Car car = carDao.getCarByGlobalKey(v.getViewedCarsPK().getGlobalKey());
                    if (car != null && car.getStockQuantity() > 0) {
                        referenceCars.add(car);
                    }
                }

            } else {
                // CHƯA ĐĂNG NHẬP: lấy từ cookie
                Cookie[] cookies = request.getCookies();
                Set<String> globalKeys = new LinkedHashSet<>();

                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if ("favoriteCars".equals(c.getName()) || "viewedCars".equals(c.getName())) {
                            String value = URLDecoder.decode(c.getValue(), "UTF-8");
                            if (value != null && !value.isEmpty()) {
                                String[] arr = value.split(",");
                                for (String s : arr) {
                                    if (!s.trim().isEmpty()) {
                                        globalKeys.add(s.trim());
                                    }
                                }
                            }
                        }
                    }
                }

                for (String key : globalKeys) {
                    Car car = carDao.getCarByGlobalKey(key);
                    if (car != null && car.getStockQuantity() > 0) {
                        referenceCars.add(car);
                    }
                }
            }

            // ==== Section: Gợi ý xe ====
            // Gộp và loại trùng theo carId
            Map<Integer, Car> uniqueRefCars = new LinkedHashMap<>();
            for (Car c : referenceCars) {
                uniqueRefCars.putIfAbsent(c.getCarId(), c);
            }

            List<Car> uniqueRefList = new ArrayList<>(uniqueRefCars.values());

            suggestionCars = SmartSuggestion.suggestFromFavorite(uniqueRefList, allCars);

            if (suggestionCars.size() > 6) {
                suggestionCars = suggestionCars.subList(0, 6);
            }
            // ==== Section: setAttribute để hiển thị ====
            request.setAttribute("suggestionCars", suggestionCars);
            request.setAttribute("favoriteGlobalKeys", favoriteGlobalKeys);
        } finally {
            em.close();
        }

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}

