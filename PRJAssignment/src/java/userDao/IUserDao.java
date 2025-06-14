package userDao;

import model.User;
import java.util.List;

public interface IUserDao {
    
    /**
     * Xác thực người dùng
     * @param username Tên đăng nhập
     * @param password Mật khẩu
     * @return User object nếu đăng nhập thành công, null nếu thất bại
     */
    User authenticate(String username, String password);
    
    /**
     * Tìm người dùng theo username
     * @param username Tên đăng nhập
     * @return User object hoặc null
     */
    User findByUsername(String username);
    
    /**
     * Tìm người dùng theo ID
     * @param maUser ID người dùng
     * @return User object hoặc null
     */
    User findById(int maUser);
    
    /**
     * Tìm người dùng theo email
     * @param email Email người dùng
     * @return User object hoặc null
     */
    User findByEmail(String email);
    
    /**
     * Lấy danh sách tất cả người dùng
     * @return List<User>
     */
    List<User> findAll();
    
    /**
     * Lấy danh sách người dùng theo role
     * @param role Role của người dùng
     * @return List<User>
     */
    List<User> findByRole(String role);
    
    /**
     * Thêm người dùng mới
     * @param user User object
     * @return true nếu thành công, false nếu thất bại
     */
    boolean insert(User user);
    
    /**
     * Cập nhật thông tin người dùng
     * @param user User object
     * @return true nếu thành công, false nếu thất bại
     */
    boolean update(User user);
    
    /**
     * Xóa người dùng (soft delete - chuyển trạng thái)
     * @param maUser ID người dùng
     * @return true nếu thành công, false nếu thất bại
     */
    boolean delete(int maUser);
    
    /**
     * Thay đổi mật khẩu
     * @param maUser ID người dùng
     * @param newPassword Mật khẩu mới
     * @return true nếu thành công, false nếu thất bại
     */
    boolean changePassword(int maUser, String newPassword);
    
    /**
     * Thay đổi trạng thái người dùng
     * @param maUser ID người dùng
     * @param trangThai Trạng thái mới
     * @return true nếu thành công, false nếu thất bại
     */
    boolean changeStatus(int maUser, String trangThai);
    
    /**
     * Kiểm tra username đã tồn tại
     * @param username Tên đăng nhập
     * @return true nếu đã tồn tại, false nếu chưa
     */
    boolean isUsernameExists(String username);
    
    /**
     * Kiểm tra email đã tồn tại
     * @param email Email
     * @return true nếu đã tồn tại, false nếu chưa
     */
    boolean isEmailExists(String email);
}
