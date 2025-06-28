package invoicedetailDAO;

import model.InvoiceDetail;
import model.Car;        // Import Car
// Không cần import CarModel và CarBrand nếu Car chỉ lưu String
// import model.CarModel;
// import model.CarBrand;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;

public class InvoiceDetailDAO implements IInvoiceDetailDAO {

    @Override
    public InvoiceDetail getInvoiceDetailById(Integer maCTHD) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InvoiceDetail invoiceDetail = null;
        String sql = "SELECT cthd.*, xe.MaDong, xe.TenXe, xe.NamSanXuat, xe.MauSac, xe.SoKhung, xe.SoMay, xe.GiaBan, "
                + "dx.TenDong, dx.LoaiXe, dx.LoaiNhienLieu, dx.SoChoNgoi, "
                + // Lấy thêm các trường của DongXe
                "hx.TenHang, ncc.TenNCC "
                + // Lấy thêm TenHang và TenNCC
                "FROM ChiTietHoaDon cthd "
                + "JOIN XeOTo xe ON cthd.MaXe = xe.MaXe "
                + "JOIN DongXe dx ON xe.MaDong = dx.MaDong "
                + "JOIN HangXe hx ON dx.MaHang = hx.MaHang "
                + "JOIN NhaCungCap ncc ON xe.MaNCC = ncc.MaNCC "
                + // Giả định tên bảng là NhaCungCap
                "WHERE cthd.MaCTHD = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maCTHD);
            rs = ps.executeQuery();

            if (rs.next()) {
                invoiceDetail = new InvoiceDetail(
                        rs.getInt("MaCTHD"),
                        rs.getInt("MaHD"),
                        rs.getInt("MaXe"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("DonGia"),
                        rs.getBigDecimal("ThanhTien")
                );

                Car car = new Car();
                car.setMaXe(rs.getInt("MaXe"));
                car.setMaDong(rs.getInt("MaDong")); // Set MaDong
                car.setMaNCC(rs.getInt("MaNCC"));   // Set MaNCC (cần có trong query nếu muốn populate)
                car.setTenXe(rs.getString("TenXe"));
                car.setNamSanXuat(rs.getObject("NamSanXuat", Integer.class));
                car.setMauSac(rs.getString("MauSac"));
                car.setSoKhung(rs.getString("SoKhung"));
                car.setSoMay(rs.getString("SoMay"));
                car.setGiaBan(rs.getBigDecimal("GiaBan"));

                // Gán trực tiếp các String từ ResultSet vào đối tượng Car
                car.setTenDong(rs.getString("TenDong"));
                car.setLoaiXe(rs.getString("LoaiXe"));
                car.setNhienLieu(rs.getString("LoaiNhienLieu")); // Thêm cột LoaiNhienLieu vào SQL nếu muốn lấy
                car.setSoChoNgoi(rs.getObject("SoChoNgoi", Integer.class)); // Thêm cột SoChoNgoi vào SQL nếu muốn lấy

                car.setTenHang(rs.getString("TenHang"));
                car.setTenNCC(rs.getString("TenNCC")); // Set TenNCC

                invoiceDetail.setXeOto(car); // Gán đối tượng Car vào InvoiceDetail
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return invoiceDetail;
    }

    @Override
    public List<InvoiceDetail> getInvoiceDetailsByInvoiceId(Integer maHD) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InvoiceDetail> list = new ArrayList<>();
        String sql = "SELECT cthd.*, xe.MaDong, xe.TenXe, xe.NamSanXuat, xe.MauSac, xe.SoKhung, xe.SoMay, xe.GiaBan, "
                + "dx.TenDong, dx.LoaiXe, dx.LoaiNhienLieu, dx.SoChoNgoi, "
                + // Lấy thêm các trường của DongXe
                "hx.TenHang, ncc.TenNCC "
                + // Lấy thêm TenHang và TenNCC
                "FROM ChiTietHoaDon cthd "
                + "JOIN XeOTo xe ON cthd.MaXe = xe.MaXe "
                + "JOIN DongXe dx ON xe.MaDong = dx.MaDong "
                + "JOIN HangXe hx ON dx.MaHang = hx.MaHang "
                + "JOIN NhaCungCap ncc ON xe.MaNCC = ncc.MaNCC "
                + "WHERE cthd.MaHD = ?";
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs = ps.executeQuery();

            while (rs.next()) {
                InvoiceDetail cthd = new InvoiceDetail(
                        rs.getInt("MaCTHD"),
                        rs.getInt("MaHD"),
                        rs.getInt("MaXe"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("DonGia"),
                        rs.getBigDecimal("ThanhTien")
                );

                Car car = new Car();
                car.setMaXe(rs.getInt("MaXe"));
                car.setMaDong(rs.getInt("MaDong"));
                car.setMaNCC(rs.getInt("MaNCC"));
                car.setTenXe(rs.getString("TenXe"));
                car.setNamSanXuat(rs.getObject("NamSanXuat", Integer.class));
                car.setMauSac(rs.getString("MauSac"));
                car.setSoKhung(rs.getString("SoKhung"));
                car.setSoMay(rs.getString("SoMay"));
                car.setGiaBan(rs.getBigDecimal("GiaBan"));

                // Gán trực tiếp các String từ ResultSet vào đối tượng Car
                car.setTenDong(rs.getString("TenDong"));
                car.setLoaiXe(rs.getString("LoaiXe"));
                car.setNhienLieu(rs.getString("LoaiNhienLieu"));
                car.setSoChoNgoi(rs.getObject("SoChoNgoi", Integer.class));

                car.setTenHang(rs.getString("TenHang"));
                car.setTenNCC(rs.getString("TenNCC"));

                cthd.setXeOto(car);
                list.add(cthd);
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return list;
    }

    @Override
    public boolean addInvoiceDetail(Connection conn, InvoiceDetail invoiceDetail) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO ChiTietHoaDon (MaHD, MaXe, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, invoiceDetail.getMaHD());
            ps.setInt(2, invoiceDetail.getMaXe());
            ps.setInt(3, invoiceDetail.getSoLuong());
            ps.setBigDecimal(4, invoiceDetail.getDonGia());
            ps.setBigDecimal(5, invoiceDetail.getThanhTien());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(null, ps);
        }
    }

