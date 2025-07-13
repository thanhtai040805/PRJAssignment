CREATE DATABASE CarShopDB;
GO

USE CarShopDB;
GO


ALTER DATABASE CarShopDB
SET SINGLE_USER
WITH ROLLBACK IMMEDIATE;

DROP DATABASE CarShopDB;

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
(N'Vũ Thị Phượng', '1987-09-14', N'Nữ', N'987 CMT8, Q.10, TP.HCM', '0956789012', 'vuthiphuong@email.com', '079087006789', N'Luật sư', 40000000),
(N'Ngô Minh Quân', '1991-01-12', N'Nam', N'11A Trường Chinh, Q.Tân Bình', '0900000007', 'ngominhquan7@email.com', '079081007890', N'Nhân viên ngân hàng', 20000000),
(N'Huỳnh Thị Hòa', '1993-06-20', N'Nữ', N'22B Phan Đình Phùng, Q.Phú Nhuận', '0900000008', 'huynhthihoa8@email.com', '079082008901', N'Nhân viên bảo hiểm', 17000000),
(N'Phạm Văn Hậu', '1986-04-05', N'Nam', N'33C Điện Biên Phủ, Q.Bình Thạnh', '0900000009', 'phamvanhau9@email.com', '079083009012', N'Kỹ sư điện', 24000000),
(N'Đỗ Thị Nga', '1994-08-17', N'Nữ', N'44D Hoàng Sa, Q.3', '0900000010', 'dothinga10@email.com', '079084010123', N'Kinh doanh tự do', 22000000),
(N'Trần Văn Lộc', '1983-02-11', N'Nam', N'55E Nguyễn Văn Trỗi, Q.Phú Nhuận', '0900000011', 'tranvanloc11@email.com', '079085011234', N'Kỹ sư cơ khí', 26000000),
(N'Lê Thị Kim', '1990-09-09', N'Nữ', N'66F Trần Hưng Đạo, Q.1', '0900000012', 'lethikim12@email.com', '079086012345', N'Bác sĩ nha khoa', 32000000),
(N'Nguyễn Văn Quý', '1987-12-19', N'Nam', N'77G Lê Quang Định, Q.Bình Thạnh', '0900000013', 'nguyenvanquy13@email.com', '079087013456', N'Nhà báo', 21000000),
(N'Thái Thị Minh', '1995-05-15', N'Nữ', N'88H Cộng Hòa, Q.Tân Bình', '0900000014', 'thaithiminh14@email.com', '079088014567', N'Kế toán', 19000000),
(N'Hoàng Văn Tú', '1991-03-03', N'Nam', N'99I Nguyễn Trãi, Q.5', '0900000015', 'hoangvantu15@email.com', '079089015678', N'Tài xế', 15000000),
(N'Trương Thị Mai', '1992-11-22', N'Nữ', N'100J 3 Tháng 2, Q.10', '0900000016', 'truongthimai16@email.com', '079090016789', N'Nhân sự', 18000000),
(N'Võ Minh Hùng', '1984-06-01', N'Nam', N'101K Nguyễn Oanh, Gò Vấp', '0900000017', 'vominhhung17@email.com', '079091017890', N'Bán hàng', 16000000),
(N'Lý Thị Thảo', '1996-07-07', N'Nữ', N'102L Dương Bá Trạc, Q.8', '0900000018', 'lythithao18@email.com', '079092018901', N'Chuyên viên pháp lý', 28000000),
(N'Bùi Văn Phát', '1989-10-20', N'Nam', N'103M Hồng Bàng, Q.6', '0900000019', 'buivanphat19@email.com', '079093019012', N'Công nhân', 14000000),
(N'Tăng Thị Hiền', '1993-01-30', N'Nữ', N'104N An Dương Vương, Q.5', '0900000020', 'tangthihien20@email.com', '079094020123', N'Chuyên viên tuyển dụng', 20000000),
(N'Hà Văn Hưng', '1990-02-15', N'Nam', N'105O Lê Văn Sỹ, Q.Phú Nhuận', '0900000021', 'havanhung21@email.com', '079095021234', N'Bác sĩ', 33000000),
(N'Mai Thị Hạnh', '1991-09-23', N'Nữ', N'106P Bạch Đằng, Q.Bình Thạnh', '0900000022', 'maithihanh22@email.com', '079096022345', N'Luật sư', 41000000),
(N'Lâm Văn Tiến', '1985-12-12', N'Nam', N'107Q Xô Viết Nghệ Tĩnh, Q.BT', '0900000023', 'lamvantien23@email.com', '079097023456', N'Thợ sửa xe', 15000000),
(N'Tô Thị Ngọc', '1994-04-18', N'Nữ', N'108R Nguyễn Đình Chiểu, Q.3', '0900000024', 'tothingoc24@email.com', '079098024567', N'Thiết kế đồ họa', 27000000),
(N'Nguyễn Hoàng Long', '1986-08-08', N'Nam', N'109S Bùi Thị Xuân, Q.1', '0900000025', 'nguyenhoanglong25@email.com', '079099025678', N'IT Helpdesk', 23000000),
(N'Phan Thị Lan', '1995-05-25', N'Nữ', N'110T Hoàng Diệu, Q.4', '0900000026', 'phanthilan26@email.com', '079090026789', N'Kế toán', 18000000),
(N'Huỳnh Văn Đạt', '1992-03-12', N'Nam', N'111U Nam Kỳ Khởi Nghĩa, Q.3', '0900000027', 'huynhvandat27@email.com', '079091027890', N'Ngân hàng', 29000000),
(N'Đinh Thị Bích', '1987-07-27', N'Nữ', N'112V Lê Văn Lương, Q.7', '0900000028', 'dinhthibich28@email.com', '079092028901', N'Bán hàng', 16000000),
(N'Lương Minh Trí', '1988-11-11', N'Nam', N'113W Trần Não, Q.2', '0900000029', 'luongminhtri29@email.com', '079093029012', N'Thầy giáo', 21000000),
(N'Trịnh Thị Thu', '1993-10-01', N'Nữ', N'114X Nguyễn Xiển, Q.9', '0900000030', 'trinhthithu30@email.com', '079094030123', N'Sales', 17000000),
(N'Tạ Văn Đức', '1984-04-24', N'Nam', N'115Y Lê Văn Việt, Thủ Đức', '0900000031', 'tavanduc31@email.com', '079095031234', N'IT', 24000000),
(N'Đào Thị Xuân', '1996-02-14', N'Nữ', N'116Z Nguyễn Thị Minh Khai, Q.1', '0900000032', 'daothixuan32@email.com', '079096032345', N'Nhân sự', 22000000),
(N'Hoàng Văn Hào', '1991-06-06', N'Nam', N'117A Võ Thị Sáu, Q.3', '0900000033', 'hoangvanhao33@email.com', '079097033456', N'Chạy xe công nghệ', 15000000),
(N'Cao Thị Diễm', '1989-08-29', N'Nữ', N'118B Điện Biên Phủ, Q.BT', '0900000034', 'caothidiem34@email.com', '079098034567', N'Quản trị viên', 25000000),
(N'Phùng Văn Khoa', '1983-01-18', N'Nam', N'119C Lý Chính Thắng, Q.3', '0900000035', 'phungvankhoa35@email.com', '079099035678', N'Bảo vệ', 14000000),
(N'Trương Mỹ Linh', '1990-09-30', N'Nữ', N'120D Lý Thường Kiệt, Q.10', '0900000036', 'truongmylinh36@email.com', '079090036789', N'Người mẫu', 60000000),
(N'Đỗ Văn Minh', '1986-10-16', N'Nam', N'121E Nguyễn Văn Cừ, Q.5', '0900000037', 'dovanminh37@email.com', '079091037890', N'Nhạc sĩ', 38000000),
(N'Lê Thị Sen', '1995-01-04', N'Nữ', N'122F An Dương Vương, Q.6', '0900000038', 'lethisen38@email.com', '079092038901', N'Thủ thư', 15000000),
(N'Nguyễn Hoài Nam', '1988-03-28', N'Nam', N'123G Phạm Ngũ Lão, Q.1', '0900000039', 'nguyenhoainam39@email.com', '079093039012', N'Chủ quán ăn', 35000000),
(N'Võ Thị Hạnh', '1993-12-01', N'Nữ', N'124H Nguyễn Văn Đậu, Q.BT', '0900000040', 'vothihanh40@email.com', '079094040123', N'Bếp trưởng', 27000000);

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
(N'Công ty Xe Mới Kim Lộc', N'789 Quốc lộ 1B, Thanh Khê, TP.Đà Nẵng', '02838654321', 'kimloc@email.com', '0307654321004', N'Phạm Văn E', '0905000005', N'Xe mới', '2018-05-12'),
(N'Công ty Xe Tuệ Tài', N'789 Quốc lộ 1C, Thanh Xuân, TP.Hà Nội', '02838321456', 'taitue@email.com', '0301298767004', N'Bùi Văn F', '0904333004', N'Xe cũ', '2018-05-12');

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
(N'Honda', N'Nhật Bản', 'www.h	onda.com', N'Hãng xe Nhật Bản uy tín với công nghệ động cơ tiên tiến'),
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
(5, N'CX-5', N'SUV', 5, N'Xăng', N'Crossover 5 chỗ với thiết kế KODO và công nghệ SKYACTIV'),
(6, N'Seltos', N'SUV', 5, N'Xăng', N'SUV cỡ B với thiết kế trẻ trung và hiện đại'),
(6, N'Sorento', N'SUV', 7, N'Dầu', N'SUV 7 chỗ cao cấp với nhiều tính năng an toàn'),
(4, N'Everest', N'SUV', 7, N'Dầu', N'SUV 7 chỗ địa hình mạnh mẽ'),
(1, N'Xpander', N'Van', 7, N'Xăng', N'MPV 7 chỗ linh hoạt cho gia đình'),
(1, N'Outlander', N'SUV', 7, N'Xăng', N'SUV 7 chỗ với thiết kế thể thao'),
(1, N'XL7', N'SUV', 7, N'Xăng', N'SUV 7 chỗ giá rẻ cho thị trường Việt Nam'),
(1, N'VinFast Lux A', N'Sedan', 5, N'Xăng', N'Sedan hạng D của VinFast'),
(1, N'VinFast Fadil', N'Hatchback', 5, N'Xăng', N'Hatchback cỡ A của VinFast'),
(1, N'BMW 3 Series', N'Sedan', 5, N'Xăng', N'Sedan hạng sang của BMW'),
(1, N'Mercedes C-Class', N'Sedan', 5, N'Xăng', N'Sedan hạng sang của Mercedes-Benz'),
(1, N'Audi Q5', N'SUV', 5, N'Xăng', N'SUV hạng sang của Audi'),
(1, N'Lexus RX', N'SUV', 5, N'Hybrid', N'SUV hạng sang hybrid của Lexus'),
(1, N'Peugeot 3008', N'SUV', 5, N'Xăng', N'SUV Pháp với thiết kế độc đáo'),
(1, N'Volkswagen Tiguan', N'SUV', 5, N'Xăng', N'SUV Đức với chất lượng cao'),
(1, N'Subaru Forester', N'SUV', 5, N'Xăng', N'SUV với hệ dẫn động 4 bánh toàn thời gian'),
(1, N'MG HS', N'SUV', 5, N'Xăng', N'SUV Anh với giá cả hợp lý'),
(1, N'Changan CS75', N'SUV', 5, N'Xăng', N'SUV Trung Quốc với trang bị hiện đại'),
(1, N'Volvo XC60', N'SUV', 5, N'Xăng', N'SUV hạng sang Thụy Điển an toàn'),
(1, N'Tesla Model Y', N'SUV', 5, N'Điện', N'SUV điện cao cấp của Tesla'),
(1, N'Mercedes GLC', N'SUV', 5, N'Xăng', N'SUV hạng sang cỡ trung của Mercedes');
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
    DungTichDongCo INT,
    CongSuat INT,
    HopSo NVARCHAR(20) CHECK (HopSo IN (N'Số sàn', N'Số tự động', N'CVT')),
    KmDaDi INT DEFAULT 0,
    TinhTrang NVARCHAR(20) CHECK (TinhTrang IN (N'Mới', N'Cũ', N'Tân trang')),
    GiaNhap DECIMAL(15,0),
    GiaBan DECIMAL(15,0),
    SoLuongTon INT DEFAULT 1,
    NgayNhap DATE,
    TrangThai NVARCHAR(20) DEFAULT N'Có sẵn' CHECK (TrangThai IN (N'Có sẵn', N'Đã bán', N'Đang sửa chữa', N'Tạm khóa')),
    MoTa NTEXT,
    LinkAnh NVARCHAR(255),
    GlobalKey NVARCHAR(100),
    FOREIGN KEY (MaDong) REFERENCES DongXe(MaDong),
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG XE Ô TÔ 
-- ===============================
INSERT INTO XeOTo (MaDong, MaNCC, TenXe, NamSanXuat, MauSac, SoKhung, SoMay, DungTichDongCo, CongSuat, HopSo, KmDaDi, TinhTrang, GiaNhap, GiaBan, SoLuongTon, NgayNhap, LinkAnh, GlobalKey)
VALUES
(1, 1, N'Toyota Camry 2.5Q', 2023, N'Trắng ngọc trai', 'TC2023001', 'TCE2023001', 2494, 209, N'Số tự động', 0, N'Mới', 1200000000, 1350000000, 2, '2023-01-15', N'/CarImage/1.jfif', N'ToyotaCamry2.5Q' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(1, 1, N'Toyota Camry 2.0G', 2023, N'Đen', 'TC2023002', 'TCE2023002', 1987, 169, N'CVT', 0, N'Mới', 1050000000, 1180000000, 3, '2023-02-20', N'/CarImage/2.jfif', N'ToyotaCamry2.0G' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(2, 1, N'Toyota Fortuner 2.7V', 2023, N'Bạc', 'TF2023001', 'TFE2023001', 2694, 166, N'Số tự động', 0, N'Mới', 1400000000, 1550000000, 1, '2023-03-10', N'/CarImage/3.jfif', N'ToyotaFortuner2.7V' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(3, 2, N'Honda Civic RS', 2023, N'Đỏ', 'HC2023001', 'HCE2023001', 1498, 178, N'CVT', 0, N'Mới', 850000000, 950000000, 2, '2023-01-25', N'/CarImage/4.jfif', N'HondaCivicRS' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(4, 2, N'Honda CR-V L', 2023, N'Xám', 'HR2023001', 'HRE2023001', 1498, 190, N'CVT', 0, N'Mới', 1150000000, 1280000000, 1, '2023-02-14', N'/CarImage/5.jfif', N'HondaCRVL' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(5, 3, N'Hyundai Elantra 2.0', 2022, N'Trắng', 'HE2022001', 'HEE2022001', 1999, 159, N'CVT', 15000, N'Cũ', 650000000, 750000000, 1, '2023-04-05', N'/CarImage/6.jfif', N'HyundaiElantra2.0' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(6, 3, N'Hyundai Tucson 2.0', 2023, N'Xanh', 'HT2023001', 'HTE2023001', 1999, 156, N'Số tự động', 0, N'Mới', 980000000, 1100000000, 2, '2023-03-20', N'/CarImage/7.jfif', N'HyundaiTucson2.0' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(8, 5, N'Mazda CX-5 Premium', 2023, N'Đen', 'MCX52023001', 'MCE2023001', 1998, 188, N'Số tự động', 0, N'Mới', 950000000, 1050000000, 2, '2023-04-01', N'/CarImage/8.jfif', N'MazdaCX5Premium' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(5, 3, N'Mazda 3 Sport', 2023, N'Đỏ', 'M32023001', 'M3E2023001', 1496, 110, N'Số sàn', 0, N'Mới', 600000000, 720000000, 3, '2023-03-15', N'/CarImage/9.jfif', N'Mazda3Sport' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(9, 3, N'Kia Seltos 1.4 Turbo', 2023, N'Trắng', 'KS2023001', 'KSE2023001', 1353, 138, N'CVT', 0, N'Mới', 680000000, 770000000, 2, '2023-04-10', N'/CarImage/10.jfif', N'KiaSeltos1.4Turbo' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(10, 3, N'Kia Sorento Signature', 2023, N'Xám', 'KSO2023001', 'KSOE2023001', 2199, 198, N'Số tự động', 0, N'Mới', 1150000000, 1300000000, 1, '2023-04-18', N'/CarImage/11.jfif', N'KiaSorentoSignature' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(7, 4, N'Ford Ranger XLS', 2022, N'Xanh dương', 'FR2022001', 'FRE2022001', 2198, 160, N'Số sàn', 12000, N'Cũ', 700000000, 800000000, 1, '2023-05-05', N'/CarImage/12.jfif', N'FordRangerXLS' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(11, 4, N'Ford Everest Titanium', 2023, N'Trắng', 'FE2023001', 'FEE2023001', 1996, 210, N'Số tự động', 0, N'Mới', 1300000000, 1450000000, 1, '2023-06-01', N'/CarImage/13.jfif', N'FordEverestTitanium' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(12, 4, N'Mitsubishi Xpander Cross', 2023, N'Cam', 'MX2023001', 'MXE2023001', 1499, 105, N'CVT', 0, N'Mới', 650000000, 730000000, 2, '2023-05-12', N'/CarImage/14.jfif', N'MitsubishiXpanderCross' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(13, 4, N'Mitsubishi Outlander', 2022, N'Đen', 'MO2022001', 'MOE2022001', 2360, 165, N'Số tự động', 10000, N'Cũ', 800000000, 890000000, 1, '2023-06-10', N'/CarImage/15.jfif', N'MitsubishiOutlander' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(14, 4, N'Suzuki XL7', 2023, N'Bạc', 'SX2023001', 'SXE2023001', 1462, 103, N'CVT', 0, N'Mới', 620000000, 700000000, 3, '2023-06-20', N'/CarImage/16.jfif', N'SuzukiXL7' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(15, 5, N'VinFast Lux A2.0', 2021, N'Trắng', 'VL2021001', 'VLE2021001', 1998, 174, N'Số tự động', 5000, N'Cũ', 800000000, 880000000, 1, '2023-07-01', N'/CarImage/17.jfif', N'VinFastLuxA2.0' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(16, 5, N'VinFast Fadil Plus', 2022, N'Xanh', 'VF2022001', 'VFE2022001', 1498, 98, N'CVT', 10000, N'Cũ', 400000000, 450000000, 2, '2023-07-10', N'/CarImage/18.jfif', N'VinFastFadilPlus' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(17, 6, N'BMW 320i Sport Line', 2022, N'Đỏ', 'BM3202022001', 'BME3202022001', 1998, 184, N'Số tự động', 7000, N'Cũ', 1500000000, 1650000000, 1, '2023-07-15', N'/CarImage/19.jfif', N'BMW320iSportLine' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(18, 6, N'Mercedes-Benz C200 Avantgarde', 2023, N'Xám', 'MB2023001', 'MBE2023001', 1496, 204, N'Số tự động', 0, N'Mới', 1690000000, 1850000000, 1, '2023-07-20', N'/CarImage/20.jfif', N'MercedesBenzC200Avantgarde' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(19, 1, N'Audi Q5 Sportback', 2023, N'Trắng', 'AQ52023001', 'AQ5E2023001', 1984, 249, N'Số tự động', 0, N'Mới', 2300000000, 2550000000, 1, '2023-07-25', N'/CarImage/21.jfif', N'AudiQ5Sportback' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(20, 1, N'Lexus RX500h F Sport', 2023, N'Đen', 'LRX2023001', 'LRXE2023001', 2393, 366, N'Số tự động', 0, N'Mới', 4300000000, 4700000000, 1, '2023-08-01', N'/CarImage/22.jfif', N'LexusRX500hFSport' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(21, 2, N'Peugeot 3008 Allure', 2023, N'Xám', 'P30082023001', 'P3E2023001', 1598, 165, N'Số tự động', 0, N'Mới', 1120000000, 1250000000, 1, '2023-08-05', N'/CarImage/23.jfif', N'Peugeot3008Allure' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(22, 2, N'Volkswagen Tiguan Elegance', 2022, N'Xanh rêu', 'VT2022001', 'VTE2022001', 1984, 180, N'Số tự động', 5000, N'Cũ', 1700000000, 1850000000, 1, '2023-08-10', N'/CarImage/24.jfif', N'VolkswagenTiguanElegance' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(23, 3, N'Subaru Forester i-S EyeSight', 2023, N'Trắng', 'SF2023001', 'SFE2023001', 1995, 156, N'CVT', 0, N'Mới', 1040000000, 1150000000, 2, '2023-08-15', N'/CarImage/25.jfif', N'SubaruForesteriSEyeSight' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(24, 3, N'MG HS Trophy', 2023, N'Đỏ', 'MGHS2023001', 'MGHSE2023001', 1598, 225, N'Số tự động', 0, N'Mới', 850000000, 950000000, 1, '2023-08-20', N'/CarImage/26.jfif', N'MGHSTrophy' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(25, 4, N'Changan CS75 Plus', 2023, N'Bạc', 'CCS752023001', 'CCS75E2023001', 1499, 177, N'Số tự động', 0, N'Mới', 720000000, 800000000, 1, '2023-08-25', N'/CarImage/27.jfif', N'ChanganCS75Plus' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(26, 4, N'Volvo XC60 B6 AWD', 2023, N'Đen', 'VXC602023001', 'VXC60E2023001', 1969, 300, N'Số tự động', 0, N'Mới', 2800000000, 3050000000, 1, '2023-09-01', N'/CarImage/28.jfif', N'VolvoXC60B6AWD' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(27, 5, N'Tesla Model Y RWD', 2023, N'Trắng', 'TMY2023001', 'TMYE2023001', NULL, 220, N'Số tự động', 0, N'Mới', 1800000000, 1950000000, 1, '2023-09-05', N'/CarImage/29.jfif', N'TeslaModelYRWD' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000')),
(28, 6, N'Mercedes-Benz GLC300 4MATIC', 2023, N'Xám', 'MBGLC2023001', 'MBGLCE2023001', 1991, 258, N'Số tự động', 0, N'Mới', 2600000000, 2800000000, 1, '2023-09-10', N'/CarImage/30.jfif', N'MercedesBenzGLC3004MATIC' + FORMAT(ABS(CHECKSUM(NEWID())) % 100000, '00000'));
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
(6, 4, '2023-07-15', 1100000000, 0, 1100000000, N'Chờ xử lý', N'Đặt cọc trước'),
(9, 4, '2025-01-01', 950000000, 20000000, 930000000, N'Đã giao', N'Mua trả góp'),
(5, 3, '2025-01-05', 1550000000, 50000000, 1500000000, N'Đã hủy', N'Đặt cọc'),
(6, 4, '2025-01-10', 750000000, 0, 750000000, N'Đã hủy', N'Đặt cọc'),
(4, 4, '2025-01-15', 950000000, 100000000, 850000000, N'Đã hủy', N'Mua trả góp'),
(10, 3, '2025-01-20', 950000000, 50000000, 900000000, N'Đã giao', N'Thanh toán tiền mặt'),
(10, 3, '2025-01-25', 1350000000, 0, 1350000000, N'Chờ xử lý', N'Khách VIP'),
(2, 3, '2025-01-30', 950000000, 20000000, 930000000, N'Đã xác nhận', N'Khách VIP'),
(3, 3, '2025-02-04', 1100000000, 0, 1100000000, N'Đã giao', N'Mua trả góp'),
(3, 3, '2025-02-09', 1350000000, 20000000, 1330000000, N'Đã giao', N'Mua trả góp'),
(7, 3, '2025-02-14', 1550000000, 100000000, 1450000000, N'Đã hủy', N'Khách VIP'),
(5, 4, '2025-02-19', 1100000000, 0, 1100000000, N'Đã xác nhận', N'Mua trả góp'),
(2, 3, '2025-02-24', 950000000, 50000000, 900000000, N'Đã giao', N'Thanh toán tiền mặt'),
(4, 4, '2025-03-01', 1350000000, 100000000, 1250000000, N'Đã giao', N'Đặt cọc'),
(7, 3, '2025-03-06', 1100000000, 0, 1100000000, N'Đã xác nhận', N'Thanh toán tiền mặt'),
(8, 4, '2025-03-11', 750000000, 0, 750000000, N'Đã hủy', N'Đặt cọc'),
(3, 4, '2025-03-16', 950000000, 0, 950000000, N'Chờ xử lý', N'Mua trả góp'),
(1, 3, '2025-03-21', 1550000000, 50000000, 1500000000, N'Đã xác nhận', N'Khách VIP'),
(9, 4, '2025-03-26', 1350000000, 100000000, 1250000000, N'Đã giao', N'Khách VIP'),
(8, 3, '2025-03-31', 1100000000, 0, 1100000000, N'Đã giao', N'Thanh toán tiền mặt'),
(6, 3, '2025-04-05', 950000000, 20000000, 930000000, N'Đã giao', N'Khách VIP'),
(4, 4, '2025-04-10', 1550000000, 0, 1550000000, N'Đã giao', N'Mua trả góp'),
(1, 3, '2025-04-15', 1100000000, 100000000, 1000000000, N'Đã giao', N'Đặt cọc'),
(7, 4, '2025-04-20', 1350000000, 20000000, 1330000000, N'Đã giao', N'Mua trả góp'),
(6, 3, '2025-04-25', 750000000, 0, 750000000, N'Đã hủy', N'Thanh toán tiền mặt'),
(5, 4, '2025-04-30', 950000000, 50000000, 900000000, N'Chờ xử lý', N'Mua trả góp'),
(8, 3, '2025-05-05', 1550000000, 100000000, 1450000000, N'Đã giao', N'Khách VIP'),
(1, 3, '2025-05-10', 1350000000, 0, 1350000000, N'Đã xác nhận', N'Mua trả góp'),
(10, 4, '2025-05-15', 1100000000, 0, 1100000000, N'Đã xác nhận', N'Mua trả góp'),
(2, 4, '2025-05-20', 750000000, 0, 750000000, N'Đã xác nhận', N'Khách VIP'),
(2, 3, '2025-05-25', 950000000, 50000000, 900000000, N'Chờ xử lý', N'Khách VIP'),
(7, 3, '2025-05-30', 1100000000, 20000000, 1080000000, N'Đã giao', N'Thanh toán tiền mặt'),
(6, 3, '2025-06-04', 1350000000, 50000000, 1300000000, N'Đã giao', N'Khách VIP'),
(5, 4, '2025-06-09', 750000000, 0, 750000000, N'Đã hủy', N'Mua trả góp'),
(1, 4, '2025-06-14', 950000000, 100000000, 850000000, N'Đã hủy', N'Đặt cọc'),
(9, 3, '2025-06-19', 1550000000, 100000000, 1450000000, N'Đã giao', N'Khách VIP'),
(2, 3, '2025-06-24', 1350000000, 0, 1350000000, N'Đã giao', N'Khách VIP'),
(8, 4, '2025-06-29', 1100000000, 0, 1100000000, N'Chờ xử lý', N'Thanh toán tiền mặt'),
(6, 3, '2025-07-04', 750000000, 0, 750000000, N'Đã hủy', N'Đặt cọc'),
(9, 3, '2025-07-09', 950000000, 50000000, 900000000, N'Đã giao', N'Thanh toán tiền mặt'),
(1, 3, '2025-07-11', 900000000, 0, 900000000, N'Đã giao', N'Hóa đơn tạm'),
(2, 4, '2025-07-12', 1000000000, 0, 1000000000, N'Đã giao', N'Hóa đơn tạm');
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
(6, 7, 1, 1100000000, 1100000000),
(7, 9, 1, 950000000, 950000000),
(8, 1, 1, 1550000000, 1550000000),
(9, 10, 1, 750000000, 750000000),
(10, 1, 1, 950000000, 950000000),
(11, 10, 1, 950000000, 950000000),
(12, 5, 1, 1350000000, 1350000000),
(13, 3, 1, 950000000, 950000000),
(14, 1, 1, 1100000000, 1100000000),
(15, 4, 1, 1350000000, 1350000000),
(16, 1, 1, 1550000000, 1550000000),
(17, 7, 1, 1100000000, 1100000000),
(18, 2, 1, 950000000, 950000000),
(19, 9, 1, 1350000000, 1350000000),
(20, 4, 1, 1100000000, 1100000000),
(21, 3, 1, 750000000, 750000000),
(22, 6, 1, 950000000, 950000000),
(23, 2, 1, 1550000000, 1550000000),
(24, 6, 1, 1350000000, 1350000000),
(25, 8, 1, 1100000000, 1100000000),
(26, 3, 1, 950000000, 950000000),
(27, 2, 1, 1550000000, 1550000000),
(28, 7, 1, 1350000000, 1350000000),
(29, 5, 1, 1100000000, 1100000000),
(30, 6, 1, 750000000, 750000000),
(31, 4, 1, 950000000, 950000000),
(32, 1, 1, 1550000000, 1550000000),
(33, 6, 1, 1350000000, 1350000000),
(34, 3, 1, 1100000000, 1100000000),
(35, 8, 1, 750000000, 750000000),
(36, 10, 1, 950000000, 950000000),
(37, 2, 1, 1350000000, 1350000000),
(38, 4, 1, 750000000, 750000000),
(39, 9, 1, 950000000, 950000000),
(40, 5, 1, 1100000000, 1100000000),
(41, 2, 1, 1350000000, 1350000000),
(42, 7, 1, 750000000, 750000000),
(43, 9, 1, 950000000, 950000000),
(44, 1, 1, 1550000000, 1550000000),
(45, 10, 1, 1350000000, 1350000000),
(46, 3, 1, 750000000, 750000000);
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
(N'Trả góp vay ngân hàng', N'Vay mua xe trả góp qua ngân hàng');
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
(6, 4, 300000000, '2023-07-15', '111222333', N'ACB Bank', 'ACB20230715001', N'Chờ xử lý', N'Đặt cọc, chờ duyệt vay'),
(7, 3, 930000000, '2025-01-01', '473827492', N'ACB', 'GD007', N'Thành công', N'Thanh toán trước'),
(8, 4, 1500000000, '2025-01-05', '193847281', N'Techcombank', 'GD008', N'Thành công', N'Thanh toán sau'),
(9, 1, 750000000, '2025-01-10', NULL, NULL, NULL, N'Thành công', N'Thanh toán trước'),
(10, 2, 850000000, '2025-01-15', '837462918', N'Vietcombank', 'GD010', N'Thành công', N'Thanh toán trước'),
(11, 1, 900000000, '2025-01-20', NULL, NULL, NULL, N'Thành công', N'Thanh toán sau'),
(12, 2, 1350000000, '2025-01-25', '937461029', N'BIDV', 'GD012', N'Chờ xử lý', N'Thanh toán sau'),
(13, 4, 930000000, '2025-01-30', '983726193', N'Vietcombank', 'GD013', N'Thành công', N'Thanh toán sau'),
(14, 4, 1100000000, '2025-02-04', '182736445', N'Techcombank', 'GD014', N'Thành công', N'Thanh toán sau'),
(15, 4, 1330000000, '2025-02-09', '347816234', N'ACB', 'GD015', N'Thành công', N'Thanh toán trước'),
(16, 2, 1450000000, '2025-02-14', '192837465', N'BIDV', 'GD016', N'Chờ xử lý', N'Thanh toán sau'),
(17, 1, 1100000000, '2025-02-19', NULL, NULL, NULL, N'Thành công', N'Thanh toán trước'),
(18, 1, 900000000, '2025-02-24', NULL, NULL, NULL, N'Thành công', N'Thanh toán trước'),
(19, 3, 1250000000, '2025-03-01', '129837465', N'Techcombank', 'GD019', N'Thành công', N'Thanh toán trước'),
(20, 3, 1100000000, '2025-03-06', '182736450', N'BIDV', 'GD020', N'Thành công', N'Thanh toán trước'),
(21, 2, 750000000, '2025-03-11', '384756291', N'ACB', 'GD021', N'Thành công', N'Thanh toán trước'),
(22, 2, 950000000, '2025-03-16', '987654321', N'Vietcombank', 'GD022', N'Chờ xử lý', N'Thanh toán sau'),
(23, 1, 1500000000, '2025-03-21', NULL, NULL, NULL, N'Thành công', N'Thanh toán trước'),
(24, 2, 1250000000, '2025-03-26', '123123123', N'ACB', 'GD024', N'Thành công', N'Thanh toán trước'),
(25, 3, 1100000000, '2025-03-31', '111111111', N'Techcombank', 'GD025', N'Thành công', N'Thanh toán sau'),
(26, 1, 930000000, '2025-04-05', NULL, NULL, NULL, N'Thành công', N'Thanh toán trước'),
(27, 4, 1550000000, '2025-04-10', '444555666', N'BIDV', 'GD027', N'Thành công', N'Thanh toán sau'),
(28, 2, 1000000000, '2025-04-15', '333666999', N'ACB', 'GD028', N'Thành công', N'Thanh toán trước'),
(29, 3, 1330000000, '2025-04-20', '888999111', N'Techcombank', 'GD029', N'Thành công', N'Thanh toán trước'),
(30, 1, 750000000, '2025-04-25', NULL, NULL, NULL, N'Thành công', N'Thanh toán trước'),
(31, 2, 900000000, '2025-04-30', '444888111', N'Vietcombank', 'GD031', N'Chờ xử lý', N'Thanh toán sau'),
(32, 4, 1450000000, '2025-05-05', '999111333', N'BIDV', 'GD032', N'Thành công', N'Thanh toán sau'),
(33, 3, 1350000000, '2025-05-10', '222333444', N'ACB', 'GD033', N'Thành công', N'Thanh toán trước'),
(34, 4, 1100000000, '2025-05-15', '555222111', N'Vietcombank', 'GD034', N'Thành công', N'Thanh toán sau'),
(35, 2, 750000000, '2025-05-20', '111333555', N'Techcombank', 'GD035', N'Thành công', N'Thanh toán sau'),
(36, 2, 900000000, '2025-05-25', '555444222', N'ACB', 'GD036', N'Chờ xử lý', N'Thanh toán trước'),
(37, 4, 1080000000, '2025-05-30', '333777999', N'BIDV', 'GD037', N'Thành công', N'Thanh toán tiền mặt'),
(38, 3, 1300000000, '2025-06-04', '222888333', N'Techcombank', 'GD038', N'Thành công', N'Thanh toán sau'),
(39, 1, 750000000, '2025-06-09', NULL, NULL, NULL, N'Thành công', N'Thanh toán trước'),
(40, 2, 850000000, '2025-06-14', '999000111', N'Vietcombank', 'GD040', N'Thành công', N'Thanh toán trước'),
(41, 4, 1450000000, '2025-06-19', '111999333', N'BIDV', 'GD041', N'Thành công', N'Thanh toán tiền mặt'),
(42, 2, 1350000000, '2025-06-24', '444222666', N'ACB', 'GD042', N'Thành công', N'Thanh toán trước'),
(43, 3, 1100000000, '2025-06-29', '333000999', N'Techcombank', 'GD043', N'Chờ xử lý', N'Thanh toán tiền mặt'),
(44, 1, 750000000, '2025-07-04', NULL, NULL, NULL, N'Thành công', N'Thanh toán sau'),
(45, 4, 900000000, '2025-07-09', '888111000', N'BIDV', 'GD045', N'Thành công', N'Thanh toán trước'),
(46, 3, 900000000, '2025-07-12', '998877665', N'ACB', 'GD046', N'Thành công', N'Thanh toán tiền mặt');
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
-- 12. BẢNG BẢO HÀNH
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
-- 13. BẢNG DOANH SỐ NHÂN VIÊN
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

-- ===============================
-- 14. BẢNG TÀI KHOẢN
-- ===============================
CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL UNIQUE,
    Password NVARCHAR(100) NOT NULL,
    Role NVARCHAR(20) CHECK (Role IN (N'admin', N'nhanvien', N'khachhang')) NOT NULL,
    MaKH INT NULL,
    MaNV INT NULL,
    TrangThai NVARCHAR(20) DEFAULT N'Hoạt động' CHECK (TrangThai IN (N'Hoạt động', N'Tạm khóa')),

    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO

-- ===============================
-- DỮ LIỆU BẢNG TÀI KHOẢN
-- ===============================
INSERT INTO Users (Username, Password, Role)
VALUES
('admin1', '123456', N'admin'),
('admin2', '123456', N'admin');

INSERT INTO Users (Username, Password, Role, MaKH)
VALUES
('nguyenvanan', '123456', N'khachhang', 1),
('tranthibinh', '123456', N'khachhang', 2),
('lehoangcuong', '123456', N'khachhang', 3),
('phamthidung', '123456', N'khachhang', 4),
('hoangvanem', '123456', N'khachhang', 5),
('vuthiphuong', '123456', N'khachhang', 6);

INSERT INTO Users (Username, Password, Role, MaNV)
VALUES
('levanmanh', '123456', N'nhanvien', 1),
('nguyenthilinh', '123456', N'nhanvien', 2),
('tranvanson', '123456', N'nhanvien', 3),
('phanthihoa', '123456', N'nhanvien', 4),
('dangvantuan', '123456', N'nhanvien', 5),
('buithimai', '123456', N'nhanvien', 6);

-- Xe yêu thích
CREATE TABLE FavoriteCars (
    UserID INT NOT NULL,
    GlobalKey NVARCHAR(100) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
	PRIMARY KEY (UserID, GlobalKey)
);

-- Lịch sử trang đã truy cập (theo path)
CREATE TABLE PageHistory (
    UserID INT NOT NULL,
    Path NVARCHAR(255) NOT NULL,
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (UserID, Path),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);


-- Lịch sử xe đã xem (theo GlobalKey)
CREATE TABLE ViewedCars (
    UserID INT NOT NULL,
    GlobalKey NVARCHAR(100) NOT NULL,
    ViewedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (UserID, GlobalKey),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);


CREATE TABLE ConversationHistory (
    HistoryID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    MessageRole NVARCHAR(20) NOT NULL CHECK (MessageRole IN (N'user', N'assistant')),
    MessageContent NVARCHAR(MAX) NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    SessionID NVARCHAR(100) NULL,

    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
GO

