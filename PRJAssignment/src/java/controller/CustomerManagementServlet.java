package controller;

import customerDAO.CustomerDAO;
import userDao.UserDao;
import model.Customer;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard/customer")
public class CustomerManagementServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<Map<String, Object>> customers;
        if (search != null && !search.trim().isEmpty()) {
            customers = customerDAO.searchCustomersWithUser(search);
        } else {
            customers = customerDAO.getAllCustomersWithUser();
        }
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/dashboard/customerDashboard.jsp").forward(request, response);
    }

    // TODO: doPost cho thêm/sửa/xóa
}
