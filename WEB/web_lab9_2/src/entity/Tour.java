package entity;

import javax.persistence.*;

/**
 * class that represent tour entity
 * @author Vera Shavela
 * @version 1.0.0
 */

@NamedQueries({
        @NamedQuery(
                name = "readTours",
                query = "SELECT t FROM Tour t"
        ),
        @NamedQuery(
                name = "readTour",
                query = "SELECT t FROM Tour t WHERE t.id = :id"
        ),
        @NamedQuery(
                name = "deleteTours",
                query = "DELETE FROM Tour"
        ),
        @NamedQuery(
                name = "deleteTour",
                query = "DELETE FROM Tour t WHERE t.id = :id"
        ),
        @NamedQuery(
                name = "readBurningToursOfSpecifiedType",
                query = "SELECT t FROM Tour t JOIN TourType tt on t.type.id = tt.id WHERE t.type.id = :typeId AND t.isBurning = TRUE"
        )
})
@Entity
@Table(name = Tour.tableName)
public class Tour implements DBObject {
    public static final String tableName = "tours";
    public static final String tourTypeIdColumnName = "type_id";
    public static final String isBurningColumnName = "is_burning";

    /**
     * id of tour
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * name of tour
     */
    private String name;

    /**
     * cost of tour
     */
    private double cost;

    /**
     * location of tour
     */
    private String location;

    /**
     * id of tour type
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Tour.tourTypeIdColumnName)
    private TourType type;

    /**
     * is tour burning
     */
    @Column(name = Tour.isBurningColumnName)
    private boolean isBurning;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Integer getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setType(TourType type) {
        this.type = type;
    }

    public TourType getType() {
        return type;
    }

    public void setIsBurning(boolean isBurning) {
        this.isBurning = isBurning;
    }

    public boolean getIsBurning() {
        return isBurning;
    }

    @Override
    public String toString() {
        return String.format("Tour:\n\tid - %d\n\ttypeId - %d\n\tname - %s\n\tcost - %.2f\n\tlocation - %s\n\tisBurning - %b", id, type.getID(), name, cost, location, isBurning);
    }
}
