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
        String jpql = "SELECT c FROM Car c " +
                      "JOIN FETCH c.carModel m " +
                      "JOIN FETCH m.carBrand " +
                      "WHERE c.status = :status " +
                      "ORDER BY c.importDate DESC";
        return em.createQuery(jpql, Car.class)
                .setParameter("status", "Có sẵn")
                .setMaxResults(7)
                .getResultList();
    }

    // Lấy danh sách xe bán chạy nhất
    public List<Car> getBestSellerCars() {
        String jpql = "SELECT c FROM Car c " +
                      "JOIN FETCH c.carModel m " +
                      "JOIN FETCH m.carBrand " +
                      "WHERE c.status = :status " +
                      "ORDER BY c.salePrice DESC";
        return em.createQuery(jpql, Car.class)
                .setParameter("status", "Có sẵn")
                .setMaxResults(5)
                .getResultList();
    }

    // Lấy danh sách xe xếp hạng cao
    public List<Car> getRankingCars() {
        String jpql = "SELECT c FROM Car c " +
                      "JOIN FETCH c.carModel m " +
                      "JOIN FETCH m.carBrand " +
                      "WHERE c.status = :status " +
                      "ORDER BY c.salePrice DESC";
        return em.createQuery(jpql, Car.class)
                .setParameter("status", "Có sẵn")
                .setMaxResults(4)
                .getResultList();
    }

    // Lấy danh sách xe gợi ý
    public List<Car> getRecommendCars() {
        String jpql = "SELECT c FROM Car c " +
                      "JOIN FETCH c.carModel m " +
                      "JOIN FETCH m.carBrand " +
                      "WHERE c.status = :status " +
                      "ORDER BY c.importDate DESC";
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

    public Car getCarByGlobalKey(String globalKey) {
        String jpql = "SELECT c FROM Car c " +
                      "JOIN FETCH c.carModel m " +
                      "JOIN FETCH m.carBrand " +
                      "WHERE c.globalKey = :globalKey";
        TypedQuery<Car> query = em.createQuery(jpql, Car.class);
        query.setParameter("globalKey", globalKey);
        List<Car> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
>>>>>>> origin/master


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
        String jpql = "SELECT c FROM Car c " +
                      "JOIN FETCH c.carModel m " +
                      "JOIN FETCH m.carBrand " +
                      "WHERE c.globalKey = :globalKey";
        TypedQuery<Car> query = em.createQuery(jpql, Car.class);
        query.setParameter("globalKey", globalKey);
        List<Car> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    // Method để test kết nối database
    public boolean testConnection() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                LOGGER.log(Level.INFO, "Database connection test successful");
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection test failed", e);
        }
        return false;
    }

    // Method để đếm số lượng xe trong database
    public int getCarCount() {
        String sql = "SELECT COUNT(*) FROM XeOTo WHERE TrangThai = N'Có sẵn'";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int count = rs.getInt(1);
                LOGGER.log(Level.INFO, "Total available cars in database: {0}", count);
                return count;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error counting cars", e);
        }
        return 0;
    }

    @Override
    public boolean updateSoLuongTon(Integer maXe, int soLuongGiam) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE XeOTo SET SoLuongTon = SoLuongTon - ? WHERE MaXe = ? AND SoLuongTon >= ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, soLuongGiam);
            ps.setInt(2, maXe);
            ps.setInt(3, soLuongGiam); // Ensure quantity doesn't go negative
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }
}
