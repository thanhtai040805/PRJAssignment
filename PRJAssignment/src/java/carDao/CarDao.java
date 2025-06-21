package carDao;

import model.Car;
import java.util.ArrayList;
import java.util.List;

public class CarDao implements ICarDao {

    private List<Car> carList = new ArrayList<>();

    @Override
    public void addCar(Car car) {
        carList.add(car);
    }

    @Override
    public boolean removeCar(int maXe) {
        return carList.removeIf(c -> c.getMaXe() == maXe);
    }

    @Override
    public boolean updateCar(Car updatedCar) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getMaXe() == updatedCar.getMaXe()) {
                carList.set(i, updatedCar);
                return true;
            }
        }
        return false;
    }

    @Override
    public Car getCarById(int maXe) {
        for (Car car : carList) {
            if (car.getMaXe() == maXe) {
                return car;
            }
        }
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        return new ArrayList<>(carList);
    }
}
