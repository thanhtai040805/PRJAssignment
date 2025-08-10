package carDao;

import model.ReviewCar;
import util.GenericDAO;
import java.util.List;

public class ReviewCarDAO extends GenericDAO<ReviewCar, Long> {

    @Override
    protected Class<ReviewCar> getEntityClass() {
        return ReviewCar.class;
    }

    public List<ReviewCar> getReviewsByCar(String globalKey) {
        String jpql = "SELECT r FROM ReviewCar r "
                + "WHERE r.globalKey = :gk AND r.status = :status ORDER BY r.createdAt DESC";
        return em.createQuery(jpql, ReviewCar.class)
                .setParameter("gk", globalKey)
                .setParameter("status", "Hiện")
                .getResultList();
    }

    public int deleteReviewsByCar(String globalKey) {
        String jpql = "DELETE FROM ReviewCar r WHERE r.globalKey = :gk";
        return em.createQuery(jpql)
                .setParameter("gk", globalKey)
                .executeUpdate();
    }
}
