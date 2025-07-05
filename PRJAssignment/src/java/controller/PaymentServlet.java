// PaymentServlet.java
package controller;

import carDao.CarDao;
import model.Car;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/payment/*")
public class PaymentServlet extends HttpServlet {

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
        if (pathInfo == null || pathInfo.equals("/")) {
            em.close();
            response.sendRedirect(request.getContextPath() + "/error/500.jsp");
            return;
        }
        String globalKey = pathInfo.substring(1);
        Car car = carDao.getCarByGlobalKey(globalKey);
        request.setAttribute("carToBuy", car);
        em.close();
        request.getRequestDispatcher("/car/carPayment.jsp").forward(request, response);
    }
}
