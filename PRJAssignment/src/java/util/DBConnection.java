package util; // <-- Đảm bảo package này đúng với vị trí file của bạn

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; // Import thêm
import java.sql.ResultSet;       // Import thêm
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    public static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=CarShopDB;encrypt=false;trustServerCertificate=true"; // Thêm encrypt và trustServerCertificate cho SQL Server
    public static String userDB = "sa";
    public static String passDB = "123456";

    // Static block để load driver (đảm bảo driver được load khi lớp được tải)
    static {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Không thể tải driver JDBC: " + driverName, ex);
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(dbURL, userDB, passDB);
            return con;
        } catch (SQLException ex) { // Bắt SQLException cụ thể hơn
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Lỗi kết nối CSDL:", ex);
        }
        return null;
    }

    // --- BẮT ĐẦU PHẦN BỔ SUNG CÁC PHƯƠNG THỨC CLOSE ---
    /**
     * Đóng Connection, PreparedStatement và ResultSet.
     *
     * @param conn Đối tượng Connection để đóng.
     * @param ps Đối tượng PreparedStatement để đóng.
     * @param rs Đối tượng ResultSet để đóng.
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Lỗi khi đóng ResultSet:", ex);
        } finally {
            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Lỗi khi đóng PreparedStatement:", ex);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Lỗi khi đóng Connection:", ex);
                }
            }
        }
    }

    /**
     * Đóng Connection và PreparedStatement.
     *
     * @param conn Đối tượng Connection để đóng.
     * @param ps Đối tượng PreparedStatement để đóng.
     */
    public static void close(Connection conn, PreparedStatement ps) {
        close(conn, ps, null); // Gọi lại phương thức đóng 3 tham số, truyền null cho ResultSet
    }

    /**
     * Đóng chỉ Connection.
     *
     * @param conn Đối tượng Connection để đóng.
     */
    public static void close(Connection conn) {
        close(conn, null, null); // Gọi lại phương thức đóng 3 tham số, truyền null cho PreparedStatement và ResultSet
    }

    // --- KẾT THÚC PHẦN BỔ SUNG ---
    public static void main(String[] args) {
        try (Connection con = getConnection()) { // Sử dụng try-with-resources là tốt
            if (con != null) {
                System.out.println("Connect CarShopDB successfully!");
            }
        } catch (SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Lỗi SQL trong main:", e);
        }
    }
}
