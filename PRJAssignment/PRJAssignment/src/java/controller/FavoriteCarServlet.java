package controller;

import carDao.CarDao;
import userDao.FavoriteCarDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Car;
import model.User;
import model.FavoriteCars;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/favoriteCar")
public class FavoriteCarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object currentUser = (session != null) ? session.getAttribute("currentUser") : null;
        List<String> globalKeys = new ArrayList<>();

        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        try {
            if (currentUser == null) {
                // Lấy từ cookie
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if ("favoriteCars".equals(c.getName())) {
                            String value = URLDecoder.decode(c.getValue(), "UTF-8");
                            if (value != null && !value.isEmpty()) {
                                globalKeys.addAll(Arrays.asList(value.split(",")));
                            }
                            break;
                        }
                    }
                }
            } else {
                // Lấy từ database bảng FavoriteCars
                User user = (User) currentUser;
                int userId = user.getUserId();

                FavoriteCarDAO favoriteCarDAO = new FavoriteCarDAO();
                favoriteCarDAO.setEntityManager(em);

                List<FavoriteCars> list = favoriteCarDAO.findByUserId(userId);
                for (FavoriteCars fc : list) {
                    globalKeys.add(fc.getFavoriteCarsPK().getGlobalKey());
                }
            }

            // Lấy thông tin chi tiết các xe từ DB
            List<Car> favoriteCars = new ArrayList<>();
            if (!globalKeys.isEmpty()) {
                CarDao carDao = new CarDao();
                carDao.setEntityManager(em);
                for (String key : globalKeys) {
                    Car car = carDao.getCarByGlobalKey(key);
                    if (car != null) favoriteCars.add(car);
                }
            }

            request.setAttribute("favoriteCars", favoriteCars);
            request.getRequestDispatcher("/car/favoriteCar.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}
