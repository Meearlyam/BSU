package model;

/**
 * This is the class of Wing with some functions to work with wings
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Wing {
    /**
     * length of the Wing
     */
    private final double length;

    /**
     * length getter
     * @return length of the wing
     */
    public double getLength() {
        return length;
    }

    /**
     * width of the Wing
     */
    private final double width;

    /**
     * width getter
     */
    public double getWidth() {
        return width;
    }

    /**
     * No damage (efficiency) status of the Wing
     */
    private boolean efficiency;

    /**
     * efficiency getter
     * @return no damage status of the wing
     */
    public boolean getEfficiency() {
        return efficiency;
    }

    /**
     * efficiency setter
     * @param efficiency no damage status of the Wing
     */
    public void setEfficiency(boolean efficiency) {
        this.efficiency = efficiency;
    }

    /**
     * Model name of the Wing
     */
    private String wingName;


    /**
     * Constructor of the wing with given length and width
     * @param w width of the wing
     * @param l length of the wing
     * @param n model name of the wing
     */
    public Wing(double w, double l, String n) {
        this.width = w;
        this.length = l;
        this.efficiency = true;
        this.wingName = n;
    }

    /**
     * Default constructor to create a wing
     */
    public Wing(){
        this.width = 3;
        this.length = 15;
        this.efficiency = true;
        this.wingName = "Standard";
    }

    /**
     * Converts Wing to string
     * @return the wing as a string
     */
    @Override
    public String toString() {
        return "Model :\t\t" + wingName + "\nWing width :\t" + width + "\nWing length :\t"
                + length + "\nStatus :\t\t" + (efficiency ? "Serviceable" : "Not serviceable");
    }
}
