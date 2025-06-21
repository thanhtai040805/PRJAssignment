package carDao;

import java.util.List;
import model.Car;

public interface ICarDao {
    void addCar(Car car);
    boolean removeCar(int maXe);
    boolean updateCar(Car car);
    Car getCarById(int maXe);
    List<Car> getAllCars();
}
