package controller;

import model.dao.ClientDAO;
import model.dao.OrderDAO;
import model.dao.TourDAO;
import model.dao.TourTypeDAO;
import model.entity.Client;
import model.entity.Order;
import model.entity.Tour;
import model.entity.TourType;
import model.exception.DAOException;
import model.exception.TourAgencyControllerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * controller
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class TourAgencyController {
    private Logger logger = LogManager.getLogger("controller_layer");

    public List<Map.Entry<Tour, TourType>> getAllToursOfAgency() throws TourAgencyControllerException {
        List<Map.Entry<Tour, TourType>> tours;
        try {
            TourDAO rd = new TourDAO();
            tours = rd.readAllTours();
            logger.info("got all tours of agency");
        } catch(DAOException e) {
            throw new TourAgencyControllerException("Failed to get tours of agency", e);
        }
        return tours;
    }

    public List<Map.Entry<Client, Tour>> getOrdersByClientId(int clientId) throws TourAgencyControllerException {
        List<Map.Entry<Client, Tour>> clientOrders;
        try {
            OrderDAO od = new OrderDAO();
            clientOrders = od.readOrdersByClientId(clientId);
            logger.info("got orders by client id");
        } catch(DAOException e) {
            throw new TourAgencyControllerException("Failed to get records by client id", e);
        }
        return clientOrders;
    }

    public List<Map.Entry<Tour, TourType>> getBurningToursByTourTypeId(int tourTypeId) throws TourAgencyControllerException {
        List<Map.Entry<Tour, TourType>> burningTours;
        try {
            TourDAO rd = new TourDAO();
            burningTours = rd.readBurningToursByTourTypeId(tourTypeId);
            logger.info("got burning tours of specified type");
        } catch(DAOException e) {
            throw new TourAgencyControllerException("Failed to get burning tours of agency", e);
        }
        return burningTours;
    }

    public void makeAnOrder(int tourId, int clientId) throws TourAgencyControllerException {
        try {
            OrderDAO od = new OrderDAO();
            Order order = new Order(-1, tourId, clientId);
            od.insertOrder(order);
            logger.info("made new order");
        } catch(DAOException e) {
            throw new TourAgencyControllerException("Failed to make new order", e);
        }
    }

    public void setDiscountToClients(int discount, int N) throws TourAgencyControllerException {
        try {
            ClientDAO cd = new ClientDAO();
            cd.updateClientsDiscount(discount, N);
            logger.info("set discount to clients");
        } catch(DAOException e) {
            throw new TourAgencyControllerException("Failed to set discount to clients", e);
        }
    }

    public List<Client> getAllClientsOfAgency() throws TourAgencyControllerException {
        List<Client> clients;
        try {
            ClientDAO rd = new ClientDAO();
            clients = rd.readAllClients();
            logger.info("got all clients of agency");
        } catch(DAOException e) {
            throw new TourAgencyControllerException("Failed to get clients of agency", e);
        }
        return clients;
    }

    public List<TourType> getAllTourTypes() throws TourAgencyControllerException {
        List<TourType> tourTypes;
        try {
            TourTypeDAO rd = new TourTypeDAO();
            tourTypes = rd.readAllTourTypes();
            logger.info("got all tour types of agency");
        } catch(DAOException e) {
            throw new TourAgencyControllerException("Failed to get tour types of agency", e);
        }
        return tourTypes;
    }
}
