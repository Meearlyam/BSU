package model;

/**
 * This is the class of Airplane with some functions to work with airplanes
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Airplane {
    /**
     * Model name of the Airplane
     */
    private final String name;

    /**
     * Model name getter
     */
    public String getName() {
        return name;
    }


    /**
     * left wing
     */
    private Wing lWing;

    /**
     * right wing
     */
    private Wing rWing;


    /**
     * Start point of the flight
     */
    private String start;

    /**
     * Start point getter
     */
    public String getStart() {
        return start;
    }


    /**
     * End point of the flight
     */
    private String end;

    /**
     * End point getter
     */
    public String getEnd() {
        return end;
    }

    /**
     * End point setter
     * @param e new finish point of the flight
     */
    public void setEnd(String e) {
        this.end = e;
    }


    /**
     * Default constructor of the Airplane
     */
    public Airplane() {
        this.name = "DAName";       /* DA - Default Airplane */
        this.start = "Minsk";
        this.end = "NOT DEFINED";
        this.lWing = new Wing(2, 4, "Small wing");
        this.rWing = new Wing(2, 4, "Small wing");
    }


    /**
     * Constructor of the Airplane with given model name
     * @param name model name of the Airplane
     * @param start start point of the route
     */
    public Airplane(String name, String start) {
        this.name = name;
        this.start = start;
        this.end = "NOT DEFINED";
        this.lWing = new Wing();
        this.rWing = new Wing();
    }

    /**
     * Constructor of the Airplane with given model name
     * @param name model name of the Airplane
     * @param start start point of the route
     * @param end end point if the route
     */
    public Airplane(String name, String start, String end) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.lWing = new Wing();
        this.rWing = new Wing();
    }

    /**
     * Method that sets start and end points of the route
     * @param start start point of the route
     * @param end end point of the route
     */
    public void setRoute(String start, String end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Method that gets start and end point of the route
     * @return start and end points of the route as a string
     */
    public String getRoute() {
        return "\nStart point : " + getStart() + "\nEnd point :\t" + (end.isEmpty() ? "NOT DEFINED" : getEnd());
    }

    /**
     * Method that demonstrate airplane flying
     * @param end end point of the flight
     */
    public void fly(String end) {
        /* Check both wings' efficiency status */
        this.setEnd(end);
        if(!(lWing.getEfficiency() && rWing.getEfficiency())) {
            System.out.println("The airplane cannot fly because of the"
                    + "faulty wings.");
        }
        else {
            System.out.println("Wings Efficiency Status : SERVICEABLE");
            System.out.println("CURRENT ROUTE :" + getRoute());
            System.out.println("The airplane is flying...\n");
        }
    }

    /**
     * Converts Airplane to string
     * @return the airplane as a string
     */
    @Override
    public String toString() {
        return "Airplane model :\t\t" + name + "ROUTE : \t\t" + getRoute();
    }
}
