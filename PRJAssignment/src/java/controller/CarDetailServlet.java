package controller;

import carDao.CarDao;
import model.Car;
import util.SmartSuggestion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// The @WebServlet annotation defines the URL pattern this servlet will respond to.
// "/detail/*" means it will handle any URL that starts with "/detail/"
// For example: /yourApp/detail/some-global-key
@WebServlet("/detail/*")
public class CarDetailServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CarDetailServlet.class.getName());

    private final CarDao carDao = new CarDao(); // Your CarDao instance

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the part of the URL path that comes after the servlet mapping.
        // For "/detail/your-car-global-key", pathInfo will be "/your-car-global-key".
        String pathInfo = request.getPathInfo();
        String contextPath = request.getContextPath(); // e.g., /MyWebApp

        // --- Input Validation and GlobalKey Extraction ---
        if (pathInfo == null || pathInfo.equals("/")) {
            LOGGER.log(Level.WARNING, "CarDetailServlet received request without globalKey in path. Redirecting to home.");
            response.sendRedirect(contextPath + "/home");
            return;
        }

        // Remove the leading '/' from pathInfo to get the actual globalKey.
        // Example: "/your-car-global-key" becomes "your-car-global-key"
        String globalKey = pathInfo.substring(1);

        // If your globalKey might contain further slashes (e.g., /detail/key/extra),
        // you might want to extract only the first segment:
        // if (globalKey.contains("/")) {
        //     globalKey = globalKey.substring(0, globalKey.indexOf("/"));
        // }
        if (globalKey.isEmpty()) {
            LOGGER.log(Level.WARNING, "CarDetailServlet received empty globalKey. Redirecting to home.");
            response.sendRedirect(contextPath + "/home");
            return;
        }
        LOGGER.log(Level.INFO, "Processing car detail request for globalKey: {0}", globalKey);

        // --- Retrieve Car Details using globalKey ---
        Car car = carDao.getCarByGlobalKey(globalKey);

        if (car == null) {
            LOGGER.log(Level.WARNING, "Car with globalKey '{0}' not found. Showing error page.", globalKey);
            request.setAttribute("error", "Xe bạn tìm không tồn tại hoặc đã bị xóa!");
            request.getRequestDispatcher("/error/404.jsp").forward(request, response);
            return;
        }

        LOGGER.log(Level.INFO, "DEBUG Log: Đối tượng Car được lấy từ DAO cho globalKey: {0}", globalKey);
        LOGGER.log(Level.INFO, "DEBUG Log: Giá trị linkAnh trong đối tượng Car (nguyên bản): '{0}'", car.getLinkAnh());
        LOGGER.log(Level.INFO, "DEBUG Log: hasImage() trả về: {0}", car.hasImage());
        LOGGER.log(Level.INFO, "DEBUG Log: getImageOrDefault() trả về: '{0}'", car.getImageOrDefault());
        // KẾT THÚC CÁC DÒNG LOG

        // --- Handle Viewed Cars (Session Logic) ---
        HttpSession session = request.getSession();
        List<Car> viewedCars = (List<Car>) session.getAttribute("viewedCars");

        if (viewedCars == null) {
            viewedCars = new ArrayList<>();
        }

        // Ensure the current car is at the top of the viewed list, without duplicates
        boolean alreadyViewed = false;
        for (int i = 0; i < viewedCars.size(); i++) {
            if (viewedCars.get(i).getMaXe() == car.getMaXe()) {
                Car temp = viewedCars.remove(i);
                viewedCars.add(0, temp); // Move to front
                alreadyViewed = true;
                break;
            }
        }

        if (!alreadyViewed) {
            viewedCars.add(0, car); // Add new car to front
            if (viewedCars.size() > 5) { // Limit to 5 most recently viewed cars
                viewedCars.remove(viewedCars.size() - 1);
            }
        }
        session.setAttribute("viewedCars", viewedCars);

        // --- Handle Favorite Cars (Session Logic - if needed for SmartSuggestion) ---
        List<Car> favoriteCars = (List<Car>) session.getAttribute("favoriteCars");
        if (favoriteCars == null) {
            favoriteCars = new ArrayList<>();
        }
        session.setAttribute("favoriteCars", favoriteCars);

        // --- Generate Smart Suggestions ---
        List<Car> suggestions = SmartSuggestion.suggest(viewedCars, favoriteCars);
        // Remove the current car from suggestions to avoid self-suggestion
        suggestions.removeIf(s -> s.getMaXe() == car.getMaXe());

        // --- Set Attributes and Forward to JSP ---
        request.setAttribute("carDetail", car); // The main car object to display
        request.setAttribute("suggestionCars", suggestions); // List of suggested cars

        // Forward to the carDetail.jsp in the /car/ directory
        request.getRequestDispatcher("/car/carDetail.jsp").forward(request, response);
    }
}
