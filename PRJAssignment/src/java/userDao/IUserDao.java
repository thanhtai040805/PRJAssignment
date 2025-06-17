package userDao;

import model.User;
import java.util.List;

public interface IUserDao {

    User authenticate(String username, String password);

    User findByUsername(String username);

    User findById(int maUser);

    User findByEmail(String email);

    List<User> findAll();

    List<User> findByRole(String role);

    boolean insert(User user);

    boolean update(User user);

    boolean delete(int maUser);

    boolean changePassword(int maUser, String newPassword);

    boolean changeStatus(int maUser, String trangThai);

    boolean isUsernameExists(String username);

    boolean isEmailExists(String email);
}
