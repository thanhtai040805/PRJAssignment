package carDao;

import model.Car;
import util.DBConnection; // Assuming you have this utility class for DB connection

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarDao implements ICarDao { // Assuming ICarDao exists

    private static final Logger LOGGER = Logger.getLogger(CarDao.class.getName());

    public CarDao() {
        // Constructor, can be empty or used for initialization
    }

    // Helper method to load a Car object from a ResultSet
    private Car extractCarFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setMaXe(rs.getInt("MaXe"));
        car.setMaDong(rs.getInt("MaDong"));
        car.setMaNCC(rs.getInt("MaNCC"));
        car.setTenXe(rs.getString("TenXe"));
        car.setNamSanXuat(rs.getInt("NamSanXuat"));
        car.setMauSac(rs.getString("MauSac"));
        car.setSoKhung(rs.getString("SoKhung"));
        car.setSoMay(rs.getString("SoMay"));
        // Check for NULL values before getting int to avoid issues with primitive types
        // For int, getInt() returns 0 for SQL NULL by default, which might be acceptable
        // but for robustness, consider checking rs.wasNull() if 0 is a valid value.
        car.setDungTichDongCo(rs.getInt("DungTichDongCo"));
        if (rs.wasNull()) {
            car.setDungTichDongCo(0); // Or handle as needed
        }
        car.setCongSuat(rs.getInt("CongSuat"));
        if (rs.wasNull()) {
            car.setCongSuat(0); // Or handle as needed
        }
        car.setHopSo(rs.getString("HopSo"));
        car.setKmDaDi(rs.getInt("KmDaDi"));
        if (rs.wasNull()) {
            car.setKmDaDi(0); // Or handle as needed
        }
        car.setTinhTrang(rs.getString("TinhTrang"));
        car.setGiaNhap(rs.getBigDecimal("GiaNhap"));
        car.setGiaBan(rs.getBigDecimal("GiaBan"));
        car.setSoLuongTon(rs.getInt("SoLuongTon"));
        if (rs.wasNull()) {
            car.setSoLuongTon(0); // Or handle as needed
        }
        car.setNgayNhap(rs.getDate("NgayNhap"));
        car.setTrangThai(rs.getString("TrangThai"));
        car.setMoTa(rs.getString("MoTa"));
        car.setLinkAnh(rs.getString("LinkAnh"));

        // --- SỬA ĐỔI QUAN TRỌNG: ĐỌC GLOBALKEY TỪ DATABASE ---
        // GlobalKey is stored in DB, so it MUST be read from ResultSet
        car.setGlobalKey(rs.getString("GlobalKey")); // <<< Thêm dòng này
        // --------------------------------------------------------

        // Information from linked tables (assuming these columns are aliased correctly in SQL)
        // Ensure these column names (e.g., "TenHang") match the aliases in your SQL SELECT statements.
        car.setTenHang(rs.getString("TenHang"));
        car.setTenDong(rs.getString("TenDong"));
        car.setLoaiXe(rs.getString("LoaiXe")); // Alias from LoaiXe table, assuming 'TenLoai' becomes 'LoaiXe'
        car.setNhienLieu(rs.getString("TenNhienLieu")); // Alias from NhienLieu table
        car.setSoChoNgoi(rs.getInt("SoChoNgoi")); // Alias from SoChoNgoi table
        if (rs.wasNull()) {
            car.setSoChoNgoi(0); // Or handle as needed
        }
        car.setTenNCC(rs.getString("TenNCC")); // Alias from NhaCungCap table

        return car;
    }

    // --- CRUD Operations ---
    @Override
    public void addCar(Car car) {
        // Đảm bảo tên bảng là XeOTo, không phải Cars nếu đó là tên thực tế trong DB của bạn
        String sql = "INSERT INTO XeOTo (MaDong, MaNCC, TenXe, NamSanXuat, MauSac, SoKhung, SoMay, "
                + "DungTichDongCo, CongSuat, HopSo, KmDaDi, TinhTrang, GiaNhap, GiaBan, SoLuongTon, "
                + "NgayNhap, TrangThai, MoTa, LinkAnh, GlobalKey) "
                + // Đảm bảo 'GlobalKey' khớp với tên cột trong DB
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
            ps.setDate(16, new java.sql.Date(car.getNgayNhap().getTime())); // Convert util.Date to sql.Date
            ps.setString(17, car.getTrangThai());
            ps.setString(18, car.getMoTa());
            ps.setString(19, car.getLinkAnh());
            ps.setString(20, car.getGlobalKey()); // Lưu globalKey vào DB
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding car: " + car.getTenXe(), e);
        }
    }

    @Override
    public boolean removeCar(int maXe) {
        String sql = "DELETE FROM XeOTo WHERE MaXe = ?"; // Đảm bảo tên bảng là XeOTo
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maXe);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error removing car with ID: " + maXe, e);
            return false;
        }
    }

    @Override
    public boolean updateCar(Car updatedCar) {
        String sql = "UPDATE XeOTo SET MaDong=?, MaNCC=?, TenXe=?, NamSanXuat=?, MauSac=?, "
                + // Đảm bảo tên bảng là XeOTo
                "SoKhung=?, SoMay=?, DungTichDongCo=?, CongSuat=?, HopSo=?, KmDaDi=?, "
                + "TinhTrang=?, GiaNhap=?, GiaBan=?, SoLuongTon=?, NgayNhap=?, TrangThai=?, "
                + "MoTa=?, LinkAnh=?, GlobalKey=? WHERE MaXe = ?"; // Đảm bảo 'GlobalKey' khớp với tên cột trong DB
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, updatedCar.getMaDong());
            ps.setInt(2, updatedCar.getMaNCC());
            ps.setString(3, updatedCar.getTenXe());
            ps.setInt(4, updatedCar.getNamSanXuat());
            ps.setString(5, updatedCar.getMauSac());
            ps.setString(6, updatedCar.getSoKhung());
            ps.setString(7, updatedCar.getSoMay());
            ps.setInt(8, updatedCar.getDungTichDongCo());
            ps.setInt(9, updatedCar.getCongSuat());
            ps.setString(10, updatedCar.getHopSo());
            ps.setInt(11, updatedCar.getKmDaDi());
            ps.setString(12, updatedCar.getTinhTrang());
            ps.setBigDecimal(13, updatedCar.getGiaNhap());
            ps.setBigDecimal(14, updatedCar.getGiaBan());
            ps.setInt(15, updatedCar.getSoLuongTon());
            ps.setDate(16, new java.sql.Date(updatedCar.getNgayNhap().getTime())); // Convert util.Date to sql.Date
            ps.setString(17, updatedCar.getTrangThai());
            ps.setString(18, updatedCar.getMoTa());
            ps.setString(19, updatedCar.getLinkAnh());
            ps.setString(20, updatedCar.getGlobalKey()); // Cập nhật globalKey trong DB
            ps.setInt(21, updatedCar.getMaXe());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating car with ID: " + updatedCar.getMaXe(), e);
            return false;
        }
    }

    @Override
    public Car getCarById(int maXe) {
        Car car = null;
        // Đảm bảo tên bảng là XeOTo và các cột join là chính xác
        String sql = "SELECT c.*, h.TenHang, d.TenDong, l.TenLoai AS LoaiXe, nl.TenNhienLieu, sch.SoChoNgoi, ncc.TenNCC "
                + "FROM XeOTo c "
                + // Đổi Cars thành XeOTo
                "LEFT JOIN HangXe h ON c.MaDong = h.MaHang "
                + // Có thể MaDong nên là MaHang nếu MaHang là khóa ngoại? Kiểm tra lại mối quan hệ
                "LEFT JOIN DongXe d ON c.MaDong = d.MaDong "
                + "LEFT JOIN LoaiXe l ON d.MaLoai = l.MaLoai "
                + "LEFT JOIN NhienLieu nl ON c.MaNhienLieu = nl.MaNhienLieu "
                + // Cần đảm bảo cột MaNhienLieu tồn tại trong XeOTo
                "LEFT JOIN SoChoNgoi sch ON c.MaSoChoNgoi = sch.MaSoChoNgoi "
                + // Cần đảm bảo cột MaSoChoNgoi tồn tại trong XeOTo
                "LEFT JOIN NhaCungCap ncc ON c.MaNCC = ncc.MaNCC "
                + "WHERE c.MaXe = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maXe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    car = extractCarFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting car by ID: " + maXe, e);
        }
        return car;
    }

    public Car getCarByGlobalKey(String globalKey) {
        Car car = null;
        // Đảm bảo câu SQL SELECT * FROM XeOTo hoặc SELECT tất cả các cột cần thiết
        String SQL = """
        SELECT x.*, d.TenDong, h.TenHang, d.LoaiXe, d.NhienLieu, d.SoChoNgoi, ncc.TenNCC
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        LEFT JOIN NhaCungCap ncc ON x.MaNCC = ncc.MaNCC
        WHERE x.GlobalKey = ?
        """; // Đảm bảo bạn SELECT TẤT CẢ các cột cần thiết

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, globalKey);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // Đã tìm thấy bản ghi
                    car = new Car();
                    // BẮT ĐẦU ÁNH XẠ DỮ LIỆU TỪ ResultSet VÀO ĐỐI TƯỢNG CAR
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

                    // Ánh xạ các thông tin từ bảng liên kết (JOIN)
                    car.setTenDong(rs.getString("TenDong"));
                    car.setTenHang(rs.getString("TenHang"));
                    car.setLoaiXe(rs.getString("LoaiXe"));
                    car.setNhienLieu(rs.getString("NhienLieu"));
                    car.setSoChoNgoi(rs.getInt("SoChoNgoi"));
                    car.setTenNCC(rs.getString("TenNCC"));
                    // KẾT THÚC ÁNH XẠ
                }
            }
        } catch (SQLException e) {
            // Ghi log lỗi chi tiết để debug
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        // Đảm bảo tên bảng là XeOTo và các cột join là chính xác
        String sql = "SELECT c.*, h.TenHang, d.TenDong, l.TenLoai AS LoaiXe, nl.TenNhienLieu, sch.SoChoNgoi, ncc.TenNCC "
                + "FROM XeOTo c "
                + // Đổi Cars thành XeOTo
                "LEFT JOIN HangXe h ON c.MaDong = h.MaHang "
                + // Có thể MaDong nên là MaHang nếu MaHang là khóa ngoại? Kiểm tra lại mối quan hệ
                "LEFT JOIN DongXe d ON c.MaDong = d.MaDong "
                + "LEFT JOIN LoaiXe l ON d.MaLoai = l.MaLoai "
                + "LEFT JOIN NhienLieu nl ON c.MaNhienLieu = nl.MaNhienLieu "
                + // Cần đảm bảo cột MaNhienLieu tồn tại trong XeOTo
                "LEFT JOIN SoChoNgoi sch ON c.MaSoChoNgoi = sch.MaSoChoNgoi "
                + // Cần đảm bảo cột MaSoChoNgoi tồn tại trong XeOTo
                "LEFT JOIN NhaCungCap ncc ON c.MaNCC = ncc.MaNCC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cars.add(extractCarFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all cars", e);
        }
        return cars;
    }

    // You'll likely need other methods here, e.g., for filter, search, etc.
}
