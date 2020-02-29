package model.entity;

import java.math.BigDecimal;

/**
 * class that represent tour entity
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Tour {

    /**
     * id of tour
     */
    private int id;

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
    private int typeId;

    /**
     * is tour burning
     */
    private boolean isBurning;

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

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setIsBurning(boolean isBurning) {
        this.isBurning = isBurning;
    }

    public boolean getIsBurning() {
        return isBurning;
    }

    /**
     * constructor to create tour
     * @param id id of tour
     * @param name name of tour
     * @param cost cost of tour
     * @param location location of tour
     * @param isBurning isBurning of tour
     */
    public Tour(int id, int typeId, String name, double cost, String location, boolean isBurning) {
        setId(id);
        setTypeId(typeId);
        setName(name);
        setCost(cost);
        setLocation(location);
        setIsBurning(isBurning);
    }

    @Override
    public String toString() {
        return String.format("Tour:\n\tid - %d\n\ttypeId - %d\n\tname - %s\n\tcost - %.2f\n\tlocation - %s\n\tisBurning - %b", id, typeId, name, cost, location, typeId, isBurning);
    }
}
