CREATE DATABASE CarShopDB;
GO

USE CarShopDB;
GO

-- ===============================
-- 1. BẢNG KHÁCH HÀNG
-- ===============================
CREATE TABLE KhachHang (
    MaKH INT IDENTITY(1,1) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh NVARCHAR(10) CHECK (GioiTinh IN (N'Nam', N'Nữ', N'Khác')),
    DiaChi NVARCHAR(255),
    SoDienThoai NVARCHAR(15) UNIQUE,
    Email NVARCHAR(100) UNIQUE,
    CCCD NVARCHAR(12) UNIQUE,
    NgheNghiep NVARCHAR(100),
    MucThuNhap DECIMAL(15,0),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai NVARCHAR(20) DEFAULT N'Hoạt động' CHECK (TrangThai IN (N'Hoạt động', N'Tạm khóa'))
);
GO

-- ===============================
-- DỮ LIỆU BẢNG KHÁCH HÀNG
-- ===============================
INSERT INTO KhachHang (HoTen, NgaySinh, GioiTinh, DiaChi, SoDienThoai, Email, CCCD, NgheNghiep, MucThuNhap) VALUES
(N'Nguyễn Văn An', '1985-03-15', N'Nam', N'123 Lê Lợi, Q.1, TP.HCM', '0901234567', 'nguyenvanan@email.com', '079085001234', N'Kỹ sư IT', 25000000),
(N'Trần Thị Bình', '1990-07-22', N'Nữ', N'456 Nguyễn Huệ, Q.3, TP.HCM', '0912345678', 'tranthibinh@email.com', '079090002345', N'Bác sĩ', 35000000),
(N'Lê Hoàng Cường', '1982-11-10', N'Nam', N'789 Hai Bà Trưng, Q.5, TP.HCM', '0923456789', 'lehoangcuong@email.com', '079082003456', N'Doanh nhân', 50000000),
(N'Phạm Thị Dung', '1988-05-08', N'Nữ', N'321 Lý Tự Trọng, Q.1, TP.HCM', '0934567890', 'phamthidung@email.com', '079088004567', N'Giáo viên', 15000000),
(N'Hoàng Văn Em', '1992-12-25', N'Nam', N'654 Võ Văn Tần, Q.3, TP.HCM', '0945678901', 'hoangvanem@email.com', '079092005678', N'Kế toán', 18000000),
(N'Vũ Thị Phượng', '1987-09-14', N'Nữ', N'987 Cách Mạng Tháng 8, Q.10, TP.HCM', '0956789012', 'vuthiphuong@email.com', '079087006789', N'Luật sư', 40000000);
GO

-- ===============================
-- 2. BẢNG NHÂN VIÊN
-- ===============================
CREATE TABLE NhanVien (
    MaNV INT IDENTITY(1,1) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh NVARCHAR(10) CHECK (GioiTinh IN (N'Nam', N'Nữ', N'Khác')),
    DiaChi NVARCHAR(255),
    SoDienThoai NVARCHAR(15) UNIQUE,
    Email NVARCHAR(100) UNIQUE,
    CCCD NVARCHAR(12) UNIQUE,
    ChucVu NVARCHAR(50),
    PhongBan NVARCHAR(50),
    LuongCoBan DECIMAL(12,0),
    HeSoLuong DECIMAL(3,2) DEFAULT 1.0,
    NgayVaoLam DATE,
    TrangThai NVARCHAR(20) DEFAULT N'Đang làm việc' CHECK (TrangThai IN (N'Đang làm việc', N'Nghỉ việc', N'Tạm nghỉ'))
);
GO

