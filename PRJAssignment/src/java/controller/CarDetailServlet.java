package controller;

import carDao.CarDao;
import model.Car;
import util.SmartSuggestion;
import carDao.ReviewCarDAO;
import carDao.CarRatingDAO;
import model.ReviewCar;
import model.CarRating;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import model.FavoriteCars;
import model.User;
import userDao.FavoriteCarDAO;

@WebServlet("/detail/*")
public class CarDetailServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CarDetailServlet.class.getName());
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

    String pathInfo = request.getPathInfo();
    String contextPath = request.getContextPath();

    if (pathInfo == null || pathInfo.equals("/")) {
        em.close();
        response.sendRedirect(contextPath + "/home");
        return;
    }

    String globalKey = pathInfo.substring(1);
    if (globalKey.isEmpty()) {
        em.close();
        response.sendRedirect(contextPath + "/home");
        return;
    }

    Car car = carDao.getCarByGlobalKey(globalKey);
    if (car == null) {
        em.close();
        request.setAttribute("error", "Xe bạn tìm không tồn tại hoặc đã bị xóa!");
        request.getRequestDispatcher("/error/404.jsp").forward(request, response);
        return;
    }

    HttpSession session = request.getSession();
    List<Car> viewedCars = (List<Car>) session.getAttribute("viewedCars");
    if (viewedCars == null) {
        viewedCars = new ArrayList<>();
    }
    viewedCars.removeIf(c -> c.getCarId().equals(car.getCarId()));
    viewedCars.add(0, car);
    if (viewedCars.size() > 5) {
        viewedCars.remove(viewedCars.size() - 1);
    }
    session.setAttribute("viewedCars", viewedCars);

    List<Car> allCars = carDao.getAllCarsAvailable();
    List<Car> suggestions = SmartSuggestion.suggestFromCar(car, allCars);
    suggestions.removeIf(s -> s.getCarId().equals(car.getCarId()));
    if (suggestions.size() > 4) {
        suggestions = suggestions.subList(0, 4);
    }

    request.setAttribute("carDetail", car);
    request.setAttribute("suggestionCars", suggestions);

    List<String> favoriteGlobalKeys = new ArrayList<>();
    Object currentUser = session.getAttribute("currentUser");
    if (currentUser != null) {
        User user = (User) currentUser;
        int userId = user.getUserId();
        FavoriteCarDAO favoriteCarDAO = new FavoriteCarDAO();
        favoriteCarDAO.setEntityManager(em);
        List<FavoriteCars> favorites = favoriteCarDAO.findByUserId(userId);
        for (FavoriteCars f : favorites) {
            favoriteGlobalKeys.add(f.getFavoriteCarsPK().getGlobalKey());
        }
    }
    request.setAttribute("favoriteGlobalKeys", favoriteGlobalKeys);

    // ===== Thêm đoạn này để lấy dữ liệu review & rating =====
    ReviewCarDAO reviewCarDAO = new ReviewCarDAO();
    reviewCarDAO.setEntityManager(em);
    List<ReviewCar> reviews = reviewCarDAO.getReviewsByCar(globalKey);
    request.setAttribute("reviews", reviews);

    CarRatingDAO ratingDAO = new CarRatingDAO();
    ratingDAO.setEntityManager(em);
    double avgRating = ratingDAO.getAverageRating(globalKey);
    long totalRatings = ratingDAO.getTotalRatings(globalKey);
    request.setAttribute("avgRating", avgRating);
    request.setAttribute("totalRatings", totalRatings);
    // ========================================================

    em.close();
    request.getRequestDispatcher("/car/carDetail.jsp").forward(request, response);
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String globalKey = request.getParameter("globalKey");

        // Nếu không gửi qua body, thì lấy từ URL
        if ((globalKey == null || globalKey.isEmpty()) && request.getPathInfo() != null) {
            globalKey = request.getPathInfo().substring(1); // Bỏ dấu "/"
        }

        if (globalKey == null || globalKey.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        EntityManager em = emf.createEntityManager();
        CarDao carDao = new CarDao();
        carDao.setEntityManager(em);

        Car car = carDao.getCarByGlobalKey(globalKey);
        em.close();

        boolean isAvailable = car != null && car.getStockQuantity() > 0;

        response.setContentType("application/json");
        response.getWriter().write("{\"available\":" + isAvailable + "}");
    }

}