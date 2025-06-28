package carDao;

import model.Car;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarDao implements ICarDao {

    private static final Logger LOGGER = Logger.getLogger(CarDao.class.getName());

    // SQL Queries được sửa lại để đúng với cấu trúc database
    private static final String SQL_GET_SHOWCASE_CARS = """
        SELECT TOP 7 x.MaXe, x.TenXe, x.LinkAnh, h.TenHang, d.TenDong, x.GlobalKey
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        WHERE x.TrangThai = N'Có sẵn'
        ORDER BY x.NgayNhap DESC
        """;

    private static final String SQL_GET_BEST_SELLER_CARS = """
        SELECT TOP 2 x.MaXe, x.TenXe, x.NamSanXuat, x.MauSac, x.TinhTrang,
                     x.GiaBan, x.LinkAnh, h.TenHang, d.TenDong, d.LoaiXe, x.GlobalKey
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        LEFT JOIN ChiTietHoaDon ct ON x.MaXe = ct.MaXe
        WHERE x.TrangThai = N'Có sẵn'
        GROUP BY x.MaXe, x.TenXe, x.NamSanXuat, x.MauSac, x.TinhTrang,
                 x.GiaBan, x.LinkAnh, h.TenHang, d.TenDong, d.LoaiXe, x.GlobalKey
        ORDER BY COUNT(ct.MaXe) DESC, x.GiaBan DESC
        """;

    private static final String SQL_GET_RANKING_CARS = """
        SELECT TOP 4 x.MaXe, x.TenXe, h.TenHang, d.TenDong, x.GlobalKey
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        WHERE x.TrangThai = N'Có sẵn'
        ORDER BY x.GiaBan DESC
        """;

    private static final String SQL_GET_RECOMMEND_CARS = """
        SELECT TOP 4 x.MaXe, x.TenXe, x.GiaBan, x.TinhTrang, x.LinkAnh, x.GlobalKey
        FROM XeOTo x
        WHERE x.TrangThai = N'Có sẵn'
        ORDER BY x.NgayNhap DESC
        """;

    private static final String SQL_GET_PROVIDERS = """
        SELECT MaNCC, TenNCC FROM NhaCungCap WHERE TrangThai = N'Hoạt động'
        """;

    // SQL Query chính được sửa lại
    private static final String SQL_GET_ALL_CARS = """
        SELECT x.*, h.TenHang, d.TenDong, d.LoaiXe, d.NhienLieu, d.SoChoNgoi, ncc.TenNCC
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        LEFT JOIN NhaCungCap ncc ON x.MaNCC = ncc.MaNCC
        WHERE x.TrangThai = N'Có sẵn'
        """;

    private static final String SQL_GET_CAR_BY_ID = """
        SELECT x.*, h.TenHang, d.TenDong, d.LoaiXe, d.NhienLieu, d.SoChoNgoi, ncc.TenNCC
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        LEFT JOIN NhaCungCap ncc ON x.MaNCC = ncc.MaNCC
        WHERE x.MaXe = ?
        """;

    private static final String SQL_GET_CAR_BY_GLOBAL_KEY = """
        SELECT x.*, h.TenHang, d.TenDong, d.LoaiXe, d.NhienLieu, d.SoChoNgoi, ncc.TenNCC
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        LEFT JOIN NhaCungCap ncc ON x.MaNCC = ncc.MaNCC
        WHERE x.GlobalKey = ?
        """;

    private static final String SQL_INSERT_CAR = """
        INSERT INTO XeOTo (MaDong, MaNCC, TenXe, NamSanXuat, MauSac, SoKhung, SoMay,
        DungTichDongCo, CongSuat, HopSo, KmDaDi, TinhTrang, GiaNhap, GiaBan,
        SoLuongTon, NgayNhap, TrangThai, MoTa, LinkAnh, GlobalKey)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    private static final String SQL_DELETE_CAR = "DELETE FROM XeOTo WHERE MaXe = ?";

    private static final String SQL_UPDATE_CAR = """
        UPDATE XeOTo SET MaDong=?, MaNCC=?, TenXe=?, NamSanXuat=?, MauSac=?,
        SoKhung=?, SoMay=?, DungTichDongCo=?, CongSuat=?, HopSo=?, KmDaDi=?,
        TinhTrang=?, GiaNhap=?, GiaBan=?, SoLuongTon=?, NgayNhap=?, TrangThai=?,
        MoTa=?, LinkAnh=?, GlobalKey=? WHERE MaXe = ?
        """;

    // Method extractCarFromResultSet được sửa lại hoàn chỉnh
    private Car extractCarFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        
        // Thông tin cơ bản từ bảng XeOTo
        car.setMaXe(rs.getInt("MaXe"));
        car.setMaDong(rs.getInt("MaDong"));
        car.setMaNCC(rs.getInt("MaNCC"));
        car.setTenXe(rs.getString("TenXe"));
        car.setNamSanXuat(rs.getInt("NamSanXuat"));
        car.setMauSac(rs.getString("MauSac"));
        car.setSoKhung(rs.getString("SoKhung"));
        car.setSoMay(rs.getString("SoMay"));
        car.setDungTichDongCo(rs.getInt("DungTichDongCo"));
        car.setCongSuat(rs.getInt("CongSuat"));
        car.setHopSo(rs.getString("HopSo"));
        car.setKmDaDi(rs.getInt("KmDaDi"));
        car.setTinhTrang(rs.getString("TinhTrang"));
        car.setGiaNhap(rs.getBigDecimal("GiaNhap"));
        car.setGiaBan(rs.getBigDecimal("GiaBan"));
        car.setSoLuongTon(rs.getInt("SoLuongTon"));
        car.setNgayNhap(rs.getDate("NgayNhap"));
        car.setTrangThai(rs.getString("TrangThai"));
        car.setMoTa(rs.getString("MoTa"));
        car.setLinkAnh(rs.getString("LinkAnh"));
        car.setGlobalKey(rs.getString("GlobalKey"));

        // Thông tin từ bảng liên kết - xử lý null safety
        try {
            car.setTenHang(rs.getString("TenHang"));
        } catch (SQLException e) {
            car.setTenHang(null);
        }
        
        try {
            car.setTenDong(rs.getString("TenDong"));
        } catch (SQLException e) {
            car.setTenDong(null);
        }
        
        try {
            car.setLoaiXe(rs.getString("LoaiXe"));
        } catch (SQLException e) {
            car.setLoaiXe(null);
        }
        
        try {
            car.setNhienLieu(rs.getString("NhienLieu"));
        } catch (SQLException e) {
            car.setNhienLieu(null);
        }
        
        try {
            car.setSoChoNgoi(rs.getInt("SoChoNgoi"));
        } catch (SQLException e) {
            car.setSoChoNgoi(0);
        }
        
        try {
            car.setTenNCC(rs.getString("TenNCC"));
        } catch (SQLException e) {
            car.setTenNCC(null);
        }

        return car;
    }

    @Override
    public List<Car> getShowcaseCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_SHOWCASE_CARS); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = new Car();
                car.setMaXe(rs.getInt("MaXe"));
                car.setTenXe(rs.getString("TenXe"));
                car.setLinkAnh(rs.getString("LinkAnh"));
                car.setTenHang(rs.getString("TenHang"));
                car.setTenDong(rs.getString("TenDong"));
                car.setGlobalKey(rs.getString("GlobalKey"));
                cars.add(car);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting showcase cars", e);
        }
        return cars;
    }

    @Override
    public List<Car> getBestSellerCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_BEST_SELLER_CARS); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = new Car();
                car.setMaXe(rs.getInt("MaXe"));
                car.setTenXe(rs.getString("TenXe"));
                car.setNamSanXuat(rs.getInt("NamSanXuat"));
                car.setMauSac(rs.getString("MauSac"));
                car.setTinhTrang(rs.getString("TinhTrang"));
                car.setGiaBan(rs.getBigDecimal("GiaBan"));
                car.setLinkAnh(rs.getString("LinkAnh"));
                car.setTenHang(rs.getString("TenHang"));
                car.setTenDong(rs.getString("TenDong"));
                car.setLoaiXe(rs.getString("LoaiXe"));
                car.setGlobalKey(rs.getString("GlobalKey"));
                cars.add(car);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting best seller cars", e);
        }
        return cars;
    }

    @Override
    public List<Car> getRankingCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_RANKING_CARS); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = new Car();
                car.setMaXe(rs.getInt("MaXe"));
                car.setTenXe(rs.getString("TenXe"));
                car.setTenHang(rs.getString("TenHang"));
                car.setTenDong(rs.getString("TenDong"));
                car.setGlobalKey(rs.getString("GlobalKey"));
                cars.add(car);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting ranking cars", e);
        }
        return cars;
    }

    @Override
    public List<Car> getRecommendCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_RECOMMEND_CARS); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = new Car();
                car.setMaXe(rs.getInt("MaXe"));
                car.setTenXe(rs.getString("TenXe"));
                car.setGiaBan(rs.getBigDecimal("GiaBan"));
                car.setTinhTrang(rs.getString("TinhTrang"));
                car.setLinkAnh(rs.getString("LinkAnh"));
                car.setGlobalKey(rs.getString("GlobalKey"));
                cars.add(car);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting recommend cars", e);
        }
        return cars;
    }

    @Override
    public List<Map<String, Object>> getActiveProviders() {
        List<Map<String, Object>> providers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_PROVIDERS); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> provider = new HashMap<>();
                provider.put("maNCC", rs.getInt("MaNCC"));
                provider.put("tenNCC", rs.getString("TenNCC"));
                providers.add(provider);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting active providers", e);
        }
        return providers;
    }

    @Override
    public void addCar(Car car) {
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT_CAR)) {

            ps.setInt(1, car.getMaDong());
            ps.setInt(2, car.getMaNCC());
            ps.setString(3, car.getTenXe());
            ps.setInt(4, car.getNamSanXuat());
            ps.setString(5, car.getMauSac());
            ps.setString(6, car.getSoKhung());
            ps.setString(7, car.getSoMay());
            ps.setInt(8, car.getDungTichDongCo());
            ps.setInt(9, car.getCongSuat());
            ps.setString(10, car.getHopSo());
            ps.setInt(11, car.getKmDaDi());
            ps.setString(12, car.getTinhTrang());
            ps.setBigDecimal(13, car.getGiaNhap());
            ps.setBigDecimal(14, car.getGiaBan());
            ps.setInt(15, car.getSoLuongTon());
            ps.setDate(16, new java.sql.Date(car.getNgayNhap().getTime()));
            ps.setString(17, car.getTrangThai());
            ps.setString(18, car.getMoTa());
            ps.setString(19, car.getLinkAnh());
            ps.setString(20, car.getGlobalKey());

            ps.executeUpdate();
            LOGGER.log(Level.INFO, "Car added successfully: {0}", car.getTenXe());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding car: " + car.getTenXe(), e);
        }
    }

    @Override
    public boolean removeCar(int maXe) {
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE_CAR)) {
            ps.setInt(1, maXe);
            int result = ps.executeUpdate();
            LOGGER.log(Level.INFO, "Car removal result for ID {0}: {1}", new Object[]{maXe, result > 0});
            return result > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error removing car with ID: " + maXe, e);
            return false;
        }
    }

    @Override
    public boolean updateCar(Car car) {
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_CAR)) {

            ps.setInt(1, car.getMaDong());
            ps.setInt(2, car.getMaNCC());
            ps.setString(3, car.getTenXe());
            ps.setInt(4, car.getNamSanXuat());
            ps.setString(5, car.getMauSac());
            ps.setString(6, car.getSoKhung());
            ps.setString(7, car.getSoMay());
            ps.setInt(8, car.getDungTichDongCo());
            ps.setInt(9, car.getCongSuat());
            ps.setString(10, car.getHopSo());
            ps.setInt(11, car.getKmDaDi());
            ps.setString(12, car.getTinhTrang());
            ps.setBigDecimal(13, car.getGiaNhap());
            ps.setBigDecimal(14, car.getGiaBan());
            ps.setInt(15, car.getSoLuongTon());
            ps.setDate(16, new java.sql.Date(car.getNgayNhap().getTime()));
            ps.setString(17, car.getTrangThai());
            ps.setString(18, car.getMoTa());
            ps.setString(19, car.getLinkAnh());
            ps.setString(20, car.getGlobalKey());
            ps.setInt(21, car.getMaXe());

            int result = ps.executeUpdate();
            LOGGER.log(Level.INFO, "Car update result for ID {0}: {1}", new Object[]{car.getMaXe(), result > 0});
            return result > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating car with ID: " + car.getMaXe(), e);
            return false;
        }
    }

    @Override
    public Car getCarById(int maXe) {
        Car car = null;
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(SQL_GET_CAR_BY_ID)) {
            ps.setInt(1, maXe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    car = extractCarFromResultSet(rs);
                    LOGGER.log(Level.INFO, "Found car by ID {0}: {1}", new Object[]{maXe, car.getTenXe()});
                } else {
                    LOGGER.log(Level.WARNING, "No car found with ID: {0}", maXe);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting car by ID: " + maXe, e);
        }
        return car;
    }

    @Override
    public Car getCarByGlobalKey(String globalKey) {
        Car car = null;
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(SQL_GET_CAR_BY_GLOBAL_KEY)) {
            ps.setString(1, globalKey);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    car = extractCarFromResultSet(rs);
                    LOGGER.log(Level.INFO, "Found car by GlobalKey {0}: {1}", new Object[]{globalKey, car.getTenXe()});
                } else {
                    LOGGER.log(Level.WARNING, "No car found with GlobalKey: {0}", globalKey);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting car by GlobalKey: " + globalKey, e);
        }
        return car;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(SQL_GET_ALL_CARS); 
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                list.add(car);
            }
            
            LOGGER.log(Level.INFO, "Retrieved {0} cars from database", list.size());
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all cars", e);
        }
        return list;
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
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
            
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
}
