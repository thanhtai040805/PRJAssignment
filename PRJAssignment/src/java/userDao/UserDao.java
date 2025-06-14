package userDao;

import model.User;
import model.CustomerProfile;
import model.EmployeeProfile;
import model.AdminProfile;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // Trong thực tế nên hash password

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean insert(User user) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Insert vào bảng Users
            String userSql = """
                INSERT INTO Users (Username, Password, Email, HoTen, NgaySinh, GioiTinh, 
                                 DiaChi, SoDienThoai, CCCD, Role, TrangThai)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

            PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, user.getUsername());
            userStmt.setString(2, user.getPassword());
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
                throw new SQLException("Tạo user thất bại, không có hàng nào được thêm.");
            }

            // Lấy ID user vừa tạo
            ResultSet generatedKeys = userStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int maUser = generatedKeys.getInt(1);
                user.setMaUser(maUser);

                // Insert profile theo role
                insertProfile(conn, user);
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int maUser) {
        return changeStatus(maUser, "Tạm khóa");
    }

    @Override
    public boolean changePassword(int maUser, String newPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE MaUser = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword); // Trong thực tế nên hash password
            stmt.setInt(2, maUser);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeStatus(int maUser, String trangThai) {
        String sql = "UPDATE Users SET TrangThai = ? WHERE MaUser = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trangThai);
            stmt.setInt(2, maUser);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Private helper methods
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setMaUser(rs.getInt("MaUser"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setEmail(rs.getString("Email"));
        user.setHoTen(rs.getString("HoTen"));
        user.setNgaySinh(rs.getDate("NgaySinh"));
        user.setGioiTinh(rs.getString("GioiTinh"));
        user.setDiaChi(rs.getString("DiaChi"));
        user.setSoDienThoai(rs.getString("SoDienThoai"));
        user.setCccd(rs.getString("CCCD"));
        user.setRole(rs.getString("Role"));
        user.setNgayTao(rs.getTimestamp("NgayTao"));
        user.setTrangThai(rs.getString("TrangThai"));

        // Map profile theo role
        String role = user.getRole();
        if ("CUSTOMER".equals(role)) {
            CustomerProfile profile = new CustomerProfile();
            profile.setMaUser(user.getMaUser());
            profile.setNgheNghiep(rs.getString("NgheNghiep"));
            BigDecimal mucThuNhap = rs.getBigDecimal("MucThuNhap");
            profile.setMucThuNhap(mucThuNhap);
            user.setCustomerProfile(profile);
        } else if ("EMPLOYEE".equals(role)) {
            EmployeeProfile profile = new EmployeeProfile();
            profile.setMaUser(user.getMaUser());
            profile.setChucVu(rs.getString("ChucVu"));
            profile.setPhongBan(rs.getString("PhongBan"));
            profile.setLuongCoBan(rs.getBigDecimal("LuongCoBan"));
            profile.setHeSoLuong(rs.getBigDecimal("HeSoLuong"));
            profile.setNgayVaoLam(rs.getDate("NgayVaoLam"));
            user.setEmployeeProfile(profile);
        } else if ("ADMIN".equals(role)) {
            AdminProfile profile = new AdminProfile();
            profile.setMaUser(user.getMaUser());
            profile.setCapDoQuyen(rs.getInt("CapDoQuyen"));
            profile.setNgayCapQuyen(rs.getDate("NgayCapQuyen"));
            user.setAdminProfile(profile);
        }

        return user;
    }

    private void insertProfile(Connection conn, User user) throws SQLException {
        String role = user.getRole();
        
        if ("CUSTOMER".equals(role) && user.getCustomerProfile() != null) {
            String sql = "INSERT INTO CustomerProfile (MaUser, NgheNghiep, MucThuNhap) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getMaUser());
            stmt.setString(2, user.getCustomerProfile().getNgheNghiep());
            stmt.setBigDecimal(3, user.getCustomerProfile().getMucThuNhap());
            stmt.executeUpdate();
        } else if ("EMPLOYEE".equals(role) && user.getEmployeeProfile() != null) {
            String sql = "INSERT INTO EmployeeProfile (MaUser, ChucVu, PhongBan, LuongCoBan, HeSoLuong, NgayVaoLam) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getMaUser());
            stmt.setString(2, user.getEmployeeProfile().getChucVu());
            stmt.setString(3, user.getEmployeeProfile().getPhongBan());
            stmt.setBigDecimal(4, user.getEmployeeProfile().getLuongCoBan());
            stmt.setBigDecimal(5, user.getEmployeeProfile().getHeSoLuong());
            stmt.setDate(6, user.getEmployeeProfile().getNgayVaoLam() != null ? 
                        new java.sql.Date(user.getEmployeeProfile().getNgayVaoLam().getTime()) : null);
            stmt.executeUpdate();
        } else if ("ADMIN".equals(role) && user.getAdminProfile() != null) {
            String sql = "INSERT INTO AdminProfile (MaUser, CapDoQuyen, NgayCapQuyen) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getMaUser());
            stmt.setInt(2, user.getAdminProfile().getCapDoQuyen());
            stmt.setDate(3, user.getAdminProfile().getNgayCapQuyen() != null ? 
                        new java.sql.Date(user.getAdminProfile().getNgayCapQuyen().getTime()) : null);
            stmt.executeUpdate();
        }
    }
}
