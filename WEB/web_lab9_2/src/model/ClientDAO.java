package model;

import entity.Client;
import entity.Client_;
import exception.ClientDAOExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * ClientDAO class
 *
 * @author Vers Shavela
 * @version 1.0.0
 */
public final class ClientDAO extends DAO {

    /** Create new instance of Client DAO with manager factory
     *
     * @param factory - entity manager factory
     */
    public ClientDAO(EntityManagerFactory factory) {
        super(factory);
    }

    /**
     * returns list of all tour agency clients
     *
     * @return clients
     * @throws ClientDAOExecutionException if can't execute query or have problems with connection
     */
    public List<Client> readAllClients() throws ClientDAOExecutionException {
        EntityManager manager = null;
        List<Client> clients;

        try {
            manager = this.entityManagerFactory.createEntityManager();

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Client> query = builder.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);
            query.select(root);
            TypedQuery<Client> typedQuery = manager.createQuery(query);
            clients = typedQuery.getResultList();
        } catch (Exception e) {
            throw new ClientDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
        return clients;
    }

    /**
     * read client by id
     *
     * @param clientId id of client
     * @return clients
     * @throws ClientDAOExecutionException if can't execute query or have problems with connection
     */
    public Client readClientById(int clientId) throws ClientDAOExecutionException {
        EntityManager manager = null;
        Client client;

        try {
            manager = this.entityManagerFactory.createEntityManager();

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Client> query = builder.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);
            Predicate idCondition = builder.equal(root.get(Client_.id), clientId);
            query.select(root).where(idCondition);
            TypedQuery<Client> typedQuery = manager.createQuery(query);
            client = typedQuery.getSingleResult();
            return client;
        } catch (Exception e) {
            throw new ClientDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
    }

    /**
     * update client personal discount
     *
     * @param discount personal discount to set
     * @param paidOrders amount of paid orders
     * @throws ClientDAOExecutionException if can't execute query or have problems with connection
     */
    public void updateClientsDiscount(int discount, int paidOrders) throws ClientDAOExecutionException {
        EntityManager manager = null;
        List<Client> clients;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Client> query = builder.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);
            Predicate paidOrdersCondition = builder.gt(root.get(Client_.paidOrdersAmount), paidOrders);
            query.select(root).where(paidOrdersCondition);
            TypedQuery<Client> typedQuery = manager.createQuery(query);
            clients = typedQuery.getResultList();

            if(clients.isEmpty()) {
                transaction.rollback();
                return;
            }

            for (Client client: clients) {
                client.setPersonalDiscount(discount);
            }

            transaction.commit();
        } catch (Exception e) {
            throw new ClientDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
    }
}
