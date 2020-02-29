package model.entity;

/**
 * class that represent order entity
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Order {

    /**
     * id of order
     */
    private int id;

    /**
     * id of tour
     */
    private int tourId;

    /**
     * id of client
     */
    private int clientId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    /**
     * constructor to create order
     * @param id id of order
     */
    public Order(int id, int tourId, int clientId) {
        setId(id);
        setTourId(tourId);
        setClientId(clientId);
    }

    @Override
    public String toString() {
        return String.format("Order:\n\tid - %d\n\ttourId - %d\n\tclientId - %d", id, tourId, clientId);
    }
}
