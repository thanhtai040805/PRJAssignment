package controller;

import employeeDAO.EmployeeDAO;
import userDao.UserDao;
import model.Employee;
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

@WebServlet("/dashboard/employee")
public class EmployeeManagementServlet extends HttpServlet {
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        List<Map<String, Object>> employees;
        if (search != null && !search.trim().isEmpty()) {
            employees = employeeDAO.searchEmployeesWithUser(search);
        } else {
            employees = employeeDAO.getAllEmployeesWithUser();
        }
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("/dashboard/employeeDashboard.jsp").forward(request, response);
    }

    // TODO: doPost cho thêm/sửa/xóa
}
