package controller;

import carDao.CarDao;
import model.Car;
import model.FavoriteCars;
import model.User;
import userDao.FavoriteCarDAO;
import util.SmartSuggestion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

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
        em.close();
        request.getRequestDispatcher("/car/carDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String globalKey = request.getParameter("globalKey");
        response.setContentType("application/json");
        EntityManager em = emf.createEntityManager();

        try {
            CarDao carDao = new CarDao();
            carDao.setEntityManager(em);
            Car car = carDao.getCarByGlobalKey(globalKey);

            boolean available = false;
            if (car != null && car.getStockQuantity() != null) {
                available = car.getStockQuantity() > 0;
            }

            response.getWriter().write("{\"available\":" + available + "}");
        } finally {
            em.close();
        }
    }
}
