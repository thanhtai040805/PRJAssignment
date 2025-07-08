
// CheckoutServlet.java
package controller;

import carDao.CarDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Car;
import model.Customer;
import paymentmethodDAO.PaymentMethodDAO;
import model.PaymentMethod;

import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String carIdParam = request.getParameter("carId");
        if (carIdParam == null) {
            response.sendRedirect("/error.jsp");
            return;
        }

        int carId = Integer.parseInt(carIdParam);
        EntityManager em = emf.createEntityManager();

        try {
            CarDao carDao = new CarDao();
            carDao.setEntityManager(em);
            Car carToBuy = carDao.findById(carId);

            if (carToBuy == null) {
                response.sendRedirect("/error.jsp");
                return;
            }

            PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();
            paymentMethodDAO.setEntityManager(em);
            List<PaymentMethod> paymentMethods = paymentMethodDAO.getAll();

            request.setAttribute("carToBuy", carToBuy);
            request.setAttribute("paymentMethods", paymentMethods);

            request.getRequestDispatcher("/car/paymentForm.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}
