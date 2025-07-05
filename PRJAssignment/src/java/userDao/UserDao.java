package userDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.User;
import util.GenericDAO; // Giữ nguyên GenericDAO nếu bạn vẫn dùng
import java.util.List;
// import org.mindrot.jbcrypt.BCrypt; // <-- Bỏ import này

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao extends GenericDAO<User, Integer> {

    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    private EntityManager em;

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public User authenticate(String username, String plaintextPassword) {
        if (em == null) {
            LOGGER.log(Level.SEVERE, "EntityManager is null in UserDao.authenticate. Cannot proceed.");
            throw new IllegalStateException("EntityManager has not been set for UserDao.");
        }

        LOGGER.info("Attempting to authenticate user: " + username);

        // THAY ĐỔI JPQL TẠI ĐÂY: Biến 'Hoạt động' thành một tham số có tên ':statusActive'
        String jpql = "SELECT u FROM User u WHERE u.username = :username AND u.status = :statusActive";
        TypedQuery<User> query = em.createQuery(jpql, User.class);

        query.setParameter("username", username);
        // THÊM DÒNG NÀY: Gán giá trị cho tham số ':statusActive'
        query.setParameter("statusActive", "Hoạt động"); // Giá trị này phải khớp chính xác với DB (chữ hoa/thường, dấu)

        try {
            User user = query.getSingleResult();
            LOGGER.info("User found in DB: " + user.getUsername() + ", Role: " + user.getRole() + ", Status: " + user.getStatus());
            LOGGER.info("Password from DB: " + user.getPassword());
            LOGGER.info("Plaintext password received for check: " + plaintextPassword);

            if (plaintextPassword.equals(user.getPassword())) {
                LOGGER.info("Plaintext password match. Authentication successful for user: " + username);
                return user;
            } else {
                LOGGER.warning("Plaintext password mismatch for user: " + username);
                return null;
            }
        } catch (NoResultException e) {
            // Log message vẫn có thể hiển thị 'Ho?t ??ng' tùy thuộc vào console encoding,
            // nhưng lỗi gốc từ DB sẽ được giải quyết.
            LOGGER.warning("No user found with username '" + username + "' or status is not 'Hoạt động'.");
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred during authentication for user: " + username, e);
            throw new RuntimeException("Error during authentication", e);
        }
    }

    public User findByUsername(String username) {
        if (em == null) {
            LOGGER.log(Level.SEVERE, "EntityManager is null in UserDao.findByUsername. Cannot proceed.");
            throw new IllegalStateException("EntityManager has not been set for UserDao.");
        }
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findByRole(String role) {
        if (em == null) {
            LOGGER.log(Level.SEVERE, "EntityManager is null in UserDao.findByRole. Cannot proceed.");
            throw new IllegalStateException("EntityManager has not been set for UserDao.");
        }
        String jpql = "SELECT u FROM User u WHERE u.role = :role";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("role", role);
        return query.getResultList();
    }

    public boolean changePassword(int userId, String newPlaintextPassword) {
        if (em == null) {
            LOGGER.log(Level.SEVERE, "EntityManager is null in UserDao.changePassword. Cannot proceed.");
            throw new IllegalStateException("EntityManager has not been set for UserDao.");
        }
        User user = em.find(User.class, userId);
        if (user != null) {
            user.setPassword(newPlaintextPassword); // <-- Lưu plaintext password mới
            em.merge(user);
            return true;
        }
        return false;
    }

    public boolean changeStatus(int userId, String trangThai) {
        if (em == null) {
            LOGGER.log(Level.SEVERE, "EntityManager is null in UserDao.changeStatus. Cannot proceed.");
            throw new IllegalStateException("EntityManager has not been set for UserDao.");
        }
        User user = em.find(User.class, userId);
        if (user != null) {
            user.setStatus(trangThai);
            em.merge(user);
            return true;
        }
        return false;
    }

    public boolean isUsernameExists(String username) {
        if (em == null) {
            LOGGER.log(Level.SEVERE, "EntityManager is null in UserDao.isUsernameExists. Cannot proceed.");
            throw new IllegalStateException("EntityManager has not been set for UserDao.");
        }
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.username = :username";
        Long count = em.createQuery(jpql, Long.class).setParameter("username", username).getSingleResult();
        return count > 0;
    }

    /**
     * Hàm main để kiểm tra độc lập chức năng của UserDao.authenticate. CHỈ SỬ
     * DỤNG CHO MỤC ĐÍCH KIỂM THỬ VÀ GỠ LỖI. HÃY XÓA HOẶC VÔ HIỆU HÓA TRƯỚC KHI
     * TRIỂN KHAI SẢN PHẨM.
     */
}
