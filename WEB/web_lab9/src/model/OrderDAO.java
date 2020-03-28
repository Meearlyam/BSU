package model;

import entity.Order;
import exception.OrderDAOExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public final class OrderDAO extends DAO {

    /** Create new instance of Order DAO with manager factory
     *
     * @param factory - entity manager factory
     */
    public OrderDAO(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * read orders
     *
     * @return orders
     * @throws OrderDAOExecutionException if can't execute query or have problems with connection
     */
    public List<Order> readAllOrders() throws OrderDAOExecutionException {
        List<Order> orders;
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            orders = manager.createNamedQuery("readOrders", Order.class).getResultList();

            if (orders.isEmpty()) {
                transaction.rollback();
                return orders;
            }
            transaction.commit();
        } catch (Exception e) {
            throw new OrderDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
        return orders;
    }

    /**
     * read order by id
     *
     * @param id id of the order
     * @return order
     * @throws OrderDAOExecutionException if can't execute query or have problems with connection
     */
    public Order readOrderById(int id) throws OrderDAOExecutionException {
        Order order = null;
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            order = manager.createNamedQuery("readOrder", Order.class).setParameter("id", id).getSingleResult();

            if(order == null) {
                transaction.rollback();
                return new Order();
            }
            transaction.commit();
        }
        catch (Exception e) {
            throw new OrderDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
        return order;
    }

    /**
     * get orders by client id
     *
     * @return list of orders
     * @throws OrderDAOExecutionException if can't execute query or have problems with connection
     */
    public List<Order> readOrdersByClientId(int clientId) throws OrderDAOExecutionException {
        List<Order> clientOrders;
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            clientOrders = manager.createNamedQuery("readOneClientOrders", Order.class).setParameter("clientId", clientId).getResultList();

            if(clientOrders.isEmpty()) {
                transaction.rollback();
                return clientOrders;
            }

            transaction.commit();
            return clientOrders;
        }
        catch (Exception e) {
            throw new OrderDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
    }

    /**
     * insert order
     *
     * @throws OrderDAOExecutionException if can't execute query or have problems with connection
     */
    public void insertOrder(Order order) throws OrderDAOExecutionException {
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            manager.persist(order);
        }
        catch (Exception e) {
            throw new OrderDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
    }

    /**
     * delete order
     *
     * @param order order to delete from database
     * @throws OrderDAOExecutionException if can't execute query or have problems with connection
     */
    public void deleteOrder(Order order) throws OrderDAOExecutionException {
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            int deletedAmount = manager.createNamedQuery("deleteOrder", Order.class).setParameter("id", order.getID()).executeUpdate();

            if(deletedAmount < 1) {
                transaction.rollback();
                return;
            }

            transaction.commit();
        }
        catch (Exception e) {
            throw new OrderDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
    }

    /**
     * delete orders
     *
     * @throws OrderDAOExecutionException if can't execute query or have problems with connection
     */
    public void deleteOrders() throws OrderDAOExecutionException {
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            int isDeleted = manager.createNamedQuery("deleteOrders", Order.class).executeUpdate();

            if(isDeleted < 1) {
                transaction.rollback();
                return;
            }

            transaction.commit();
        }
        catch (Exception e) {
            throw new OrderDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
    }

}
