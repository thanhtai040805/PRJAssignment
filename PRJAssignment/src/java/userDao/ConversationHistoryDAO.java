package userDao;

import java.util.List;
import model.ConversationHistory;

public class ConversationHistoryDAO extends util.GenericDAO<ConversationHistory, Integer> {

    @Override
    protected Class<ConversationHistory> getEntityClass() {
        return ConversationHistory.class;
    }

    public List<ConversationHistory> findByUserId(int userId) {
        return em.createNamedQuery("ConversationHistory.findByUserID", ConversationHistory.class)
            .setParameter("userID", userId)
            .getResultList();
    }

    // Hàm lưu lịch sử hội thoại
    public void save(ConversationHistory conversationHistory) {
        em.getTransaction().begin();
        if (conversationHistory.getHistoryID() == null) {
            em.persist(conversationHistory);
        } else {
            em.merge(conversationHistory);
        }
        em.getTransaction().commit();
    }
}
