package invoiceDAO;

import invoicedetailDAO.IInvoiceDetailDAO;
import invoicedetailDAO.InvoiceDetailDAO;
import model.Invoice;
import model.Customer; // Import các Model liên quan
import model.Employee;
import model.Payment;
import model.PaymentMethod;
import model.InvoiceDetail; // Để lấy chi tiết hóa đơn
import model.Car; // Để lấy thông tin xe trong chi tiết
import model.CarModel; // Giả sử DongXe và HangXe có trong Model
import model.CarBrand;

import java.sql.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class InvoiceDAO implements IInvoiceDAO {

    @Override
    public Invoice getInvoiceById(Integer invoiceId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Invoice invoice = null;
        String sql = "SELECT * FROM HoaDonBan WHERE MaHD = ?"; // Giả sử tên bảng là HoaDonBan

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, invoiceId);
            rs = ps.executeQuery();

            if (rs.next()) {
                invoice = new Invoice(
                    rs.getInt("MaHD"),
                    rs.getInt("MaKH"),
                    rs.getObject("MaNV", Integer.class),
                    rs.getDate("NgayLap").toLocalDate(),
                    rs.getBigDecimal("TongTien"),
                    rs.getBigDecimal("TienGiam"),
                    rs.getBigDecimal("ThanhTien"),
                    rs.getString("TrangThai"),
                    rs.getString("GhiChu")
                );
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return invoice;
    }

    @Override
    public Invoice getInvoiceFullInfo(Integer invoiceId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Invoice invoice = null;
        String sql = "SELECT hd.*, " +
                     "kh.HoTen AS KH_HoTen, kh.DiaChi AS KH_DiaChi, kh.SoDienThoai AS KH_SoDienThoai, kh.Email AS KH_Email, " +
                     "nv.HoTen AS NV_HoTen, nv.ChucVu AS NV_ChucVu, " +
                     "tt.MaTT, tt.MaPTTT, tt.SoTien AS TT_SoTien, tt.NgayThanhToan AS TT_NgayThanhToan, tt.SoTaiKhoan, tt.TenNganHang, tt.MaGiaoDich, tt.TrangThai AS TT_TrangThai, tt.GhiChu AS TT_GhiChu, " +
                     "pttt.TenPTTT AS PTTT_TenPTTT " +
                     "FROM HoaDonBan hd " +
                     "JOIN KhachHang kh ON hd.MaKH = kh.MaKH " +
                     "LEFT JOIN NhanVien nv ON hd.MaNV = nv.MaNV " +
                     "LEFT JOIN ThanhToan tt ON hd.MaHD = tt.MaHD " +
                     "LEFT JOIN PhuongThucThanhToan pttt ON tt.MaPTTT = pttt.MaPTTT " +
                     "WHERE hd.MaHD = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, invoiceId);
            rs = ps.executeQuery();

            if (rs.next()) {
                invoice = new Invoice(
                    rs.getInt("MaHD"),
                    rs.getInt("MaKH"),
                    rs.getObject("MaNV", Integer.class),
                    rs.getDate("NgayLap").toLocalDate(),
                    rs.getBigDecimal("TongTien"),
                    rs.getBigDecimal("TienGiam"),
                    rs.getBigDecimal("ThanhTien"),
                    rs.getString("TrangThai"),
                    rs.getString("GhiChu")
                );

                // Populate related objects
                Customer customer = new Customer();
                customer.setMaKH(rs.getInt("MaKH"));
                customer.setHoTen(rs.getString("KH_HoTen"));
                customer.setDiaChi(rs.getString("KH_DiaChi"));
                customer.setSoDienThoai(rs.getString("KH_SoDienThoai"));
                customer.setEmail(rs.getString("KH_Email"));
                invoice.setKhachHang(customer); // Đã đổi tên getKhachHang thành getCustomer

                if (rs.getObject("MaNV") != null) {
                    Employee employee = new Employee();
                    employee.setMaNV(rs.getInt("MaNV"));
                    employee.setHoTen(rs.getString("NV_HoTen"));
                    employee.setChucVu(rs.getString("NV_ChucVu"));
                    invoice.setNhanVien(employee); // Đã đổi tên getNhanVien thành getEmployee
                }

                if (rs.getObject("MaTT") != null) {
                    Payment payment = new Payment();
                    payment.setMaTT(rs.getInt("MaTT"));
                    payment.setMaHD(invoiceId);
                    payment.setMaPTTT(rs.getObject("MaPTTT", Integer.class));
                    payment.setSoTien(rs.getBigDecimal("TT_SoTien"));
                    payment.setNgayThanhToan(rs.getDate("TT_NgayThanhToan").toLocalDate());
                    payment.setSoTaiKhoan(rs.getString("SoTaiKhoan"));
                    payment.setTenNganHang(rs.getString("TenNganHang"));
                    payment.setMaGiaoDich(rs.getString("MaGiaoDich"));
                    payment.setTrangThai(rs.getString("TT_TrangThai"));
                    payment.setGhiChu(rs.getString("TT_GhiChu"));

                    PaymentMethod paymentMethod = new PaymentMethod();
                    paymentMethod.setMaPTTT(rs.getObject("MaPTTT", Integer.class));
                    paymentMethod.setTenPTTT(rs.getString("PTTT_TenPTTT"));
                    payment.setPhuongThucThanhToan(paymentMethod);
                    invoice.setThanhToan(payment); // Đã đổi tên getThanhToan thành getPayment
                }

                // Lấy chi tiết hóa đơn
                IInvoiceDetailDAO chiTietDao = new InvoiceDetailDAO(); // Khởi tạo DAO mới
                invoice.setChiTietHoaDons(chiTietDao.getInvoiceDetailsByInvoiceId(invoiceId)); // Đổi tên phương thức
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return invoice;
    }


    @Override
    public Integer addInvoice(Connection conn, Invoice invoice) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer generatedId = null;
        String sql = "INSERT INTO HoaDonBan (MaKH, MaNV, NgayLap, TongTien, TienGiam, ThanhTien, TrangThai, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY() AS ID;";

        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, invoice.getMaKH());
            ps.setObject(2, invoice.getMaNV());
            ps.setDate(3, Date.valueOf(invoice.getNgayLap()));
            ps.setBigDecimal(4, invoice.getTongTien());
            ps.setBigDecimal(5, invoice.getTienGiam());
            ps.setBigDecimal(6, invoice.getThanhTien());
            ps.setString(7, invoice.getTrangThai());
            ps.setString(8, invoice.getGhiChu());

            ps.execute();

            if (ps.getMoreResults()) {
                rs = ps.getResultSet();
                if (rs.next()) {
                    generatedId = rs.getInt("ID");
                }
            }
        } finally {
            DBConnection.close(null, ps, rs);
        }
        return generatedId;
    }

    @Override
    public boolean updateInvoice(Invoice invoice) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE HoaDonBan SET MaKH = ?, MaNV = ?, NgayLap = ?, TongTien = ?, TienGiam = ?, ThanhTien = ?, TrangThai = ?, GhiChu = ? WHERE MaHD = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setObject(1, invoice.getMaKH());
            ps.setObject(2, invoice.getMaNV());
            ps.setDate(3, Date.valueOf(invoice.getNgayLap()));
            ps.setBigDecimal(4, invoice.getTongTien());
            ps.setBigDecimal(5, invoice.getTienGiam());
            ps.setBigDecimal(6, invoice.getThanhTien());
            ps.setString(7, invoice.getTrangThai());
            ps.setString(8, invoice.getGhiChu());
            ps.setInt(9, invoice.getMaHD());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public boolean deleteInvoice(Integer invoiceId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM HoaDonBan WHERE MaHD = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, invoiceId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public List<Invoice> getAllInvoices() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonBan";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice(
                    rs.getInt("MaHD"),
                    rs.getInt("MaKH"),
                    rs.getObject("MaNV", Integer.class),
                    rs.getDate("NgayLap").toLocalDate(),
                    rs.getBigDecimal("TongTien"),
                    rs.getBigDecimal("TienGiam"),
                    rs.getBigDecimal("ThanhTien"),
                    rs.getString("TrangThai"),
                    rs.getString("GhiChu")
                );
                invoices.add(invoice);
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return invoices;
    }
}