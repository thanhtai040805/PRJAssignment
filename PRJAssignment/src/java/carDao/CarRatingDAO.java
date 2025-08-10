package carDao;

import jakarta.persistence.TypedQuery;
import model.CarRating;
import util.GenericDAO;
import java.util.List;

public class CarRatingDAO extends GenericDAO<CarRating, Long> {

    @Override
    protected Class<CarRating> getEntityClass() {
        return CarRating.class;
    }

    /**
     * Thêm mới hoặc cập nhật điểm đánh giá (rating) của user cho 1 xe
     */
    public void addOrUpdateRating(CarRating ratingObj) {
        String jpql = "SELECT r FROM CarRating r WHERE r.globalKey = :gk AND r.userID = :uid";
        List<CarRating> existing = em.createQuery(jpql, CarRating.class)
                .setParameter("gk", ratingObj.getGlobalKey())
                .setParameter("uid", ratingObj.getUserID())
                .getResultList();

        if (existing.isEmpty()) {
            em.persist(ratingObj);
        } else {
            CarRating old = existing.get(0);
            old.setRating(ratingObj.getRating());
            old.setShortComment(ratingObj.getShortComment());
            em.merge(old);
        }
    }

    /**
     * Lấy điểm trung bình của xe theo GlobalKey
     */
    public double getAverageRating(String globalKey) {
        String jpql = "SELECT AVG(r.rating) FROM CarRating r WHERE r.globalKey = :gk AND r.status = :status";
        Double avg = em.createQuery(jpql, Double.class)
                .setParameter("gk", globalKey)
                .setParameter("status", "Hiện")
                .getSingleResult();
        return avg != null ? avg : 0.0;
    }

    /**
     * Đếm tổng số lượt đánh giá của xe
     */
    public long getTotalRatings(String globalKey) {
        String jpql = "SELECT COUNT(r) FROM CarRating r WHERE r.globalKey = :gk AND r.status = :status";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("gk", globalKey)
                .setParameter("status", "Hiện")
                .getSingleResult();
        return count != null ? count : 0;
    }

    /**
     * Lấy danh sách đánh giá của 1 xe
     */
    public List<CarRating> getRatingsByCar(String globalKey) {
        String jpql = "SELECT r FROM CarRating r WHERE r.globalKey = :gk AND r.status = :status ORDER BY r.createdAt DESC";
        TypedQuery<CarRating> query = em.createQuery(jpql, CarRating.class);
        query.setParameter("gk", globalKey);
        query.setParameter("status", "Hiện");
        return query.getResultList();
    }
}
