package customerDAO;

import jakarta.persistence.EntityManager;
import model.Customer;
import util.GenericDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO extends GenericDAO<Customer, Integer> {

    @Override
    protected Class<Customer> getEntityClass() {
        return Customer.class;
    }

    public List<Map<String, Object>> getAllCustomersWithUser() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT kh.*, u.Username, u.Password FROM KhachHang kh LEFT JOIN Users u ON kh.MaKH = u.MaKH";
        try (Connection conn = util.DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaKH", rs.getInt("MaKH"));
                row.put("HoTen", rs.getString("HoTen"));
                row.put("NgaySinh", rs.getDate("NgaySinh"));
                row.put("GioiTinh", rs.getString("GioiTinh"));
                row.put("DiaChi", rs.getString("DiaChi"));
                row.put("SoDienThoai", rs.getString("SoDienThoai"));
                row.put("Email", rs.getString("Email"));
                row.put("CCCD", rs.getString("CCCD"));
                row.put("NgheNghiep", rs.getString("NgheNghiep"));
                row.put("MucThuNhap", rs.getBigDecimal("MucThuNhap"));
                row.put("NgayTao", rs.getTimestamp("NgayTao"));
                row.put("TrangThai", rs.getString("TrangThai"));
                row.put("Username", rs.getString("Username"));
                row.put("Password", rs.getString("Password"));
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Map<String, Object>> searchCustomersWithUser(String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT kh.*, u.Username, u.Password FROM KhachHang kh LEFT JOIN Users u ON kh.MaKH = u.MaKH WHERE kh.HoTen LIKE ? OR kh.Email LIKE ? OR kh.SoDienThoai LIKE ? OR u.Username LIKE ?";
        try (Connection conn = util.DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaKH", rs.getInt("MaKH"));
                row.put("HoTen", rs.getString("HoTen"));
                row.put("NgaySinh", rs.getDate("NgaySinh"));
                row.put("GioiTinh", rs.getString("GioiTinh"));
                row.put("DiaChi", rs.getString("DiaChi"));
                row.put("SoDienThoai", rs.getString("SoDienThoai"));
                row.put("Email", rs.getString("Email"));
                row.put("CCCD", rs.getString("CCCD"));
                row.put("NgheNghiep", rs.getString("NgheNghiep"));
                row.put("MucThuNhap", rs.getBigDecimal("MucThuNhap"));
                row.put("NgayTao", rs.getTimestamp("NgayTao"));
                row.put("TrangThai", rs.getString("TrangThai"));
                row.put("Username", rs.getString("Username"));
                row.put("Password", rs.getString("Password"));
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