-- ===============================
-- DỮ LIỆU BẢNG NHÂN VIÊN
-- ===============================
INSERT INTO NhanVien (HoTen, NgaySinh, GioiTinh, DiaChi, SoDienThoai, Email, CCCD, ChucVu, PhongBan, LuongCoBan, HeSoLuong, NgayVaoLam) VALUES
(N'Lê Văn Mạnh', '1980-04-12', N'Nam', N'11 Đồng Khởi, Q.1, TP.HCM', '0911111111', 'levanmanh@company.com', '079080011111', N'Giám đốc kinh doanh', N'Kinh doanh', 15000000, 2.5, '2015-01-15'),
(N'Nguyễn Thị Linh', '1985-08-20', N'Nữ', N'22 Nam Kỳ Khởi Nghĩa, Q.3, TP.HCM', '0922222222', 'nguyenthilinh@company.com', '079085022222', N'Trưởng phòng bán hàng', N'Bán hàng', 12000000, 2.0, '2016-03-10'),
(N'Trần Văn Sơn', '1990-02-14', N'Nam', N'33 Lê Duẩn, Q.1, TP.HCM', '0933333333', 'tranvanson@company.com', '079090033333', N'Nhân viên bán hàng', N'Bán hàng', 8000000, 1.5, '2018-06-20'),
(N'Phan Thị Hoa', '1988-12-05', N'Nữ', N'44 Pasteur, Q.3, TP.HCM', '0944444444', 'phanthihoa@company.com', '079088044444', N'Nhân viên bán hàng', N'Bán hàng', 8000000, 1.5, '2019-02-28'),
(N'Đặng Văn Tuấn', '1983-06-30', N'Nam', N'55 Điện Biên Phủ, Q.1, TP.HCM', '0955555555', 'dangvantuan@company.com', '079083055555', N'Trưởng phòng kỹ thuật', N'Kỹ thuật', 13000000, 2.2, '2017-09-15'),
(N'Bùi Thị Mai', '1991-10-18', N'Nữ', N'66 Cộng Hòa, Q.Tân Bình, TP.HCM', '0966666666', 'buithimai@company.com', '079091066666', N'Nhân viên kỹ thuật', N'Kỹ thuật', 9000000, 1.8, '2020-01-10');
GO

-- ===============================
-- 3. BẢNG NHÀ CUNG CẤP
-- ===============================
CREATE TABLE NhaCungCap (
    MaNCC INT IDENTITY(1,1) PRIMARY KEY,
    TenNCC NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255),
    SoDienThoai NVARCHAR(15),
    Email NVARCHAR(100),
    MaSoThue NVARCHAR(20) UNIQUE,
    NguoiLienHe NVARCHAR(100),
    SoDienThoaiLienHe NVARCHAR(15),
    LoaiNCC NVARCHAR(20) CHECK (LoaiNCC IN (N'Xe mới', N'Xe cũ', N'Phụ tùng', N'Dịch vụ')),
    NgayHopTac DATE,
    TrangThai NVARCHAR(20) DEFAULT N'Hoạt động' CHECK (TrangThai IN (N'Hoạt động', N'Tạm dừng', N'Chấm dứt'))
);
GO

-- ===============================
-- DỮ LIỆU BẢNG NHÀ CUNG CẤP
-- ===============================
INSERT INTO NhaCungCap (TenNCC, DiaChi, SoDienThoai, Email, MaSoThue, NguoiLienHe, SoDienThoaiLienHe, LoaiNCC, NgayHopTac) VALUES
(N'Toyota Việt Nam', N'Khu CN Biên Hòa, Đồng Nai', '02513861888', 'info@toyota.com.vn', '0300465656001', N'Nguyễn Văn A', '0901000001', N'Xe mới', '2010-01-15'),
(N'Honda Việt Nam', N'Khu CN Vĩnh Lộc, TP.HCM', '02837150150', 'info@honda.com.vn', '0300512345002', N'Trần Thị B', '0902000002', N'Xe mới', '2012-03-20'),
(N'Hyundai Thành Công', N'Ninh Bình', '02293881234', 'contact@htv.com.vn', '0300678901003', N'Lê Văn C', '0903000003', N'Xe mới', '2015-07-10'),
(N'Công ty Xe Cũ Hồng Phát', N'789 Quốc lộ 1A, Bình Tân, TP.HCM', '02838123456', 'hongphat@email.com', '0301234567004', N'Phạm Văn D', '0904000004', N'Xe cũ', '2018-05-12'),
(N'Xưởng Phụ Tùng Minh Tuấn', N'234 Lạc Long Quân, Q.11, TP.HCM', '02839876543', 'minhtuan@email.com', '0302345678005', N'Hoàng Thị E', '0905000005', N'Phụ tùng', '2019-08-25'),
(N'Dịch Vụ Ô Tô Thành Đạt', N'567 Võ Văn Kiệt, Q.5, TP.HCM', '02838765432', 'thanhdat@email.com', '0303456789006', N'Vũ Văn F', '0906000006', N'Dịch vụ', '2020-02-14');
GO

