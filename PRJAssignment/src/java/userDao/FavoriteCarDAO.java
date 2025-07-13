/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userDao;

import java.util.List;
import model.FavoriteCars;
import model.FavoriteCarsPK; // Nếu dùng @EmbeddedId

public class FavoriteCarDAO extends util.GenericDAO<FavoriteCars, FavoriteCarsPK> {

    @Override
    protected Class<FavoriteCars> getEntityClass() {
        return FavoriteCars.class;
    }

    public List<FavoriteCars> findByUserId(int userId) {
        return em.createNamedQuery("FavoriteCars.findByUserID", FavoriteCars.class)
                .setParameter("userID", userId)
                .getResultList();
    }
}
