package controller;

import carDao.CarDao;
import model.Car;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.FavoriteCars;
import model.User;
import userDao.FavoriteCarDAO;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search/*"})
public class SearchServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class.getName());

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        super.init();
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        CarDao carDao = new CarDao();
        carDao.setEntityManager(em);

        String pathInfo = request.getPathInfo();
        LOGGER.log(Level.INFO, "Search request with path: {0}", pathInfo);

        String keyword = request.getParameter("keyword");
        if (keyword != null && !keyword.trim().isEmpty()) {
            em.close();
            handleSimpleSearch(request, response, keyword);
            return;
        }

        if (hasAdvancedSearchParams(request)) {
            em.close();
            handleAdvancedSearchFromForm(request, response);
            return;
        }

        if (pathInfo != null && pathInfo.startsWith("/result/")) {
            try {
                SearchCriteria criteria = parseUrlParameters(pathInfo);
                if (carDao.getEntityManager() != null) {
                    LOGGER.severe("CarDao đã setEntityManager!");
                }
                List<Car> searchResults = searchCarsAdvanced(carDao, criteria);
                request.setAttribute("searchResults", searchResults);
                request.setAttribute("searchCriteria", criteria);
                request.setAttribute("totalResults", searchResults.size());

                List<String> favoriteGlobalKeys = new ArrayList<>();
                HttpSession session = request.getSession(false);
                Object currentUser = (session != null) ? session.getAttribute("currentUser") : null;
                if (currentUser != null) {
                    // Lấy từ database nếu đã đăng nhập
                    User user = (User) currentUser;
                    int userId = user.getUserId();
                    FavoriteCarDAO favoriteCarDAO = new FavoriteCarDAO();
                    favoriteCarDAO.setEntityManager(em);
                    List<FavoriteCars> favorites = favoriteCarDAO.findByUserId(userId);
                    for (FavoriteCars f : favorites) {
                        favoriteGlobalKeys.add(f.getFavoriteCarsPK().getGlobalKey());
                    }
                    request.setAttribute("favoriteGlobalKeys", favoriteGlobalKeys);
                }
                request.setAttribute("favoriteGlobalKeys", favoriteGlobalKeys);
                request.getRequestDispatcher("/car/searchCar.jsp").forward(request, response);
                em.close(); // Đóng sau khi forward
            } catch (Exception e) {
                em.close();
                LOGGER.log(Level.SEVERE, "Error in advanced search", e);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL không hợp lệ: " + e.getMessage());
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private boolean hasAdvancedSearchParams(HttpServletRequest request) {
        return request.getParameter("maker") != null
                || request.getParameter("type") != null
                || request.getParameter("color") != null
                || request.getParameter("minYear") != null
                || request.getParameter("maxYear") != null
                || request.getParameter("minPrice") != null
                || request.getParameter("maxPrice") != null
                || request.getParameter("condition") != null
                || request.getParameter("transmission") != null
                || request.getParameter("minEngine") != null
                || request.getParameter("maxEngine") != null
                || request.getParameter("minPower") != null
                || request.getParameter("maxPower") != null
                || request.getParameter("maxKm") != null;
    }

    private void handleAdvancedSearchFromForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<String> urlParts = new ArrayList<>();

        String maker = request.getParameter("maker");
        if (maker != null && !maker.trim().isEmpty()) {
            urlParts.add("Maker");
            urlParts.add(maker);
        }

        String type = request.getParameter("type");
        if (type != null && !type.trim().isEmpty()) {
            urlParts.add("Type");
            urlParts.add(type);
        }

        String color = request.getParameter("color");
        if (color != null && !color.trim().isEmpty()) {
            urlParts.add("Color");
            urlParts.add(color);
        }

        String minYear = request.getParameter("minYear");
        String maxYear = request.getParameter("maxYear");
        if ((minYear != null && !minYear.trim().isEmpty())
                || (maxYear != null && !maxYear.trim().isEmpty())) {
            urlParts.add("Year");
            String yearValue = "";
            if (minYear != null && !minYear.trim().isEmpty()
                    && maxYear != null && !maxYear.trim().isEmpty()) {
                yearValue = minYear + "-" + maxYear;
            } else if (minYear != null && !minYear.trim().isEmpty()) {
                yearValue = minYear + "-2025";
            } else if (maxYear != null && !maxYear.trim().isEmpty()) {
                yearValue = "2020-" + maxYear;
            }
            urlParts.add(yearValue);
        }

        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        if ((minPrice != null && !minPrice.trim().isEmpty())
                || (maxPrice != null && !maxPrice.trim().isEmpty())) {
            urlParts.add("Price");
            String priceValue = "";
            if (minPrice != null && !minPrice.trim().isEmpty()
                    && maxPrice != null && !maxPrice.trim().isEmpty()) {
                priceValue = minPrice + "-" + maxPrice;
            } else if (minPrice != null && !minPrice.trim().isEmpty()) {
                priceValue = minPrice + "-10000000000";
            } else if (maxPrice != null && !maxPrice.trim().isEmpty()) {
                priceValue = "0-" + maxPrice;
            }
            urlParts.add(priceValue);
        }

        String condition = request.getParameter("condition");
        if (condition != null && !condition.trim().isEmpty()) {
            urlParts.add("Condition");
            urlParts.add(condition);
        }

        String transmission = request.getParameter("transmission");
        if (transmission != null && !transmission.trim().isEmpty()) {
            urlParts.add("Transmission");
            urlParts.add(transmission);
        }

        String minEngine = request.getParameter("minEngine");
        String maxEngine = request.getParameter("maxEngine");
        if ((minEngine != null && !minEngine.trim().isEmpty())
                || (maxEngine != null && !maxEngine.trim().isEmpty())) {
            urlParts.add("Engine");
            String engineValue = "";
            if (minEngine != null && !minEngine.trim().isEmpty()
                    && maxEngine != null && !maxEngine.trim().isEmpty()) {
                engineValue = minEngine + "-" + maxEngine;
            } else if (minEngine != null && !minEngine.trim().isEmpty()) {
                engineValue = minEngine + "-4000";
            } else if (maxEngine != null && !maxEngine.trim().isEmpty()) {
                engineValue = "1000-" + maxEngine;
            }
            urlParts.add(engineValue);
        }

        String minPower = request.getParameter("minPower");
        String maxPower = request.getParameter("maxPower");
        if ((minPower != null && !minPower.trim().isEmpty())
                || (maxPower != null && !maxPower.trim().isEmpty())) {
            urlParts.add("Power");
            String powerValue = "";
            if (minPower != null && !minPower.trim().isEmpty()
                    && maxPower != null && !maxPower.trim().isEmpty()) {
                powerValue = minPower + "-" + maxPower;
            } else if (minPower != null && !minPower.trim().isEmpty()) {
                powerValue = minPower + "-500";
            } else if (maxPower != null && !maxPower.trim().isEmpty()) {
                powerValue = "100-" + maxPower;
            }
            urlParts.add(powerValue);
        }

        String maxKm = request.getParameter("maxKm");
        if (maxKm != null && !maxKm.trim().isEmpty()) {
            urlParts.add("Km");
            urlParts.add(maxKm);
        }

        String searchUrl = request.getContextPath() + "/search/result";
        if (!urlParts.isEmpty()) {
            searchUrl += "/" + String.join("/", urlParts);
        }

        LOGGER.log(Level.INFO, "Redirecting to: {0}", searchUrl);
        response.sendRedirect(searchUrl);
    }

    private void handleSimpleSearch(HttpServletRequest request, HttpServletResponse response, String keyword)
            throws ServletException, IOException {
        try {
            String searchUrl = request.getContextPath() + "/search/result/Keyword/"
                    + java.net.URLEncoder.encode(keyword, "UTF-8");
            response.sendRedirect(searchUrl);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in simple search", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi tìm kiếm");
        }
    }

    private SearchCriteria parseUrlParameters(String pathInfo) throws IllegalArgumentException {
        SearchCriteria criteria = new SearchCriteria();
        String params = pathInfo.substring("/result/".length());
        String[] parts = params.split("/");
        for (int i = 0; i < parts.length - 1; i += 2) {
            String key = parts[i];
            String value = parts[i + 1];
            switch (key.toLowerCase()) {
                case "keyword":
                case "timkiem":
                    criteria.setKeyword(decodeUrlParam(value));
                    break;
                case "maker":
                case "hang":
                    criteria.setMaker(decodeUrlParam(value));
                    break;
                case "color":
                case "mau":
                    criteria.setColor(decodeUrlParam(value));
                    break;
                case "year":
                case "nam":
                    try {
                        if (value.contains("-")) {
                            String[] yearRange = value.split("-");
                            criteria.setMinYear(Integer.valueOf(yearRange[0]));
                            criteria.setMaxYear(Integer.valueOf(yearRange[1]));
                        } else {
                            criteria.setYear(Integer.valueOf(value));
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Năm không hợp lệ: " + value);
                    }
                    break;
                case "price":
                case "gia":
                    try {
                        if (value.contains("-")) {
                            String[] priceRange = value.split("-");
                            criteria.setMinPrice(new BigDecimal(priceRange[0]));
                            criteria.setMaxPrice(new BigDecimal(priceRange[1]));
                        } else {
                            criteria.setMaxPrice(new BigDecimal(value));
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Giá không hợp lệ: " + value);
                    }
                    break;
                case "type":
                case "loai":
                    criteria.setCarType(decodeUrlParam(value));
                    break;
                case "condition":
                case "tinhtrang":
                    criteria.setCondition(decodeUrlParam(value));
                    break;
                case "transmission":
                case "hopso":
                    criteria.setTransmission(decodeUrlParam(value));
                    break;
                case "engine":
                case "dongco":
                    try {
                        if (value.contains("-")) {
                            String[] engineRange = value.split("-");
                            criteria.setMinEngine(Integer.valueOf(engineRange[0]));
                            criteria.setMaxEngine(Integer.valueOf(engineRange[1]));
                        } else {
                            criteria.setMinEngine(Integer.valueOf(value));
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Dung tích động cơ không hợp lệ: " + value);
                    }
                    break;
                case "power":
                case "congsuat":
                    try {
                        if (value.contains("-")) {
                            String[] powerRange = value.split("-");
                            criteria.setMinPower(Integer.parseInt(powerRange[0]));
                            criteria.setMaxPower(Integer.parseInt(powerRange[1]));
                        } else {
                            criteria.setMinPower(Integer.parseInt(value));
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Công suất không hợp lệ: " + value);
                    }
                    break;
                case "km":
                case "kmdadi":
                    try {
                        criteria.setMaxKm(Integer.parseInt(value));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Số km không hợp lệ: " + value);
                    }
                    break;
                default:
                    LOGGER.log(Level.WARNING, "Unknown search parameter: {0}", key);
                    break;
            }
        }
        return criteria;
    }

    private String decodeUrlParam(String param) {
        try {
            return java.net.URLDecoder.decode(param, "UTF-8");
        } catch (Exception e) {
            return param;
        }
    }

    private List<Car> searchCarsAdvanced(CarDao carDao, SearchCriteria criteria) {
        List<Car> allCars = carDao.getAll();
        List<Car> results = new ArrayList<>();
        for (Car car : allCars) {
            if (matchesCriteria(car, criteria)) {
                results.add(car);
            }
        }
        return results;
    }

    private boolean matchesKeyword(Car car, String keyword) {
        return (car.getCarName() != null && car.getCarName().toLowerCase().contains(keyword))
                || (car.getColor() != null && car.getColor().toLowerCase().contains(keyword))
                || (car.getCondition() != null && car.getCondition().toLowerCase().contains(keyword));
    }

    private boolean matchesCriteria(Car car, SearchCriteria criteria) {
        if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
            if (!matchesKeyword(car, criteria.getKeyword().toLowerCase().trim())) {
                return false;
            }
        }
        if (criteria.getMaker() != null && !criteria.getMaker().isEmpty()) {
            if (!criteria.getMaker().equalsIgnoreCase(car.getCarBrandName())) {
                return false;
            }
        }
        if (criteria.getColor() != null && !criteria.getColor().isEmpty()) {
            if (car.getColor() == null || !car.getColor().equalsIgnoreCase(criteria.getColor())) {
                return false;
            }
        }
        if (criteria.getYear() != null) {
            if (car.getYear() == null || !car.getYear().equals(criteria.getYear())) {
                return false;
            }
        }
        if (criteria.getMinYear() != null && (car.getYear() == null || car.getYear() < criteria.getMinYear())) {
            return false;
        }
        if (criteria.getMaxYear() != null && (car.getYear() == null || car.getYear() > criteria.getMaxYear())) {
            return false;
        }
        if (criteria.getMinPrice() != null && car.getSalePrice() != null
                && (new BigDecimal(car.getSalePrice()).compareTo(criteria.getMinPrice()) < 0)) {
            return false;
        }
        if (criteria.getMaxPrice() != null && car.getSalePrice() != null
                && (new BigDecimal(car.getSalePrice()).compareTo(criteria.getMaxPrice()) > 0)) {
            return false;
        }
        if (criteria.getCarType() != null && !criteria.getCarType().isEmpty()) {
            if (!criteria.getCarType().equalsIgnoreCase(car.getCarType())) {
                return false;
            }
        }
        if (criteria.getCondition() != null && !criteria.getCondition().isEmpty()) {
            if (car.getCondition() == null || !car.getCondition().equalsIgnoreCase(criteria.getCondition())) {
                return false;
            }
        }
        if (criteria.getTransmission() != null && !criteria.getTransmission().isEmpty()) {
            if (car.getTransmission() == null || !car.getTransmission().equalsIgnoreCase(criteria.getTransmission())) {
                return false;
            }
        }
        if (criteria.getMinEngine() != null && (car.getEngineCapacity() == null || car.getEngineCapacity() < criteria.getMinEngine())) {
            return false;
        }
        if (criteria.getMaxEngine() != null && (car.getEngineCapacity() == null || car.getEngineCapacity() > criteria.getMaxEngine())) {
            return false;
        }
        if (criteria.getMinPower() != null && (car.getPower() == null || car.getPower() < criteria.getMinPower())) {
            return false;
        }
        if (criteria.getMaxPower() != null && (car.getPower() == null || car.getPower() > criteria.getMaxPower())) {
            return false;
        }
        if (criteria.getMaxKm() != null && (car.getMileage() == null || car.getMileage() > criteria.getMaxKm())) {
            return false;
        }
        return "Có sẵn".equals(car.getStatus());
    }

    public static class SearchCriteria {

        private String keyword;
        private String maker;
        private String color;
        private Integer year;
        private Integer minYear;
        private Integer maxYear;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private String carType;
        private String condition;
        private String transmission;
        private Integer minEngine;
        private Integer maxEngine;
        private Integer minPower;
        private Integer maxPower;
        private Integer maxKm;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getMaker() {
            return maker;
        }

        public void setMaker(String maker) {
            this.maker = maker;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getMinYear() {
            return minYear;
        }

        public void setMinYear(Integer minYear) {
            this.minYear = minYear;
        }

        public Integer getMaxYear() {
            return maxYear;
        }

        public void setMaxYear(Integer maxYear) {
            this.maxYear = maxYear;
        }

        public BigDecimal getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(BigDecimal minPrice) {
            this.minPrice = minPrice;
        }

        public BigDecimal getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(BigDecimal maxPrice) {
            this.maxPrice = maxPrice;
        }

        public String getCarType() {
            return carType;
        }

        public void setCarType(String carType) {
            this.carType = carType;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getTransmission() {
            return transmission;
        }

        public void setTransmission(String transmission) {
            this.transmission = transmission;
        }

        public Integer getMinEngine() {
            return minEngine;
        }

        public void setMinEngine(Integer minEngine) {
            this.minEngine = minEngine;
        }

        public Integer getMaxEngine() {
            return maxEngine;
        }

        public void setMaxEngine(Integer maxEngine) {
            this.maxEngine = maxEngine;
        }

        public Integer getMinPower() {
            return minPower;
        }

        public void setMinPower(Integer minPower) {
            this.minPower = minPower;
        }

        public Integer getMaxPower() {
            return maxPower;
        }

        public void setMaxPower(Integer maxPower) {
            this.maxPower = maxPower;
        }

        public Integer getMaxKm() {
            return maxKm;
        }

        public void setMaxKm(Integer maxKm) {
            this.maxKm = maxKm;
        }

        @Override
        public String toString() {
            return "SearchCriteria{"
                    + "keyword='" + keyword + '\''
                    + ", maker='" + maker + '\''
                    + ", color='" + color + '\''
                    + ", year=" + year
                    + ", minPrice=" + minPrice
                    + ", maxPrice=" + maxPrice
                    + ", carType='" + carType + '\''
                    + ", condition='" + condition + '\''
                    + '}';
        }
    }

    @Override
    public String getServletInfo() {
        return "SearchServlet xử lý tìm kiếm xe với URL pattern phức tạp";
    }
}
