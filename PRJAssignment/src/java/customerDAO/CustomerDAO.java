package customerDAO;

import model.Customer;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import util.*;

public class CustomerDAO implements ICustomerDAO {

    @Override
    public Customer getCustomerById(Integer customerId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Customer customer = null;
        String sql = "SELECT * FROM KhachHang WHERE MaKH = ?"; // Giả sử tên bảng là KhachHang

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customer(
                    rs.getInt("MaKH"),
                    rs.getString("HoTen"),
                    rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toLocalDate() : null,
                    rs.getString("GioiTinh"),
                    rs.getString("DiaChi"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("CCCD"),
                    rs.getString("NgheNghiep"),
                    rs.getObject("MucThuNhap", Long.class),
                    rs.getTimestamp("NgayTao") != null ? rs.getTimestamp("NgayTao").toLocalDateTime() : null,
                    rs.getString("TrangThai")
                );
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return customer;
    }

    @Override
    public Integer addCustomer(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer generatedId = null;
        String sql = "INSERT INTO KhachHang (HoTen, NgaySinh, GioiTinh, DiaChi, SoDienThoai, Email, CCCD, NgheNghiep, MucThuNhap, NgayTao, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY() AS ID;";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getHoTen());
            ps.setDate(2, customer.getNgaySinh() != null ? Date.valueOf(customer.getNgaySinh()) : null);
            ps.setString(3, customer.getGioiTinh());
            ps.setString(4, customer.getDiaChi());
            ps.setString(5, customer.getSoDienThoai());
            ps.setString(6, customer.getEmail());
            ps.setString(7, customer.getCccd());
            ps.setString(8, customer.getNgheNghiep());
            ps.setObject(9, customer.getMucThuNhap());
            ps.setTimestamp(10, customer.getNgayTao() != null ? Timestamp.valueOf(customer.getNgayTao()) : null);
            ps.setString(11, customer.getTrangThai());

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
    public boolean updateCustomer(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE KhachHang SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, CCCD = ?, NgheNghiep = ?, MucThuNhap = ?, TrangThai = ? WHERE MaKH = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getHoTen());
            ps.setDate(2, customer.getNgaySinh() != null ? Date.valueOf(customer.getNgaySinh()) : null);
            ps.setString(3, customer.getGioiTinh());
            ps.setString(4, customer.getDiaChi());
            ps.setString(5, customer.getSoDienThoai());
            ps.setString(6, customer.getEmail());
            ps.setString(7, customer.getCccd());
            ps.setString(8, customer.getNgheNghiep());
            ps.setObject(9, customer.getMucThuNhap());
            ps.setString(10, customer.getTrangThai());
            ps.setInt(11, customer.getMaKH());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public boolean deleteCustomer(Integer customerId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM KhachHang WHERE MaKH = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("MaKH"),
                    rs.getString("HoTen"),
                    rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toLocalDate() : null,
                    rs.getString("GioiTinh"),
                    rs.getString("DiaChi"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("CCCD"),
                    rs.getString("NgheNghiep"),
                    rs.getObject("MucThuNhap", Long.class),
                    rs.getTimestamp("NgayTao") != null ? rs.getTimestamp("NgayTao").toLocalDateTime() : null,
                    rs.getString("TrangThai")
                );
                customers.add(customer);
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return customers;
    }
}