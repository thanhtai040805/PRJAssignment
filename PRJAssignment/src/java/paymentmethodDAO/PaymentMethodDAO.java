package paymentmethodDAO;

import model.PaymentMethod;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class PaymentMethodDAO implements IPaymentMethodDAO {

    @Override
    public PaymentMethod getPaymentMethodById(Integer paymentMethodId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PaymentMethod pttt = null;
        String sql = "SELECT * FROM PhuongThucThanhToan WHERE MaPTTT = ?"; // Giả sử tên bảng là PhuongThucThanhToan

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, paymentMethodId);
            rs = ps.executeQuery();

            if (rs.next()) {
                pttt = new PaymentMethod(
                    rs.getInt("MaPTTT"),
                    rs.getString("TenPTTT"),
                    rs.getString("MoTa"),
                    rs.getTimestamp("NgayTao") != null ? rs.getTimestamp("NgayTao").toLocalDateTime() : null,
                    rs.getTimestamp("NgayCapNhat") != null ? rs.getTimestamp("NgayCapNhat").toLocalDateTime() : null,
                    rs.getString("TrangThai")
                );
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return pttt;
    }

    @Override
    public Integer addPaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer generatedId = null;
        String sql = "INSERT INTO PhuongThucThanhToan (TenPTTT, MoTa, NgayTao, TrangThai) VALUES (?, ?, ?, ?); SELECT SCOPE_IDENTITY() AS ID;";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, paymentMethod.getTenPTTT());
            ps.setString(2, paymentMethod.getMoTa());
            ps.setTimestamp(3, paymentMethod.getNgayTao() != null ? Timestamp.valueOf(paymentMethod.getNgayTao()) : null);
            ps.setString(4, paymentMethod.getTrangThai());

            ps.execute();

            if (ps.getMoreResults()) {
                rs = ps.getResultSet();
                if (rs.next()) {
                    generatedId = rs.getInt("ID");
                }
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return generatedId;
    }

    @Override
    public boolean updatePaymentMethod(PaymentMethod paymentMethod) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE PhuongThucThanhToan SET TenPTTT = ?, MoTa = ?, NgayCapNhat = ?, TrangThai = ? WHERE MaPTTT = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, paymentMethod.getTenPTTT());
            ps.setString(2, paymentMethod.getMoTa());
            ps.setTimestamp(3, paymentMethod.getNgayCapNhat() != null ? Timestamp.valueOf(paymentMethod.getNgayCapNhat()) : null);
            ps.setString(4, paymentMethod.getTrangThai());
            ps.setInt(5, paymentMethod.getMaPTTT());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public boolean deletePaymentMethod(Integer paymentMethodId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM PhuongThucThanhToan WHERE MaPTTT = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, paymentMethodId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PaymentMethod> list = new ArrayList<>();
        String sql = "SELECT * FROM PhuongThucThanhToan";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                PaymentMethod pttt = new PaymentMethod(
                    rs.getInt("MaPTTT"),
                    rs.getString("TenPTTT"),
                    rs.getString("MoTa"),
                    rs.getTimestamp("NgayTao") != null ? rs.getTimestamp("NgayTao").toLocalDateTime() : null,
                    rs.getTimestamp("NgayCapNhat") != null ? rs.getTimestamp("NgayCapNhat").toLocalDateTime() : null,
                    rs.getString("TrangThai")
                );
                list.add(pttt);
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return list;
    }
}