-- ===============================
-- 4. BẢNG HÃNG XE
-- ===============================
CREATE TABLE HangXe (
    MaHang INT IDENTITY(1,1) PRIMARY KEY,
    TenHang NVARCHAR(50) NOT NULL UNIQUE,
    QuocGia NVARCHAR(50),
    Website NVARCHAR(100),
    MoTa NTEXT
);
GO

-- ===============================
-- DỮ LIỆU BẢNG HÃNG XE
-- ===============================
INSERT INTO HangXe (TenHang, QuocGia, Website, MoTa) VALUES
(N'Toyota', N'Nhật Bản', 'www.toyota.com', N'Thương hiệu ô tô hàng đầu thế giới, nổi tiếng về độ tin cậy và tiết kiệm nhiên liệu'),
(N'Honda', N'Nhật Bản', 'www.honda.com', N'Hãng xe Nhật Bản uy tín với công nghệ động cơ tiên tiến'),
(N'Hyundai', N'Hàn Quốc', 'www.hyundai.com', N'Thương hiệu ô tô Hàn Quốc với thiết kế hiện đại và giá cả hợp lý'),
(N'Ford', N'Mỹ', 'www.ford.com', N'Hãng xe Mỹ lâu đời với các dòng xe bán tải và SUV mạnh mẽ'),
(N'Mazda', N'Nhật Bản', 'www.mazda.com', N'Thương hiệu Nhật Bản nổi tiếng với thiết kế thể thao và công nghệ SKYACTIV'),
(N'Kia', N'Hàn Quốc', 'www.kia.com', N'Hãng xe Hàn Quốc với thiết kế năng động và bảo hành dài hạn');
GO

