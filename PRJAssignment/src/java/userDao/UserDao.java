package userDao;

import model.User;
import util.DBConnection;

import java.sql.*;

public class UserDao {

    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ? AND TrangThai = N'Hoạt động'";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(User user) {
        String sql = "INSERT INTO Users (Username, Password, Role, MaKH, MaNV, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setObject(4, user.getMaKH()); // nullable
            stmt.setObject(5, user.getMaNV()); // nullable
            stmt.setString(6, user.getTrangThai());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
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

    public boolean registerUser(User user) {
        if (isUsernameExists(user.getUsername())) {
            return false;
        }
        return insert(user);
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setMaUser(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setRole(rs.getString("Role"));
        user.setMaKH((Integer) rs.getObject("MaKH"));
        user.setMaNV((Integer) rs.getObject("MaNV"));
        user.setTrangThai(rs.getString("TrangThai"));
        return user;
    }
}
