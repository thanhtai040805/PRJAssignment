package controller;

import carDao.CarDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Car;
import model.CarModel;
import model.CarBrand;
import model.Supplier;
import supplierDAO.SupplierDAO;

import jakarta.persistence.*;
import java.io.*;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/dashboard/car")
@MultipartConfig
public class CarServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CarServlet.class.getName());
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("PRJPU");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        CarDao carDao = new CarDao();
        carDao.setEntityManager(em);

        String action = req.getParameter("action");
        String view = "/dashboard/carDashboard/viewCars.jsp";

        try {
            switch (action == null ? "" : action) {
                case "edit":
                    Car carToEdit = carDao.findById(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("car", carToEdit);

                    // 👉 Thêm dòng này để load models
                    List<CarModel> modelsEdit = em.createQuery("SELECT m FROM CarModel m", CarModel.class).getResultList();
                    req.setAttribute("models", modelsEdit);

                    // 👉 Và load suppliers
                    SupplierDAO supplierDAOEdit = new SupplierDAO();
                    supplierDAOEdit.setEntityManager(em);
                    List<Supplier> suppliersEdit = supplierDAOEdit.getAll();
                    req.setAttribute("suppliers", suppliersEdit);

                    view = "/dashboard/carDashboard/editCar.jsp";
                    break;
                case "insert":
                    List<CarModel> models = em.createQuery("SELECT m FROM CarModel m", CarModel.class).getResultList();
                    req.setAttribute("models", models);

                    SupplierDAO supplierDAO = new SupplierDAO();
                    supplierDAO.setEntityManager(em);
                    List<Supplier> suppliers = supplierDAO.getAll();
                    req.setAttribute("suppliers", suppliers);

                    view = "/dashboard/carDashboard/insertCar.jsp";
                    break;
                default:
                    String search = req.getParameter("search");
                    String sort = req.getParameter("sort");
                    req.setAttribute("search", search);
                    req.setAttribute("sort", sort);

                    List<Car> cars = carDao.getAllCarsAvailable();

                    // Tìm kiếm theo tên xe
                    if (search != null && !search.trim().isEmpty()) {
                        String keyword = search.trim().toLowerCase();
                        cars = cars.stream()
                                .filter(c -> c.getCarName() != null && c.getCarName().toLowerCase().contains(keyword))
                                .collect(Collectors.toList());
                    }

                    // Sắp xếp
                    if (sort != null) {
                        switch (sort) {
                            case "key-asc":
                                cars.sort(Comparator.comparing(Car::getGlobalKey, Comparator.nullsLast(String::compareToIgnoreCase)));
                                break;
                            case "key-desc":
                                cars.sort(Comparator.comparing(Car::getGlobalKey, Comparator.nullsLast(String::compareToIgnoreCase)).reversed());
                                break;
                            case "price-asc":
                                cars.sort(Comparator.comparing(Car::getSalePrice));
                                break;
                            case "price-desc":
                                cars.sort(Comparator.comparing(Car::getSalePrice).reversed());
                                break;
                        }
                    }

                    req.setAttribute("cars", cars);
                    break;
            }
            req.getRequestDispatcher(view).forward(req, resp);
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("======== VÀO doPost() ========");
        String action = req.getParameter("action");
        LOGGER.info("POST action: " + action);

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction(); // lấy transaction
        CarDao carDao = new CarDao();
        carDao.setEntityManager(em);

        try {
            tx.begin(); // 👉 MỞ TRANSACTION TRƯỚC
            switch (action) {
                case "insert":
                    Car newCar = getCarFromInsertForm(req, em);
                    carDao.add(newCar);
                    break;
                case "update":
                    Car updatedCar = getCarFromUpdateForm(req, em);
                    carDao.update(updatedCar);
                    break;
                case "delete":
                    String idStr = req.getParameter("id");
                    if (idStr != null && !idStr.isEmpty()) {
                        int id = Integer.parseInt(idStr);
                        boolean result = carDao.remove(id);
                        LOGGER.info("🗑 Xoá xe có ID: " + id + " => " + (result ? "THÀNH CÔNG" : "THẤT BẠI"));
                    } else {
                        LOGGER.warning("⚠ Không có ID để xoá.");
                    }
                    break;
                default:
                    LOGGER.warning("⚠ Không xác định action: " + action);
                    break;
            }
            tx.commit(); // 👉 CHỐT TRANSACTION SAU KHI CRUD XONG
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "❌ Lỗi xử lý action = " + action, ex);
            if (tx.isActive()) {
                tx.rollback(); // 👉 ROLLBACK nếu có lỗi
            }
        } finally {
            em.close();
        }

        resp.sendRedirect(req.getContextPath() + "/dashboard/car");
    }

    private Car getCarFromInsertForm(HttpServletRequest req, EntityManager em) throws IOException, ServletException {
        Car car = new Car();

        // Lấy model từ CarModel
        int modelId = Integer.parseInt(req.getParameter("modelId"));
        CarModel carModel = em.find(CarModel.class, modelId);
        car.setCarModel(carModel);

        // ✅ Lấy nhà cung cấp từ supplierId
        int supplierId = Integer.parseInt(req.getParameter("supplierId"));
        Supplier supplier = em.find(Supplier.class, supplierId);
        car.setSupplier(supplier);

        car.setCarName(req.getParameter("carName"));
        car.setColor(req.getParameter("color"));
        car.setYear(Integer.parseInt(req.getParameter("year")));
        car.setStockQuantity(Integer.parseInt(req.getParameter("stockQuantity")));
        car.setGlobalKey(req.getParameter("globalKey"));
        car.setStatus(req.getParameter("status"));
        car.setCondition(req.getParameter("condition"));
        car.setChassisNumber(req.getParameter("chassisNumber"));
        car.setEngineNumber(req.getParameter("engineNumber"));
        car.setImportDate(new Date());

        car.setSalePrice(Long.parseLong(req.getParameter("price").replace(",", "").trim()));

        String importPriceStr = req.getParameter("importPrice");
        if (importPriceStr != null && !importPriceStr.isBlank()) {
            car.setImportPrice(Long.parseLong(importPriceStr.trim()));
        }

        String engineCapStr = req.getParameter("engineCapacity");
        if (engineCapStr != null && !engineCapStr.isBlank()) {
            car.setEngineCapacity(Integer.parseInt(engineCapStr.trim()));
        }

        String powerStr = req.getParameter("power");
        if (powerStr != null && !powerStr.isBlank()) {
            car.setPower(Integer.parseInt(powerStr.trim()));
        }

        String transmission = req.getParameter("transmission");
        if (transmission != null && !transmission.isBlank()) {
            car.setTransmission(transmission.trim());
        }

        String description = req.getParameter("description");
        if (description != null) {
            car.setDescription(description.trim());
        }

        if ("Cũ".equals(car.getCondition())) {
            String kmValue = req.getParameter("kmDaDi");
            if (kmValue == null || kmValue.trim().isEmpty()) {
                throw new IllegalArgumentException("Phải nhập số Km đã đi cho xe cũ.");
            }
            car.setMileage(Integer.parseInt(kmValue));
        } else {
            car.setMileage(0);
        }

        // Ảnh
        Part imagePart = req.getPart("carImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + imagePart.getSubmittedFileName();
            String uploadPath = req.getServletContext().getRealPath("/CarImage");

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            imagePart.write(uploadPath + File.separator + fileName);
            car.setImageLink("/CarImage/" + fileName);
        }

        return car;
    }

    private Car getCarFromUpdateForm(HttpServletRequest req, EntityManager em) throws IOException, ServletException {
        int carId = Integer.parseInt(req.getParameter("id"));
        CarDao carDao = new CarDao();
        carDao.setEntityManager(em);
        Car car = carDao.findById(carId);

        if (car == null) {
            throw new IllegalArgumentException("Xe không tồn tại với ID: " + carId);
        }

        // ✅ Cập nhật Supplier (nếu cho phép chỉnh sửa nhà cung cấp)
        int supplierId = Integer.parseInt(req.getParameter("supplierId"));
        Supplier supplier = em.find(Supplier.class, supplierId);
        car.setSupplier(supplier);

        car.setCarName(req.getParameter("carName"));
        car.setColor(req.getParameter("color"));
        car.setYear(Integer.parseInt(req.getParameter("year")));
        car.setStockQuantity(Integer.parseInt(req.getParameter("stockQuantity")));
        car.setGlobalKey(req.getParameter("globalKey"));
        car.setStatus(req.getParameter("status"));
        car.setCondition(req.getParameter("condition"));
        car.setImportDate(new Date());

        car.setSalePrice(Long.parseLong(req.getParameter("price").replace(",", "").trim()));

        String importPriceStr = req.getParameter("importPrice");
        if (importPriceStr != null && !importPriceStr.isBlank()) {
            car.setImportPrice(Long.parseLong(importPriceStr.trim()));
        }

        String engineCapStr = req.getParameter("engineCapacity");
        if (engineCapStr != null && !engineCapStr.isBlank()) {
            car.setEngineCapacity(Integer.parseInt(engineCapStr.trim()));
        }

        String powerStr = req.getParameter("power");
        if (powerStr != null && !powerStr.isBlank()) {
            car.setPower(Integer.parseInt(powerStr.trim()));
        }

        String transmission = req.getParameter("transmission");
        if (transmission != null && !transmission.isBlank()) {
            car.setTransmission(transmission.trim());
        }

        String description = req.getParameter("description");
        if (description != null) {
            car.setDescription(description.trim());
        }

        if ("Cũ".equals(car.getCondition())) {
            String kmValue = req.getParameter("kmDaDi");
            if (kmValue == null || kmValue.trim().isEmpty()) {
                throw new IllegalArgumentException("Phải nhập số Km đã đi cho xe cũ.");
            }
            car.setMileage(Integer.parseInt(kmValue));
        } else {
            car.setMileage(0);
        }

        Part imagePart = req.getPart("carImage");
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + imagePart.getSubmittedFileName();
            String uploadPath = req.getServletContext().getRealPath("/CarImage");

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            imagePart.write(uploadPath + File.separator + fileName);
            car.setImageLink("/CarImage/" + fileName);
        } else {
            String oldImageLink = req.getParameter("oldImageLink");
            car.setImageLink(oldImageLink);
        }

        return car;
    }
}
