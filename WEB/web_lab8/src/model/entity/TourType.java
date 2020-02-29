package model.entity;


/**
 * class that represent tour type entity
 * @author Vera Shavela
 * @version 1.0.0
 */
public class TourType {

    /**
     * id of tour type
     */
    private int id;

    /**
     * name of tour type
     */
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * constructor to create tour type
     * @param id id of tour type
     * @param name name of tour type
     */
    public TourType(int id, String name) {
        setId(id);
        setName(name);
    }

    @Override
    public String toString() {
        return String.format("TourType:\n\tid - %d\n\tname - %s", id, name);
    }
}
