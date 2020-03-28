package model;

import entity.Tour;
import entity.TourType;
import entity.TourType_;
import entity.Tour_;
import exception.TourDAOExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Tour> query = builder.createQuery(Tour.class);
            Root<Tour> root = query.from(Tour.class);

            query.select(root);
            TypedQuery<Tour> typedQuery = manager.createQuery(query);
            tours = typedQuery.getResultList();
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
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Tour> query = builder.createQuery(Tour.class);
            Root<Tour> root = query.from(Tour.class);

            Predicate idCondition = builder.equal(root.get(Tour_.id), id);
            query.select(root).where(idCondition);
            TypedQuery<Tour> typedQuery = manager.createQuery(query);
            tour = typedQuery.getSingleResult();
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
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Tour> query = builder.createQuery(Tour.class);
            Root<Tour> root = query.from(Tour.class);


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
        EntityManager manager;
        List<Tour> burningTours = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();

            CriteriaBuilder builder = manager.getCriteriaBuilder();

            CriteriaQuery<TourType> typeQuery = builder.createQuery(TourType.class);
            Root<TourType> typeRoot = typeQuery.from(TourType.class);
            Predicate idCondition = builder.equal(typeRoot.get(TourType_.id), tourTypeId);
            typeQuery.select(typeRoot).where(idCondition);
            TypedQuery<TourType> typeTypedQuery = manager.createQuery(typeQuery);
            final TourType tourType = typeTypedQuery.getSingleResult();

            if(tourType == null) {
                transaction.rollback();
                return burningTours;
            }

            CriteriaQuery<Tour> tourQuery = builder.createQuery(Tour.class);
            Root<Tour> tourRoot = tourQuery.from(Tour.class);
            Predicate typeCondition = builder.equal(tourRoot.get(Tour_.type), tourType);
            Predicate burningCondition = builder.equal(tourRoot.get(Tour_.isBurning), true);

            tourQuery.select(tourRoot).where(builder.and(typeCondition, burningCondition));
            TypedQuery<Tour> tourTypeTypedQuery = manager.createQuery(tourQuery);
            burningTours = tourTypeTypedQuery.getResultList();
            transaction.commit();
        } catch (Exception e) {
            throw new TourDAOExecutionException(e.getMessage());
        }
        return burningTours;
    }
}
