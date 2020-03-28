package model;

import entity.Client;
import entity.Client_;
import entity.Order;
import entity.Order_;
import exception.OrderDAOExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            Order order = manager.createNamedQuery("readOrder", Order.class).setParameter("id", id).getSingleResult();

            if(order == null) {
                transaction.rollback();
                return new Order();
            }
            transaction.commit();
            return order;
        }
        catch (Exception e) {
            throw new OrderDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
    }

    /**
     * get orders by client id
     *
     * @return list of orders
     * @throws OrderDAOExecutionException if can't execute query or have problems with connection
     */
    public List<Order> readOrdersByClientId(int clientId) throws OrderDAOExecutionException {
        List<Order> clientOrders = null;
        EntityManager manager = null;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();

            CriteriaBuilder builder = manager.getCriteriaBuilder();

            CriteriaQuery<Client> queryClient = builder.createQuery(Client.class);
            Root<Client> clientRoot = queryClient.from(Client.class);
            Predicate idCondition = builder.equal(clientRoot.get(Client_.id), clientId);
            queryClient.select(clientRoot).where(idCondition);
            TypedQuery<Client> clientTypedQuery = manager.createQuery(queryClient);
            final Client client = clientTypedQuery.getSingleResult();

            if(client == null) {
                transaction.rollback();
                return clientOrders;
            }

            CriteriaQuery<Order> queryOrder = builder.createQuery(Order.class);
            Root<Order> orderRoot = queryOrder.from(Order.class);
            Predicate clientCondition = builder.equal(orderRoot.get(Order_.client), client);
            queryOrder.select(orderRoot).where(clientCondition);
            TypedQuery<Order> orderTypedQuery = manager.createQuery(queryOrder);
            clientOrders = orderTypedQuery.getResultList();

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
