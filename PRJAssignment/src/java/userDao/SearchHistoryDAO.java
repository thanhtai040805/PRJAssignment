/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userDao;

import java.util.List;
import model.PageHistory;
import model.PageHistoryPK;
import util.GenericDAO;

/**
 *
 * @author This PC
 */
public class SearchHistoryDAO extends GenericDAO<PageHistory, PageHistoryPK> {

    @Override
    protected Class<PageHistory> getEntityClass() {
        return PageHistory.class;
    }

    public List<PageHistory> findByUserId(int userId) {
        return em.createNamedQuery("PageHistory.findByUserID", PageHistory.class)
                .setParameter("userID", userId)
                .getResultList();
    }

}
