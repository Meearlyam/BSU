package model;

import entity.TourType;
import exception.TourTypeDAOExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * TourTypeDAO class
 *
 * @author Vers Shavela
 * @version 1.0.0
 */
public final class TourTypeDAO extends DAO {

    /** Create new instance of Tour Type DAO with manager factory
     *
     * @param factory - entity manager factory
     */
    public TourTypeDAO(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * read tour types
     *
     * @return tour types
     * @throws TourTypeDAOExecutionException if Can't execute query or problems with connection
     */
    public List<TourType> readAllTourTypes() throws TourTypeDAOExecutionException {
        List<TourType> tourTypes;
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            tourTypes = manager.createNamedQuery("readTypes", TourType.class).getResultList();
        } catch (Exception e) {
            throw new TourTypeDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
        return tourTypes;
    }

    /**
     * read tour type by id
     *
     * @return tour type
     * @throws TourTypeDAOExecutionException if Can't execute query or problems with connection
     */
    public TourType readTourTypeById(int id) throws TourTypeDAOExecutionException {
        TourType tourType = null;
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            tourType = manager.createNamedQuery("readType", TourType.class).setParameter("typeId", id).getSingleResult();
        } catch (Exception e) {
            throw new TourTypeDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
        return tourType;
    }

    /**
     * insert tour type
     *
     * @throws TourTypeDAOExecutionException if Can't execute query or problems with connection
     */
    public void insertTourType(TourType tourType) throws TourTypeDAOExecutionException {
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            manager.persist(tourType);
        } catch (Exception e) {
            throw new TourTypeDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
    }

    /**
     * delete tour type
     *
     * @param tourType tour type to delete
     * @throws TourTypeDAOExecutionException if Can't execute query or problems with connection
     */
    public void deleteTourType(TourType tourType) throws TourTypeDAOExecutionException {
        EntityManager manager = null;
        try {
            manager = this.entityManagerFactory.createEntityManager();
            manager.createNamedQuery("deleteType", TourType.class).setParameter("typeId", tourType.getID()).getResultList();
        } catch (Exception e) {
            throw new TourTypeDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
    }

    /**
     * delete tour type
     *
     * @throws TourTypeDAOExecutionException if Can't execute query or problems with connection
     */
    public void deleteTourTypes() throws TourTypeDAOExecutionException {
        EntityManager manager = null;
        try {
            manager = this.entityManagerFactory.createEntityManager();
            manager.createNamedQuery("deleteTypes", TourType.class).getResultList();
        } catch (Exception e) {
            throw new TourTypeDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
    }
}
