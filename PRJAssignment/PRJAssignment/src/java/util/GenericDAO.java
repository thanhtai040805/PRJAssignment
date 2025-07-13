package util;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericDAO<T, ID> {

    private static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());

    protected EntityManager em;

    protected abstract Class<T> getEntityClass();

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public boolean add(T entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding entity", e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    public boolean remove(ID id) {
        try {
            T entity = em.find(getEntityClass(), id);
            if (entity != null) {
                em.getTransaction().begin();
                em.remove(entity);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error removing entity with id: " + id, e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    public boolean update(T entity) {
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating entity", e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    // Lấy theo id
    public T getById(ID id) {
        try {
            return em.find(getEntityClass(), id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting entity by id: " + id, e);
            return null;
        }
    }

    // Lấy tất cả
    public List<T> getAll() {
        try {
            String jpql = "SELECT e FROM " + getEntityClass().getSimpleName() + " e";
            return em.createQuery(jpql, getEntityClass()).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting all entities", e);
            return java.util.Collections.emptyList();
        }
    }
}
