package runner;

import controller.Controller;
import exceptions.ParserException;
import exceptions.XMLException;
import model.ParsingModeEnum;
import model.company.Company;
import model.company.CompanyDirector;
import model.staff.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;

/**
 * Class to demonstrate parsing
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Runner {
    private static final String XSD_FILE = "schema.xsd";

    private static final String XML_FILE = "company.xml";

    private static final String PARSING_MODE = "-d";

    /**
     * Logger for creating logfile via log4j2
     */
    private static final Logger logger = LogManager.getLogger();

    public static void main(String... args) {
        logger.info("Controller started");

        Controller controller = new Controller(new Company(new CompanyDirector()));
        logger.info("Company was created");

        boolean result = false;
        try {
            result = controller.validate(XML_FILE, XSD_FILE);
        } catch (XMLException e) {
            logger.warn(e);
        }
        if (result) {
            logger.info("Valid xml");
        } else {
            logger.info("Failed to validate xml");
            return;
        }

        ParsingModeEnum mode = null;
        switch (PARSING_MODE) {
            case "-d":
                mode = ParsingModeEnum.DOM;
                break;
            case "-s":
                mode = ParsingModeEnum.SAX;
                break;
            case "-st":
                mode = ParsingModeEnum.StAX;
                break;
        }

        List<Staff> staffs;
        logger.info("Try to parse xml");
        try {
            staffs = controller.parseMedicinesList(XML_FILE, mode);
        } catch (ParserException e) {
            logger.warn(e);
            return;
        }
        logger.info("Add parsed staff into company");
        for (Staff medicine : Objects.requireNonNull(staffs)) {
            controller.addStaff(medicine);
        }

        printStaffList("FULL LIST OF STAFF IN COMPANY", controller.getCompanyStaff());
        logger.info("Finish work");
    }


    private static void printStaffList(String title, List<Staff> list) {
        logger.info("\n" + " ========================= " + title + " ========================= ");
        if (!list.isEmpty()) {
            for (Staff staff : list) {
                logger.info("\n" + staff);
            }
        } else {
            logger.info("No items");
        }
    }
}
