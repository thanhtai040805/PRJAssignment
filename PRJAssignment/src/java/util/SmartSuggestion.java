package util; // Đảm bảo đúng package

import model.Car;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class SmartSuggestion {

    public static List<Car> suggest(List<Car> viewedCars, List<Car> favoriteCars) {
        // Sử dụng LinkedHashMap để duy trì thứ tự và đảm bảo không trùng lặp
        Map<Integer, Car> uniqueSuggestions = new LinkedHashMap<>();

        // Ưu tiên xe đã yêu thích
        for (Car car : favoriteCars) {
            uniqueSuggestions.put(car.getMaXe(), car);
        }

        // Tiếp theo là xe đã xem, nhưng vẫn ưu tiên các xe được xem gần đây hơn (đầu danh sách)
        for (Car car : viewedCars) {
            // Chỉ thêm nếu chưa có trong danh sách gợi ý (để ưu tiên xe yêu thích)
            uniqueSuggestions.putIfAbsent(car.getMaXe(), car);
        }

        // Lấy danh sách từ map
        List<Car> suggestions = new ArrayList<>(uniqueSuggestions.values());

        // Lấy thêm một số xe ngẫu nhiên nếu danh sách gợi ý chưa đủ
        // (Bạn cần một CarDao hoặc cách khác để lấy danh sách xe tổng thể)
        // Ví dụ: Giả sử có một phương thức getAllCars() từ CarDao
        // CarDao carDao = new CarDao(); // Bạn sẽ cần một instance của CarDao ở đây
        // List<Car> allCars = carDao.getAllCars(); // Lấy tất cả xe

        // Nếu muốn thêm logic gợi ý phức tạp hơn (ví dụ: theo hãng, loại, v.v.),
        // bạn sẽ cần truyền thêm dữ liệu hoặc truy vấn database từ đây.
        // Hiện tại, chúng ta chỉ trả về các xe từ viewed/favorite.

        // Nếu bạn muốn sắp xếp gợi ý theo một tiêu chí nào đó (ví dụ: giá giảm dần)
        // Collections.sort(suggestions, Comparator.comparing(Car::getGiaBan).reversed());

        return suggestions;
    }

    // Bạn có thể thêm các phương thức gợi ý khác nếu cần, ví dụ dựa trên thuộc tính xe
    public static List<Car> suggestByBrand(String brandName) {
        // Logic để lấy xe theo hãng từ database
        // Ví dụ: return CarDao.getCarsByBrand(brandName);
        return new ArrayList<>(); // Placeholder
    }

    public static List<Car> suggestByType(String carType) {
        // Logic để lấy xe theo loại từ database
        // Ví dụ: return CarDao.getCarsByType(carType);
        return new ArrayList<>(); // Placeholder
    }
}


