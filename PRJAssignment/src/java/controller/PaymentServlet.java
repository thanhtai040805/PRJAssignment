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

        if (car != null) {
            try {
                boolean updated = carDao.updateSoLuongTon(car.getMaXe(), 1);
                if (!updated) {
                    // Nếu không thể giảm số lượng (hết hàng)
                    request.setAttribute("errorMessage", "Xe này đã hết hàng hoặc không thể mua được lúc này.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
            } catch (SQLException e) {
                throw new ServletException("Lỗi khi cập nhật số lượng tồn kho", e);
            }

            request.setAttribute("carToBuy", car);
            request.getRequestDispatcher("/car/carPayment.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/error/500.jsp");
        }
    }

}
