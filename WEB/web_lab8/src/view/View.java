package view;

import controller.TourAgencyController;
import model.entity.Client;
import model.entity.Tour;
import model.entity.TourType;
import model.exception.TourAgencyControllerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Printer;

import java.util.List;
import java.util.Map;

public class View {
    private static Logger logger = LogManager.getLogger();

    private static final String HEADER_1 = "Print info about all tours in Tour Agency";
    private static final String HEADER_2 = "Print info about all orders of the specified client";
    private static final String HEADER_3 = "Print info about burning tours of the specified type";
    private static final String HEADER_4 = "Make an order of tour";
    private static final String HEADER_5 = "Set a discount to client with more than N orders";
    private static final int N = 3;

    public static void main(String[] args) {
        TourAgencyController controller = new TourAgencyController();
        try {
            List<Map.Entry<Tour, TourType>> tours = controller.getAllToursOfAgency();
            Printer.printHeader(HEADER_1);
            Printer.printListOfTuples(tours);

            List<Map.Entry<Client, Tour>> clientOrders = controller.getOrdersByClientId(2);
            Printer.printHeader(HEADER_2);
            Printer.printListOfTuples(clientOrders);

            List<Map.Entry<Tour, TourType>> burningTours = controller.getBurningToursByTourTypeId(2);
            Printer.printHeader(HEADER_3);
            Printer.printListOfTuples(burningTours);

            Printer.printHeader(HEADER_4);
            controller.makeAnOrder(2, 3);

            Printer.printHeader(HEADER_5);
            controller.setDiscountToClients(20, N);
            List<Client> clients = controller.getAllClientsOfAgency();
            Printer.printList(clients);
        } catch (TourAgencyControllerException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }
}
