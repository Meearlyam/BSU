package view;

import controller.TourAgencyController;
import entity.Client;
import entity.Order;
import entity.Tour;
import exception.TourAgencyControllerException;
import util.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * View
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class View {
    private static Logger logger = LogManager.getLogger();

    private static final String HEADER_1 = "Print info about all tours in Tour Agency";
    private static final String HEADER_2 = "Print info about all orders of the specified client";
    private static final String HEADER_3 = "Print info about burning tours of the specified type";
    private static final String HEADER_4 = "Make an order of tour";
    private static final String HEADER_5 = "Set a discount to client with more than N orders";
    private static final int N = 2;

    public static void main(String[] args) {

        TourAgencyController controller = new TourAgencyController();
        try {
            List<Tour> tours = controller.getAllToursOfAgency();
            Printer.printList(tours, HEADER_1);

            List<Order> clientOrders = controller.getOrdersByClientId(2);
            Printer.printList(clientOrders, HEADER_2);

            List<Tour> burningTours = controller.getBurningToursByTourTypeId(2);
            Printer.printList(burningTours, HEADER_3);

            Printer.printHeader(HEADER_4);
            controller.makeAnOrder(2, 3);

            controller.setDiscountToClients(25, N);
            List<Client> clients = controller.getAllClientsOfAgency();
            Printer.printList(clients, HEADER_5);
        } catch (TourAgencyControllerException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }
}
