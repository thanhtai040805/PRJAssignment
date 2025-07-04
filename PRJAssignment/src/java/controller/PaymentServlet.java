package controller;

import carDao.CarDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Car;
import java.sql.*;

import java.io.IOException;

@WebServlet("/payment/*")
public class PaymentServlet extends HttpServlet {

    private final CarDao carDao = new CarDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/error/500.jsp");
            return;
        }
        String globalKey = pathInfo.substring(1);
        Car car = carDao.getCarByGlobalKey(globalKey);
        request.setAttribute("carToBuy", car);
        request.getRequestDispatcher("/car/carPayment.jsp").forward(request, response);

    }

}
