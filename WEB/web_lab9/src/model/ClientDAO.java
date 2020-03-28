package model;

import entity.Client;
import exception.ClientDAOExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
     * read clients
     *
     * @return clients
     * @throws ClientDAOExecutionException if can't execute query or have problems with connection
     */
    public List<Client> readAllClients() throws ClientDAOExecutionException {
        EntityManager manager = null;
        List<Client> clients;

        try {
            manager = this.entityManagerFactory.createEntityManager();
            clients = manager.createNamedQuery("readClients", Client.class).getResultList();
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
            client = manager.createNamedQuery("readClient", Client.class).setParameter("id", clientId).getSingleResult();
        } catch (Exception e) {
            throw new ClientDAOExecutionException(e.getMessage());
        }
        finally {
            this.closeManager(manager);
        }
        return client;
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

        try {
            manager = this.entityManagerFactory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            int updated = manager.createNamedQuery("updateClients", Client.class).setParameter("personalDiscount", discount).setParameter("paidOrdersAmount", paidOrders).executeUpdate();

            if(updated < 1) {
                transaction.rollback();
                return;
            }
            transaction.commit();
        } catch (Exception e) {
            throw new ClientDAOExecutionException(e.getMessage());
        } finally {
            this.closeManager(manager);
        }
    }
}
