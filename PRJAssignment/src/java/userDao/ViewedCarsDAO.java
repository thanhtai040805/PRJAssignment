/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userDao;
import java.util.List;
import model.ViewedCars;
import model.ViewedCarsPK;
import util.GenericDAO;

/**
 * * * @author This PC
 */
public class ViewedCarsDAO extends GenericDAO<ViewedCars, ViewedCarsPK> {

    @Override
    protected Class<ViewedCars> getEntityClass() {
        return ViewedCars.class;
    }
    
    public List<ViewedCars> findByUserId(int userId) {
        return em.createNamedQuery("ViewedCars.findByUserID", ViewedCars.class)
                .setParameter("userID", userId)
                .getResultList();
    }
}
