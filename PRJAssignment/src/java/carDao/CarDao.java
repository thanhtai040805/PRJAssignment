package carDao;

import jakarta.persistence.TypedQuery;
import model.Car;
import util.GenericDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarDao extends GenericDAO<Car, Integer> {

    @Override
    protected Class<Car> getEntityClass() {
        return Car.class;
    }

    // Lấy danh sách xe nổi bật (showcase)
    public List<Car> getShowcaseCars() {
        String jpql = "SELECT c FROM Car c "
                + "JOIN FETCH c.carModel m "
                + "JOIN FETCH m.carBrand "
                + "WHERE c.status = :status "
                + "ORDER BY c.importDate DESC";
        return em.createQuery(jpql, Car.class)
                .setParameter("status", "Có sẵn")
                .setMaxResults(7)
                .getResultList();
    }

    // Lấy danh sách xe bán chạy nhất
    public List<Car> getBestSellerCars() {
        String jpql = "SELECT c FROM Car c "
                + "JOIN FETCH c.carModel m "
                + "JOIN FETCH m.carBrand "
                + "WHERE c.status = :status "
                + "ORDER BY c.salePrice DESC";
        return em.createQuery(jpql, Car.class)
                .setParameter("status", "Có sẵn")
                .setMaxResults(5)
                .getResultList();
    }

    // Lấy danh sách xe xếp hạng cao
    public List<Car> getRankingCars() {
        String jpql = "SELECT c FROM Car c "
                + "JOIN FETCH c.carModel m "
                + "JOIN FETCH m.carBrand "
                + "WHERE c.status = :status "
                + "ORDER BY c.salePrice DESC";
        return em.createQuery(jpql, Car.class)
                .setParameter("status", "Có sẵn")
                .setMaxResults(4)
                .getResultList();
    }

    // Lấy danh sách xe gợi ý
    public List<Car> getRecommendCars() {
        String jpql = "SELECT c FROM Car c "
                + "JOIN FETCH c.carModel m "
                + "JOIN FETCH m.carBrand "
                + "WHERE c.status = :status "
                + "ORDER BY c.importDate DESC";
        return em.createQuery(jpql, Car.class)
                .setParameter("status", "Có sẵn")
                .setMaxResults(4)
                .getResultList();
    }

    // Lấy danh sách nhà cung cấp đang hoạt động
    public List<Map<String, Object>> getActiveProviders() {
        String jpql = "SELECT n.supplierId, n.supplierName FROM Supplier n WHERE n.status = :status";
        List<Object[]> results = em.createQuery(jpql, Object[].class)
                .setParameter("status", "Hoạt động")
                .getResultList();
        List<Map<String, Object>> providers = new java.util.ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("supplierId", row[0]);
            map.put("supplierName", row[1]);
            providers.add(map);
        }
        return providers;
    }

    // Cập nhật số lượng tồn kho
    public boolean updateSoLuongTon(Integer maXe, int soLuongGiam) {
        Car car = em.find(Car.class, maXe);
        if (car != null && car.getStockQuantity() >= soLuongGiam) {
            car.setStockQuantity(car.getStockQuantity() - soLuongGiam);
            em.merge(car);
            return true;
        }
        return false;
    }

    // Lấy xe theo globalKey
    public Car getCarByGlobalKey(String globalKey) {
        String jpql = "SELECT c FROM Car c "
                + "JOIN FETCH c.carModel m "
                + "JOIN FETCH m.carBrand "
                + "WHERE c.globalKey = :globalKey";
        TypedQuery<Car> query = em.createQuery(jpql, Car.class);
        query.setParameter("globalKey", globalKey);
        List<Car> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public Car findById(int carId) {
        return em.find(Car.class, carId);
    }

    public List<Car> getAllCarsAvailable() {
        String jpql = "SELECT c FROM Car c "
                + "JOIN FETCH c.carModel m "
                + "JOIN FETCH m.carBrand "
                + "WHERE c.status = :status";
        return em.createQuery(jpql, Car.class)
                .setParameter("status", "Có sẵn")
                .getResultList();
    }

    public List<Car> searchAndSort(String keyword, String sortField, String sortOrder) {
        String jpql = "SELECT c FROM Car c JOIN FETCH c.carModel m JOIN FETCH m.carBrand WHERE c.status = 'Có sẵn'";

        if (keyword != null && !keyword.trim().isEmpty()) {
            jpql += " AND (LOWER(c.carName) LIKE :kw OR LOWER(c.globalKey) LIKE :kw)";
        }

        if (sortField != null && !sortField.isEmpty()) {
            jpql += " ORDER BY c." + sortField + ("desc".equalsIgnoreCase(sortOrder) ? " DESC" : " ASC");
        }

        TypedQuery<Car> query = em.createQuery(jpql, Car.class);

        if (keyword != null && !keyword.trim().isEmpty()) {
            query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        }

        return query.getResultList();
    }

    @Override
    public boolean remove(Integer id) {
        try {
            Car car = em.find(getEntityClass(), id);
            if (car != null) {
                em.getTransaction().begin();

                // Lấy globalKey từ xe cần xoá
                String globalKey = car.getGlobalKey();

                // ❌ Xoá các bản ghi liên quan trong bảng FavoriteCars (dựa vào globalKey)
                em.createQuery("DELETE FROM FavoriteCars f WHERE f.favoriteCarsPK.globalKey = :globalKey")
                        .setParameter("globalKey", globalKey)
                        .executeUpdate();

                // ❌ Xoá các bản ghi liên quan trong bảng ViewedCars (dựa vào globalKey)
                em.createQuery("DELETE FROM ViewedCars v WHERE v.viewedCarsPK.globalKey = :globalKey")
                        .setParameter("globalKey", globalKey)
                        .executeUpdate();

                // ❌ Xoá các bản ghi liên quan trong bảng InvoiceDetail (dựa vào carId)
                em.createQuery("DELETE FROM InvoiceDetail i WHERE i.carId = :carId")
                        .setParameter("carId", id)
                        .executeUpdate();

                // ✅ Cuối cùng xoá Car
                em.remove(car);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

}
