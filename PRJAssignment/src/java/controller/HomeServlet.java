package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Car;
import util.DBConnection;

@WebServlet(urlPatterns = {"/", "/home"})
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Các câu lệnh SQL được cập nhật với cột LinkAnh
    private static final String SQL_GET_SHOWCASE_CARS = """
        SELECT TOP 7 x.MaXe, x.TenXe, x.LinkAnh, h.TenHang, d.TenDong
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        WHERE x.TrangThai = N'Có sẵn'
        ORDER BY x.NgayNhap DESC
        """;

    private static final String SQL_GET_BEST_SELLER_CARS = """
        SELECT TOP 2 x.MaXe, x.TenXe, x.NamSanXuat, x.MauSac, x.TinhTrang,
                       x.GiaBan, x.LinkAnh, h.TenHang, d.TenDong, d.LoaiXe
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        LEFT JOIN ChiTietHoaDon ct ON x.MaXe = ct.MaXe
        WHERE x.TrangThai = N'Có sẵn'
        GROUP BY x.MaXe, x.TenXe, x.NamSanXuat, x.MauSac, x.TinhTrang,
                 x.GiaBan, x.LinkAnh, h.TenHang, d.TenDong, d.LoaiXe
        ORDER BY COUNT(ct.MaXe) DESC, x.GiaBan DESC
        """;

    private static final String SQL_GET_RANKING_CARS = """
        SELECT TOP 4 x.MaXe, x.TenXe, h.TenHang, d.TenDong
        FROM XeOTo x
        INNER JOIN DongXe d ON x.MaDong = d.MaDong
        INNER JOIN HangXe h ON d.MaHang = h.MaHang
        WHERE x.TrangThai = N'Có sẵn'
        ORDER BY x.GiaBan DESC
        """;

    private static final String SQL_GET_RECOMMEND_CARS = """
        SELECT TOP 4 x.MaXe, x.TenXe, x.GiaBan, x.TinhTrang, x.LinkAnh
        FROM XeOTo x
        WHERE x.TrangThai = N'Có sẵn'
        ORDER BY x.NgayNhap DESC
        """;

    private static final String SQL_GET_PROVIDERS = """
        SELECT MaNCC, TenNCC FROM NhaCungCap WHERE TrangThai = N'Hoạt động'
        """;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Car> showcaseCars = getShowcaseCars();
            List<Car> bestSellerCars = getBestSellerCars();
            List<Car> rankingCars = getRankingCars();
            List<Car> recommendCars = getRecommendCars();
            List<Map<String, Object>> providers = getProviders();

            request.setAttribute("showcaseCars", showcaseCars);
            request.setAttribute("bestSellerCars", bestSellerCars);
            request.setAttribute("rankingCars", rankingCars);
            request.setAttribute("recommendCars", recommendCars);
            request.setAttribute("providers", providers);

            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Lỗi khi tải dữ liệu trang chủ: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Lấy danh sách xe để hiển thị trong phần showcase
     */
    private List<Car> getShowcaseCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_GET_SHOWCASE_CARS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = new Car();
                car.setMaXe(rs.getInt("MaXe"));
                car.setTenXe(rs.getString("TenXe"));
                car.setLinkAnh(rs.getString("LinkAnh")); // Đổi từ setHinhAnh thành setLinkAnh
                car.setTenHang(rs.getString("TenHang"));
                car.setTenDong(rs.getString("TenDong"));
                cars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    /**
     * Lấy danh sách xe bán chạy nhất
     */
    private List<Car> getBestSellerCars() {
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
                car.setLinkAnh(rs.getString("LinkAnh")); // Đổi từ setHinhAnh thành setLinkAnh
                car.setTenHang(rs.getString("TenHang"));
                car.setTenDong(rs.getString("TenDong"));
                car.setLoaiXe(rs.getString("LoaiXe"));
                cars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    /**
     * Lấy danh sách xe xếp hạng
     */
    private List<Car> getRankingCars() {
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
                cars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    /**
     * Lấy danh sách xe được đề xuất
     */
    private List<Car> getRecommendCars() {
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
                car.setLinkAnh(rs.getString("LinkAnh")); // Đổi từ setHinhAnh thành setLinkAnh
                cars.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    /**
     * Lấy danh sách nhà cung cấp cho filter
     */
    private List<Map<String, Object>> getProviders() {
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
            e.printStackTrace();
        }

        return providers;
    }
}
