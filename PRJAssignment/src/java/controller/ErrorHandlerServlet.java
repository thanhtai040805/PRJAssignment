package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/error/404", "/error/403", "/error/500"})
public class ErrorHandlerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        
        // Log error information
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        String requestUrl = (String) request.getAttribute("javax.servlet.error.request_uri");
        
        System.err.println("Error occurred: ");
        System.err.println("Status Code: " + statusCode);
        System.err.println("Error Message: " + errorMessage);
        System.err.println("Request URL: " + requestUrl);
        
        // Forward to appropriate error page
        if (requestURI.contains("404")) {
            request.getRequestDispatcher("/error/404.jsp").forward(request, response);
        } else if (requestURI.contains("403")) {
            request.getRequestDispatcher("/error/403.jsp").forward(request, response);
        } else if (requestURI.contains("500")) {
            request.getRequestDispatcher("/error/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
