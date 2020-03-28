package controller;

import entity.Client;
import entity.Order;
import entity.Tour;
import entity.TourType;
import exception.*;
import model.ClientDAO;
import model.OrderDAO;
import model.TourDAO;
import model.TourTypeDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * controller
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class TourAgencyController {
    private Logger logger = LogManager.getLogger("controller_layer");

    private EntityManagerFactory managerFactory;

    public TourAgencyController() {
        managerFactory = Persistence.createEntityManagerFactory("LOCAL_TEST");
    }

    /**
     * get list of all agency tours
     *
     * @return list of tours
     * @throws TourAgencyControllerException
     */
    public List<Tour> getAllToursOfAgency() throws TourAgencyControllerException {
        List<Tour> tours;
        try {
            TourDAO rd = new TourDAO(managerFactory);
            tours = rd.readAllTours();
            logger.info("got all tours of agency");
        } catch(TourDAOExecutionException e) {
            throw new TourAgencyControllerException("Failed to get tours of agency", e);
        }
        return tours;
    }

    /**
     * get client with specified id
     *
     * @param clientId id of client
     * @return client with specified id
     * @throws TourAgencyControllerException if DAO get some exception
     */
    public List<Order> getOrdersByClientId(int clientId) throws TourAgencyControllerException {
        List<Order> clientOrders;
        try {
            OrderDAO od = new OrderDAO(managerFactory);
            clientOrders = od.readOrdersByClientId(clientId);
            logger.info("got orders by client id");
        } catch(OrderDAOExecutionException e) {
            throw new TourAgencyControllerException("Failed to get records by client id", e);
        }
        return clientOrders;
    }

    /**
     * get burning tours of specified id
     *
     * @param tourTypeId tour type id
     * @return list of burning tours
     * @throws TourAgencyControllerException if DAO get some exception
     */
    public List<Tour> getBurningToursByTourTypeId(int tourTypeId) throws TourAgencyControllerException {
        List<Tour> burningTours;
        try {
            TourDAO rd = new TourDAO(managerFactory);
            burningTours = rd.readBurningToursByTourTypeId(tourTypeId);
            logger.info("got burning tours of specified type");
        } catch(TourDAOExecutionException e) {
            throw new TourAgencyControllerException("Failed to get burning tours of agency", e);
        }
        return burningTours;
    }


    public void makeAnOrder(int tourId, int clientId) throws TourAgencyControllerException {
        try {
            OrderDAO od = new OrderDAO(managerFactory);
            TourDAO td = new TourDAO(managerFactory);
            ClientDAO cd = new ClientDAO(managerFactory);
            Order order = new Order(-1, td.readTourById(tourId), cd.readClientById(clientId));
            od.insertOrder(order);
            logger.info("made new order");
        } catch(TourDAOExecutionException e) {
            throw new TourAgencyControllerException("Failed to take tour by it's id", e);
        } catch(ClientDAOExecutionException e) {
            throw new TourAgencyControllerException("Failed to take client by it's id", e);
        } catch(OrderDAOExecutionException e) {
            throw new TourAgencyControllerException("Failed to make new order", e);
        }
    }

    public void setDiscountToClients(int discount, int N) throws TourAgencyControllerException {
        try {
            ClientDAO cd = new ClientDAO(managerFactory);
            cd.updateClientsDiscount(discount, N);
            logger.info("set discount to clients");
        } catch(ClientDAOExecutionException e) {
            throw new TourAgencyControllerException("Failed to set discount to clients", e);
        }
    }

    public List<Client> getAllClientsOfAgency() throws TourAgencyControllerException {
        List<Client> clients;
        try {
            ClientDAO rd = new ClientDAO(managerFactory);
            clients = rd.readAllClients();
            logger.info("got all clients of agency");
        } catch(ClientDAOExecutionException e) {
            throw new TourAgencyControllerException("Failed to get clients of agency", e);
        }
        return clients;
    }

    public List<TourType> getAllTourTypes() throws TourAgencyControllerException {
        List<TourType> tourTypes;
        try {
            TourTypeDAO rd = new TourTypeDAO(managerFactory);
            tourTypes = rd.readAllTourTypes();
            logger.info("got all tour types of agency");
        } catch(TourTypeDAOExecutionException e) {
            throw new TourAgencyControllerException("Failed to get tour types of agency", e);
        }
        return tourTypes;
    }
}
