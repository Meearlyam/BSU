package model;

import entity.Tour;
import exception.TourDAOExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public final class TourDAO extends DAO {

    /**
     * constructor
     *
     * @param factory - entity manager factory
     */
    public TourDAO(EntityManagerFactory factory) {
        super(factory);
    }


    /**
     * read all tours
     *
     * @return list of tours
     * @throws TourDAOExecutionException if can't execute query or have problems with connection
     */
    public List<Tour> readAllTours() throws TourDAOExecutionException {
        List<Tour> tours;
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            tours = manager.createNamedQuery("readTours", Tour.class).getResultList();
        } catch (Exception e) {
            throw new TourDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
        return tours;
    }

    /**
     * read tour by id
     *
     * @return tour
     * @throws TourDAOExecutionException if can't execute query or have problems with connection
     */
    public Tour readTourById(int id) throws TourDAOExecutionException {
        Tour tour;
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            tour = manager.createNamedQuery("readTour", Tour.class).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new TourDAOExecutionException(e.getMessage());
        }
        return tour;
    }

    /**
     * insert tour
     *
     * @throws TourDAOExecutionException if can't execute query or have problems with connection
     */
    public void insertTour(Tour tour) throws TourDAOExecutionException {
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            manager.persist(tour);
        } catch (Exception e) {
            throw new TourDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
    }

    /**
     * delete tour
     *
     * @throws TourDAOExecutionException if can't execute query or have problems with connection
     */
    public void deleteTour(Tour tour) throws TourDAOExecutionException {
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            manager.createNamedQuery("deleteTour", Tour.class).setParameter("id", tour.getID()).executeUpdate();
        } catch (Exception e) {
            throw new TourDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
    }

    /**
     * read burning tours by tour type id
     *
     * @throws TourDAOExecutionException if can't execute query or have problems with connection
     */
    public List<Tour> readBurningToursByTourTypeId(int tourTypeId) throws TourDAOExecutionException {
        EntityManager manager = null;
        List<Tour> burningTours;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            burningTours = manager.createNamedQuery("readBurningToursOfSpecifiedType", Tour.class).setParameter("typeId", tourTypeId).getResultList();
        } catch (Exception e) {
            throw new TourDAOExecutionException(e.getMessage());
        }
        return burningTours;
    }
}
