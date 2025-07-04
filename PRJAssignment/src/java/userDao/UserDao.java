package userDao;

import model.User;
import model.Customer; // Assuming this Customer model is updated to have BigDecimal for income
import model.Employee;
import model.AdminProfile;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal; // Import BigDecimal
import java.time.LocalDate;   // <-- Make sure this is imported
import java.time.LocalDateTime; // <-- Make sure this is imported
import java.util.Date;

public class UserDao implements IUserDao {

    @Override
    public User authenticate(String username, String password) {
        String sql = """
            SELECT u.*, 
                   cp.NgheNghiep, cp.MucThuNhap,
                   ep.ChucVu, ep.PhongBan, ep.LuongCoBan, ep.HeSoLuong, ep.NgayVaoLam,
                   ap.CapDoQuyen, ap.NgayCapQuyen
            FROM Users u
            LEFT JOIN CustomerProfile cp ON u.MaUser = cp.MaUser
            LEFT JOIN EmployeeProfile ep ON u.MaUser = ep.MaUser
            LEFT JOIN AdminProfile ap ON u.MaUser = ap.MaUser
            WHERE u.Username = ? AND u.Password = ? AND u.TrangThai = N'Hoạt động'
            """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // In a real application, you should hash passwords

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            System.err.println("Database error during authentication: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        String sql = """
            SELECT u.*, 
                   cp.NgheNghiep, cp.MucThuNhap,
                   ep.ChucVu, ep.PhongBan, ep.LuongCoBan, ep.HeSoLuong, ep.NgayVaoLam,
                   ap.CapDoQuyen, ap.NgayCapQuyen
            FROM Users u
            LEFT JOIN CustomerProfile cp ON u.MaUser = cp.MaUser
            LEFT JOIN EmployeeProfile ep ON u.MaUser = ep.MaUser
            LEFT JOIN AdminProfile ap ON u.MaUser = ap.MaUser
            WHERE u.Username = ?
            """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            System.err.println("Database error finding user by username: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(int maUser) {
        String sql = """
            SELECT u.*, 
                   cp.NgheNghiep, cp.MucThuNhap,
                   ep.ChucVu, ep.PhongBan, ep.LuongCoBan, ep.HeSoLuong, ep.NgayVaoLam,
                   ap.CapDoQuyen, ap.NgayCapQuyen
            FROM Users u
            LEFT JOIN CustomerProfile cp ON u.MaUser = cp.MaUser
            LEFT JOIN EmployeeProfile ep ON u.MaUser = ep.MaUser
            LEFT JOIN AdminProfile ap ON u.MaUser = ap.MaUser
            WHERE u.MaUser = ?
            """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            System.err.println("Database error finding user by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        String sql = """
            SELECT u.*, 
                   cp.NgheNghiep, cp.MucThuNhap,
                   ep.ChucVu, ep.PhongBan, ep.LuongCoBan, ep.HeSoLuong, ep.NgayVaoLam,
                   ap.CapDoQuyen, ap.NgayCapQuyen
            FROM Users u
            LEFT JOIN CustomerProfile cp ON u.MaUser = cp.MaUser
            LEFT JOIN EmployeeProfile ep ON u.MaUser = ep.MaUser
            LEFT JOIN AdminProfile ap ON u.MaUser = ap.MaUser
            WHERE u.Email = ?
            """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            System.err.println("Database error finding user by email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = """
            SELECT u.*, 
                   cp.NgheNghiep, cp.MucThuNhap,
                   ep.ChucVu, ep.PhongBan, ep.LuongCoBan, ep.HeSoLuong, ep.NgayVaoLam,
                   ap.CapDoQuyen, ap.NgayCapQuyen
            FROM Users u
            LEFT JOIN CustomerProfile cp ON u.MaUser = cp.MaUser
            LEFT JOIN EmployeeProfile ep ON u.MaUser = ep.MaUser
            LEFT JOIN AdminProfile ap ON u.MaUser = ap.MaUser
            ORDER BY u.NgayTao DESC
            """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Database error finding all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> findByRole(String role) {
        List<User> users = new ArrayList<>();
        String sql = """
            SELECT u.*, 
                   cp.NgheNghiep, cp.MucThuNhap,
                   ep.ChucVu, ep.PhongBan, ep.LuongCoBan, ep.HeSoLuong, ep.NgayVaoLam,
                   ap.CapDoQuyen, ap.NgayCapQuyen
            FROM Users u
            LEFT JOIN CustomerProfile cp ON u.MaUser = cp.MaUser
            LEFT JOIN EmployeeProfile ep ON u.MaUser = ep.MaUser
            LEFT JOIN AdminProfile ap ON u.MaUser = ap.MaUser
            WHERE u.Role = ?
            ORDER BY u.NgayTao DESC
            """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Database error finding users by role: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean insert(User user) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert into Users table
            String userSql = """
                INSERT INTO Users (Username, Password, Email, HoTen, NgaySinh, GioiTinh, 
                                 DiaChi, SoDienThoai, CCCD, Role, TrangThai)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

            PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, user.getUsername());
            userStmt.setString(2, user.getPassword()); // In a real application, you should hash passwords
            userStmt.setString(3, user.getEmail());
            userStmt.setString(4, user.getHoTen());
            userStmt.setDate(5, user.getNgaySinh() != null ? new java.sql.Date(user.getNgaySinh().getTime()) : null);
            userStmt.setString(6, user.getGioiTinh());
            userStmt.setString(7, user.getDiaChi());
            userStmt.setString(8, user.getSoDienThoai());
            userStmt.setString(9, user.getCccd());
            userStmt.setString(10, user.getRole());
            userStmt.setString(11, user.getTrangThai());

            int affectedRows = userStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Get generated user ID
            ResultSet generatedKeys = userStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int maUser = generatedKeys.getInt(1);
                user.setMaUser(maUser); // Set the generated ID back to the user object

                // Insert profile based on role
                insertProfile(conn, user);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }

