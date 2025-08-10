package employeeDAO;

import model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDAO {

    private static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public List<Employee> getAll() {
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching all employees", e);
            return null;
        }
    }

    // Optional: Chỉ lấy nhân viên đang làm việc
    public List<Employee> getActiveEmployees() {
        try {
            TypedQuery<Employee> query = em.createQuery(
                    "SELECT e FROM Employee e WHERE TRIM(e.status) LIKE :status", Employee.class);
            query.setParameter("status", "%Đang làm việc%");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching active employees", e);
            return null;
        }
    }

    public Employee findById(int id) {
        return em.find(Employee.class, id);
    }

    public List<Map<String, Object>> getAllEmployeesWithUser() {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT nv.*, u.Username, u.Password FROM NhanVien nv LEFT JOIN Users u ON nv.MaNV = u.MaNV";
        try (Connection conn = util.DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaNV", rs.getInt("MaNV"));
                row.put("HoTen", rs.getString("HoTen"));
                row.put("NgaySinh", rs.getDate("NgaySinh"));
                row.put("GioiTinh", rs.getString("GioiTinh"));
                row.put("DiaChi", rs.getString("DiaChi"));
                row.put("SoDienThoai", rs.getString("SoDienThoai"));
                row.put("Email", rs.getString("Email"));
                row.put("CCCD", rs.getString("CCCD"));
                row.put("ChucVu", rs.getString("ChucVu"));
                row.put("PhongBan", rs.getString("PhongBan"));
                row.put("LuongCoBan", rs.getBigDecimal("LuongCoBan"));
                row.put("HeSoLuong", rs.getBigDecimal("HeSoLuong"));
                row.put("NgayVaoLam", rs.getDate("NgayVaoLam"));
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

    public List<Map<String, Object>> searchEmployeesWithUser(String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT nv.*, u.Username, u.Password FROM NhanVien nv LEFT JOIN Users u ON nv.MaNV = u.MaNV WHERE nv.HoTen LIKE ? OR nv.Email LIKE ? OR nv.SoDienThoai LIKE ? OR u.Username LIKE ?";
        try (Connection conn = util.DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("MaNV", rs.getInt("MaNV"));
                row.put("HoTen", rs.getString("HoTen"));
                row.put("NgaySinh", rs.getDate("NgaySinh"));
                row.put("GioiTinh", rs.getString("GioiTinh"));
                row.put("DiaChi", rs.getString("DiaChi"));
                row.put("SoDienThoai", rs.getString("SoDienThoai"));
                row.put("Email", rs.getString("Email"));
                row.put("CCCD", rs.getString("CCCD"));
                row.put("ChucVu", rs.getString("ChucVu"));
                row.put("PhongBan", rs.getString("PhongBan"));
                row.put("LuongCoBan", rs.getBigDecimal("LuongCoBan"));
                row.put("HeSoLuong", rs.getBigDecimal("HeSoLuong"));
                row.put("NgayVaoLam", rs.getDate("NgayVaoLam"));
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
