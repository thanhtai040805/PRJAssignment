package userDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.User;

import java.util.List;

public class UserDao extends util.GenericDAO<User, Integer> {

    private EntityManager em;

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public User authenticate(String username, String password) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.status = 'Hoạt động'";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public User findByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("username", username);
        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public User findByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("email", email);
        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<User> findByRole(String role) {
        String jpql = "SELECT u FROM User u WHERE u.role = :role ORDER BY u.createdDate DESC";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("role", role);
        return query.getResultList();
    }

    public boolean changePassword(int maUser, String newPassword) {
        User user = em.find(User.class, maUser);
        if (user != null) {
            user.setPassword(newPassword);
            em.merge(user);
            return true;
        }
        return false;
    }

    public boolean changeStatus(int maUser, String trangThai) {
        User user = em.find(User.class, maUser);
        if (user != null) {
            user.setStatus(trangThai);
            em.merge(user);
            return true;
        }
        return false;
    }

    public boolean isUsernameExists(String username) {
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.username = :username";
        Long count = em.createQuery(jpql, Long.class).setParameter("username", username).getSingleResult();
        return count > 0;
    }

    public boolean isEmailExists(String email) {
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
        Long count = em.createQuery(jpql, Long.class).setParameter("email", email).getSingleResult();
        return count > 0;
    }
}