            conn.commit(); // Commit transaction
            return true;

        } catch (SQLException e) {
            System.err.println("Database error during user insertion: " + e.getMessage());
            if (conn != null) {
                try {
                    System.err.println("Transaction is being rolled back.");
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    System.err.println("Error during transaction rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close(); // Close connection
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean update(User user) {
        String sql = """
            UPDATE Users SET 
                Email = ?, HoTen = ?, NgaySinh = ?, GioiTinh = ?, 
                DiaChi = ?, SoDienThoai = ?, CCCD = ?, TrangThai = ?
            WHERE MaUser = ?
            """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getHoTen());
            stmt.setDate(3, user.getNgaySinh() != null ? new java.sql.Date(user.getNgaySinh().getTime()) : null);
            stmt.setString(4, user.getGioiTinh());
            stmt.setString(5, user.getDiaChi());
            stmt.setString(6, user.getSoDienThoai());
            stmt.setString(7, user.getCccd());
            stmt.setString(8, user.getTrangThai());
            stmt.setInt(9, user.getMaUser());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error during user update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int maUser) {
        return changeStatus(maUser, "Tạm khóa"); // Soft delete by changing status
    }

    @Override
    public boolean changePassword(int maUser, String newPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE MaUser = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword); // In a real application, you should hash passwords
            stmt.setInt(2, maUser);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error changing password: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeStatus(int maUser, String trangThai) {
        String sql = "UPDATE Users SET TrangThai = ? WHERE MaUser = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trangThai);
            stmt.setInt(2, maUser);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error changing user status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Database error checking username existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Email = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Database error checking email existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setMaUser(rs.getInt("MaUser"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setEmail(rs.getString("Email"));
        user.setHoTen(rs.getString("HoTen"));

        // Correct way to get Date from ResultSet and set to User (if User.ngaySinh is java.util.Date)
        // If User.ngaySinh is LocalDate, it would be:
        // java.sql.Date sqlDateNgaySinh = rs.getDate("NgaySinh");
        // user.setNgaySinh(sqlDateNgaySinh != null ? sqlDateNgaySinh.toLocalDate() : null);
        user.setNgaySinh(rs.getDate("NgaySinh")); // Assuming User.ngaySinh is java.util.Date

        user.setGioiTinh(rs.getString("GioiTinh"));
        user.setDiaChi(rs.getString("DiaChi"));
        user.setSoDienThoai(rs.getString("SoDienThoai"));
        user.setCccd(rs.getString("CCCD"));
        user.setRole(rs.getString("Role"));

        Timestamp ngayTaoTimestamp = rs.getTimestamp("NgayTao");
        user.setNgayTao(ngayTaoTimestamp != null ? ngayTaoTimestamp.toLocalDateTime() : null);

        user.setTrangThai(rs.getString("TrangThai"));

        String role = user.getRole();
        if ("CUSTOMER".equalsIgnoreCase(role)) {
            Customer customerProfile = new Customer();
            customerProfile.setMaKH(user.getMaUser());
            customerProfile.setNgheNghiep(rs.getString("NgheNghiep"));
            customerProfile.setMucThuNhap(rs.getBigDecimal("MucThuNhap"));
            user.setCustomer(customerProfile);
        } else if ("EMPLOYEE".equalsIgnoreCase(role)) {
            Employee employee = new Employee();
            employee.setMaUser(user.getMaUser());
            employee.setChucVu(rs.getString("ChucVu"));
            employee.setPhongBan(rs.getString("PhongBan"));
            employee.setLuongCoBan(rs.getBigDecimal("LuongCoBan"));
            employee.setHeSoLuong(rs.getBigDecimal("HeSoLuong"));

            // --- FIX FOR LINE 1 (employee.setNgayVaoLam) ---
            // Get as java.sql.Date, then convert to LocalDate
            java.sql.Date ngayVaoLamSqlDate = rs.getDate("NgayVaoLam");
            employee.setNgayVaoLam(ngayVaoLamSqlDate != null ? ngayVaoLamSqlDate.toLocalDate() : null);
            // --- END FIX ---

            user.setEmployee(employee); 
        } else if ("ADMIN".equalsIgnoreCase(role)) {
            AdminProfile adminProfile = new AdminProfile();
            adminProfile.setMaUser(user.getMaUser());
            adminProfile.setCapDoQuyen(rs.getInt("CapDoQuyen"));
            adminProfile.setNgayCapQuyen(rs.getDate("NgayCapQuyen"));
            user.setAdminProfile(adminProfile);
        }
        return user;
    }

    private void insertProfile(Connection conn, User user) throws SQLException {
        String role = user.getRole();

        if ("CUSTOMER".equalsIgnoreCase(role) && user.getCustomer() != null) {
            String sql = "INSERT INTO CustomerProfile (MaUser, NgheNghiep, MucThuNhap) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getMaUser());
            stmt.setString(2, user.getCustomer().getNgheNghiep());
            stmt.setBigDecimal(3, user.getCustomer().getMucThuNhap());
            stmt.executeUpdate();
        } else if ("EMPLOYEE".equalsIgnoreCase(role) && user.getEmployee() != null) { // Use getEmployeeProfile()
            String sql = "INSERT INTO EmployeeProfile (MaUser, ChucVu, PhongBan, LuongCoBan, HeSoLuong, NgayVaoLam) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getMaUser());
            stmt.setString(2, user.getEmployee().getChucVu());
            stmt.setString(3, user.getEmployee().getPhongBan());
            stmt.setBigDecimal(4, user.getEmployee().getLuongCoBan());
            stmt.setBigDecimal(5, user.getEmployee().getHeSoLuong());

            // --- FIX FOR LINE 2 (stmt.setDate) ---
            // Use the helper method from Employee model to convert LocalDate to java.sql.Date
            stmt.setDate(6, user.getEmployee().getNgayVaoLamAsSqlDate());
            // --- END FIX ---

            stmt.executeUpdate();
        } else if ("ADMIN".equalsIgnoreCase(role) && user.getAdminProfile() != null) {
            String sql = "INSERT INTO AdminProfile (MaUser, CapDoQuyen, NgayCapQuyen) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getMaUser());
            stmt.setInt(2, user.getAdminProfile().getCapDoQuyen());
            stmt.setDate(3, user.getAdminProfile().getNgayCapQuyen() != null
                    ? new java.sql.Date(user.getAdminProfile().getNgayCapQuyen().getTime()) : null);
            stmt.executeUpdate();
        }
    }
}
