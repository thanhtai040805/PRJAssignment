package controller;

import carDao.CarDao;
import carDao.ICarDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Car;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/", "/home"})
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ICarDao carDao = new CarDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Car> showcaseCars = carDao.getShowcaseCars();
            List<Car> bestSellerCars = carDao.getBestSellerCars();
            List<Car> rankingCars = carDao.getRankingCars();
            List<Car> recommendCars = carDao.getRecommendCars();
            List<Map<String, Object>> providers = carDao.getActiveProviders();

            if (!showcaseCars.isEmpty()) {
                Car firstCar = showcaseCars.get(0);
                System.out.println("DEBUG: GlobalKey của xe đầu tiên (trước khi gửi đến JSP): " + firstCar.getGlobalKey());
            } else {
                System.out.println("DEBUG: Không có xe nào trong showcase.");
            }

            request.setAttribute("showcaseCars", showcaseCars);
            request.setAttribute("bestSellerCars", bestSellerCars);
            request.setAttribute("rankingCars", rankingCars);
            request.setAttribute("recommendCars", recommendCars);
            request.setAttribute("providers", providers);

            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Lỗi khi tải dữ liệu trang chủ: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
