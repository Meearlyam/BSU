package entity;

import javax.persistence.*;

/**
 * class that represent order entity
 * @author Vera Shavela
 * @version 1.0.0
 */

@NamedQueries({
        @NamedQuery(
                name = "deleteOrders",
                query = "DELETE FROM Order"
        ),
        @NamedQuery(
                name = "deleteOrder",
                query = "DELETE FROM Order o WHERE o.id = :id"
        ),
        @NamedQuery(
                name = "readOrders",
                query = "SELECT o FROM Order o"
        ),
        @NamedQuery(
                name = "readOrder",
                query = "SELECT o FROM Order o WHERE o.id = :id"
        ),
        @NamedQuery(
                name = "readOneClientOrders",
                query = "SELECT o FROM Order o WHERE o.client.id = :clientId"
        )
})
@Entity
@Table(name = Order.tableName)
public class Order implements DBObject {

    public static final String tableName = "orders";
    public static final String tourIdColumnName = "tour_id";
    public static final String clientIdColumnName = "client_id";

    /**
     * id of order
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * tour
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Order.tourIdColumnName)
    private Tour tour;

    /**
     * client
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Order.clientIdColumnName)
    private Client client;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getID() {
        return id;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Tour getTour() {
        return tour;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public Order() {
        super();
    }

    public Order(int id, Tour tour, Client client) {
        this.setId(id);
        this.setTour(tour);
        this.setClient(client);
    }

    @Override
    public String toString() {
        return String.format("Order:\n\tid - %d\n\ttourId - %d\n\tclientId - %d", id, tour.getID(), client.getID());
    }
}