-- ===============================
-- 5. BẢNG DÒNG XE
-- ===============================
CREATE TABLE DongXe (
    MaDong INT IDENTITY(1,1) PRIMARY KEY,
    MaHang INT,
    TenDong NVARCHAR(100) NOT NULL,
    LoaiXe NVARCHAR(20) CHECK (LoaiXe IN (N'Sedan', N'SUV', N'Hatchback', N'Coupe', N'Pickup', N'Van', N'Khác')),
    SoChoNgoi INT,
    NhienLieu NVARCHAR(20) CHECK (NhienLieu IN (N'Xăng', N'Dầu', N'Hybrid', N'Điện', N'Khác')),
    MoTa NTEXT,
    FOREIGN KEY (MaHang) REFERENCES HangXe(MaHang)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG DÒNG XE
-- ===============================
INSERT INTO DongXe (MaHang, TenDong, LoaiXe, SoChoNgoi, NhienLieu, MoTa) VALUES
(1, N'Camry', N'Sedan', 5, N'Xăng', N'Sedan hạng D cao cấp với thiết kế sang trọng'),
(1, N'Fortuner', N'SUV', 7, N'Dầu', N'SUV 7 chỗ địa hình với khả năng vận hành mạnh mẽ'),
(2, N'Civic', N'Sedan', 5, N'Xăng', N'Sedan hạng C thể thao với công nghệ hiện đại'),
(2, N'CR-V', N'SUV', 5, N'Xăng', N'Crossover 5 chỗ tiện nghi và tiết kiệm nhiên liệu'),
(3, N'Elantra', N'Sedan', 5, N'Xăng', N'Sedan hạng C với thiết kế hiện đại và trang bị phong phú'),
(3, N'Tucson', N'SUV', 5, N'Xăng', N'SUV 5 chỗ với thiết kế năng động và công nghệ an toàn'),
(4, N'Ranger', N'Pickup', 5, N'Dầu', N'Bán tải đa dụng với khả năng chở hàng và địa hình tốt'),
(5, N'CX-5', N'SUV', 5, N'Xăng', N'Crossover 5 chỗ với thiết kế KODO và công nghệ SKYACTIV');
GO

-- ===============================
-- 6. BẢNG XE Ô TÔ
-- ===============================
CREATE TABLE XeOTo (
    MaXe INT IDENTITY(1,1) PRIMARY KEY,
    MaDong INT,
    MaNCC INT,
    TenXe NVARCHAR(100) NOT NULL,
    NamSanXuat INT,
    MauSac NVARCHAR(30),
    SoKhung NVARCHAR(50) UNIQUE,
    SoMay NVARCHAR(50) UNIQUE,
    DungTichDongCo INT, -- cc
    CongSuat INT, -- HP
    HopSo NVARCHAR(20) CHECK (HopSo IN (N'Số sàn', N'Số tự động', N'CVT')),
    KmDaDi INT DEFAULT 0,
    TinhTrang NVARCHAR(20) CHECK (TinhTrang IN (N'Mới', N'Cũ', N'Tân trang')),
    GiaNhap DECIMAL(15,0),
    GiaBan DECIMAL(15,0),
    SoLuongTon INT DEFAULT 1,
    NgayNhap DATE,
    TrangThai NVARCHAR(20) DEFAULT N'Có sẵn' CHECK (TrangThai IN (N'Có sẵn', N'Đã bán', N'Đang sửa chữa', N'Tạm khóa')),
    MoTa NTEXT,
    FOREIGN KEY (MaDong) REFERENCES DongXe(MaDong),
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG XE Ô TÔ
-- ===============================
INSERT INTO XeOTo (MaDong, MaNCC, TenXe, NamSanXuat, MauSac, SoKhung, SoMay, DungTichDongCo, CongSuat, HopSo, KmDaDi, TinhTrang, GiaNhap, GiaBan, SoLuongTon, NgayNhap) VALUES
(1, 1, N'Toyota Camry 2.5Q', 2023, N'Trắng ngọc trai', 'TC2023001', 'TCE2023001', 2494, 209, N'Số tự động', 0, N'Mới', 1200000000, 1350000000, 2, '2023-01-15'),
(1, 1, N'Toyota Camry 2.0G', 2023, N'Đen', 'TC2023002', 'TCE2023002', 1987, 169, N'CVT', 0, N'Mới', 1050000000, 1180000000, 3, '2023-02-20'),
(2, 1, N'Toyota Fortuner 2.7V', 2023, N'Bạc', 'TF2023001', 'TFE2023001', 2694, 166, N'Số tự động', 0, N'Mới', 1400000000, 1550000000, 1, '2023-03-10'),
(3, 2, N'Honda Civic RS', 2023, N'Đỏ', 'HC2023001', 'HCE2023001', 1498, 178, N'CVT', 0, N'Mới', 850000000, 950000000, 2, '2023-01-25'),
(4, 2, N'Honda CR-V L', 2023, N'Xám', 'HR2023001', 'HRE2023001', 1498, 190, N'CVT', 0, N'Mới', 1150000000, 1280000000, 1, '2023-02-14'),
(5, 3, N'Hyundai Elantra 2.0', 2022, N'Trắng', 'HE2022001', 'HEE2022001', 1999, 159, N'CVT', 15000, N'Cũ', 650000000, 750000000, 1, '2023-04-05'),
(6, 3, N'Hyundai Tucson 2.0', 2023, N'Xanh', 'HT2023001', 'HTE2023001', 1999, 156, N'Số tự động', 0, N'Mới', 980000000, 1100000000, 2, '2023-03-20');
GO

-- ===============================
-- 7. BẢNG HÓA ĐƠN BÁN HÀNG
-- ===============================
CREATE TABLE HoaDonBan (
    MaHD INT IDENTITY(1,1) PRIMARY KEY,
    MaKH INT,
    MaNV INT,
    NgayLap DATE NOT NULL,
    TongTien DECIMAL(15,0),
    TienGiam DECIMAL(15,0) DEFAULT 0,
    ThanhTien DECIMAL(15,0),
    TrangThai NVARCHAR(20) DEFAULT N'Chờ xử lý' CHECK (TrangThai IN (N'Chờ xử lý', N'Đã xác nhận', N'Đã giao', N'Đã hủy')),
    GhiChu NTEXT,
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG HÓA ĐƠN BÁN HÀNG
-- ===============================
INSERT INTO HoaDonBan (MaKH, MaNV, NgayLap, TongTien, TienGiam, ThanhTien, TrangThai, GhiChu) VALUES
(1, 3, '2023-05-15', 1350000000, 50000000, 1300000000, N'Đã giao', N'Khách hàng mua trả góp'),
(2, 4, '2023-05-20', 950000000, 0, 950000000, N'Đã giao', N'Thanh toán tiền mặt'),
(3, 3, '2023-06-10', 1550000000, 100000000, 1450000000, N'Đã giao', N'Khách VIP giảm giá'),
(4, 4, '2023-06-25', 1280000000, 30000000, 1250000000, N'Đã xác nhận', N'Chờ giao xe'),
(5, 3, '2023-07-05', 750000000, 20000000, 730000000, N'Đã giao', N'Xe cũ bảo hành 1 năm'),
(6, 4, '2023-07-15', 1100000000, 0, 1100000000, N'Chờ xử lý', N'Đặt cọc trước');
GO

-- ===============================
-- 8. BẢNG CHI TIẾT HÓA ĐƠN
-- ===============================
CREATE TABLE ChiTietHoaDon (
    MaCTHD INT IDENTITY(1,1) PRIMARY KEY,
    MaHD INT,
    MaXe INT,
    SoLuong INT DEFAULT 1,
    DonGia DECIMAL(15,0),
    ThanhTien DECIMAL(15,0),
    FOREIGN KEY (MaHD) REFERENCES HoaDonBan(MaHD),
    FOREIGN KEY (MaXe) REFERENCES XeOTo(MaXe)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG CHI TIẾT HÓA ĐƠN
-- ===============================
INSERT INTO ChiTietHoaDon (MaHD, MaXe, SoLuong, DonGia, ThanhTien) VALUES
(1, 1, 1, 1350000000, 1350000000),
(2, 4, 1, 950000000, 950000000),
(3, 3, 1, 1550000000, 1550000000),
(4, 5, 1, 1280000000, 1280000000),
(5, 6, 1, 750000000, 750000000),
(6, 7, 1, 1100000000, 1100000000);
GO

-- ===============================
-- 9. BẢNG PHƯƠNG THỨC THANH TOÁN
-- ===============================
CREATE TABLE PhuongThucThanhToan (
    MaPTTT INT IDENTITY(1,1) PRIMARY KEY,
    TenPTTT NVARCHAR(50) NOT NULL UNIQUE,
    MoTa NTEXT
);
GO

-- ===============================
-- DỮ LIỆU BẢNG PHƯƠNG THỨC THANH TOÁN
-- ===============================
INSERT INTO PhuongThucThanhToan (TenPTTT, MoTa) VALUES
(N'Tiền mặt', N'Thanh toán bằng tiền mặt tại showroom'),
(N'Chuyển khoản', N'Chuyển khoản qua ngân hàng'),
(N'Thẻ tín dụng', N'Thanh toán qua thẻ tín dụng Visa/MasterCard'),
(N'Trả góp ngân hàng', N'Vay mua xe trả góp qua ngân hàng'),
(N'Trả góp công ty tài chính', N'Vay qua công ty tài chính đối tác'),
(N'Combo tiền mặt + vay', N'Một phần tiền mặt, một phần vay ngân hàng');
GO

-- ===============================
-- 10. BẢNG THANH TOÁN
-- ===============================
CREATE TABLE ThanhToan (
    MaTT INT IDENTITY(1,1) PRIMARY KEY,
    MaHD INT,
    MaPTTT INT,
    SoTien DECIMAL(15,0),
    NgayThanhToan DATE,
    SoTaiKhoan NVARCHAR(50),
    TenNganHang NVARCHAR(100),
    MaGiaoDich NVARCHAR(100),
    TrangThai NVARCHAR(20) DEFAULT N'Chờ xử lý' CHECK (TrangThai IN (N'Thành công', N'Thất bại', N'Chờ xử lý')),
    GhiChu NTEXT,
    FOREIGN KEY (MaHD) REFERENCES HoaDonBan(MaHD),
    FOREIGN KEY (MaPTTT) REFERENCES PhuongThucThanhToan(MaPTTT)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG THANH TOÁN
-- ===============================
INSERT INTO ThanhToan (MaHD, MaPTTT, SoTien, NgayThanhToan, SoTaiKhoan, TenNganHang, MaGiaoDich, TrangThai, GhiChu) VALUES
(1, 4, 1300000000, '2023-05-15', '123456789', N'Vietcombank', 'VCB20230515001', N'Thành công', N'Vay 80%, trả trước 20%'),
(2, 1, 950000000, '2023-05-20', NULL, NULL, NULL, N'Thành công', N'Thanh toán toàn bộ bằng tiền mặt'),
(3, 2, 1450000000, '2023-06-10', '987654321', N'Techcombank', 'TCB20230610001', N'Thành công', N'Chuyển khoản ngay'),
(4, 3, 500000000, '2023-06-25', '555666777', N'BIDV', 'BIDV20230625001', N'Thành công', N'Đặt cọc 500 triệu'),
(5, 1, 730000000, '2023-07-05', NULL, NULL, NULL, N'Thành công', N'Mua xe cũ thanh toán tiền mặt'),
(6, 4, 300000000, '2023-07-15', '111222333', N'ACB Bank', 'ACB20230715001', N'Chờ xử lý', N'Đặt cọc, chờ duyệt vay');
GO

-- ===============================
-- 11. BẢNG LOẠI DỊCH VỤ
-- ===============================
CREATE TABLE LoaiDichVu (
    MaLoaiDV INT IDENTITY(1,1) PRIMARY KEY,
    TenLoaiDV NVARCHAR(100) NOT NULL UNIQUE,
    MoTa NTEXT
);
GO

-- ===============================
-- DỮ LIỆU BẢNG LOẠI DỊCH VỤ
-- ===============================
INSERT INTO LoaiDichVu (TenLoaiDV, MoTa) VALUES
(N'Bảo dưỡng định kỳ', N'Các dịch vụ bảo dưỡng xe theo lịch trình của hãng'),
(N'Sửa chữa chung', N'Sửa chữa các hư hỏng thông thường của xe'),
(N'Thay thế phụ tùng', N'Thay thế các bộ phận hư hỏng'),
(N'Chăm sóc ngoại thất', N'Rửa xe, đánh bóng, phủ ceramic'),
(N'Chăm sóc nội thất', N'Vệ sinh, bảo dưỡng nội thất xe'),
(N'Kiểm tra kỹ thuật', N'Kiểm tra tổng thể tình trạng xe');
GO

-- ===============================
-- 12. BẢNG DỊCH VỤ BẢO TRÌ
-- ===============================
CREATE TABLE DichVuBaoTri (
    MaDV INT IDENTITY(1,1) PRIMARY KEY,
    MaLoaiDV INT,
    TenDichVu NVARCHAR(100) NOT NULL,
    GiaDichVu DECIMAL(10,0),
    ThoiGianThucHien INT, -- phút
    MoTa NTEXT,
    TrangThai NVARCHAR(20) DEFAULT N'Hoạt động' CHECK (TrangThai IN (N'Hoạt động', N'Tạm dừng')),
    FOREIGN KEY (MaLoaiDV) REFERENCES LoaiDichVu(MaLoaiDV)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG DỊCH VỤ BẢO TRÌ
-- ===============================
INSERT INTO DichVuBaoTri (MaLoaiDV, TenDichVu, GiaDichVu, ThoiGianThucHien, MoTa) VALUES
(1, N'Thay dầu động cơ', 500000, 30, N'Thay dầu và lọc dầu động cơ'),
(1, N'Kiểm tra phanh', 300000, 45, N'Kiểm tra và điều chỉnh hệ thống phanh'),
(2, N'Sửa chữa hệ thống điện', 800000, 120, N'Chẩn đoán và sửa chữa các lỗi điện'),
(3, N'Thay lốp xe', 1200000, 60, N'Thay bộ lốp mới cho xe'),
(4, N'Rửa xe cao cấp', 200000, 90, N'Rửa xe, hút bụi, đánh bóng'),
(5, N'Vệ sinh nội thất', 400000, 60, N'Vệ sinh ghế da, thảm lót sàn'),
(6, N'Kiểm tra tổng quát', 150000, 30, N'Kiểm tra các hệ thống cơ bản của xe'),
(2, N'Sửa chữa hộp số', 2500000, 480, N'Sửa chữa và bảo dưỡng hộp số tự động');
GO

-- ===============================
-- 13. BẢNG PHIẾU BẢO TRÌ
-- ===============================
CREATE TABLE PhieuBaoTri (
    MaPhieu INT IDENTITY(1,1) PRIMARY KEY,
    MaXe INT,
    MaKH INT,
    MaNV INT,
    NgayTiepNhan DATE,
    NgayHoanThanh DATE,
    KmHienTai INT,
    VanDe NTEXT,
    TongTien DECIMAL(15,0),
    TrangThai NVARCHAR(20) DEFAULT N'Tiếp nhận' CHECK (TrangThai IN (N'Tiếp nhận', N'Đang sửa', N'Chờ phụ tùng', N'Hoàn thành', N'Đã giao')),
    GhiChu NTEXT,
    FOREIGN KEY (MaXe) REFERENCES XeOTo(MaXe),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO 

-- ===============================
-- DỮ LIỆU BẢNG PHIẾU BẢO TRÌ
-- ===============================
INSERT INTO PhieuBaoTri (MaXe, MaKH, MaNV, NgayTiepNhan, NgayHoanThanh, KmHienTai, VanDe, TongTien, TrangThai, GhiChu) VALUES
(1, 1, 5, '2023-08-15', '2023-08-15', 5000, N'Bảo dưỡng định kỳ 5000km', 800000, N'Hoàn thành', N'Thay dầu và kiểm tra phanh'),
(4, 2, 6, '2023-09-10', NULL, 3000, N'Tiếng ồn từ hệ thống phanh', 300000, N'Đang sửa', N'Đang kiểm tra hệ thống phanh'),
(3, 3, 5, '2023-09-20', '2023-09-22', 8000, N'Thay lốp và bảo dưỡng', 1400000, N'Đã giao', N'Thay bộ lốp mới và rửa xe'),
(6, 4, 6, '2023-10-05', NULL, 12000, N'Xe không nổ được máy', 2500000, N'Chờ phụ tùng', N'Cần thay bộ phận hộp số'),
(5, 5, 5, '2023-10-12', '2023-10-12', 20000, N'Vệ sinh xe và kiểm tra', 750000, N'Hoàn thành', N'Rửa xe cao cấp và vệ sinh nội thất'),
(7, 6, 6, '2023-10-20', NULL, 2000, N'Kiểm tra xe mới mua', 150000, N'Tiếp nhận', N'Kiểm tra tổng quát xe mới');
GO

-- ===============================
-- 14. BẢNG CHI TIẾT BẢO TRÌ
-- ===============================
CREATE TABLE ChiTietBaoTri (
    MaCTBT INT IDENTITY(1,1) PRIMARY KEY,
    MaPhieu INT,
    MaDV INT,
    SoLuong INT DEFAULT 1,
    DonGia DECIMAL(10,0),
    ThanhTien DECIMAL(15,0),
    GhiChu NTEXT,
    FOREIGN KEY (MaPhieu) REFERENCES PhieuBaoTri(MaPhieu),
    FOREIGN KEY (MaDV) REFERENCES DichVuBaoTri(MaDV)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG CHI TIẾT BẢO TRÌ
-- ===============================
INSERT INTO ChiTietBaoTri (MaPhieu, MaDV, SoLuong, DonGia, ThanhTien, GhiChu) VALUES
(1, 1, 1, 500000, 500000, N'Sử dụng dầu tổng hợp cao cấp'),
(1, 2, 1, 300000, 300000, N'Phanh hoạt động bình thường'),
(2, 2, 1, 300000, 300000, N'Đang kiểm tra chi tiết'),
(3, 4, 1, 1200000, 1200000, N'Thay bộ lốp Michelin'),
(3, 5, 1, 200000, 200000, N'Rửa xe sau khi thay lốp'),
(4, 8, 1, 2500000, 2500000, N'Cần đặt phụ tùng từ hãng'),
(5, 5, 1, 200000, 200000, N'Rửa xe cao cấp'),
(5, 6, 1, 400000, 400000, N'Vệ sinh ghế da và thảm'),
(5, 7, 1, 150000, 150000, N'Kiểm tra tổng quát'),
(6, 7, 1, 150000, 150000, N'Kiểm tra xe mới mua');
GO

-- ===============================
-- 15. BẢNG BẢO HÀNH
-- ===============================
CREATE TABLE BaoHanh (
    MaBH INT IDENTITY(1,1) PRIMARY KEY,
    MaXe INT,
    MaKH INT,
    NgayBatDau DATE,
    NgayKetThuc DATE,
    LoaiBaoHanh NVARCHAR(100),
    SoKmBaoHanh INT,
    DieuKien NTEXT,
    TrangThai NVARCHAR(20) DEFAULT N'Còn hiệu lực' CHECK (TrangThai IN (N'Còn hiệu lực', N'Hết hạn', N'Đã sử dụng')),
    FOREIGN KEY (MaXe) REFERENCES XeOTo(MaXe),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG BẢO HÀNH
-- ===============================
INSERT INTO BaoHanh (MaXe, MaKH, NgayBatDau, NgayKetThuc, LoaiBaoHanh, SoKmBaoHanh, DieuKien) VALUES
(1, 1, '2023-05-15', '2026-05-15', N'Bảo hành toàn diện', 100000, N'Bảo hành 3 năm hoặc 100.000km, tùy điều kiện nào đến trước'),
(4, 2, '2023-05-20', '2026-05-20', N'Bảo hành toàn diện', 100000, N'Bảo hành 3 năm hoặc 100.000km theo tiêu chuẩn Honda'),
(3, 3, '2023-06-10', '2028-06-10', N'Bảo hành mở rộng', 150000, N'Bảo hành 5 năm hoặc 150.000km cho khách VIP'),
(5, 4, '2023-06-25', '2026-06-25', N'Bảo hành toàn diện', 100000, N'Bảo hành 3 năm hoặc 100.000km theo tiêu chuẩn Honda'),
(6, 5, '2023-07-05', '2024-07-05', N'Bảo hành xe cũ', 20000, N'Bảo hành 1 năm hoặc 20.000km cho xe cũ'),
(7, 6, '2023-07-15', '2026-07-15', N'Bảo hành toàn diện', 100000, N'Bảo hành 3 năm hoặc 100.000km theo tiêu chuẩn Hyundai');
GO

-- ===============================
-- 16. BẢNG DOANH SỐ NHÂN VIÊN
-- ===============================
CREATE TABLE DoanhSoNhanVien (
    MaDS INT IDENTITY(1,1) PRIMARY KEY,
    MaNV INT,
    Thang INT,
    Nam INT,
    SoXeBan INT DEFAULT 0,
    DoanhThu DECIMAL(15,0) DEFAULT 0,
    HoaHong DECIMAL(12,0) DEFAULT 0,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    CONSTRAINT unique_nv_thang_nam UNIQUE (MaNV, Thang, Nam)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG DOANH SỐ NHÂN VIÊN
-- ===============================
INSERT INTO DoanhSoNhanVien (MaNV, Thang, Nam, SoXeBan, DoanhThu, HoaHong) VALUES
(3, 5, 2023, 2, 2250000000, 45000000),
(4, 5, 2023, 1, 950000000, 19000000),
(3, 6, 2023, 2, 2800000000, 56000000),
(4, 6, 2023, 1, 1250000000, 25000000),
(3, 7, 2023, 1, 730000000, 14600000),
(4, 7, 2023, 1, 1100000000, 22000000),
(3, 8, 2023, 0, 0, 0),
(4, 8, 2023, 0, 0, 0),
(3, 9, 2023, 0, 0, 0),
(4, 9, 2023, 0, 0, 0);
GO