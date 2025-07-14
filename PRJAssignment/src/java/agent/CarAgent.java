package agent;

import userDao.FavoriteCarDAO;
import userDao.ConversationHistoryDAO;
import userDao.ViewedCarsDAO;
import userDao.SearchHistoryDAO;
import carDao.CarDao;
import model.FavoriteCars;
import model.Car;
import model.ConversationHistory;
import model.ViewedCars;
import model.PageHistory;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Date;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CarAgent {

    private final String apiKey;
    private final FavoriteCarDAO favoriteCarDAO;
    private final CarDao carDao;
    private final ConversationHistoryDAO conversationHistoryDAO;
    private final ViewedCarsDAO viewedCarsDAO;
    private final SearchHistoryDAO searchHistoryDAO;

    public CarAgent(
            String apiKey,
            FavoriteCarDAO favoriteCarDAO,
            CarDao carDao,
            ConversationHistoryDAO conversationHistoryDAO,
            ViewedCarsDAO viewedCarsDAO,
            SearchHistoryDAO searchHistoryDAO
    ) {
        this.apiKey = apiKey;
        this.favoriteCarDAO = favoriteCarDAO;
        this.carDao = carDao;
        this.conversationHistoryDAO = conversationHistoryDAO;
        this.viewedCarsDAO = viewedCarsDAO;
        this.searchHistoryDAO = searchHistoryDAO;
    }

    /**
     * Xử lý prompt, chỉ trả về trực tiếp các thông tin cho phép (favorite,
     * conversation). Các phần dữ liệu lịch sử khác chỉ dùng để truyền vào
     * prompt AI khi cần.
     */
    public String handlePrompt(String prompt, Integer userId, String sessionId, HttpServletRequest request, HttpServletResponse response) {
        boolean isLoggedIn = (userId != null && userId > 0);

        if (isLoggedIn) {
            saveConversationHistory("user", prompt, userId, sessionId);
        }

        String conversationHistory = isLoggedIn
                ? getConversationHistoryForPrompt(userId)
                : getConversationHistoryForPrompt(request);

        String actionJson = callPerplexityForAction(prompt, userId);
        if (actionJson == null || actionJson.isEmpty()) {
            String fallback = "Không thể phân tích ý định từ AI.";
            if (isLoggedIn) {
                saveConversationHistory("assistant", fallback, userId, sessionId);
            }
            return fallback;
        }

        Gson gson = new Gson();
        JsonObject actionObj;
        try {
            actionObj = gson.fromJson(actionJson, JsonObject.class);
        } catch (Exception e) {
            String fallback = callPerplexitySonar(prompt);
            if (isLoggedIn) {
                saveConversationHistory("assistant", fallback, userId, sessionId);
            }
            return fallback;
        }

        String action = actionObj.has("action") ? actionObj.get("action").getAsString() : "";
        String answer;

        switch (action) {
            case "analyze_favorite_cars":
            case "recommend_from_favorite_cars":
            case "compare_favorite_cars":
                if (isLoggedIn) {
                    String data = getFavoriteCarsRaw(userId);
                    String viewedCars = getViewedCarsForPrompt(userId);
                    String searchHistory = getSearchHistoryForPrompt(userId);
                    String userQuestion = actionObj.has("userQuestion") ? actionObj.get("userQuestion").getAsString() : prompt;
                    String aiPrompt
                            = "Lịch sử hội thoại:\n" + conversationHistory
                            + "\nDanh sách xe yêu thích:\n" + data
                            + "\nXe đã xem:\n" + viewedCars
                            + "\nLịch sử tìm kiếm:\n" + searchHistory
                            + "\nHãy trả lời hoặc tư vấn cho câu hỏi: " + userQuestion;
                    answer = callPerplexitySonar(aiPrompt);
                } else {
                    String data = getFavoriteCarsFromCookie(request);
                    String viewedCars = getViewedCarsForPrompt(request);
                    String searchHistory = getSearchHistoryForPrompt(request);
                    String userQuestion = actionObj.has("userQuestion") ? actionObj.get("userQuestion").getAsString() : prompt;
                    String aiPrompt
                            = "Lịch sử hội thoại:\n" + conversationHistory
                            + "\nDanh sách xe yêu thích:\n" + data
                            + "\nXe đã xem:\n" + viewedCars
                            + "\nLịch sử tìm kiếm:\n" + searchHistory
                            + "\nHãy trả lời hoặc tư vấn cho câu hỏi: " + userQuestion;
                    answer = callPerplexitySonar(aiPrompt);
                }
                break;

            case "get_conversation_history":
                answer = isLoggedIn
                        ? getConversationHistoryPlain(userId)
                        : getConversationHistoryFromCookie(request);
                break;

            case "update_favorite_car":
                String globalKey = actionObj.has("globalKey") ? actionObj.get("globalKey").getAsString() : null;
                if (globalKey == null || globalKey.trim().isEmpty()) {
                    answer = "Thiếu thông tin xe để cập nhật yêu thích.";
                } else {
                    answer = isLoggedIn
                            ? updateFavoriteCar(userId, globalKey)
                            : updateFavoriteCarInCookie(globalKey, request, response);
                }
                break;

            case "suggest_cars_by_budget":
                double budget = actionObj.has("budget") ? actionObj.get("budget").getAsDouble() : -1;
                List<Car> carsByBudget = carDao.getAll().stream()
                        .filter(car -> car.getSalePrice() <= budget)
                        .collect(Collectors.toList());
                answer = suggestAndReply(carsByBudget, "Dưới đây là các xe phù hợp với ngân sách " + budget + ":", conversationHistory, prompt);
                break;

            case "personalized_recommendation":
                String trend = isLoggedIn ? analyzeUserTrend(userId) : "";
                List<Car> personalizedCars = isLoggedIn ? multiSourceSuggest(userId) : smartSuggest(request);
                String title = "Gợi ý cá nhân hóa dành cho bạn.";
                if (!trend.isEmpty()) {
                    title += " " + trend;
                }
                answer = suggestAndReply(personalizedCars, title, conversationHistory, prompt);
                break;

            case "clarify_information":
                String missingField = actionObj.has("missingField") ? actionObj.get("missingField").getAsString() : "";
                answer = "Bạn vui lòng bổ sung thông tin: " + missingField + " để mình có thể tư vấn chính xác hơn nhé!";
                break;

            default:
                String carListText = getAllCarsForPrompt();
                String aiPrompt = "Dưới đây là danh sách các xe hiện có trong hệ thống:\n" + carListText
                        + "\nLịch sử hội thoại:\n" + conversationHistory
                        + "\nUser: " + prompt;
                answer = callPerplexitySonar(aiPrompt);
        }

        if (isLoggedIn) {
            saveConversationHistory("assistant", answer, userId, sessionId);
        }

        return answer;
    }

    // ==== LỌC TOP XE PHÙ HỢP NHẤT ====
    private double score(Car car) {
        double price = car.getSalePrice() != null ? car.getSalePrice() : 0;
        int year = car.getYear() != null ? car.getYear() : 0;
        int seat = car.getSeatCount() != null ? car.getSeatCount() : 0;
        return year * 1000 + seat * 100 - price / 1_000_000;
    }

    private List<Car> selectTopCars(List<Car> cars) {
        List<Car> sorted = cars.stream()
                .sorted((a, b) -> Double.compare(score(b), score(a)))
                .collect(Collectors.toList());

        if (sorted.size() <= 3) {
            return sorted;
        }

        double score3 = score(sorted.get(2));
        double score4 = sorted.size() > 3 ? score(sorted.get(3)) : score3;

        if (score3 - score4 > 1000) {
            return sorted.subList(0, 3);
        }

        double topScore = score(sorted.get(0));
        List<Car> topCars = new ArrayList<>();
        for (Car car : sorted) {
            if (topCars.size() >= 4) {
                break;
            }
            if (Math.abs(score(car) - topScore) < 1e-6 || topCars.size() < 3) {
                topCars.add(car);
            }
        }
        return topCars;
    }

    // ==== PHÂN TÍCH XU HƯỚNG CÁ NHÂN ====
    private String analyzeUserTrend(int userId) {
        List<ViewedCars> viewed = viewedCarsDAO.findByUserId(userId);
        if (viewed == null || viewed.isEmpty()) {
            return "";
        }

        java.util.Map<String, Integer> brandCount = new java.util.HashMap<>();
        java.util.Map<String, Integer> modelCount = new java.util.HashMap<>();
        java.util.Map<String, Integer> fuelCount = new java.util.HashMap<>();
        java.util.Map<Integer, Integer> seatCount = new java.util.HashMap<>();
        java.util.Map<String, Integer> typeCount = new java.util.HashMap<>();
        java.util.Map<Long, Integer> priceGroupCount = new java.util.HashMap<>();

        for (ViewedCars v : viewed) {
            Car car = carDao.getCarByGlobalKey(v.getViewedCarsPK().getGlobalKey());
            if (car != null) {
                String brand = car.getCarBrandName();
                if (!brand.isEmpty()) {
                    brandCount.put(brand, brandCount.getOrDefault(brand, 0) + 1);
                }

                String model = car.getCarModelName();
                if (!model.isEmpty()) {
                    modelCount.put(model, modelCount.getOrDefault(model, 0) + 1);
                }

                String fuel = car.getFuelType();
                if (!fuel.isEmpty()) {
                    fuelCount.put(fuel, fuelCount.getOrDefault(fuel, 0) + 1);
                }

                Integer seat = car.getSeatCount();
                if (seat != null) {
                    seatCount.put(seat, seatCount.getOrDefault(seat, 0) + 1);
                }

                String type = car.getCarType();
                if (!type.isEmpty()) {
                    typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
                }

                Long price = car.getSalePrice();
                if (price != null && price > 0) {
                    long priceGroup = (price / 100_000_000L) * 100_000_000L;
                    priceGroupCount.put(priceGroup, priceGroupCount.getOrDefault(priceGroup, 0) + 1);
                }
            }
        }

        String topBrand = brandCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        String topModel = modelCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        String topFuel = fuelCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        Integer topSeat = seatCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        String topType = typeCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        Long topPriceGroup = priceGroupCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        StringBuilder trend = new StringBuilder();
        if (topBrand != null) {
            trend.append("Bạn thường xem xe hãng: ").append(topBrand).append(". ");
        }
        if (topModel != null) {
            trend.append("Dòng xe phổ biến: ").append(topModel).append(". ");
        }
        if (topFuel != null) {
            trend.append("Loại nhiên liệu ưa thích: ").append(topFuel).append(". ");
        }
        if (topSeat != null) {
            trend.append("Số chỗ ngồi phổ biến: ").append(topSeat).append(". ");
        }
        if (topType != null) {
            trend.append("Kiểu xe thường xem: ").append(topType).append(". ");
        }
        if (topPriceGroup != null) {
            trend.append("Tầm giá bạn quan tâm nhiều: khoảng ").append(String.format("%,d", topPriceGroup)).append(" VNĐ. ");
        }
        return trend.toString();
    }

    // ==== GỢI Ý XE ĐA NGUỒN (chỉ lấy top 3-4 xe) ====
    private List<Car> multiSourceSuggest(int userId) {
        List<String> viewed = viewedCarsDAO.findByUserId(userId).stream()
                .map(v -> v.getViewedCarsPK().getGlobalKey())
                .collect(Collectors.toList());
        List<String> favorite = favoriteCarDAO.findByUserId(userId).stream()
                .map(f -> f.getFavoriteCarsPK().getGlobalKey())
                .collect(Collectors.toList());

        java.util.Map<String, Integer> brandCount = new java.util.HashMap<>();
        java.util.Map<String, Integer> modelCount = new java.util.HashMap<>();
        java.util.Map<String, Integer> fuelCount = new java.util.HashMap<>();
        java.util.Map<Integer, Integer> seatCount = new java.util.HashMap<>();
        java.util.Map<String, Integer> typeCount = new java.util.HashMap<>();
        java.util.Map<Long, Integer> priceGroupCount = new java.util.HashMap<>();

        for (String key : viewed) {
            Car car = carDao.getCarByGlobalKey(key);
            if (car != null) {
                String brand = car.getCarBrandName();
                if (!brand.isEmpty()) {
                    brandCount.put(brand, brandCount.getOrDefault(brand, 0) + 1);
                }

                String model = car.getCarModelName();
                if (!model.isEmpty()) {
                    modelCount.put(model, modelCount.getOrDefault(model, 0) + 1);
                }

                String fuel = car.getFuelType();
                if (!fuel.isEmpty()) {
                    fuelCount.put(fuel, fuelCount.getOrDefault(fuel, 0) + 1);
                }

                Integer seat = car.getSeatCount();
                if (seat != null) {
                    seatCount.put(seat, seatCount.getOrDefault(seat, 0) + 1);
                }

                String type = car.getCarType();
                if (!type.isEmpty()) {
                    typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
                }

                Long price = car.getSalePrice();
                if (price != null && price > 0) {
                    long priceGroup = (price / 100_000_000L) * 100_000_000L;
                    priceGroupCount.put(priceGroup, priceGroupCount.getOrDefault(priceGroup, 0) + 1);
                }
            }
        }
        for (String key : favorite) {
            Car car = carDao.getCarByGlobalKey(key);
            if (car != null) {
                String brand = car.getCarBrandName();
                if (!brand.isEmpty()) {
                    brandCount.put(brand, brandCount.getOrDefault(brand, 0) + 1);
                }

                String model = car.getCarModelName();
                if (!model.isEmpty()) {
                    modelCount.put(model, modelCount.getOrDefault(model, 0) + 1);
                }

                String fuel = car.getFuelType();
                if (!fuel.isEmpty()) {
                    fuelCount.put(fuel, fuelCount.getOrDefault(fuel, 0) + 1);
                }

                Integer seat = car.getSeatCount();
                if (seat != null) {
                    seatCount.put(seat, seatCount.getOrDefault(seat, 0) + 1);
                }

                String type = car.getCarType();
                if (!type.isEmpty()) {
                    typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
                }

                Long price = car.getSalePrice();
                if (price != null && price > 0) {
                    long priceGroup = (price / 100_000_000L) * 100_000_000L;
                    priceGroupCount.put(priceGroup, priceGroupCount.getOrDefault(priceGroup, 0) + 1);
                }
            }
        }

        String topBrand = brandCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        String topModel = modelCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        String topFuel = fuelCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        Integer topSeat = seatCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        String topType = typeCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        Long topPriceGroup = priceGroupCount.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey).orElse(null);

        List<Car> all = carDao.getAll();
        List<Car> filtered = all.stream()
                .filter(car -> !viewed.contains(car.getGlobalKey()) && !favorite.contains(car.getGlobalKey()))
                .filter(car
                        -> (topBrand != null && topBrand.equals(car.getCarBrandName()))
                || (topModel != null && topModel.equals(car.getCarModelName()))
                || (topFuel != null && topFuel.equals(car.getFuelType()))
                || (topSeat != null && topSeat.equals(car.getSeatCount()))
                || (topType != null && topType.equals(car.getCarType()))
                || (topPriceGroup != null && car.getSalePrice() != null
                && (car.getSalePrice() / 100_000_000L) * 100_000_000L == topPriceGroup)
                )
                .collect(Collectors.toList());
        return selectTopCars(filtered);
    }

    // ==== GỢI Ý XE DỰA TRÊN COOKIE (chỉ lấy top 3-4 xe) ====
    private List<Car> smartSuggest(HttpServletRequest request) {
        List<String> viewed = getListFromCookie(request, "viewedCars");
        List<String> favorite = getListFromCookie(request, "favoriteCars");
        List<Car> all = carDao.getAll();
        List<Car> filtered = all.stream()
                .filter(car -> !viewed.contains(car.getGlobalKey()) && !favorite.contains(car.getGlobalKey()))
                .collect(Collectors.toList());
        return selectTopCars(filtered);
    }

    // ==== HÀM HỖ TRỢ ĐỂ TRẢ LỜI GỢI Ý XE ====
    private String suggestAndReply(List<Car> cars, String title, String conversationHistory, String prompt) {
        List<Car> topCars = selectTopCars(cars);
        if (topCars == null || topCars.isEmpty()) {
            return "Không tìm thấy xe phù hợp với yêu cầu của bạn.";
        }
        StringBuilder carList = new StringBuilder(title + "\n");
        int stt = 1;
        for (Car car : topCars) {
            carList.append(stt++).append(". ").append(car.toPromptString()).append("\n");
        }
        String aiPrompt = carList.toString()
                + "\nLịch sử hội thoại:\n" + conversationHistory
                + "\nUser: " + prompt;
        return callPerplexitySonar(aiPrompt);
    }

    // ====== Các hàm lấy dữ liệu để truyền vào prompt AI ======
    public String getAllCarsForPrompt() {
        List<Car> allCars = carDao.getAll();
        if (allCars.isEmpty()) {
            return "Không có xe nào trong hệ thống.";
        }
        StringBuilder carList = new StringBuilder();
        int stt = 1;
        for (Car car : allCars) {
            carList.append(stt++).append(". ").append(car.toPromptString()).append("\n");
        }
        return carList.toString();
    }

    public String getConversationHistoryForPrompt(int userId) {
        List<ConversationHistory> historyList = conversationHistoryDAO.findByUserId(userId);
        if (historyList == null || historyList.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (ConversationHistory ch : historyList) {
            sb.append(ch.getMessageRole()).append(": ").append(ch.getMessageContent()).append("\n");
        }
        return sb.toString();
    }

    public String getConversationHistoryForPrompt(HttpServletRequest request) {
        List<String> history = getListFromCookie(request, "conversationHistory");
        if (history.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String msg : history) {
            sb.append(msg).append("\n");
        }
        return sb.toString();
    }

    public String getViewedCarsForPrompt(int userId) {
        List<ViewedCars> histories = viewedCarsDAO.findByUserId(userId);
        if (histories == null || histories.isEmpty()) {
            return "";
        }
        List<String> globalKeys = histories.stream()
                .map(v -> v.getViewedCarsPK().getGlobalKey())
                .collect(Collectors.toList());
        List<Car> cars = new ArrayList<>();
        for (String key : globalKeys) {
            Car car = carDao.getCarByGlobalKey(key);
            if (car != null) {
                cars.add(car);
            }
        }
        StringBuilder sb = new StringBuilder();
        int stt = 1;
        for (Car car : cars) {
            sb.append(stt++).append(". ").append(car.toPromptString()).append("\n");
        }
        return sb.toString();
    }

    public String getViewedCarsForPrompt(HttpServletRequest request) {
        List<String> globalKeys = getListFromCookie(request, "viewedCars");
        if (globalKeys.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int stt = 1;
        for (String key : globalKeys) {
            Car car = carDao.getCarByGlobalKey(key);
            if (car != null) {
                sb.append(stt++).append(". ").append(car.toPromptString()).append("\n");
            }
        }
        return sb.toString();
    }

    public String getSearchHistoryForPrompt(int userId) {
        List<PageHistory> histories = searchHistoryDAO.findByUserId(userId);
        if (histories == null || histories.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int stt = 1;
        for (PageHistory h : histories) {
            sb.append(stt++).append(". ").append(h.getPageHistoryPK().getPath()).append("\n");
        }
        return sb.toString();
    }

    public String getSearchHistoryForPrompt(HttpServletRequest request) {
        List<String> history = getListFromCookie(request, "searchHistory");
        if (history.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int stt = 1;
        for (String path : history) {
            sb.append(stt++).append(". ").append(path).append("\n");
        }
        return sb.toString();
    }

    // ====== Các hàm thao tác với DB hoặc cookie cho user ======
    private String getFavoriteCarsFromCookie(HttpServletRequest request) {
        List<String> globalKeys = getListFromCookie(request, "favoriteCars");
        if (globalKeys.isEmpty()) {
            return "Bạn chưa có xe yêu thích nào trong danh sách.";
        }
        StringBuilder carList = new StringBuilder("Danh sách xe yêu thích của bạn (chưa đăng nhập):\n");
        int stt = 1;
        for (String key : globalKeys) {
            Car car = carDao.getCarByGlobalKey(key);
            if (car != null) {
                carList.append(stt++).append(". ").append(car.toPromptString()).append("\n");
            } else {
                carList.append(stt++).append(". [Không tìm thấy thông tin xe với mã ").append(key).append("]\n");
            }
        }
        return carList.toString();
    }

    private List<String> getListFromCookie(HttpServletRequest request, String cookieName) {
        List<String> result = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    String value = java.net.URLDecoder.decode(c.getValue(), java.nio.charset.StandardCharsets.UTF_8);
                    if (value != null && !value.isEmpty()) {
                        String[] arr;
                        if ("searchHistory".equals(cookieName)) {
                            arr = value.split("\\|");
                        } else if ("conversationHistory".equals(cookieName)) {
                            arr = value.split("\n");
                        } else {
                            arr = value.split(",");
                        }
                        for (String s : arr) {
                            if (!s.trim().isEmpty()) {
                                result.add(s.trim());
                            }
                        }
                    }
                    break;
                }
            }
        }
        return result;
    }

    private String updateFavoriteCar(int userId, String globalKey) {
        try {
            FavoriteCars existing = favoriteCarDAO.getById(new model.FavoriteCarsPK(userId, globalKey));
            if (existing == null) {
                FavoriteCars favorite = new FavoriteCars();
                favorite.setFavoriteCarsPK(new model.FavoriteCarsPK(userId, globalKey));
                favorite.setCreatedAt(new Date());
                favoriteCarDAO.add(favorite);
                return "Đã thêm vào yêu thích!";
            } else {
                favoriteCarDAO.remove(existing.getFavoriteCarsPK());
                return "Đã bỏ khỏi yêu thích!";
            }
        } catch (Exception e) {
            return "Có lỗi khi cập nhật yêu thích: " + e.getMessage();
        }
    }

    private String updateFavoriteCarInCookie(String globalKey, HttpServletRequest request, HttpServletResponse response) {
        List<String> favoriteList = getListFromCookie(request, "favoriteCars");
        boolean added;
        if (!favoriteList.contains(globalKey)) {
            favoriteList.add(globalKey);
            added = true;
        } else {
            favoriteList.remove(globalKey);
            added = false;
        }
        String newValue = java.net.URLEncoder.encode(String.join(",", favoriteList), java.nio.charset.StandardCharsets.UTF_8);
        Cookie cookie = new Cookie("favoriteCars", newValue);
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        response.addCookie(cookie);
        return added ? "Đã thêm vào yêu thích (cookie)!" : "Đã bỏ khỏi yêu thích (cookie)!";
    }

    public String getConversationHistoryPlain(int userId) {
        List<ConversationHistory> historyList = conversationHistoryDAO.findByUserId(userId);
        if (historyList == null || historyList.isEmpty()) {
            return "Bạn chưa có lịch sử hội thoại nào.";
        }
        StringBuilder sb = new StringBuilder("Lịch sử hội thoại:\n");
        int stt = 1;
        for (ConversationHistory ch : historyList) {
            sb.append(stt++).append(". [").append(ch.getCreatedAt()).append("] ")
                    .append(ch.getMessageRole()).append(": ")
                    .append(ch.getMessageContent()).append("\n");
        }
        return sb.toString();
    }

    private String getConversationHistoryFromCookie(HttpServletRequest request) {
        List<String> history = getListFromCookie(request, "conversationHistory");
        if (history.isEmpty()) {
            return "Bạn chưa có lịch sử hội thoại nào.";
        }
        StringBuilder sb = new StringBuilder("Lịch sử hội thoại (chưa đăng nhập):\n");
        int stt = 1;
        for (String msg : history) {
            sb.append(stt++).append(". ").append(msg).append("\n");
        }
        return sb.toString();
    }

    private String getFavoriteCarsRaw(int userId) {
        List<FavoriteCars> favorites = favoriteCarDAO.findByUserId(userId);
        if (favorites == null || favorites.isEmpty()) {
            return "Không có xe yêu thích.";
        }
        StringBuilder carList = new StringBuilder();
        int stt = 1;
        for (FavoriteCars fav : favorites) {
            String globalKey = fav.getFavoriteCarsPK().getGlobalKey();
            Car car = carDao.getCarByGlobalKey(globalKey);
            if (car != null) {
                carList.append(stt++).append(". ").append(car.toPromptString()).append("\n");
            }
        }
        return carList.toString();
    }

    public void saveConversationHistory(String messageRole, String messageContent, Integer userId, String sessionId) {
        ConversationHistory ch = new ConversationHistory();
        ch.setMessageRole(messageRole);
        ch.setMessageContent(messageContent);
        ch.setUserID(userId);
        ch.setSessionID(sessionId);
        ch.setCreatedAt(new java.util.Date());
        conversationHistoryDAO.save(ch);
    }

    // Gọi LLM để phân tích prompt, trả về action JSON - ĐÃ CẬP NHẬT PROMPT ACTION MỚI
    private String callPerplexityForAction(String prompt, Integer userId) {
        try {
            URL url = new URL("https://api.perplexity.ai/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setDoOutput(true);

            JsonObject systemMessage = new JsonObject();
            systemMessage.addProperty("role", "system");
            systemMessage.addProperty("content",
                    "Bạn là Agent AI cho website bán xe. Khi nhận prompt, hãy phân tích ý định và trả về action cần thực hiện cùng các tham số dưới dạng JSON. "
                    + "Các action có thể gồm: 'analyze_favorite_cars', 'recommend_from_favorite_cars', 'compare_favorite_cars', "
                    + "'get_conversation_history', 'get_all_cars', 'update_favorite_car', "
                    + "'suggest_cars_by_budget' (kèm budget), 'suggest_cars_by_usage' (kèm usage), "
                    + "'suggest_cars_by_brand' (kèm brand), 'suggest_cars_by_feature' (kèm feature), "
                    + "'personalized_recommendation', 'clarify_information' (kèm missingField). "
                    + "Luôn trả về action và các tham số cần thiết ở dạng JSON."
            );

            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", prompt);

            JsonArray messages = new JsonArray();
            messages.add(systemMessage);
            messages.add(userMessage);

            JsonObject payload = new JsonObject();
            payload.addProperty("model", "sonar");
            payload.add("messages", messages);
            payload.addProperty("max_tokens", 500);
            payload.addProperty("temperature", 0.2);

            String jsonInputString = payload.toString();

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            InputStreamReader isr;
            if (status >= 200 && status < 300) {
                isr = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
            } else {
                isr = new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(isr)) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            if (status >= 400) {
                return null;
            }

            Gson gson = new Gson();
            JsonObject jsonObj = gson.fromJson(response.toString(), JsonObject.class);
            JsonArray choices = jsonObj.getAsJsonArray("choices");
            if (choices != null && choices.size() > 0) {
                JsonObject messageResp = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                if (messageResp != null && messageResp.has("content")) {
                    return messageResp.get("content").getAsString();
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // Gọi Perplexity Sonar để sinh câu trả lời tự nhiên cho prompt bất kỳ - không thay đổi
    private String callPerplexitySonar(String prompt) {
        try {
            URL url = new URL("https://api.perplexity.ai/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setDoOutput(true);

            JsonObject systemMessage = new JsonObject();
            systemMessage.addProperty("role", "system");
            systemMessage.addProperty("content",
                    "Bạn là Agent AI chatbot cho trang web chuyên bán xe cả mới và cũ. Luôn trả lời chính xác, ngắn gọn, thân thiện, ưu tiên dữ liệu thực tế từ hệ thống.");

            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", prompt);

            JsonArray messages = new JsonArray();
            messages.add(systemMessage);
            messages.add(userMessage);

            JsonObject payload = new JsonObject();
            payload.addProperty("model", "sonar");
            payload.add("messages", messages);
            payload.addProperty("max_tokens", 500);
            payload.addProperty("temperature", 0.7);

            String jsonInputString = payload.toString();

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            InputStreamReader isr;
            if (status >= 200 && status < 300) {
                isr = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
            } else {
                isr = new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(isr)) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            if (status >= 400) {
                return "Lỗi Perplexity API: " + response.toString();
            }

            Gson gson = new Gson();
            JsonObject jsonObj = gson.fromJson(response.toString(), JsonObject.class);
            JsonArray choices = jsonObj.getAsJsonArray("choices");
            if (choices != null && choices.size() > 0) {
                JsonObject messageResp = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                if (messageResp != null && messageResp.has("content")) {
                    return messageResp.get("content").getAsString();
                }
            }
            return "[Không thể trích xuất câu trả lời từ Perplexity]";
        } catch (Exception e) {
            return "Lỗi khi gọi Perplexity API: " + e.getMessage();
        }
    }
}
