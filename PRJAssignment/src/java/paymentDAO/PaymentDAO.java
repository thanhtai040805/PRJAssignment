package paymentDAO;

import java.math.BigDecimal;
import model.Payment;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // Thêm mới hóa đơn thanh toán
    public boolean insertPayment(Payment payment) {
        String sql = """
            INSERT INTO HoaDonBan (MaKH, MaNV, NgayLap, TongTien, TienGiam, ThanhTien, TrangThai, GhiChu)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, payment.getMaHD()); // giả sử getMaHD là MaKH
            ps.setInt(2, payment.getMaPTTT()); // giả sử getMaPTTT là MaNV
            ps.setDate(3, Date.valueOf(payment.getNgayThanhToan()));
            ps.setBigDecimal(4, payment.getSoTien());
            ps.setBigDecimal(5, BigDecimal.ZERO); // TienGiam (nếu cần sửa)
            ps.setBigDecimal(6, payment.getSoTien()); // ThanhTien (giả sử = soTien)
            ps.setString(7, payment.getTrangThai());
            ps.setString(8, payment.getGhiChu());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        payment.setMaTT(rs.getInt(1));
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Lấy danh sách tất cả hóa đơn
    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonBan ORDER BY NgayLap DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Payment payment = new Payment();
                payment.setMaTT(rs.getInt("MaHD"));
                payment.setMaHD(rs.getInt("MaKH"));
                payment.setMaPTTT(rs.getInt("MaNV"));
                payment.setNgayThanhToan(rs.getDate("NgayLap").toLocalDate());
                payment.setSoTien(rs.getBigDecimal("TongTien"));
                payment.setTrangThai(rs.getString("TrangThai"));
                payment.setGhiChu(rs.getString("GhiChu"));

                list.add(payment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tìm hóa đơn theo mã
    public Payment getPaymentById(int maTT) {
        String sql = "SELECT * FROM HoaDonBan WHERE MaHD = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTT);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Payment payment = new Payment();
                    payment.setMaTT(rs.getInt("MaHD"));
                    payment.setMaHD(rs.getInt("MaKH"));
                    payment.setMaPTTT(rs.getInt("MaNV"));
                    payment.setNgayThanhToan(rs.getDate("NgayLap").toLocalDate());
                    payment.setSoTien(rs.getBigDecimal("TongTien"));
                    payment.setTrangThai(rs.getString("TrangThai"));
                    payment.setGhiChu(rs.getString("GhiChu"));

                    return payment;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Cập nhật trạng thái thanh toán
    public boolean updatePaymentStatus(int maTT, String newStatus) {
        String sql = "UPDATE HoaDonBan SET TrangThai = ? WHERE MaHD = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, maTT);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa hóa đơn
    public boolean deletePayment(int maTT) {
        String sql = "DELETE FROM HoaDonBan WHERE MaHD = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTT);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
