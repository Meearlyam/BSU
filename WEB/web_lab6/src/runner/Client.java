package runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import model.company.RemoteCompany;
import model.staff.Staff;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;

/**
 * Client class which work with rmi methods of company
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Client {

    /**
     * log4j logger
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Client main method
     *
     * @param args command line params
     */
    public static void main(String[] args) {
        Registry reg;
        RemoteCompany stub;

        try {
            reg = LocateRegistry.getRegistry(9001);
            stub = (RemoteCompany)reg.lookup("Company");
        } catch(RemoteException | NotBoundException e) {
            logger.warn(e);
            return;
        }

        Random rnd = new Random();
        int opNum = rnd.nextInt(6);
        try {
            logger.info("I came to the company");
            stub.comeIn();
            List<Staff> staffs = stub.getStaff();
            printStaffList("COMPANY FULL STAFF LIST", staffs);
            List<Staff>  detStaff;
            switch(opNum) {
                case 0:
                    detStaff = stub.selectSpecificStaff(5, "Workman department");
                    printStaffList("Staff of WORKMAN DEPARTMENT with experience less than 3 years", detStaff);
                    break;
                case 1:
                    detStaff = stub.selectStaffByDepartment("Finance department");
                    printStaffList("Staff of FINANCE DEPARTMENT", detStaff);
                    break;
                case 2:
                    detStaff = stub.selectStaffByExperience(5.0, 10.0);
                    printStaffList("Staff with experience between 5 and 10 years", detStaff);
                    break;
                case 3:
                    detStaff = stub.sortStaffByExperience(true);
                    printStaffList("Staff list SORTED BY EXPERIENCE IN REVERSED ORDER", detStaff);
                    break;
                case 4:
                    detStaff = stub.sortStaffByExperience(false);
                    printStaffList("Staff list SORTED BY EXPERIENCE IN NOT REVERSED ORDER", detStaff);
                    break;
                case 5:
                    int staffAmount = stub.countTotalStaffAmount();
                    logger.info("Total amount of staff in company : ", staffAmount);
                    return;
                default:
                    detStaff = staffs;
            }

            for(Staff staff : detStaff) {
                logger.info("Try to talk to\n" + staff);
                if(stub.consultWithStaff(staff)) {
                    logger.info("Successful consultation");
                    logger.info(staff.performWork());
                    break;
                }
                logger.info("Not lucky enough to meet this employee, try another from list");
            }

        } catch (RemoteException e) {
            logger.warn(e);
        } finally {
            try {
                stub.goOut();
            } catch(RemoteException e) {
                logger.warn(e);
            }
            logger.info("I go out from the company");
        }
    }

    /**
     * method to display info about all staff in company
     * @param header that determines info for user
     * @param staffList list of described staff
     */
    public static void printStaffList(String header, List<Staff> staffList) {
        logger.info("\n =========== " + header + " =========== ");
        if(staffList.size() > 0) {
            for(Staff staff : staffList) {
                logger.info("\n" + staff);
            }
        }
        else {
            logger.info("No staff");
        }
    }
}