    @Override
    public boolean updateInvoiceDetail(InvoiceDetail invoiceDetail) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE ChiTietHoaDon SET MaHD = ?, MaXe = ?, SoLuong = ?, DonGia = ?, ThanhTien = ? WHERE MaCTHD = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, invoiceDetail.getMaHD());
            ps.setInt(2, invoiceDetail.getMaXe());
            ps.setInt(3, invoiceDetail.getSoLuong());
            ps.setBigDecimal(4, invoiceDetail.getDonGia());
            ps.setBigDecimal(5, invoiceDetail.getThanhTien());
            ps.setInt(6, invoiceDetail.getMaCTHD());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public boolean deleteInvoiceDetail(Integer maCTHD) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM ChiTietHoaDon WHERE MaCTHD = ?";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maCTHD);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DBConnection.close(conn, ps);
        }
    }

    @Override
    public List<InvoiceDetail> getAllInvoiceDetails() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InvoiceDetail> list = new ArrayList<>();
        // Nếu bạn muốn lấy thông tin đầy đủ về Car, CarModel, CarBrand ở đây,
        // bạn sẽ cần thực hiện JOIN tương tự như các phương thức trên.
        // Hiện tại, nó chỉ lấy thông tin từ bảng ChiTietHoaDon.
        String sql = "SELECT * FROM ChiTietHoaDon";

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                InvoiceDetail cthd = new InvoiceDetail(
                        rs.getInt("MaCTHD"),
                        rs.getInt("MaHD"),
                        rs.getInt("MaXe"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("DonGia"),
                        rs.getBigDecimal("ThanhTien")
                );
                // Nếu cần thông tin chi tiết về XeOto, bạn sẽ cần gọi một DAO khác
                // hoặc viết câu query JOIN tương tự như các method khác.
                list.add(cthd);
            }
        } finally {
            DBConnection.close(conn, ps, rs);
        }
        return list;
    }
}
