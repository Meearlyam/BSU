package entity;


import javax.persistence.*;

/**
 * class that represent tour type entity
 * @author Vera Shavela
 * @version 1.0.0
 */

@NamedQueries({
        @NamedQuery(
                name = "readTypes",
                query = "SELECT tt FROM TourType tt"
        ),
        @NamedQuery(
                name = "readType",
                query = "SELECT tt FROM TourType tt WHERE tt.id = :typeId"
        ),
        @NamedQuery(
                name = "deleteTypes",
                query = "DELETE FROM TourType"
        ),
        @NamedQuery(
                name = "deleteType",
                query = "DELETE FROM TourType tt WHERE tt.id = :typeId"
        )
})
@Entity
@Table(name = TourType.tableName)
public class TourType implements DBObject {

    public static final String tableName = "tour_types";

    /**
     * id of tour type
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * name of tour type
     */
    private String name;

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

    @Override
    public String toString() {
        return String.format("TourType:\n\tid - %d\n\tname - %s", id, name);
    }
}
