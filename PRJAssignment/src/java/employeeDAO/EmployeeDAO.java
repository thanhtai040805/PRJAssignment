package employeeDAO;

import model.Employee;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class EmployeeDAO implements IEmployeeDAO {

    @Override
    public Employee getEmployeeById(Integer employeeId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Employee employee = null;
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ?"; // Giả sử tên bảng là NhanVien

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, employeeId);
            rs = ps.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                    rs.getInt("MaNV"),
                    rs.getString("HoTen"),
                    rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toLocalDate() : null,
                    rs.getString("GioiTinh"),
                    rs.getString("DiaChi"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("CCCD"),
                    rs.getString("ChucVu"),
                    rs.getDate("NgayVaoLam") != null ? rs.getDate("NgayVaoLam").toLocalDate() : null,
                    rs.getBigDecimal("LuongCoBan"),
                    rs.getString("TrangThai"),
                    rs.getTimestamp("NgayTao") != null ? rs.getTimestamp("NgayTao").toLocalDateTime() : null,
                    rs.getTimestamp("NgayCapNhat") != null ? rs.getTimestamp("NgayCapNhat").toLocalDateTime() : null
                );
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return employee;
    }

    @Override
    public Integer addEmployee(Employee employee) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer generatedId = null;
        String sql = "INSERT INTO NhanVien (HoTen, NgaySinh, GioiTinh, DiaChi, SoDienThoai, Email, CCCD, ChucVu, NgayVaoLam, LuongCoBan, TrangThai, NgayTao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY() AS ID;";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getHoTen());
            ps.setDate(2, employee.getNgaySinh() != null ? Date.valueOf(employee.getNgaySinh()) : null);
            ps.setString(3, employee.getGioiTinh());
            ps.setString(4, employee.getDiaChi());
            ps.setString(5, employee.getSoDienThoai());
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getCccd());
            ps.setString(8, employee.getChucVu());
            ps.setDate(9, employee.getNgayVaoLam() != null ? Date.valueOf(employee.getNgayVaoLam()) : null);
            ps.setBigDecimal(10, employee.getLuongCoBan());
            ps.setString(11, employee.getTrangThai());
            ps.setTimestamp(12, employee.getNgayTao() != null ? Timestamp.valueOf(employee.getNgayTao()) : null);

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
    public boolean updateEmployee(Employee employee) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE NhanVien SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, SoDienThoai = ?, Email = ?, CCCD = ?, ChucVu = ?, NgayVaoLam = ?, LuongCoBan = ?, TrangThai = ?, NgayCapNhat = ? WHERE MaNV = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getHoTen());
            ps.setDate(2, employee.getNgaySinh() != null ? Date.valueOf(employee.getNgaySinh()) : null);
            ps.setString(3, employee.getGioiTinh());
            ps.setString(4, employee.getDiaChi());
            ps.setString(5, employee.getSoDienThoai());
            ps.setString(6, employee.getEmail());
            ps.setString(7, employee.getCccd());
            ps.setString(8, employee.getChucVu());
            ps.setDate(9, employee.getNgayVaoLam() != null ? Date.valueOf(employee.getNgayVaoLam()) : null);
            ps.setBigDecimal(10, employee.getLuongCoBan());
            ps.setString(11, employee.getTrangThai());
            ps.setTimestamp(12, employee.getNgayCapNhat() != null ? Timestamp.valueOf(employee.getNgayCapNhat()) : null);
            ps.setInt(13, employee.getMaNV());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public boolean deleteEmployee(Integer employeeId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM NhanVien WHERE MaNV = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, employeeId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("MaNV"),
                    rs.getString("HoTen"),
                    rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toLocalDate() : null,
                    rs.getString("GioiTinh"),
                    rs.getString("DiaChi"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email"),
                    rs.getString("CCCD"),
                    rs.getString("ChucVu"),
                    rs.getDate("NgayVaoLam") != null ? rs.getDate("NgayVaoLam").toLocalDate() : null,
                    rs.getBigDecimal("LuongCoBan"),
                    rs.getString("TrangThai"),
                    rs.getTimestamp("NgayTao") != null ? rs.getTimestamp("NgayTao").toLocalDateTime() : null,
                    rs.getTimestamp("NgayCapNhat") != null ? rs.getTimestamp("NgayCapNhat").toLocalDateTime() : null
                );
                employees.add(employee);
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return employees;
    }
}