package carDao;

import java.util.List;
import java.util.Map;
import model.Car;
import java.sql.*;

public interface ICarDao {

    void addCar(Car car);

    boolean removeCar(int maXe);

    boolean updateCar(Car car);

    Car getCarById(int maXe);

    Car getCarByGlobalKey(String globalKey);

    List<Car> getAllCars();

    List<Car> getShowcaseCars();

    List<Car> getBestSellerCars();

    List<Car> getRankingCars();

    List<Car> getRecommendCars();

    List<Map<String, Object>> getActiveProviders(); 

    boolean updateSoLuongTon(Integer maXe, int soLuongGiam) throws SQLException;
}
