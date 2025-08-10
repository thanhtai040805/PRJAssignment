package controller;

import carDao.CarDao;
import employeeDAO.EmployeeDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Car;
import model.Customer;
import model.Employee;
import model.PaymentMethod;
import paymentmethodDAO.PaymentMethodDAO;

import java.io.IOException;
import java.util.List;

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

        if (emf == null) {
            response.sendRedirect(request.getContextPath() + "/error/500.jsp");
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                response.sendRedirect(request.getContextPath() + "/error/500.jsp");
                return;
            }

            String globalKey = pathInfo.substring(1); // Bỏ dấu "/"
            CarDao carDao = new CarDao();
            carDao.setEntityManager(em);

            Car car = carDao.getCarByGlobalKey(globalKey);
            if (car == null) {
                response.sendRedirect(request.getContextPath() + "/error/404.jsp");
                return;
            }
            request.setAttribute("carToBuy", car);

            // Lấy phương thức thanh toán
            PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();
            paymentMethodDAO.setEntityManager(em);
            List<PaymentMethod> paymentMethods = paymentMethodDAO.getAll();
            request.setAttribute("paymentMethods", paymentMethods);

            // Lấy danh sách nhân viên
            EmployeeDAO employeeDAO = new EmployeeDAO();
            employeeDAO.setEntityManager(em);
            List<Employee> employeeList = employeeDAO.getActiveEmployees(); // hoặc getAll()
            request.setAttribute("employeeList", employeeList);
            System.out.println("Danh sách nhân viên: " + employeeList.size());

            // Lấy khách hàng đăng nhập
            HttpSession session = request.getSession(false);
            if (session != null) {
                Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
                if (loggedInCustomer != null) {
                    request.setAttribute("loggedInCustomer", loggedInCustomer);
                }
            }

            // Chuyển tới JSP
            request.getRequestDispatcher("/car/carPayment.jsp").forward(request, response);

        } finally {
            // Đảm bảo EntityManager luôn được đóng
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
