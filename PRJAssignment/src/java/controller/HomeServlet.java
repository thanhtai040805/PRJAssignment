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
import java.util.List;
import java.util.Map;

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

        request.getRequestDispatcher("/home.jsp").forward(request, response);
        em.close();

    }
}
