package controller;

import model.Airplane;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic class demonstrates functions of Airplane and Wing classes
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Controller {

    /**
     * Method to demonstrate work with Airplane and Wing classes
     * @return result string of the work with Airplane and Wing classes
     */
    public static String startWork() {
        List<Airplane> airplanes = new ArrayList<>();

        airplanes.add(new Airplane());
        airplanes.add(new Airplane("Oceanic-815", "Sydney"));
        airplanes.add(new Airplane("Aerovip-112", "New York", "Los Angeles"));

        String outputString = "\t\tBEFORE SETTING\n";

        for (Airplane airplane :
                airplanes) {
            outputString += "\nAirplane : " + airplane.getName();
            if(airplane.getEnd() == "NOT DEFINED") {
                outputString += "\nStart : " + airplane.getStart() + "\nEnd : NOT DEFINED\n";
            }
            else {
                outputString += airplane.getRoute();
            }
        }

        outputString += "\n\n\t\tAFTER SETTING\n";
        for (Airplane airplane :
                airplanes) {
            outputString += "\nAirplane : " + airplane.getName();
            if(airplane.getEnd() == "NOT DEFINED") {
                airplane.setEnd("Monaco");
            }
            else {
                airplane.setRoute("Minsk", "Moscow");
            }
            outputString += airplane.getRoute() + "\n";
            airplane.fly("Moscow");
        }


        return outputString;

    }

}
