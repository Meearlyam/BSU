package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * dao abstract class
 * @author Vera Shavela
 * @version 1.0.0
 */
public abstract class DAO {

    /**
     * Factory to get entity manager
     */
    protected EntityManagerFactory entityManagerFactory;

    /**
     * constructor
     *
     * @param factory - entity manager factory
     */
    protected DAO(EntityManagerFactory factory) {
        this.entityManagerFactory = factory;
    }

    /**
     * Close given opened entity manager
     *
     * @param manager - opened manager
     */
    protected void closeManager(EntityManager manager) {
        if(manager != null && !manager.isOpen()) {
            manager.close();
        }
    }

}
